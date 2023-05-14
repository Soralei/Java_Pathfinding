package main.java.com.example.pathfinding;

import javax.swing.*;
import java.awt.*;

public class AStarGUI {
    public static void main(String[] args) {
        AStarGrid grid = new AStarGrid(100, 100);

        JFrame jFrame = new JFrame("A*");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(grid.getWidth() * 10, grid.getHeight() * 10);
        jFrame.setLocationRelativeTo(null);

        jFrame.setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                for(int i = 0; i < grid.getaStarNodes().length; i++) {
                    AStarNode node = grid.getaStarNodes()[i];
                    g.drawRect(node.getVector2D().x() * 10, node.getVector2D().y() * 10, 10, 10);
                }
            }
        });

        jFrame.setVisible(true);
        jFrame.repaint();
    }
}
