package pl.codesharks.pathfinder.map;

import pl.codesharks.pathfinder.node.Node;

public interface Map<E extends Node<E>> {

    public void setObstacle(int x, int y, boolean isObstacle);

    public E getNode(int x, int y);

    public E getFactoryObject();

    public void setStartLocation(int x, int y);

    public void setGoalLocation(int x, int y);

    public int getStartLocationX();

    public int getStartLocationY();

    public E getStartNode();

    public int getGoalLocationX();

    public int getGoalLocationY();

    public E getGoalLocation();

    public float getDistanceBetween(E node1, E node2);

    public int getMapWidth();

    public int getMapHeight();

    public void clear();
}
