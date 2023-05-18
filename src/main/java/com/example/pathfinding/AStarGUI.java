package main.java.com.example.pathfinding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarGUI {

    private static void doDrawRect(Graphics g, int x, int y, int squareSize, int actualSize, int offset) {
        g.drawRect((x + offset) * squareSize - (actualSize/2), (y + offset) * squareSize - (actualSize/2), actualSize, actualSize);
    }

    private static void doFillRect(Graphics g, int x, int y, int squareSize, int actualSize, int offset) {
        g.fillRect((x + offset) * squareSize - (actualSize/2), (y + offset) * squareSize - (actualSize/2), actualSize, actualSize);
    }

    private final static JFrame jFrame = new JFrame("A* GUI");
    private static Vector2D sq1 = null;
    private static Vector2D sq2 = null;
    private static boolean isAddingBlocks = false;
    private static boolean isRemovingBlocks = false;
    private static boolean debugMode = false;
    private final static AStarGrid aStarGrid = new AStarGrid(100, 100);
    private final static AStarMain aStarMain = new AStarMain(aStarGrid);
    private final static AStarMainDebug aStarMainDebug = new AStarMainDebug(jFrame, aStarGrid);
    private static ArrayList<AStarNode> path = null;
    private static ArrayList<AStarNode> debugPath = aStarMainDebug.getPath();

    public static void main(String[] args) {
        jFrame.setSize((100 + 5) * 10, (100 + 5) * 10);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int OFFSET = 1;
        int SQUARE_SIZE = 10;
        int PATH_SIZE = 5;

        JPanel jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                for (int i = 0; i < 100 * 100; i++) {
                    int x = i % 100;
                    int y = i / 100;
                    doDrawRect(g, x, y, SQUARE_SIZE, SQUARE_SIZE, OFFSET);
                }

                g.setColor(Color.BLUE);
                if(sq1 != null) { doFillRect(g, sq1.x(), sq1.y(), SQUARE_SIZE, PATH_SIZE + 4, OFFSET); }
                if(sq2 != null) { doFillRect(g, sq2.x(), sq2.y(), SQUARE_SIZE, PATH_SIZE + 4, OFFSET); }

                g.setColor(Color.BLUE);
                if(path != null) {
                    path.forEach(p -> doFillRect(g, p.getVector2D().x(), p.getVector2D().y(), SQUARE_SIZE, PATH_SIZE, OFFSET));
                }

                if(debugMode) {
                    debugPath.forEach(p -> doFillRect(g, p.getVector2D().x(), p.getVector2D().y(), SQUARE_SIZE, PATH_SIZE, OFFSET));
                }

                // They use the same grid, so unless they have been manually assigned separate grids, this won't do anything.
                g.setColor(Color.BLACK);
                if(debugMode) {
                    aStarMainDebug.getaStarGrid().getImpassable().forEach(blocked -> doFillRect(g, blocked.x(), blocked.y(), SQUARE_SIZE, SQUARE_SIZE, OFFSET));
                } else {
                    aStarMain.getaStarGrid().getImpassable().forEach(blocked -> doFillRect(g, blocked.x(), blocked.y(), SQUARE_SIZE, SQUARE_SIZE, OFFSET));
                }

                // Used to display open and closed nodes.
                if(debugMode) {
                    PriorityQueue<AStarNode> openNodes = aStarMainDebug.getOpenNodes();
                    ArrayList<AStarNode> closedNodes = aStarMainDebug.getClosedNodes();

                    if(openNodes != null && openNodes.size() > 0) {
                        g.setColor(Color.GREEN);
                        openNodes.forEach(open -> doDrawRect(g, open.getVector2D().x(), open.getVector2D().y(), SQUARE_SIZE, 9, OFFSET));
                    }

                    if(closedNodes != null && closedNodes.size() > 0) {
                        g.setColor(Color.RED);
                        closedNodes.forEach(closed -> doDrawRect(g, closed.getVector2D().x(), closed.getVector2D().y(), SQUARE_SIZE, 9, OFFSET));
                    }
                }
            }
        };

        jPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if(isAddingBlocks || isRemovingBlocks) {
                    int squareX = (e.getX() - 5) / 10;
                    int squareY = (e.getY() - 5) / 10;
                    Vector2D clickPos = new Vector2D(squareX, squareY);
                    Set<Vector2D> impassable = debugMode ? aStarMainDebug.getaStarGrid().getImpassable() : aStarMain.getaStarGrid().getImpassable();
                    if(isAddingBlocks) {
                        impassable.add(clickPos);
                    } else if (isRemovingBlocks) {
                        impassable.remove(clickPos);
                    }
                    jFrame.repaint();
                }
            }
        });

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    debugMode = !debugMode;
                    jPanel.repaint();
                }
            }
        });

        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == MouseEvent.BUTTON3) {
                    if(!isAddingBlocks) isAddingBlocks = true;
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    if(!isRemovingBlocks) isRemovingBlocks = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(isAddingBlocks) isAddingBlocks = false;
                if(isRemovingBlocks) isRemovingBlocks = false;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int squareX = (e.getX() - 5) / 10;
                int squareY = (e.getY() - 5) / 10;

                Vector2D clickPos = new Vector2D(squareX, squareY);

                if(squareX < 0 || squareY < 0) return;

                int action = 0;
                if(e.getButton() == MouseEvent.BUTTON1) {
                    if(sq1 != null && sq2 != null) {
                        sq1 = null;
                        sq2 = null;
                        action++;
                    } else if (sq1 != null) {
                        if(clickPos.equals(sq1)) {
                            sq1 = null;
                            action++;
                        }
                    } else if(sq2 != null) {
                        if(clickPos.equals(sq2)) {
                            sq2 = null;
                            action++;
                        }
                    }

                    if(sq1 == null && action <= 0) { sq1 = clickPos; action++; }
                    if(sq2 == null && action <= 0) { sq2 = clickPos; action++; }

                    if(sq1 != null && sq2 != null) {
                        new Thread(() -> path = debugMode ? aStarMainDebug.findPath(sq1, sq2) : aStarMain.findPath(sq1, sq2)).start();
                    }
                }

                jFrame.repaint();
            }
        });

        jFrame.setContentPane(jPanel);
        jFrame.setVisible(true);
    }
}
