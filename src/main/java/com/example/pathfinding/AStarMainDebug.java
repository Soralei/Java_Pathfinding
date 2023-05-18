package main.java.com.example.pathfinding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarMainDebug extends AStarMain {
    private final PriorityQueue<AStarNode> openNodes;
    private final ArrayList<AStarNode> closedNodes;
    private final ArrayList<AStarNode> path;
    private int delay;
    private final JFrame jFrame;

    public AStarMainDebug(JFrame jFrame, AStarGrid aStarGrid) {
        super(aStarGrid);
        this.openNodes = new PriorityQueue<>(new AStarNodeComparator());
        this.closedNodes = new ArrayList<>();
        this.path = new ArrayList<>();
        delay = 1;
        this.jFrame = jFrame;
    }

    @Override
    public ArrayList<AStarNode> findPath(Vector2D start, Vector2D target) {
        synchronized (openNodes) {
            synchronized (closedNodes) {
                openNodes.clear();
                closedNodes.clear();
                path.clear();

                // Rather than tracking all nodes on the grid, create nodes as we scan them and store them by index here.
                AStarNode[] nodes = new AStarNode[getaStarGrid().getWidth() * getaStarGrid().getHeight()];
                int index = start.x() + start.y() * getaStarGrid().getWidth();

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
                    int currentIndex = current.getVector2D().x() + current.getVector2D().y() * getaStarGrid().getWidth();
                    for(int y = -1; y <= 1; y++) {
                        for(int x = -1; x <= 1; x++) {
                            // Get the x and y of the neighbour.
                            int neighbourX = current.getVector2D().x() + x;
                            int neighbourY = current.getVector2D().y() + y;

                            // Make sure the x and y is within the grid.
                            if(neighbourX < 0 || neighbourX >= getaStarGrid().getWidth() || neighbourY < 0 || neighbourY >= getaStarGrid().getHeight()) continue;

                            // Ignore checking current.
                            if(x == 0 && y == 0) continue;

                            // Ignore blocked nodes.
                            if(getaStarGrid().getImpassable().contains(new Vector2D(neighbourX, neighbourY))) continue;

                            // Get the neighbour node. Create one if it doesn't yet exist.
                            int neighbourIndex = neighbourX + neighbourY * getaStarGrid().getWidth();
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

                            jFrame.repaint();
                            try { Thread.sleep(0, 1); } catch (InterruptedException e) { throw new RuntimeException(e); }
                        }
                    }
                }
                // No path was found, return null.
                return null;
            }
        }
    }

    @Override
    public ArrayList<AStarNode> constructPath(AStarNode from) {
        AStarNode current = from;
        do {
            path.add(current);
            current = current.getParent();
            jFrame.repaint();
            try { Thread.sleep(delay); } catch (InterruptedException e) { throw new RuntimeException(e); }
        } while(current != null);

        return path;
    }

    public PriorityQueue<AStarNode> getOpenNodes() {
        return openNodes;
    }

    public ArrayList<AStarNode> getClosedNodes() {
        return closedNodes;
    }

    public ArrayList<AStarNode> getPath() {
        return path;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
