package pl.codesharks.pathfinder.map;

import pl.codesharks.pathfinder.node.Node;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Generic list of waypoints that could store any Node extending {@link pl.codesharks.pathfinder.node.BasicNode}
 *
 * @param <E> Node type to store
 */
public class Path<E extends Node<E>> implements Iterable<E> {


    private ArrayList<E> waypoints = new ArrayList<>();

    public Path() {
    }

    public int getSize() {
        return waypoints.size();
    }

    public E getWayPoint(int index) {
        return waypoints.get(index);
    }

    /**
     * Get the x-coordinate for the waypoint at the given index.
     *
     * @param index The index of the waypoint to get the x-coordinate of.
     * @return The x coordinate at the waypoint.
     */
    public int getX(int index) {
        return getWayPoint(index).getX();
    }

    /**
     * Get the y-coordinate for the waypoint at the given index.
     *
     * @param index The index of the waypoint to get the y-coordinate of.
     * @return The y coordinate at the waypoint.
     */
    public int getY(int index) {
        return getWayPoint(index).getY();
    }

    /**
     * Append a waypoint to the path.
     *
     * @param n The node containing coordinate of the waypoint.
     */
    public void appendWayPoint(E n) {
        waypoints.add(n);
    }

    /**
     * Add a waypoint to the beginning of the path.
     *
     * @param n The node containing coordinate of the waypoint.
     */
    public void prependWayPoint(E n) {
        waypoints.add(0, n);
    }

    /**
     * Check if this path contains the WayPoint
     *
     * @param x The x coordinate of the waypoint.
     * @param y The y coordinate of the waypoint.
     * @return True if the path contains the waypoint.
     */
    public boolean contains(int x, int y) {
        for (E node : waypoints) {
            if (node.getX() == x && node.getY() == y)
                return true;
        }
        return false;
    }


    @Override
    public Iterator<E> iterator() {
        return waypoints.iterator();
    }
}
