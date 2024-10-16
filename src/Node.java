import java.util.ArrayList;

public class Node {
    private final Node parent;
    private final int col;
    private final int row;
    private final int cost;
    private final int heuristic;
    private final int visited;


    public Node(Node parent, int col, int row, int cost, int visited, int heuristic) {
        this.parent = parent;
        this.col = col;
        this.row = row;
        this.cost = cost;
        this.visited = visited;
        this.heuristic = heuristic;
    }

    public Node getParent() { return this.parent; }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getCost() {
        return this.cost;
    }

    public int getHeuristic() {
        return this.heuristic;
    }

    public int getVisited() {
        return this.visited;
    }

    @Override
    public String toString() {
        return this.col + ", " + this.row;
    }
}
