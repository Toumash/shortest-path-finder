package pl.codesharks.pathfinder.map;

import pl.codesharks.pathfinder.node.Node;

import java.util.ArrayList;

public interface Map<E extends Node<E>> {

    void setObstacle(int x, int y, boolean isObstacle);

    E getNode(int x, int y);

    E getFactoryObject();

    void setStartLocation(int x, int y);

    void setGoalLocation(int x, int y);

    int getStartLocationX();

    int getStartLocationY();

    E getStartNode();

    int getGoalLocationX();

    int getGoalLocationY();

    E getGoalNode();

    float getDistanceBetween(E node1, E node2);

    int getWidth();

    int getHeight();

    void clear();

    ArrayList<ArrayList<E>> getAllNodes();
}
