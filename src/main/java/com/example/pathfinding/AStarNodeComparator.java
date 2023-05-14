package main.java.com.example.pathfinding;

import java.util.Comparator;

public class AStarNodeComparator implements Comparator<AStarNode> {
    @Override
    public int compare(AStarNode o1, AStarNode o2) {
        return Integer.compare(o1.getfScore(), o2.getfScore());
    }
}
