package pl.codesharks.pathfinder.algorithm;

import pl.codesharks.pathfinder.map.Map;
import pl.codesharks.pathfinder.map.Path;
import pl.codesharks.pathfinder.node.Node;

public interface ShortestPathAlgorithm<E extends Node<E>> {

    Path<E> findPath(Map<E> map);

    Path<E> findPath(Map<E> map, int delayMs);

    void printPath();

    String getName();

    String getDescription();

}
