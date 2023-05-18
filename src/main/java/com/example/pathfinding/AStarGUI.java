package main.java.com.example.pathfinding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AStarGUI {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("A* GUI");
        jFrame.setSize((100 + 5) * 10, (100 + 5) * 10);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AStarMain aStarMain = new AStarMain(new AStarGrid(100, 100));
        ArrayList<AStarNode> path = aStarMain.findPath(new Vector2D(0, 0), new Vector2D(50, 99));

        int OFFSET = 1;
        int SQUARE_SIZE = 10;
        int PATH_SIZE = 5;

        jFrame.setContentPane(new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                for(int i = 0; i < 100 * 100; i++) {
                    int x = i % 100;
                    int y = i / 100;
                    g.drawRect((x + OFFSET) * SQUARE_SIZE - (SQUARE_SIZE/2), (y + OFFSET) * SQUARE_SIZE - (SQUARE_SIZE/2), SQUARE_SIZE, SQUARE_SIZE);
                }

                g.setColor(Color.GREEN);
                if(path != null) {
                    path.forEach(node -> g.fillRect((node.getVector2D().x() + OFFSET) * SQUARE_SIZE - (PATH_SIZE/2), (node.getVector2D().y() + OFFSET) * SQUARE_SIZE - (PATH_SIZE/2), PATH_SIZE, PATH_SIZE));
                }
            }
        });

        jFrame.setVisible(true);
    }
}
