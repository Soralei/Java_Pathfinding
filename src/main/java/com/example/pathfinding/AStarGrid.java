package main.java.com.example.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AStarGrid {
    private int width;
    private int height;
    private final Set<Vector2D> impassable;

    public AStarGrid(int width, int height) {
        this.width = width;
        this.height = height;
        impassable = new HashSet<>();
    }

    void addImpassable(Vector2D vector2D) {
        impassable.add(vector2D);
    }

    void removeImpassable(Vector2D vector2D) {
        impassable.remove(vector2D);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Set<Vector2D> getImpassable() {
        return impassable;
    }
}
