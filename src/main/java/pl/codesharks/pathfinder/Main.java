package pl.codesharks.pathfinder;

import pl.codesharks.pathfinder.algorithm.AStarAlgorithm;
import pl.codesharks.pathfinder.algorithm.ShortestPathAlgorithm;
import pl.codesharks.pathfinder.heuristic.ChebyshevHeuristic;
import pl.codesharks.pathfinder.map.Grid;
import pl.codesharks.pathfinder.map.Map;
import pl.codesharks.pathfinder.node.BasicNode;
import pl.codesharks.pathfinder.view.PathFinderWindow;

public class Main {

    public static void main(String[] args) {
        final int width = 600;
        final int height = 600;
        Map<BasicNode> map = new Grid<>(10, 10, new BasicNode());
        ShortestPathAlgorithm<BasicNode> algorithm = new AStarAlgorithm(new ChebyshevHeuristic());
        new PathFinderWindow<>("A star algorithm Visualizer", width, height, map, algorithm);
    }

}
