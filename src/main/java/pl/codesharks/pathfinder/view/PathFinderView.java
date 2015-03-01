package pl.codesharks.pathfinder.view;

import pl.codesharks.pathfinder.algorithm.ShortestPathAlgorithm;
import pl.codesharks.pathfinder.map.Grid;
import pl.codesharks.pathfinder.node.Basic2DNode;
import pl.codesharks.pathfinder.util.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Simple view (Canvas) used to visualise path finding algorithms.<br>
 * Uses dependency injection to give you flexibility, what algorithm {@link PathFinderView}<br>
 * and what type of map to use
 */
@SuppressWarnings("FieldCanBeLocal")
class PathFinderView extends Canvas implements Runnable, MouseListener {
    private final JFrame jFrame;
    private int height = 520;
    private int width = 520;
    private float nodeHeight = 50;
    private float nodeWidth = 50;
    private volatile Grid<Basic2DNode> map;
    private Basic2DNode start;
    private Basic2DNode goal;
    private boolean lockMouseClicks;
    private ShortestPathAlgorithm<Basic2DNode> shortestPathAlgorithm;
    private ArrayList<ShortestPathAlgorithm<Basic2DNode>> algorithms;
    private Thread displayThread;
    private Thread algorithmThread;
    private ArrayList<JMenuItem> algorihmButtons = new ArrayList<>();

    public PathFinderView(JFrame frame, int width, int height, Grid<Basic2DNode> map, ArrayList<ShortestPathAlgorithm<Basic2DNode>> algorithms) {
        this.jFrame = frame;
        this.map = map;
        this.shortestPathAlgorithm = algorithms.get(0);
        this.algorithms = algorithms;

        this.width = width;
        this.height = height;
        this.nodeHeight = (float) height / map.getHeight();
        this.nodeWidth = (float) width / map.getWidth();
        init();
    }

    private void reset() {
        map = new Grid<>(map.getWidth(), map.getHeight(), this.map.getFactoryObject());
    }

    private void renderNode(Graphics2D g, Basic2DNode node) {
        g.setColor(Color.BLACK);
        g.fillRect((int) (node.getX() * nodeWidth), (int) (node.getY() * nodeHeight), (int) nodeWidth, (int) nodeHeight);

        if (node.isStart()) g.setColor(Color.GREEN);
        else if (node.isGoal()) g.setColor(Color.ORANGE);
        else if (node.isObstacle()) g.setColor(Color.RED);
        else if (node.isOnPath()) g.setColor(Color.CYAN);
        else if (node.isVisited()) g.setColor(Color.BLUE);
        else g.setColor(Color.DARK_GRAY);

        g.fillRect((int) (node.getX() * nodeWidth + 1), (int) (node.getY() * nodeHeight + 1), (int) (nodeWidth - 1), (int) (nodeHeight - 1));
    }

    void calculatePath() {
        if (goal == null || start == null) {
            return;
        }
        switchCalculateButtonsState(false);
        lockMouseClicks = true;
        final StopWatch sw = new StopWatch();
        sw.start();

        algorithmThread = new Thread(new Runnable() {
            @Override
            public void run() {
                shortestPathAlgorithm.findPath(map, 100);
                sw.stop();
                showDialog("Calculation time:" + sw.getElapsedTimeMilliseconds() / 1000f + "s", "Computation ended", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        algorithmThread.start();

    }

    void showDialog(String message, String caption, int messageType) {
        JOptionPane.showMessageDialog(jFrame, message, caption, messageType);
    }

    /**
     * Changes buttons states. If the calculation is made, the buttons are disabled.
     * They are enabled once user clicks "new Grid"
     */
    private void switchCalculateButtonsState(final boolean value) {
        for (JMenuItem item : algorihmButtons) {
            item.setEnabled(value);
        }
    }

    @Override
    public void run() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
        }
        double lastMS = 0;
        StopWatch sw = new StopWatch();
        //noinspection InfiniteLoopStatement
        while (true) {
            Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
            sw.start();

            render(g2d);
            g2d.setColor(Color.ORANGE);
            g2d.drawString(String.valueOf(lastMS), 10, 20);

            bs.show();

            sw.stop();
            lastMS = sw.getElapsedTimeNano() / 1000000.0d;
            try {
                Thread.sleep(125);
            } catch (Exception ignored) {
            }
        }
    }

    public void init() {
        requestFocus();
        lockMouseClicks = false;
        this.addMouseListener(this);
        setUpMenu(jFrame);
    }

    private void setUpMenu(JFrame frame) {
        JMenuBar bar = new JMenuBar();
        bar.setBounds(0, 0, frame.getWidth(), 25);
        JMenu file = new JMenu("File");
        bar.add(file);


        JMenu options = new JMenu("Options");
        bar.add(options);

        JMenuItem newGrid = new JMenuItem("New Grid");
        newGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                lockMouseClicks = false;
                switchCalculateButtonsState(true);
            }
        });
        options.add(newGrid);


        for (final ShortestPathAlgorithm<Basic2DNode> spa : algorithms) {
            String name = spa.getName();
            String description = spa.getDescription();
            JMenuItem calcPath = new JMenuItem("use: " + name);

            calcPath.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shortestPathAlgorithm = spa;
                    calculatePath();
                }
            });
            calcPath.setToolTipText(description);
            algorihmButtons.add(calcPath);
            options.add(calcPath);
        }


        JMenu exit = new JMenu("exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        bar.add(options);
        frame.setJMenuBar(bar);
    }

    void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.width, this.height);

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                renderNode(g, map.getNode(x, y));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (lockMouseClicks) return;
        int c = e.getButton();
        Basic2DNode n = getNodeAt(e.getX(), e.getY());
        if (n != null) {
            if (n.isGoal() || n.isStart() || n.isObstacle()) {
                n.clear();
            }
            if (c == MouseEvent.BUTTON1) {
                n.setObstacle(true);
            } else if (c == MouseEvent.BUTTON2) {
                n.setStart(true);
                if (start != null) {
                    start.clear();
                }
                start = n;
                map.setStartLocation(n.getX(), n.getY());
                System.out.println("changed start to " + n.getX() + " " + n.getY());
            } else if (c == MouseEvent.BUTTON3) {
                n.setGoal(true);
                if (goal != null) {
                    goal.clear();
                }
                goal = n;
                map.setGoalLocation(n.getX(), n.getY());
                System.out.println("changed goal to " + n.getX() + " " + n.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    Basic2DNode getNodeAt(int x, int y) {
        System.out.println("x" + x + " y" + y);
        x /= nodeWidth;
        y /= nodeHeight;
        if (x >= 0 && y >= 0 && x < map.getWidth() && y < map.getHeight()) {
            return map.getNode(x, y);
        }
        return null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void start() {
        displayThread = new Thread(this);
        displayThread.start();
    }
}
