package main.java.com.example.pathfinding;

public record Vector2D(int x, int y) {
    @Override
    public boolean equals(Object obj) {
        if(getClass() != obj.getClass()) return false;
        Vector2D o = (Vector2D)obj;
        return (this.x == o.x && this.y == o.y);
    }
}