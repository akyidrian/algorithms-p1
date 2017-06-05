import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stack;

/**
 * Immutable data type that represents a board from an 8-puzzle problem (or it's natural generalisations).
 *
 * All methods are time proportional to N^2 (or better) in the worst case.
 *
 * Potential optimization:
 * - Use a 1d array instead of a 2d array.
 *
 * @author Aydin
 */
public class Board {

    private final int[][] blocks;  // 2d array of blocks representing the board.

    /**
     * Setup and initialise Board object.
     *
     * A block at row i and column j is given at block[i][j]. It is assumed that the blocks given contains N^2 integers
     * between 0 and N^2 - 1, with 0 representing the blank block.
     *
     * @param blocks representing the board.
     */
    public Board(int[][] blocks) {
        if (blocks == null || hasNullRow(blocks)) {
            throw new NullPointerException("Null 2d array or null row in 2d array provided.");
        }

        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            this.blocks[i] = blocks[i].clone();
        }
    }

    /**
     * Number of rows/cols in the n-by-n board.
     *
     * @return board dimension.
     */
    public int dimension() {
        return blocks.length;
    }

    /**
     * Number of blocks that are in the wrong position compared to the goal board.
     *
     * @return number of out of place blocks in the board.
     */
    public int hamming() {
        int value = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                // Ignore the blank block or blocks already in the goal position.
                if (blocks[i][j] != 0 && blocks[i][j] != goalBlock(i, j)) {
                    value++;
                }
            }
        }
        return value;
    }

    /**
     * The sum of the Manhattan distances of each block in the board. The Manhattan distance of a block is the number of
     * horizontal and vertical moves needed to reach the goal position.
     *
     * @return sum of Manhattan distances.
     */
    public int manhattan() {
        int value = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int[] goalBlock = goalIndex(blocks[i][j]);
                if (blocks[i][j] != 0) {  // Ignore blank block.
                    value += Math.abs(goalBlock[0] - i) + Math.abs(goalBlock[1] - j);
                }
            }
        }
        return value;
    }

    /**
     * Is this the goal board?
     *
     * @return true if goal board, false otherwise.
     */
    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != goalBlock(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Find a twin board at random. A twin board is any board with a pair of adjacent non-blank blocks swapped around.
     *
     * Potential optimization?
     * Choose to swap blocks that reduces hamming/manhattan priority in the twin.
     *
     * @return a twin board.
     */
    public Board twin() {
        int randBlockI;
        int randBlockJ;
        int[] randAdjacentBlock;

        do {
            randBlockI = StdRandom.uniform(dimension());
            randBlockJ = StdRandom.uniform(dimension());
            Direction dir = Direction.getDirection(StdRandom.uniform(4));
            randAdjacentBlock = Direction.getAdjacentBlockIndex(randBlockI, randBlockJ, dir);
        } while (!inBounds(randAdjacentBlock[0], randAdjacentBlock[1]) ||  // is adjacent block not inside the board?
                (blocks[randBlockI][randBlockJ] == 0) ||  // is chosen block a non-blank block?
                (blocks[randAdjacentBlock[0]][randAdjacentBlock[1]] == 0));  // is adjacent block a non-blank block?

        int[][] twin = copyBlocks();
        swapBlocks(twin, randBlockI, randBlockJ, randAdjacentBlock[0], randAdjacentBlock[1]);

        return new Board(twin);
    }

    /**
     * Does this board equal y?
     *
     * @param y object to equate.
     * @return true if this is equal to y, otherwise false.
     */
    public boolean equals(Object y) {
        if (y == this) { return true; }
        if (y == null) { return false; }
        if (y.getClass() != this.getClass()) { return false; }

        Board other = (Board) y;
        if (this.dimension() != other.dimension()) { return false; }

        // Go through each block to check for equality.
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Find all neighboring boards. Neighboring boards are all boards one move away from this board.
     *
     * @return iterable of neighboring boards.
     */
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        // Find the blank block.
        int blankBlockI = 0;
        int blankBlockJ = 0;
        outer:
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    blankBlockI = i;
                    blankBlockJ = j;
                    break outer;
                }
            }
        }

        // Find all neighboring boards by doing all possible block swaps with the blank block.
        int[] neighboringBlockToBlank;
        for (Direction direction: Direction.values()) {
            neighboringBlockToBlank = Direction.getAdjacentBlockIndex(blankBlockI, blankBlockJ, direction);
            if (inBounds(neighboringBlockToBlank[0], neighboringBlockToBlank[1])) {
                int[][] neighbor = copyBlocks();
                swapBlocks(neighbor, blankBlockI, blankBlockJ, neighboringBlockToBlank[0], neighboringBlockToBlank[1]);
                neighbors.push(new Board(neighbor));
            }
        }
        return neighbors;
    }

    /**
     * String representation of this board.
     *
     * @return string representation of this board.
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dimension()).append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                stringBuilder.append(String.format("%2d ", blocks[i][j]));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Enumeration to help find and deal with adjacent blocks.
     */
    private enum Direction {
        LEFT, RIGHT, UP, DOWN;

        /**
         * Convert direction code to an enum direction value.
         *
         * @param dirCode integer representing a particular direction.
         * @return enum direction value.
         */
        private static Direction getDirection(int dirCode) {
            switch (dirCode) {
                case 0:
                    return LEFT;
                case 1:
                    return RIGHT;
                case 2:
                    return UP;
                case 3:
                    return DOWN;
                default:
                    throw new IllegalArgumentException("Direction code needs to be in the set {0, 1, 2, 3}.");
            }
        }

        /**
         * Find the adjacent block index for the given block index/direction.
         *
         * @param i block's row index.
         * @param j block's column index.
         * @param direction to chosen adjacent block.
         * @return adjacent block's index.
         */
        private static int[] getAdjacentBlockIndex(int i, int j, Direction direction) {
            int neighborI;
            int neighborJ;
            switch (direction) {
                case LEFT:
                    neighborI = i;
                    neighborJ = j - 1;
                    break;
                case RIGHT:
                    neighborI = i;
                    neighborJ = j + 1;
                    break;
                case UP:
                    neighborI = i - 1;
                    neighborJ = j;
                    break;
                case DOWN:
                    neighborI = i + 1;
                    neighborJ = j;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid direction specified.");
            }

            return new int[] {neighborI, neighborJ};
        }
    }

    /**
     * Does that block have a null row?
     *
     * @param blocks array to check.
     * @return true if there is a null row, false otherwise.
     */
    private boolean hasNullRow(int[][] blocks) {
        for (int[] row: blocks) {
            if (row == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is the index (row,col)=(i,j) inside the board?
     *
     * @param i row index.
     * @param j column index.
     * @return true if inside the board, false otherwise.
     */
    private boolean inBounds(int i, int j) {
        return (i >= 0 && i < dimension() && j >= 0 && j < dimension());
    }

    /**
     * Make a copy of the blocks array representing the board and return it.
     *
     * @return copy of board's blocks array.
     */
    private int[][] copyBlocks() {
        int[][] copy = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
                copy[i] = blocks[i].clone();
        }
        return copy;
    }

    /**
     * Swap a pair of blocks around in 2d "blocks" array.
     *
     * @param blocks representing a board.
     * @param i row index of first block.
     * @param j column index of first block.
     * @param otherI row index of second block.
     * @param otherJ column index of second block.
     */
    private void swapBlocks(int[][] blocks, int i, int j, int otherI, int otherJ) {
        int otherBlock = blocks[otherI][otherJ];
        blocks[otherI][otherJ] = blocks[i][j];
        blocks[i][j] = otherBlock;
    }

    /**
     * Determine the correct goal block for row i and column j in the board.
     *
     * @param i row index.
     * @param j column index.
     * @return goal block.
     */
    private int goalBlock(int i, int j) {
        if ((i == (dimension() - 1)) && (j == (dimension() - 1))) {  // Bottom-right block
            return 0;  // Blank block
        } else {  // All other blocks
            return dimension() * i + (j + 1);  // Non-blank block
        }
    }

    /**
     * Find goal index for a block. Or, in other words, where the block should be in the goal board.
     *
     * @param block to find goal index for.
     * @return goal index of the block.
     */
    private int[] goalIndex(int block) {
        int i;
        int j;
        if (block == 0) {
            i = dimension() - 1;
            j = dimension() - 1;
        } else {
            j = (block - 1) % dimension();
            i = (block - (j + 1)) / dimension();
        }
        return new int[] {i, j};
    }
}
