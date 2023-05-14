package main.java.com.example.pathfinding;

import java.util.ArrayList;

public class AStarGrid {
    private final AStarNode[] aStarNodes;
    private final int width;
    private final int height;

    public AStarGrid(int width, int height) {
        this.width = width;
        this.height = height;
        aStarNodes = new AStarNode[width * height];
        for(int i = 0; i < width * height; i++) {
            int x = i % width;
            int y = i / height;
            aStarNodes[i] = new AStarNode(new Vector2D(x, y));
        }
    }

    public AStarNode getNodeAt(Vector2D vector2D) {
        int index = vector2D.x() + vector2D.y() * width;
        //if(index < 0 || index >= aStarNodes.length) return null;
        if(!isWithinGrid(vector2D)) return null;
        return aStarNodes[index];
    }

    public boolean isWithinGrid(Vector2D vector2D) {
        return (vector2D.x() >= 0 && vector2D.x() < getWidth() && vector2D.y() >= 0 && vector2D.y() < getHeight());
    }

    public AStarNode[] getaStarNodes() {
        return aStarNodes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
