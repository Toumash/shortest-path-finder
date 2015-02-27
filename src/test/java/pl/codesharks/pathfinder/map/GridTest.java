package pl.codesharks.pathfinder.map;

import org.junit.Test;
import pl.codesharks.pathfinder.node.BasicNode;

public class GridTest {

    /**
     * Checks for valid Grid initialization. All nodes should have at least one connection
     */
    @Test
    public void registerNodesTest() {
        int height = 10;
        int width = 10;
        Grid<BasicNode> g = new Grid<>(width, height, new BasicNode());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                BasicNode n = g.map.get(x).get(y);

                if (n.getNeighborList().size() == 0) {
                    throw new IllegalArgumentException("Node x:" + n.getX() + " y:" + n.getY() + " Has no neighbors");
                }

            }
        }
    }

}
