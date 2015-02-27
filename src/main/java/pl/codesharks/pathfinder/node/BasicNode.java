package pl.codesharks.pathfinder.node;

import java.util.ArrayList;

/**
 * Usual, 2D node
 */
@SuppressWarnings("UnusedDeclaration")
public class BasicNode extends Node<BasicNode> implements Comparable<BasicNode> {
    private ArrayList<BasicNode> neighborList;

    private BasicNode north;
    private BasicNode northEast;
    private BasicNode east;
    private BasicNode southEast;
    private BasicNode south;
    private BasicNode southWest;
    private BasicNode west;
    private BasicNode northWest;
    private boolean visited;
    private float distanceFromStart;
    private float heuristicDistanceFromGoal;
    private BasicNode parent;
    private int x;
    private int y;
    private boolean isObstacle;
    private boolean isStart;
    private boolean isGoal;
    private boolean isOnPath;

    /**
     * Use this only for passing as a factory!
     */
    public BasicNode() {
    }

    BasicNode(int x, int y) {
        neighborList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.visited = false;
        this.distanceFromStart = Integer.MAX_VALUE;
        this.isObstacle = false;
        this.isStart = false;
        this.isGoal = false;
    }

    BasicNode(int x, int y, boolean visited, int distanceFromStart, boolean isObstacle, boolean isStart, boolean isGoal) {
        neighborList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.visited = visited;
        this.distanceFromStart = distanceFromStart;
        this.isObstacle = isObstacle;
        this.isStart = isStart;
        this.isGoal = isGoal;
    }

    public boolean isOnPath() {
        return isOnPath;
    }

    public void setOnPath(boolean isOnPath) {
        this.isOnPath = isOnPath;
    }

    public void clear() {
        isStart = false;
        isGoal = false;
        isObstacle = false;
    }

    public BasicNode getNorth() {
        return north;
    }

    public void setNorth(BasicNode north) {
        if (north != null) {

            if (neighborList.contains(this.north)) {
                neighborList.remove(this.north);
            }
            neighborList.add(north);
        }

        this.north = north;
    }

    public BasicNode getNorthEast() {
        return northEast;
    }

    public void setNorthEast(BasicNode northEast) {
        if (neighborList.contains(this.northEast))
            neighborList.remove(this.northEast);
        neighborList.add(northEast);

        this.northEast = northEast;
    }

    public BasicNode getEast() {
        return east;
    }

    public void setEast(BasicNode east) {
        if (neighborList.contains(this.east))
            neighborList.remove(this.east);
        neighborList.add(east);

        this.east = east;
    }

    public BasicNode getSouthEast() {
        return southEast;
    }

    public void setSouthEast(BasicNode southEast) {
        if (neighborList.contains(this.southEast))
            neighborList.remove(this.southEast);
        neighborList.add(southEast);

        this.southEast = southEast;
    }

    public BasicNode getSouth() {
        return south;
    }

    public void setSouth(BasicNode south) {
        if (neighborList.contains(this.south))
            neighborList.remove(this.south);
        neighborList.add(south);

        this.south = south;
    }

    public BasicNode getSouthWest() {
        return southWest;
    }

    public void setSouthWest(BasicNode southWest) {
        if (neighborList.contains(this.southWest))
            neighborList.remove(this.southWest);
        neighborList.add(southWest);

        this.southWest = southWest;
    }

    public BasicNode getWest() {
        return west;
    }

    public void setWest(BasicNode west) {
        if (neighborList.contains(this.west))
            neighborList.remove(this.west);
        neighborList.add(west);

        this.west = west;
    }

    public BasicNode getNorthWest() {
        return northWest;
    }

    public void setNorthWest(BasicNode northWest) {
        if (neighborList.contains(this.northWest))
            neighborList.remove(this.northWest);
        neighborList.add(northWest);

        this.northWest = northWest;
    }

    public ArrayList<BasicNode> getNeighborList() {
        return neighborList;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public float getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(float f) {
        this.distanceFromStart = f;
    }

    public BasicNode getParent() {
        return parent;
    }

    public void setParent(BasicNode previousNode) {
        this.parent = previousNode;
    }

    public float getHeuristicDistanceFromGoal() {
        return heuristicDistanceFromGoal;
    }

    public void setHeuristicDistanceFromGoal(float heuristicDistanceFromGoal) {
        this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public void setGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }

    @Override
    public BasicNode valueOf(int x, int y) {
        return new BasicNode(x, y);
    }

    @Override
    public BasicNode valueOf(int x, int y, boolean visited, int distanceFromStart, boolean isObstacle, boolean isStart, boolean isGoal) {
        return new BasicNode(x, y, visited, distanceFromStart, isObstacle, isStart, isGoal);
    }

    public boolean equals(BasicNode node) {
        return (node.x == x) && (node.y == y);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(BasicNode otherNode) {
        float thisTotalDistanceFromGoal = heuristicDistanceFromGoal + distanceFromStart;
        float otherTotalDistanceFromGoal = otherNode.getHeuristicDistanceFromGoal() + otherNode.getDistanceFromStart();

        if (thisTotalDistanceFromGoal < otherTotalDistanceFromGoal) {
            return -1;
        } else if (thisTotalDistanceFromGoal > otherTotalDistanceFromGoal) {
            return 1;
        } else {
            return 0;
        }
    }

}
