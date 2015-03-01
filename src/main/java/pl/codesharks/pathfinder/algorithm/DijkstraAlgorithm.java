package pl.codesharks.pathfinder.algorithm;

import pl.codesharks.pathfinder.map.Map;
import pl.codesharks.pathfinder.map.Path;
import pl.codesharks.pathfinder.node.Basic2DNode;
import pl.codesharks.pathfinder.util.Logger;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;


public class DijkstraAlgorithm implements ShortestPathAlgorithm<Basic2DNode> {

    public static final String NAME = "Dijkstra";
    public static final String DESCRIPTION = "Always uses fastest way";

    private Logger log = null;
    private Map<Basic2DNode> map;
    private Path<Basic2DNode> shortestPath;

    public DijkstraAlgorithm() {
        if (log == null) {
            log = new Logger();
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Path<Basic2DNode> findPath(Map<Basic2DNode> map) {
        return findPath(map, 0);
    }

    @Override
    public Path<Basic2DNode> findPath(Map<Basic2DNode> map, final int delayMs) {
        this.map = map;
        int goalX = map.getGoalLocationX();
        int goalY = map.getGoalLocationY();

        int startX = map.getStartLocationX();
        int startY = map.getStartLocationY();

        map.setStartLocation(startX, startY);
        map.setGoalLocation(goalX, goalY);

        //Check if the goal node is blocked (if it is, it is impossible to find a path there)
        if (map.getNode(goalX, goalY).isObstacle()) {
            return null;
        }

        Basic2DNode v = map.getStartNode();
        v.setVisited(true);
        v.setCost(0);

        Queue<Basic2DNode> Q = new PriorityQueue<>(8, new DijkstraComparator());
        // add start
        Q.add(v);
        while (!Q.isEmpty()) {
            v = Q.poll();
            v.setVisited(true);
            for (Basic2DNode w : v.getNeighborList()) {
                if (!w.isObstacle()) {
                    if (!w.isVisited()) {
                        w.setParent(v);
                        w.setCost(v.getCost() + w.getDistance());
                        if (map.getGoalNode() == w) {
                            return reconstructPath(map.getStartNode(), v);
                        }
                        Q.add(w);
                    } else if (v.getCost() > w.getCost() + w.getDistance()) {
                        v.setCost(w.getCost() + w.getDistance());
                        v.setParent(w);
                    }
                }
            }
            if (delayMs > 0) {
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void printPath() {
        Basic2DNode node;
        for (int x = 0; x < map.getWidth(); x++) {

            if (x == 0) {
                for (int i = 0; i <= map.getWidth(); i++)
                    log.add("-");
                log.addLine("");
            }
            log.add("|");

            for (int y = 0; y < map.getHeight(); y++) {
                node = map.getNode(x, y);
                if (node.isObstacle()) {
                    log.add("X");
                } else if (node.isStart()) {
                    log.add("s");
                } else if (node.isGoal()) {
                    log.add("g");
                } else if (shortestPath.contains(node.getX(), node.getY())) {
                    log.add("¤");
                } else if (node.isVisited()) {
                    log.add("#");
                } else {
                    log.add(" ");
                }
                if (y == map.getHeight())
                    log.add("_");
            }

            log.add("|");
            log.addLine();
        }
        for (int i = 0; i <= map.getWidth(); i++)
            log.add("-");
        log.addLine();
    }

    private Path<Basic2DNode> reconstructPath(Basic2DNode startNode, Basic2DNode endNode) {
        Basic2DNode v = endNode;

        Path<Basic2DNode> path = new Path<>();
        path.prependWayPoint(v);

        while (v.getParent() != null) {
            v.setOnPath(true);
            path.prependWayPoint(v);
            v = v.getParent();
        }
        return path;
    }

    private static class DijkstraComparator implements Comparator<Basic2DNode> {
        @Override
        public int compare(Basic2DNode o1, Basic2DNode o2) {
            if (o1.getCost() > o2.getCost()) {
                return 1;
            } else if (o1.getCost() < o2.getCost()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
