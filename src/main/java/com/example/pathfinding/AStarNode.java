package main.java.com.example.pathfinding;

public class AStarNode {
    private Vector2D vector2D;
    private int fScore;
    private int gScore;
    private int hScore;
    private AStarNode parent;

    public AStarNode(Vector2D vector2D) {
        this.vector2D = vector2D;
        this.fScore = 0;
        this.gScore = 0;
        this.hScore = 0;
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

    public int gethScore() {
        return hScore;
    }

    public void sethScore(int hScore) {
        this.hScore = hScore;
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

    @Override
    public boolean equals(Object obj) {
        if(getClass() != obj.getClass()) return false;
        return vector2D.equals(((AStarNode) obj).vector2D);
    }
}
