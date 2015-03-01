package pl.codesharks.pathfinder.map;

import org.junit.Test;
import pl.codesharks.pathfinder.node.Basic2DNode;

public class GridTest {

    /**
     * Checks for valid Grid initialization. All nodes should have at least one connection
     */
    @Test
    public void registerNodesTest() {
        int height = 10;
        int width = 10;
        Grid<Basic2DNode> g = new Grid<>(width, height, new Basic2DNode());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Basic2DNode n = g.map.get(x).get(y);
                if (n.getNeighborList().size() == 0) {
                    throw new IllegalArgumentException("Node x:" + n.getX() + " y:" + n.getY() + " Has no neighbors");
                }

            }
        }
    }

}
