package pl.codesharks.pathfinder.map;

import pl.codesharks.pathfinder.node.Node;
import pl.codesharks.pathfinder.util.Logger;

import java.util.ArrayList;

/**
 * 2D grid of {@link pl.codesharks.pathfinder.node.BasicNode}
 */
public class Grid<E extends Node<E>> implements Map<E> {

    /**
     * Stores nodes as <br>
     * <code>
     * y0 x1 x2 x3<br>
     * y1 x1 x2 x3
     * </code>
     */
    protected ArrayList<ArrayList<E>> map;
    protected int startLocationX = 0;
    protected int startLocationY = 0;
    protected int goalLocationX = 0;
    protected int goalLocationY = 0;
    private int mapWidth;
    private int mapHeight;

    private Logger log = new Logger();
    private E factory;

    public Grid(int width, int height, E factory) {
        this.mapWidth = width;
        this.mapHeight = height;

        this.factory = factory;

        init();
        log.addLine("\tMap Created");
        log.addLine("\tMap Node edges registered");
    }

    private void init() {
        E node;
        map = new ArrayList<>();
        for (int y = 0; y < mapHeight; y++) {
            map.add(new ArrayList<E>());
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
    public E getNode(int x, int y) {
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
    public E getStartNode() {
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
    public E getGoalLocation() {
        return map.get(goalLocationY).get(goalLocationX);
    }

    @Override
    public float getDistanceBetween(E node1, E node2) {
        float diffY = Math.abs(node1.getY() - node2.getY());
        float diffX = Math.abs(node1.getX() - node2.getX());
        return (float) Math.sqrt(((diffX * diffX) + (diffY * diffY)));
    }

    @Override
    public int getMapWidth() {
        return mapWidth;
    }

    @Override
    public int getMapHeight() {
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

}
