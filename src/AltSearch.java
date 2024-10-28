import java.time.Duration;
import java.time.LocalTime;
import java.util.Stack;

/**
 * Implementation for Depth First Search. This class represents a depth-first search algorithm.
 * The structure is similar to Best First Search but uses a Stack instead of a Priority Queue
 * and does not use a heuristic.
 */
public class AltSearch {
    private final int d;
    private final int[] start;
    private final int coverage;
    private final boolean verbose;
    private final int time_limit;
    private final int[][] valid_Moves = {{3, 0}, {-3, 0}, {0, 3}, {0, -3}, {2, 2}, {-2, -2}, {2, -2}, {-2, 2}};
    private static final int NOT_FOUND = -1;
    private static final int NOT_TERMINATED = -100;
    private int nodes;

    /**
     * Constructor for DepthFirstSearch.
     * @param d - dimensions of the board
     * @param start - starting position
     * @param coverage - cells to be covered
     * @param verbose - print details if true
     * @param time_limit - max search time in seconds
     */
    public AltSearch(int d, int[] start, int coverage, boolean verbose, int time_limit) {
        this.d = d;
        this.start = start;
        this.coverage = coverage;
        this.verbose = verbose;
        this.time_limit = time_limit;
    }

    /**
     * The search method performs Depth-First Search to cover the board as specified.
     * It uses a stack to explore each path until it meets the coverage or times out.
     * @return int cost of the found path or NOT_FOUND/NOT_TERMINATED.
     */
    public int search() {
        LocalTime timeStart = LocalTime.now();
        Stack<Node> frontier = new Stack<>();
        nodes = 0;

        Node begin = new Node(null, start[0], start[1], 0, 1, 0);
        frontier.push(begin);

        while (!frontier.isEmpty()) {
            LocalTime time = LocalTime.now();
            if (verbose) {
                printFrontier(frontier);
            }
            if (Duration.between(timeStart, time).toSeconds() >= this.time_limit) {
                System.out.println(nodes);
                return NOT_TERMINATED;
            }

            Node current = frontier.pop();
            nodes++;

            if (current.visited() == coverage) {
                this.nodes = nodes;
                if (verbose) {
                    System.out.println(nodes);
                    String path = "";
                    System.out.println(constructPath(current, path));
                    System.out.println(current.visited());
                }
                return current.cost();
            }

            for (int[] move : valid_Moves) {
                int x = current.col() + move[0];
                int y = current.row() + move[1];

                if (validMove(d, x, y) && !inPath(current, new int[]{x, y})) {
                    int cost = current.cost() + calculateCost(move);
                    Node next = new Node(current, x, y, cost, current.visited() + 1, 0);
                    frontier.push(next);
                }
            }
        }
        return NOT_FOUND;
    }

    // Reuse helper methods from BestFirstSearch with minor adjustments for DFS
    private boolean inPath(Node node, int[] pos) {
        Node parent = node.parent();
        if (parent == null) {
            return false;
        } else if (parent.col() == pos[0] && parent.row() == pos[1]) {
            return true;
        } else {
            return inPath(parent, pos);
        }
    }

    private String constructPath(Node node, String path) {
        Node parent = node.parent();
        if (parent == null) {
            return "(" + node.col() + "," + node.row() + ")" + path;
        } else {
            return constructPath(parent, "(" + node.col() + "," + node.row() + ")" + path);
        }
    }

    public int calculateCost(int[] move) {
        if (Math.abs(move[0]) == 3 || Math.abs(move[1]) == 3) {
            return 3;
        }
        return 4;
    }

    public boolean validMove(int size, int c, int r) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }

    private void printFrontier(Stack<Node> frontier) {
        int count = 0;
        System.out.print("[");
        for (Node node : frontier) {
            System.out.print("(" + node.col() + "," + node.row() + ")");
            count++;
            if (count != frontier.size()) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public int getNodes() {
        return this.nodes;
    }
}