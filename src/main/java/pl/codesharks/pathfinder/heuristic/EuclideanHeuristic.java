package pl.codesharks.pathfinder.heuristic;

import pl.codesharks.pathfinder.map.Map;

/**
 * Standard heuristic, works with pythagorean triangle rule
 */
public class EuclideanHeuristic implements AStarHeuristic {
    @Override
    public float getEstimatedDistanceToGoal(Map map, int startX, int startY, int goalX, int goalY) {
        int dx = startX - goalX;
        int dy = startY - goalY;

        return (float) Math.sqrt((dx * dx) + (dy * dy));
    }
}
