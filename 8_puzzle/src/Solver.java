import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Aydin on 13/05/2017.
 */
public class Solver {
    private Stack<Board> boards = null;
    private boolean solvable = false;
    private int moves = -1;


    private class Node implements Comparable<Node> {
        final Board board;
        final int moves;
        final Node previous;

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

        public int priority() {
            return board.hamming() + moves;  //TODO hamming?
        }

        @Override
        public int compareTo(Node o) {
            if (priority() < o.priority()) {
                return -1;
            }
            else if (priority() > o.priority()) {
                return +1;
            }
            return 0;
        }
    }

    private Node step(MinPQ<Node> nodesPQ, Node searchNode) {
        for (Board neighbor : searchNode.board.neighbors()) {
            if ((searchNode.previous != null) && neighbor.equals(searchNode.previous.board)) {
                continue;
            }
            nodesPQ.insert(new Node(neighbor, searchNode.moves + 1, searchNode));
        }
        return nodesPQ.delMin();
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Null board provided.");
        }

        MinPQ<Node> nodesPQ = new MinPQ<>();
        nodesPQ.insert(new Node(initial));
        Node searchNode = nodesPQ.delMin();

        MinPQ<Node> twinNodesPQ = new MinPQ<>();
        twinNodesPQ.insert(new Node(initial.twin()));
        Node twinSearchNode = twinNodesPQ.delMin();
        while (!(searchNode.board.isGoal() || twinSearchNode.board.isGoal())) {
            searchNode = step(nodesPQ, searchNode);
            twinSearchNode = step(twinNodesPQ, twinSearchNode);
        }

        if (searchNode.board.isGoal()) {
            solvable = true;
            moves = searchNode.moves;

            boards = new Stack<Board>();
            while (searchNode.previous != null) {
                boards.push(searchNode.board);
                searchNode = searchNode.previous;
            }
            boards.push(initial);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() { return moves; }

    private class NodesIterable implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return boards.iterator();
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (boards == null) {
            return null;
        }
        return new NodesIterable(); //TODO
    }

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
