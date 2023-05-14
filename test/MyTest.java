import main.java.com.example.pathfinding.AStarGrid;
import main.java.com.example.pathfinding.AStarNode;
import main.java.com.example.pathfinding.Vector2D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MyTest {
    @Test
    void name() {
        int width = 100;
        int height = 100;

        AStarGrid grid = new AStarGrid(100, 100);

        AStarNode current = new AStarNode(new Vector2D(0, 0));

        // For each neighbour...
        for(int y = -1; y <= 1; y++) {
            for(int x = -1; x <= 1; x++) {
                if(x == 0 && y == 0) continue;

                Vector2D gridPosition = new Vector2D(current.getVector2D().x() + x, current.getVector2D().y() + y);
                AStarNode n = grid.getNodeAt(gridPosition);
                if(n != null) {
                    System.out.println("[" + gridPosition + "] is a valid neighbour.");
                } else {
                    System.out.println("[" + gridPosition + "] is NOT a valid neighbour.");
                }
            }
        }
    }
}
