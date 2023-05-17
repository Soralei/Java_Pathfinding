package main.java.com.example.pathfinding;

import java.util.*;

public class AStarMain {
    int WIDTH = 100;
    int HEIGHT = 100;
    int MOVEMENT_VERTICAL_COST = 10;
    int MOVEMENT_DIAGONAL_COST = 14;

    public void findPath(Vector2D start, Vector2D target) {
        final PriorityQueue<AStarNode> openNodes = new PriorityQueue<>(new AStarNodeComparator());

        AStarNode[] nodes = new AStarNode[WIDTH * HEIGHT];
        int index = start.x() + start.y() * WIDTH;

        AStarNode initial = new AStarNode(start);
        initial.setgScore(0);
        initial.setfScore(heuristicDistance(start, target));

        nodes[index] = initial;
        openNodes.add(nodes[index]);

        AStarNode current;
        while((current = openNodes.poll()) != null) {
            // If the current node's position equals the target, we found our goal.
            if(current.getVector2D().equals(target)) {
                System.out.println("Found a path to the goal.");
                return;
            }

            // Check adjacent positions of current.
            int currentIndex = current.getVector2D().x() + current.getVector2D().y() * WIDTH;
            for(int y = -1; y <= 1; y++) {
                for(int x = -1; x <= 1; x++) {
                    // Get the x and y of the neighbour.
                    int neighbourX = current.getVector2D().x() + x;
                    int neighbourY = current.getVector2D().y() + y;

                    // Make sure the x and y is within the grid.
                    if(neighbourX < 0 || neighbourX > WIDTH || neighbourY < 0 || neighbourY > HEIGHT) continue;

                    // Ignore checking current.
                    if(neighbourX == 0 && neighbourY == 0) continue;

                    // Get the neighbour node. Create one if it doesn't yet exist.
                    int neighbourIndex = neighbourX + neighbourY * WIDTH;
                    if(nodes[neighbourIndex] == null) {
                        nodes[neighbourIndex] = new AStarNode(new Vector2D(neighbourX, neighbourY));
                    }
                    AStarNode neighbour = nodes[neighbourIndex];

                    // Calculate scores and check whether the neighbour should be added to the open list for future checking.
                    int tentative_gScore = current.getgScore() + movementCost(current.getVector2D(), neighbour.getVector2D());
                    if(tentative_gScore < neighbour.getgScore()) {
                        neighbour.setParent(current);
                        neighbour.setgScore(tentative_gScore);
                        neighbour.setfScore(tentative_gScore + heuristicDistance(neighbour.getVector2D(), target));

                        if(!openNodes.contains(neighbour)) {
                            openNodes.add(neighbour);
                        }
                    }
                }
            }
        }
        // No path was found...
        System.out.println("Failed to find a path...");
    }

    private int heuristicDistance(Vector2D from, Vector2D to) {
        // Square grid standard manhattan diagonal distance calculations
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());
        return MOVEMENT_VERTICAL_COST * (dx + dy) + (MOVEMENT_VERTICAL_COST - 2 * MOVEMENT_DIAGONAL_COST) * Math.min(dx ,dy);
    }

    private int movementCost(Vector2D from, Vector2D to) {
        boolean isDiagonal = from.y() != to.y() && from.x() != to.x();
        return isDiagonal ? MOVEMENT_DIAGONAL_COST : MOVEMENT_VERTICAL_COST;
    }
}
