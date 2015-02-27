package pl.codesharks.pathfinder.heuristic;

import pl.codesharks.pathfinder.map.Map;

/**
 * Diagonal distance.
 * Best for 8-directions maps
 */
public class ChebyshevHeuristic implements AStarHeuristic {
    @Override
    public float getEstimatedDistanceToGoal(Map map, int startX, int startY, int goalX, int goalY) {
        double dx = Math.abs(startX - goalX);
        double dy = Math.abs(startY - goalY);
        return (float) (1 * Math.max(dx, dy));
    }
}
