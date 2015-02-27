package pl.codesharks.pathfinder.heuristic;

import pl.codesharks.pathfinder.map.Map;

public class ManhattanHeuristic implements AStarHeuristic {
    @Override
    public float getEstimatedDistanceToGoal(Map map, int startX, int startY, int goalX, int goalY) {
        int dx = goalX - startX;
        int dy = goalY - startY;

        return dx + dy;
    }
}
