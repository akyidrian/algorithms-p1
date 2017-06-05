import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Immutable data type that uses the A* algorithm to solve an 8-puzzle problem (or it's natural generalisations).
 *
 * Potential optimizations:
 * - Exploit the fact that the difference in Manhattan distance between a board and a neighbor is either −1 or +1.
 * - Use only one PQ to run the A* algorithm on the initial board and its twin.
 * - When two search nodes have the same Manhattan priority, you can break ties however you want, e.g., by comparing
 *   either the Hamming or Manhattan distances of the two boards.
 *
 * @author Aydin
 */
public class Solver {
    private Stack<Board> boards = null;
    private boolean solvable = false;
    private int moves = -1;

    /**
     * Find a solution to the initial board using the A* algorithm.
     *
     * @param initial problem board to solve.
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Null board provided.");
        }

        // Initial board
        MinPQ<Node> nodesPQ = new MinPQ<>();
        nodesPQ.insert(new Node(initial));
        Node searchNode = nodesPQ.delMin();

        // Twin board
        MinPQ<Node> twinNodesPQ = new MinPQ<>();
        twinNodesPQ.insert(new Node(initial.twin()));
        Node twinSearchNode = twinNodesPQ.delMin();

        // Keep searching for goal board in synchronized way with initial and twin boards.
        while (!(searchNode.board.isGoal() || twinSearchNode.board.isGoal())) {
            searchNode = searchStep(nodesPQ, searchNode);
            twinSearchNode = searchStep(twinNodesPQ, twinSearchNode);
        }

        // Set the member variable.
        if (searchNode.board.isGoal()) {
            solvable = true;
            moves = searchNode.moves;

            boards = new Stack<>();
            while (searchNode.previous != null) {  // Initial node has previous of null
                boards.push(searchNode.board);
                searchNode = searchNode.previous;
            }
            boards.push(initial);
        }
    }

    /**
     * Is the initial board solvable?
     *
     * @return true if solvable, false otherwise.
     */
    public boolean isSolvable() { return solvable; }

    /**
     * Minimum number of moves to solve initial board.
     *
     * @return minimum number of moves to solve. If unsolvable, -1 is given.
     */
    public int moves() { return moves; }

    /**
     * Shortest sequence of boards steps to the solution.
     *
     * @return iterable of the sequence of board steps. If unsolvable, null is given.
     */
    public Iterable<Board> solution() { return boards; }

    /**
     * Node class to be used in the MinPQ.
     */
    private static class Node implements Comparable<Node> {
        private final Board board;    // the board.
        private final int moves;      // number of moves made.
        private final Node previous;  // previous node.

        public Node(Board board) {
            this.board = board;
            this.moves = 0;
            this.previous = null;
        }

        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        /**
         * Priority value.
         *
         * @return pritority value.
         */
        public int priority() {
            return board.manhattan() + moves;
        }

        /**
         * Compare priority values.
         *
         * @param o node to compare to.
         * @return priority difference between this and o.
         */
        public int compareTo(Node o) {
            return this.priority() - o.priority();
        }
    }

    /**
     * Step of A* algorithm:
     * 1. Insert neighbors of the searchNode. Ignore previous board to searchNode.
     * 2. Delete and returns lowest priority node in the MinPQ.
     *
     * @param nodesPQ MinPQ filled with boards.
     * @param searchNode node to find neighbors for and insert into MinPQ.
     * @return Node with the lowest priority in the MinPQ.
     */
    private Node searchStep(MinPQ<Node> nodesPQ, Node searchNode) {
        for (Board neighbor : searchNode.board.neighbors()) {
            // Ignore the neighboring board that we've already added to MinPQ previously.
            if ((searchNode.previous != null) && neighbor.equals(searchNode.previous.board)) {
                continue;
            }
            nodesPQ.insert(new Node(neighbor, searchNode.moves + 1, searchNode));
        }
        return nodesPQ.delMin();  // Next lowest priority node in MinPQ.
    }

    /**
     * Simple test client.
     *
     * @param args file containing puzzle problem.
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
