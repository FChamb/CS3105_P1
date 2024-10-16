public record Node(Node parent, int col, int row, int cost, int visited, int heuristic) {

    @Override
    public String toString() {
        return this.col + ", " + this.row;
    }
}
