package pl.codesharks.pathfinder;

import org.junit.Test;
import pl.codesharks.pathfinder.algorithm.AStarAlgorithm;
import pl.codesharks.pathfinder.heuristic.AStarHeuristic;
import pl.codesharks.pathfinder.heuristic.ManhattanHeuristic;
import pl.codesharks.pathfinder.map.Grid;
import pl.codesharks.pathfinder.map.Path;
import pl.codesharks.pathfinder.node.BasicNode;
import pl.codesharks.pathfinder.util.Logger;
import pl.codesharks.pathfinder.util.StopWatch;

@SuppressWarnings("FieldCanBeLocal")
public class TestAStar {

    private static int mapWith = 10;
    private static int mapHeight = 10;


    @Test
    public void mainTest() throws Exception {
        Logger log = new Logger();
        StopWatch s = new StopWatch();
        s.start();

        log.addLine("=======================");
        log.addLine("Map initializing...");
        Grid<BasicNode> map = new Grid<>(mapWith, mapHeight,new BasicNode());

        map.setStartLocation(0, 0);
        map.setGoalLocation(9, 9);
        map.setObstacle(4, 6, true);
        map.setObstacle(5, 5, true);
        map.setObstacle(5, 6, true);
        map.setObstacle(6, 5, true);
        map.setObstacle(7, 5, true);

        log.addLine("Heuristic initializing...");
        AStarHeuristic heuristic = new ManhattanHeuristic();

        log.addLine("Pathfinder initializing...");
        AStarAlgorithm pathFinder = new AStarAlgorithm(heuristic);

        log.addLine("Calculating shortest path...");
        Path p = pathFinder.calculateShortestPath(map);

        if(p==null){
            throw new Exception("Path not found exception");
        }else{
            log.addLine("Path NOT null <  OK  >");
        }
        s.stop();
        log.addLine("Calculation time: " + s.getElapsedTimeMilliseconds() + "ms");

        log.addLine("Printing map of shortest path...");
        pathFinder.printPath();
        log.addLine("=======================");
    }

}

