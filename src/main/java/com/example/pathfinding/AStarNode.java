package main.java.com.example.pathfinding;

public class AStarNode {
    private Vector2D vector2D;
    private int fScore;
    private int gScore;
    private AStarNode parent;

    public AStarNode(Vector2D vector2D) {
        this.vector2D = vector2D;
        this.fScore = Integer.MAX_VALUE;
        this.gScore = Integer.MAX_VALUE;
    }

    public int getfScore() {
        return fScore;
    }

    public void setfScore(int fScore) {
        this.fScore = fScore;
    }

    public int getgScore() {
        return gScore;
    }

    public void setgScore(int gScore) {
        this.gScore = gScore;
    }

    public Vector2D getVector2D() {
        return vector2D;
    }

    public void setVector2D(Vector2D vector2D) {
        this.vector2D = vector2D;
    }

    public AStarNode getParent() {
        return parent;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }
}
