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


    private final ArrayList<E> waypoints = new ArrayList<>();

    public Path() {
    }

    public int getSize() {
        return waypoints.size();
    }

    public E getWayPoint(int index) {
        return waypoints.get(index);
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
