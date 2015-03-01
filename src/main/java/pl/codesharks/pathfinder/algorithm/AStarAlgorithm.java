package pl.codesharks.pathfinder.algorithm;

import pl.codesharks.pathfinder.heuristic.AStarHeuristic;
import pl.codesharks.pathfinder.heuristic.ManhattanHeuristic;
import pl.codesharks.pathfinder.map.Map;
import pl.codesharks.pathfinder.map.Path;
import pl.codesharks.pathfinder.node.Node;
import pl.codesharks.pathfinder.util.Logger;
import pl.codesharks.pathfinder.util.SortedList;

import java.util.ArrayList;

/**
 * A star (A*) path finding algorithm. Always uses best option available at the moment,depending on the distance from
 * START + distance to GOAL computed by heuristic algorithm
 */
public class AStarAlgorithm<T extends Node<T>> implements ShortestPathAlgorithm<T> {

    public static final String NAME = "A Star";
    public static final String DESCRIPTION = "Faster than dijkstra - uses heuristic to guess in which directon to go";
    private Logger log = null;
    private AStarHeuristic heuristic;
    /**
     * closedList The list of Nodes not searched yet, sorted by their distance to the goal as guessed by our heuristic.
     */
    private ArrayList<T> closedList;
    private SortedList<T> openList;
    private Path<T> shortestPath;
    private Map<T> map;

    /**
     * @param heuristic algorithm used to calculate distance to the goal
     */
    public AStarAlgorithm(AStarHeuristic heuristic) {
        this.heuristic = heuristic;
        init();
    }

    public AStarAlgorithm() {
        this.heuristic = new ManhattanHeuristic();
        init();
    }

    private void init() {
        if (log == null) {
            log = new Logger();
        }
        closedList = new ArrayList<>();
        openList = new SortedList<>();
    }

    public AStarHeuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(AStarHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * @param map {@link pl.codesharks.pathfinder.map.Map} instance with all the data
     * @return path of nodes to traverse
     */
    @Override
    public Path<T> findPath(Map<T> map) {
        return findPath(map, 0);
    }

    /**
     * @param map     {@link pl.codesharks.pathfinder.map.Map} instance with all the data
     * @param delayMs 0 if you want performance run, another number will sleep thread at every neighbor being checked
     * @return path of nodes to traverse
     */
    @Override
    public Path<T> findPath(Map<T> map, int delayMs) {
        if (delayMs < 0) {
            throw new IllegalArgumentException("delay cannot be less than 0! delay:" + delayMs);
        }
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

        // set up first node, and clear lists
        map.getStartNode().setCost(0);
        T s = map.getStartNode();
        s.setHeuristicDistanceToGoal(heuristic.getEstimatedDistanceToGoal(map, s.getX(), s.getY(), map.getGoalLocationX(), map.getGoalLocationY()));
        closedList.clear();
        openList.clear();
        openList.add(map.getStartNode());

        while (openList.size() != 0) {
            //sorted by lowest distance from our goal as guessed by our heuristic
            T current = openList.getFirst();

            if (current == map.getGoalNode()) {
                log.addLine("Calculating done!");
                return reconstructPath(current);
            }

            openList.remove(current);
            closedList.add(current);

            // Reveals bugs from creating connections between nodes.
            // It is impossible to have neighbor, that has no neighbors
            if (current.getNeighborList().size() == 0) {
                log.addLine("Node x:" + current.getX() + " y:" + current.getY() + " Has no neighbors");
                throw new IllegalArgumentException("Node x:" + current.getX() + " y:" + current.getY() + " Has no neighbors");
            }

            for (T neighbor : current.getNeighborList()) {
                // don't check the same node twice
                if (closedList.contains(neighbor)) {
                    continue;
                }

                neighbor.setVisited(true);

                // algorithm cant walk through walls
                if (!neighbor.isObstacle()) {
                    float neighborTotalCost = (current.getCost() + map.getDistanceBetween(current, neighbor));
                    boolean inQueue = openList.contains(neighbor);

                    if (!inQueue || neighborTotalCost < neighbor.getCost()) {
                        neighbor.setParent(current);
                        neighbor.setCost(neighborTotalCost);

                        neighbor.setHeuristicDistanceToGoal(
                                heuristic.getEstimatedDistanceToGoal(map,
                                        neighbor.getX(), neighbor.getY(),
                                        map.getGoalLocationX(), map.getGoalLocationY()));

                        if (!inQueue) {
                            openList.add(neighbor);

                            // slow down a little bit to show actual progress, if we are using graphics preview
                            if (delayMs > 0) {
                                try {
                                    Thread.sleep(delayMs);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        // executes when there was no Goal on the road (no path)
        return null;
    }

    @Override
    public void printPath() {
        T node;
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

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }


    private Path<T> reconstructPath(T node) {
        Path<T> path = new Path<>();

        while (node.getParent() != null) {
            path.prependWayPoint(node);
            node.setOnPath(true);
            node = node.getParent();
        }

        this.shortestPath = path;
        return path;
    }
}
