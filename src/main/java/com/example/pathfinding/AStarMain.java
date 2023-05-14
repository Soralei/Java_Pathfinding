package main.java.com.example.pathfinding;

import java.util.*;

public class AStarMain {
    private AStarGrid grid;

    public AStarMain(int width, int height) {
        grid = new AStarGrid(width, height);
    }

    public void findPath(Vector2D start, Vector2D target) {
        final PriorityQueue<AStarNode> openNodes = new PriorityQueue<>(new AStarNodeComparator());
        openNodes.add(grid.getNodeAt(start));

        Map<AStarNode, AStarNode> cameFrom = new HashMap<>();

        AStarNode current;
        while((current = openNodes.poll()) != null) {
            if(current.getVector2D().equals(target)) {
                // Found goal. Reconstruct path.
                return;
            }

            // For each neighbour...
            for(int y = -1; y <= 1; y++) {
                for(int x = -1; x <= 1; x++) {
                    if(x == 0 && y == 0) continue;

                    Vector2D gridPosition = new Vector2D(current.getVector2D().x() + x, current.getVector2D().y() + y);
                    AStarNode n = grid.getNodeAt(gridPosition);
                    if(n != null) {
                        //int tentative_gScore = current.getgScore() + distance(current, n);

                    }
                }
            }
        }
    }
}
