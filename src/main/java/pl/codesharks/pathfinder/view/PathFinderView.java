package pl.codesharks.pathfinder.view;

import pl.codesharks.pathfinder.algorithm.ShortestPathAlgorithm;
import pl.codesharks.pathfinder.map.Grid;
import pl.codesharks.pathfinder.map.Map;
import pl.codesharks.pathfinder.node.Node;
import pl.codesharks.pathfinder.util.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

/**
 * Simple view (Canvas) used to visualise path finding algorithms.<br>
 * Uses dependency injection to give you flexibility, what algorithm {@link PathFinderView}<br>
 * and what type of map to use
 */
@SuppressWarnings("FieldCanBeLocal")
class PathFinderView<T extends Node<T>> extends Canvas implements Runnable, MouseListener {
    private int height = 520;
    private int width = 520;
    private float nodeHeight = 50;
    private float nodeWidth = 50;
    private Map<T> map;
    private T start;
    private T goal;
    private boolean lockMouseClicks;
    private JFrame jFrame;
    private ShortestPathAlgorithm<T> shortestPathAlgorithm;
    private Thread thread;


    public PathFinderView(JFrame frame, int width, int height, Map<T> map, ShortestPathAlgorithm<T> algorithm) {
        this.jFrame = frame;
        this.map = map;
        this.shortestPathAlgorithm = algorithm;

        this.width = width;
        this.height = height;
        this.nodeHeight = (float) height / map.getMapHeight();
        this.nodeWidth = (float) width / map.getMapWidth();
        init();
    }

    private void reset() {
        map = new Grid<>(map.getMapWidth(), map.getMapHeight(), this.map.getFactoryObject());
    }

    private void renderNode(Graphics2D g, T node) {
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

    protected void calculatePath() {
        StopWatch sw = new StopWatch();
        sw.start();
        shortestPathAlgorithm.calculateShortestPath(map, 125);
        sw.stop();
        showDialog("Calculation time:" + sw.getElapsedTimeMilliseconds() / 1000f + "s", "Computation ended", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showDialog(String message, String caption, int messageType) {
        JOptionPane.showMessageDialog(jFrame, message, caption, messageType);
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
            }
        });
        options.add(newGrid);

        JMenuItem calcPath = new JMenuItem("Calculate path");
        calcPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (goal == null || start == null) {
                    return;
                }
                lockMouseClicks = true;
                calculatePath();
            }
        });
        options.add(calcPath);

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

    protected void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.width, this.height);

        for (int y = 0; y < map.getMapHeight(); y++) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                renderNode(g, map.getNode(x, y));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (lockMouseClicks) return;
        int c = e.getButton();
        T n = getNodeAt(e.getX(), e.getY());
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

    public T getNodeAt(int x, int y) {
        System.out.println("x" + x + " y" + y);
        x /= nodeWidth;
        y /= nodeHeight;
        if (x >= 0 && y >= 0 && x < map.getMapWidth() && y < map.getMapHeight()) {
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
        thread = new Thread(this);
        thread.start();
    }
}
