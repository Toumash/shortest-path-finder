package pl.codesharks.pathfinder.view;

import pl.codesharks.pathfinder.algorithm.ShortestPathAlgorithm;
import pl.codesharks.pathfinder.map.Grid;
import pl.codesharks.pathfinder.node.Basic2DNode;

import javax.swing.*;
import java.util.ArrayList;


public class PathFinderWindow extends JFrame {

    public PathFinderWindow(String title, int width, int height, Grid<Basic2DNode> map, ArrayList<ShortestPathAlgorithm<Basic2DNode>> algorithms) {

        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setResizable(false);

        this.setLocationRelativeTo(null);

        //TODO: get margin from the system api
        // Margin is a window top border + its menu
        final int marginTop = 50;
        PathFinderView finderView = new PathFinderView(this, width, height - marginTop, map, algorithms);
        finderView.setBounds(30, 30, width, height - 30);

        this.add(finderView);
        finderView.init();
        this.setVisible(true);
        finderView.start();
    }
}
