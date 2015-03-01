package pl.codesharks.pathfinder.map;

import pl.codesharks.pathfinder.node.Basic2DNode;
import pl.codesharks.pathfinder.util.Logger;

import java.util.ArrayList;

/**
 * 2D grid of {@link pl.codesharks.pathfinder.node.BasicNode}
 */
public class Grid<E extends Basic2DNode> implements Map<Basic2DNode> {

    private final int mapWidth;
    private final int mapHeight;
    private final E factory;
    /**
     * Stores nodes as <br>
     * <code>
     * y0 x1 x2 x3<br>
     * y1 x1 x2 x3
     * </code>
     */
    protected ArrayList<ArrayList<Basic2DNode>> map;
    private int startLocationX = 0;
    private int startLocationY = 0;
    private int goalLocationX = 0;
    private int goalLocationY = 0;

    public Grid(int width, int height, E factory) {
        this.mapWidth = width;
        this.mapHeight = height;

        this.factory = factory;

        init();
        Logger log = new Logger();
        log.addLine("\tMap Created");
        log.addLine("\tMap Node edges registered");
    }

    private void init() {
        Basic2DNode node;
        map = new ArrayList<>();
        for (int y = 0; y < mapHeight; y++) {
            map.add(new ArrayList<Basic2DNode>());
            for (int x = 0; x < mapWidth; x++) {
                node = factory.valueOf(x, y);
                map.get(y).add(node);
            }
        }


        //Registers connections between node edges
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                node = map.get(y).get(x);
                if (!(y == 0))
                    node.setNorth(map.get(y - 1).get(x));
                if (!(y == 0) && !(y == mapHeight - 1) && !(x == mapWidth - 1))
                    node.setNorthEast(map.get(y + 1).get(x + 1));
                if (!(x == mapWidth - 1))
                    node.setEast(map.get(y).get(x + 1));
                if (!(x == mapWidth - 1) && !(y == mapHeight - 1))
                    node.setSouthEast(map.get(y + 1).get(x + 1));
                if (!(y == mapHeight - 1))
                    node.setSouth(map.get(y + 1).get(x));
                if (!(x == 0) && !(y == mapHeight - 1))
                    node.setSouthWest(map.get(y + 1).get(x - 1));
                if (!(x == 0))
                    node.setWest(map.get(y).get(x - 1));
                if (!(x == 0) && !(y == 0))
                    node.setNorthWest(map.get(y - 1).get(x - 1));
            }
        }
    }

    @Override
    public void setObstacle(int x, int y, boolean isObstacle) {
        map.get(y).get(x).setObstacle(isObstacle);
    }

    @Override
    public Basic2DNode getNode(int x, int y) {
        return map.get(y).get(x);
    }

    @Override
    public E getFactoryObject() {
        return this.factory;
    }

    @Override
    public void setStartLocation(int x, int y) {
        map.get(startLocationY).get(startLocationX).setStart(false);
        map.get(y).get(x).setStart(true);
        startLocationX = x;
        startLocationY = y;
    }

    @Override
    public void setGoalLocation(int x, int y) {
        map.get(goalLocationY).get(goalLocationX).setGoal(false);
        map.get(y).get(x).setGoal(true);
        goalLocationX = x;
        goalLocationY = y;
    }

    @Override
    public int getStartLocationX() {
        return startLocationX;
    }

    @Override
    public int getStartLocationY() {
        return startLocationY;
    }

    @Override
    public Basic2DNode getStartNode() {
        return map.get(startLocationY).get(startLocationX);
    }

    @Override
    public int getGoalLocationX() {
        return goalLocationX;
    }

    @Override
    public int getGoalLocationY() {
        return goalLocationY;
    }

    @Override
    public Basic2DNode getGoalNode() {
        return map.get(goalLocationY).get(goalLocationX);
    }

    @Override
    public float getDistanceBetween(Basic2DNode node1, Basic2DNode node2) {
        float diffX = node1.getX() - node2.getX();
        float diffY = node1.getY() - node2.getY();
        return (float) Math.sqrt(diffX * diffX + diffY * diffY);
    }

    @Override
    public int getWidth() {
        return mapWidth;
    }

    @Override
    public int getHeight() {
        return mapHeight;
    }

    @Override
    public void clear() {
        startLocationX = 0;
        startLocationY = 0;
        goalLocationX = 0;
        goalLocationY = 0;
        init();
    }

    @Override
    public ArrayList<ArrayList<Basic2DNode>> getAllNodes() {
        return map;
    }

}
