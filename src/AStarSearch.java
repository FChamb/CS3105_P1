import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarSearch {
    private final int d;
    private final int[] start;
    private final int coverage;
    private final boolean verbose;
    private final int time_limit;
    private final int[][] valid_Moves = {{3, 0}, {-3, 0}, {0, 3}, {0, -3}, {2, 2}, {-2, -2}, {2, -2}, {-2, 2}};
    public static final int NOT_FOUND=-1;

    public AStarSearch(int d, int[] start, int coverage, boolean verbose, int time_limit) {
        this.d = d;
        this.start = start;
        this.coverage = coverage;
        this.verbose = verbose;
        this. time_limit = time_limit;
    }

    public int search() {
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(Node::heuristic));
        int nodes = 0;

        Node begin = new Node(null, start[0], start[1], 0, 1, calculateHeuristic(coverage, 1));
        frontier.add(begin);

        while (!frontier.isEmpty()) {
            if (verbose) {
                printFrontier(frontier);
            }

            Node current = frontier.poll();
            nodes++;

            if (current.visited() == coverage) {
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
                    Node next = new Node(current, x, y, cost, current.visited() + 1, calculateHeuristic(coverage, current.visited() + 1) + cost);
                    frontier.add(next);
                }
            }
        }
        return NOT_FOUND;
    }


    private int calculateHeuristic(int coverage, int visited) {
        return 3 * (coverage - visited);
    }

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

    private void printFrontier(PriorityQueue<Node> frontier) {
        int count = 0;
        System.out.print("[");
        for (Node node : frontier) {
            System.out.print("(" + node.col() + "," + node.row() + "):" + node.heuristic());
            count++;
            if (count != frontier.size()) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
