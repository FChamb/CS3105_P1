import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Implementation for A Star Search. This class represents a search algorithm for A Star. The class
 * stores private local variables that help the various methods calculate the search path. Most of these
 * variables are self-explanatory, except valid_Moves. This is a 2D array of possible moves. Each array in the
 * outer array represents a direction path the pawn can move at each turn. The numbers are added to the current
 * position.
 */
public class AStarSearch {
    private final int d;
    private final int[] start;
    private final int coverage;
    private final boolean verbose;
    private final int time_limit;
    private final int[][] valid_Moves = {{3, 0}, {-3, 0}, {0, 3}, {0, -3}, {2, 2}, {-2, -2}, {2, -2}, {-2, 2}};
    private static final int NOT_FOUND=-1;
    private static final int NOT_TERMINATED=-100;
    private int nodes;

    /**
     * This method represents the default constructor for AStar. The program sets the private attributes with
     * the provided values.
     * @param d - the dimensions of the board
     * @param start - an integer array representing where to start on the board
     * @param coverage - an integer value of how many spaces need to be touched by the pawn
     * @param verbose - a boolean value on whether to print output or not
     * @param time_limit - the maximum time limit to allow searching
     */
    public AStarSearch(int d, int[] start, int coverage, boolean verbose, int time_limit) {
        this.d = d;
        this.start = start;
        this.coverage = coverage;
        this.verbose = verbose;
        this.time_limit = time_limit;
    }

    /**
     * Search is the actual searching aspect of AStar. It is nearly identical to the BestF implementation.
     * The method grabs the current time for future reference. Then the program creates a priority queue
     * and creates a value to store how many nodes have been explored. The first state/node of the board is
     * created with default user provided selection and the node is put on the priority queue/frontier.
     * A while loop checks if the frontier is empty at each iteration. If verbose is active, the frontier is printed.
     * Then the program checks that the time has not exceeded the provided limit. Otherwise,
     * the current position is grabbed. If the number of visited cells is equal to the coverage specified the program stops
     * after printing/returning the appropriate values. Otherwise, each possible move is checked for validity and is added
     * into a new node which in turn is added to the frontier.
     * @return an integer representing the cost of finding the path
     */
    public int search() {
        LocalTime timeStart = LocalTime.now();
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(Node::heuristic));
        int nodes = 0;

        Node begin = new Node(null, start[0], start[1], 0, 1, calculateHeuristic(coverage, 1));
        frontier.add(begin);

        while (!frontier.isEmpty()) {
            LocalTime time = LocalTime.now();
            if (verbose) {
                printFrontier(frontier);
            }
            if (Duration.between(timeStart, time).toSeconds() == this.time_limit) {
                System.out.println(nodes);
                return NOT_TERMINATED;
            }

            Node current = frontier.poll();
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
                    // The only difference between the BestF implementation and AStar is the heuristic for each node includes the cost up to this point.
                    Node next = new Node(current, x, y, cost, current.visited() + 1, calculateHeuristic(coverage, current.visited() + 1) + cost);
                    frontier.add(next);
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * Calculate heuristic takes the coverage to be touched and the current number of visited cells.
     * And according to the specification three is multiplied by the difference in cells to visit and
     * already visited.
     * @param coverage - number of cells to visit
     * @param visited - number of cells currently visited
     * @return an integer for the heuristic value
     */
    private int calculateHeuristic(int coverage, int visited) {
        return 3 * (coverage - visited);
    }

    /**
     * In path is a boolean recursive method which determines if the provided position
     * is in the path taken. This function continuously calls the parent node and checks
     * its position.
     * @param node - the node at the bottom of the path to check
     * @param pos - an array of integers representing the current position
     * @return a boolean value stating whether the coords are in the path
     */
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

    /**
     * Construct path is a recursive method which returns a string of the path taken. This function
     * is provided the last node in the path and uses recursion to get the coordinates of every parent
     * node until it reaches the top.
     * @param node - the bottom of the search algorithm, the final node
     * @param path - a string variable to help pass the path with recursion
     * @return a string representing the coordinate values of the path
     */
    private String constructPath(Node node, String path) {
        Node parent = node.parent();
        if (parent == null) {
            return "(" + node.col() + "," + node.row() + ")" + path;
        } else {
            return constructPath(parent, "(" + node.col() + "," + node.row() + ")" + path);
        }
    }

    /**
     * Calculate cost finds the cost of the path from the chosen move. The function takes
     * an integer array of the chosen move direction. If the values contain 3 then it is a
     * horizontal or vertical move and the cost is 3. If the values contain anything else,
     * i.e. 2 then it is a diagonal move and the cost is 4.
     * @param move - integer array of the move the pawn will take
     * @return an integer of the cost of the current move
     */
    public int calculateCost(int[] move) {
        if (Math.abs(move[0]) == 3 || Math.abs(move[1]) == 3) {
            return 3;
        }
        return 4;
    }

    /**
     * Valid move determines if a movable direction is valid, i.e. if it is on the board.
     * The method takes the size of the board and the next column and row the pawn would
     * move to. True is returned if the move is contained within the constraints of the board.
     * False, else wise.
     * @param size - the size of the board
     * @param c - the next column position of the pawn
     * @param r - the next row position of the pawn.
     * @return a boolean value representing if a move is value.
     */
    public boolean validMove(int size, int c, int r) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }

    /**
     * Print frontier does exactly that. It prints every node in the frontier following
     * the format of the specification.
     * @param frontier - the priority queue of the frontier
     */
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

    /**
     * Get nodes is a simple getter function which returns the number of nodes
     * searched through. This is used for comparing different searches in Part B.
     * @return an integer of the number of nodes searched through.
     */
    public int getNodes() {
        return this.nodes;
    }
}
