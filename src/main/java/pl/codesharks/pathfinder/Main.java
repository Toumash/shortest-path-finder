package pl.codesharks.pathfinder;

import pl.codesharks.pathfinder.algorithm.AStarAlgorithm;
import pl.codesharks.pathfinder.algorithm.DijkstraAlgorithm;
import pl.codesharks.pathfinder.algorithm.ShortestPathAlgorithm;
import pl.codesharks.pathfinder.heuristic.ChebyshevHeuristic;
import pl.codesharks.pathfinder.map.Grid;
import pl.codesharks.pathfinder.node.Basic2DNode;
import pl.codesharks.pathfinder.view.PathFinderWindow;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        final int width = 600;
        final int height = 600;
        Grid<Basic2DNode> map = new Grid<>(10, 10, new Basic2DNode());
        ArrayList<ShortestPathAlgorithm<Basic2DNode>> algorithms = new ArrayList<>();
        algorithms.add(new DijkstraAlgorithm());
        algorithms.add(new AStarAlgorithm<Basic2DNode>(new ChebyshevHeuristic()));
        new PathFinderWindow("A star algorithm Visualizer", width, height, map, algorithms);
    }

}
