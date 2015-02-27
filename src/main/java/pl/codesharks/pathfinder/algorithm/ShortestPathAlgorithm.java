package pl.codesharks.pathfinder.algorithm;

import pl.codesharks.pathfinder.map.Map;
import pl.codesharks.pathfinder.map.Path;
import pl.codesharks.pathfinder.node.Node;

public interface ShortestPathAlgorithm<E extends Node<E>> {

    public Path<E> calculateShortestPath(Map<E> map);


    public Path<E> calculateShortestPath(Map<E> map, int delayMs);

    public void printPath();
}
