package main.java.com.example.pathfinding;

import java.util.*;

public class AStarMain {
    private AStarGrid aStarGrid;
    int MOVEMENT_VERTICAL_COST = 10;
    int MOVEMENT_DIAGONAL_COST = 14;

    public AStarMain(AStarGrid aStarGrid) {
        this.aStarGrid = aStarGrid;
    }

    public ArrayList<AStarNode> findPath(Vector2D start, Vector2D target) {
        final PriorityQueue<AStarNode> openNodes = new PriorityQueue<>(new AStarNodeComparator()); // Min heap for O(log n) time complexity.
        final ArrayList<AStarNode> closedNodes = new ArrayList<>();

        // Rather than tracking all nodes on the grid, create nodes as we scan them and store them by index here.
        AStarNode[] nodes = new AStarNode[aStarGrid.getWidth() * aStarGrid.getHeight()];
        int index = start.x() + start.y() * aStarGrid.getWidth();

        // Set up the initial node to start the algorithm from.
        AStarNode initial = new AStarNode(start);
        nodes[index] = initial;
        openNodes.add(nodes[index]);

        // Start popping nodes and perform A*.
        AStarNode current;
        while((current = openNodes.poll()) != null) {
            // If the current node's position equals the target, we found our goal.
            // Begin reconstructing the path.
            if(current.getVector2D().equals(target)) {
                return constructPath(current);
            }

            // Register the node as closed so that it can be ignored in future searches.
            closedNodes.add(current);

            // Check adjacent positions of current.
            int currentIndex = current.getVector2D().x() + current.getVector2D().y() * aStarGrid.getWidth();
            for(int y = -1; y <= 1; y++) {
                for(int x = -1; x <= 1; x++) {
                    // Get the x and y of the neighbour.
                    int neighbourX = current.getVector2D().x() + x;
                    int neighbourY = current.getVector2D().y() + y;

                    // Make sure the x and y is within the grid.
                    if(neighbourX < 0 || neighbourX >= aStarGrid.getWidth() || neighbourY < 0 || neighbourY >= aStarGrid.getHeight()) continue;

                    // Ignore checking current.
                    if(x == 0 && y == 0) continue;

                    // Get the neighbour node. Create one if it doesn't yet exist.
                    int neighbourIndex = neighbourX + neighbourY * aStarGrid.getWidth();
                    if(neighbourIndex >= nodes.length || neighbourIndex < 0) continue;
                    if(nodes[neighbourIndex] == null) {
                        nodes[neighbourIndex] = new AStarNode(new Vector2D(neighbourX, neighbourY));
                    }
                    AStarNode neighbour = nodes[neighbourIndex];

                    // Calculate scores and check whether the neighbour should be added to the open list for future checking.
                    int cost = current.getgScore() + movementCost(current.getVector2D(), neighbour.getVector2D());

                    // Track whether the neighbour is open or closed.
                    boolean neighbourOpen = openNodes.contains(neighbour);
                    boolean neighbourClosed = closedNodes.contains(neighbour);

                    // Check if the neighbour at this moment has better gscore than previously registered.
                    if(neighbourOpen && cost < neighbour.getgScore()) {
                        openNodes.remove(neighbour);
                        neighbourOpen = false;
                    }

                    // Check if the neighbour was closed, but currently has better gscore - if so, don't consider it closed.
                    // This shouldn't happen with a consistent admissible heuristic.
                    if(neighbourClosed && cost < neighbour.getgScore()) {
                        closedNodes.remove(neighbour);
                        neighbourClosed = false;
                    }

                    // Set scores, parent, and register the neighbour as an open node.
                    if(!neighbourOpen && !neighbourClosed) {
                        neighbour.setgScore(cost);
                        neighbour.setfScore(neighbour.getgScore() + heuristicDistance(neighbour.getVector2D(), target));
                        neighbour.setParent(current);
                        openNodes.add(neighbour);
                    }
                }
            }
        }
        // No path was found, return null.
        return null;
    }

    private int heuristicDistance(Vector2D from, Vector2D to) {
        // Square grid standard manhattan diagonal distance calculations
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());
        return MOVEMENT_VERTICAL_COST * (dx + dy) + (MOVEMENT_DIAGONAL_COST - 2 * MOVEMENT_VERTICAL_COST) * Math.min(dx, dy);
    }

    private int movementCost(Vector2D from, Vector2D to) {
        boolean isDiagonal = from.y() != to.y() && from.x() != to.x();
        return isDiagonal ? MOVEMENT_DIAGONAL_COST : MOVEMENT_VERTICAL_COST;
    }

    private ArrayList<AStarNode> constructPath(AStarNode from) {
        ArrayList<AStarNode> path = new ArrayList<>();
        AStarNode current = from;
        do {
            path.add(current);
            current = current.getParent();
        } while(current != null);

        return path;
    }

    public AStarGrid getaStarGrid() {
        return aStarGrid;
    }

    public void setaStarGrid(AStarGrid aStarGrid) {
        this.aStarGrid = aStarGrid;
    }
}
