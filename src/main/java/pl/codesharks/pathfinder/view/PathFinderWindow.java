package pl.codesharks.pathfinder.view;

import pl.codesharks.pathfinder.algorithm.ShortestPathAlgorithm;
import pl.codesharks.pathfinder.map.Map;
import pl.codesharks.pathfinder.node.Node;

import javax.swing.*;

/**
 * pl.codesharks.pathfinder.view
 * Created by Tomasz on 2015-02-20.
 */
public class PathFinderWindow<T extends Node<T>> extends JFrame {
    public PathFinderWindow(String title, int width, int height, Map<T> map, ShortestPathAlgorithm<T> shortestPathAlgorithm) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setResizable(false);

        this.setLocationRelativeTo(null);

        //TODO: get margin from the system api
        // Margin is a window top border + its menu
        final int marginTop = 50;
        PathFinderView<T> finderView = new PathFinderView<>(this, width, height - marginTop, map, shortestPathAlgorithm);
        finderView.setBounds(30, 30, width, height - 30);

        this.add(finderView);
        finderView.init();
        this.setVisible(true);
        finderView.start();
    }
}
