/**
 * This record class represents a node for the current state and position of the pawn
 * on the board.
 * @param parent - The parent node (i.e. the previous position on the board the pawn was)
 * @param col - the current column of the pawn
 * @param row - the current row of the pawn
 * @param cost - the current path cost of the pawn up to this point
 * @param visited - how many places the pawn has visited at this point
 * @param heuristic - the heuristic value of the pawn at this point
 */
public record Node(Node parent, int col, int row, int cost, int visited, int heuristic) {

    /**
     * A simple to string override which prints the position of the pawn for verbose mode.
     * @return String representing the current position of the pawn
     */
    @Override
    public String toString() {
        return this.col + ", " + this.row;
    }
}
