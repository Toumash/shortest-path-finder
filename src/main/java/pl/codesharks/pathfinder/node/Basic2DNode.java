package pl.codesharks.pathfinder.node;

import java.util.ArrayList;

/**
 * Usual, 8 - directions node
 */
@SuppressWarnings("UnusedDeclaration")
public class Basic2DNode extends Node<Basic2DNode> {

    private Basic2DNode north;
    private Basic2DNode northEast;
    private Basic2DNode east;
    private Basic2DNode southEast;
    private Basic2DNode south;
    private Basic2DNode southWest;
    private Basic2DNode west;
    private Basic2DNode northWest;
    private float heuristicDistanceFromGoal;


    /**
     * Use this only for passing as a factory!
     */
    public Basic2DNode() {
        distance = 1;
    }

    protected Basic2DNode(int x, int y) {
        neighborList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.visited = false;
        this.distance = 1;
        this.isObstacle = false;
        this.isStart = false;
        this.isGoal = false;

        this.cost = Node.UNVISITED;
    }

    protected Basic2DNode(int x, int y, boolean visited, int distance, boolean isObstacle, boolean isStart, boolean isGoal) {
        neighborList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.visited = visited;
        this.distance = distance;
        this.isObstacle = isObstacle;
        this.isStart = isStart;
        this.isGoal = isGoal;

        this.cost = Node.UNVISITED;
    }

    public Basic2DNode getNorth() {
        return north;
    }

    public void setNorth(Basic2DNode north) {
        if (north != null) {

            if (neighborList.contains(this.north)) {
                neighborList.remove(this.north);
            }
            neighborList.add(north);
        }

        this.north = north;
    }

    public Basic2DNode getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Basic2DNode northEast) {
        if (neighborList.contains(this.northEast))
            neighborList.remove(this.northEast);
        neighborList.add(northEast);

        this.northEast = northEast;
    }

    public Basic2DNode getEast() {
        return east;
    }

    public void setEast(Basic2DNode east) {
        if (neighborList.contains(this.east))
            neighborList.remove(this.east);
        neighborList.add(east);

        this.east = east;
    }

    public Basic2DNode getSouthEast() {
        return southEast;
    }

    public void setSouthEast(Basic2DNode southEast) {
        if (neighborList.contains(this.southEast))
            neighborList.remove(this.southEast);
        neighborList.add(southEast);

        this.southEast = southEast;
    }

    public Basic2DNode getSouth() {
        return south;
    }

    public void setSouth(Basic2DNode south) {
        if (neighborList.contains(this.south))
            neighborList.remove(this.south);
        neighborList.add(south);

        this.south = south;
    }

    public Basic2DNode getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Basic2DNode southWest) {
        if (neighborList.contains(this.southWest))
            neighborList.remove(this.southWest);
        neighborList.add(southWest);

        this.southWest = southWest;
    }

    public Basic2DNode getWest() {
        return west;
    }

    public void setWest(Basic2DNode west) {
        if (neighborList.contains(this.west))
            neighborList.remove(this.west);
        neighborList.add(west);

        this.west = west;
    }

    public Basic2DNode getNorthWest() {
        return northWest;
    }

    public void setNorthWest(Basic2DNode northWest) {
        if (neighborList.contains(this.northWest))
            neighborList.remove(this.northWest);
        neighborList.add(northWest);

        this.northWest = northWest;
    }

    public float getHeuristicDistanceFromGoal() {
        return heuristicDistanceFromGoal;
    }

    public void setHeuristicDistanceFromGoal(float heuristicDistanceFromGoal) {
        this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
    }

    @Override
    public Basic2DNode valueOf(int x, int y) {
        return new Basic2DNode(x, y);
    }

    @Override
    public Basic2DNode valueOf(int x, int y, boolean visited, int distanceFromStart, boolean isObstacle, boolean isStart, boolean isGoal) {
        return new Basic2DNode(x, y, visited, distanceFromStart, isObstacle, isStart, isGoal);
    }

    public boolean equals(Basic2DNode node) {
        return (node.x == x) && (node.y == y);
    }


}
