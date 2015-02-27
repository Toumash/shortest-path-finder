package pl.codesharks.pathfinder.node;

import java.util.ArrayList;

@SuppressWarnings("UnusedDeclaration")
public abstract class Node<T extends Node<T>> implements Comparable<T> {

    public abstract boolean isOnPath();

    public abstract void setOnPath(boolean isOnPath);

    public abstract void clear();

    public abstract T getNorth();

    public abstract void setNorth(T north);

    public abstract T getNorthEast();

    public abstract void setNorthEast(T northEast);

    public abstract T getEast();

    public abstract void setEast(T east);

    public abstract T getSouthEast();

    public abstract void setSouthEast(T southEast);

    public abstract T getSouth();

    public abstract void setSouth(T south);

    public abstract T getSouthWest();

    public abstract void setSouthWest(T southWest);

    public abstract T getWest();

    public abstract void setWest(T west);

    public abstract T getNorthWest();

    public abstract void setNorthWest(T northWest);

    public abstract ArrayList<T> getNeighborList();

    public abstract boolean isVisited();

    public abstract void setVisited(boolean visited);

    public abstract float getDistanceFromStart();

    public abstract void setDistanceFromStart(float f);

    public abstract T getParent();

    public abstract void setParent(T previousNode);

    public abstract float getHeuristicDistanceFromGoal();

    public abstract void setHeuristicDistanceFromGoal(float heuristicDistanceFromGoal);

    public abstract int getX();

    public abstract void setX(int x);

    public abstract int getY();

    public abstract void setY(int y);

    public abstract boolean isObstacle();

    public abstract void setObstacle(boolean isObstacle);

    public abstract boolean isStart();

    public abstract void setStart(boolean isStart);

    public abstract boolean isGoal();

    public abstract void setGoal(boolean isGoal);

    public abstract T valueOf(int x, int y);

    public abstract T valueOf(int x, int y, boolean visited, int distanceFromStart, boolean isObstacle, boolean isStart, boolean isGoal);

}
