package pl.codesharks.pathfinder.node;

import java.util.ArrayList;

@SuppressWarnings("UnusedDeclaration")
public abstract class Node<T extends Node> implements Comparable<T> {

    public static final float UNVISITED = Integer.MAX_VALUE-5;
    protected ArrayList<T> neighborList;

    protected boolean visited;
    protected float distance;
    protected float heuristicDistanceToGoal;
    protected T parent;
    protected int x;
    protected int y;
    protected boolean isObstacle;
    protected boolean isStart;
    protected boolean isGoal;
    protected boolean isOnPath;
    protected float cost;

    public Node() {
        neighborList = new ArrayList<>();
        this.x = 0;
        this.y = 0;
        this.visited = false;
        this.distance = Integer.MAX_VALUE;
        this.isObstacle = false;
        this.isStart = false;
        this.isGoal = false;
    }

    protected Node(int x, int y) {
        neighborList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.visited = false;
        this.distance = Integer.MAX_VALUE;
        this.isObstacle = false;
        this.isStart = false;
        this.isGoal = false;
    }

    protected Node(int x, int y, boolean visited, int distance, boolean isObstacle, boolean isStart, boolean isGoal) {
        neighborList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.visited = visited;
        this.distance = distance;
        this.isObstacle = isObstacle;
        this.isStart = isStart;
        this.isGoal = isGoal;
    }


    public float getHeuristicDistanceToGoal() {
        return heuristicDistanceToGoal;
    }

    public void setHeuristicDistanceToGoal(float heuristicDistanceToGoal) {
        this.heuristicDistanceToGoal = heuristicDistanceToGoal;
    }

    /**
     * Total cost (from start) to get to this node
     */
    public float getCost() {
        return this.cost;
    }

    /**
     * Total cost (from start) to get to this node
     */
    public void setCost(float newCost) {
        this.cost = newCost;
    }

    public boolean isOnPath() {
        return this.isOnPath;
    }

    public void setOnPath(boolean isOnPath) {
        this.isOnPath = isOnPath;
    }

    public void clear() {
        isStart = false;
        isGoal = false;
        isObstacle = false;
    }

    public ArrayList<T> getNeighborList() {
        return this.neighborList;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float value) {
        this.distance = value;
    }

    public T getParent() {
        return this.parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isObstacle() {
        return this.isObstacle;
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    public boolean isStart() {
        return this.isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public boolean isGoal() {
        return this.isGoal;
    }

    public void setGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }

    public abstract T valueOf(int x, int y);

    public abstract T valueOf(int x, int y, boolean visited, int distanceFromStart, boolean isObstacle, boolean isStart, boolean isGoal);

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(T otherNode) {
            float thisTotalDistanceFromGoal = heuristicDistanceToGoal + distance;
            float otherTotalDistanceFromGoal = otherNode.getHeuristicDistanceToGoal() + otherNode.getDistance();
            if (thisTotalDistanceFromGoal < otherTotalDistanceFromGoal) {
                return -1;
            } else if (thisTotalDistanceFromGoal > otherTotalDistanceFromGoal) {
                return 1;
            } else {
                return 0;
            }
        }
}
