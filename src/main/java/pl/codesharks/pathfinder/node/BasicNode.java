package pl.codesharks.pathfinder.node;

/**
 * Usual, 2D node
 */
@SuppressWarnings("UnusedDeclaration")
public class BasicNode extends Node<BasicNode> {

    public BasicNode() {
        super();
    }

    public BasicNode(int x, int y) {
        super(x, y);
    }

    public BasicNode(int x, int y, boolean visited, int distanceFromStart, boolean isObstacle, boolean isStart, boolean isGoal) {
        super(x, y, visited, distanceFromStart, isObstacle, isStart, isGoal);
    }

    public void clear() {
        isStart = false;
        isGoal = false;
        isObstacle = false;
    }

    @Override
    public BasicNode valueOf(int x, int y) {
        return new BasicNode(x, y);
    }

    @Override
    public BasicNode valueOf(int x, int y, boolean visited, int distanceFromStart, boolean isObstacle, boolean isStart, boolean isGoal) {
        return new BasicNode(x, y, visited, distanceFromStart, isObstacle, isStart, isGoal);
    }


    public boolean equals(BasicNode node) {
        return (node.x == x) && (node.y == y);
    }
}

