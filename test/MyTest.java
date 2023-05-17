import main.java.com.example.pathfinding.AStarMain;
import main.java.com.example.pathfinding.Vector2D;
import org.junit.jupiter.api.Test;

public class MyTest {
    @Test
    void PriorityQueueTest() {
        AStarMain aStarMain = new AStarMain();
        aStarMain.findPath(new Vector2D(10, 10), new Vector2D(20, 80));
    }
}
