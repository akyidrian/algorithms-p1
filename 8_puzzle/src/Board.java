import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

/**
 * Created by Aydin on 13/05/2017.
 */
public class Board {
    private final int[][] blocks;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    // You may assume that the constructor receives an n-by-n array containing the n2 integers between 0 and n2 − 1, where 0 represents the blank square.
    public Board(int[][] blocks) {  // TODO: Check for null?
        this.blocks = new int[blocks.length][blocks.length];
        for(int i = 0; i < blocks.length; i++) {
            this.blocks[i] = blocks[i].clone();
        }
    }

    private enum Direction {
        LEFT, RIGHT, UP, DOWN;

        public static Direction getDirection(int code) {
            if (code == 0) {
                return LEFT;
            } else if (code == 1) {
                return RIGHT;
            } else if (code == 2) {
                return UP;
            } else if (code == 3) {
                return DOWN;
            } else {
                throw new IllegalArgumentException("Code needs to be in {0, 1, 2, 3}");
            }
        }
    }

    private boolean inBounds(int i, int j) {
        int dim = dimension();
        return (i >= 0 && i < dim && j >= 0 && j < dim);
    }

    private int[] getNeighbor(int i, int j, Direction dir) {
        int neighborI;
        int neighborJ;
        switch (dir) {
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
            default:  // Down
                neighborI = i + 1;
                neighborJ = j;
                break;
        }

        return new int[] {neighborI, neighborJ};
    }

    private int[][] copyBlocks() {
        int dim = dimension();
        int[][] copy = new int[dim][dim];
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }

    private void swapBlocks(int[][] blocks, int i, int j, int otherI, int otherJ) {
        int block = blocks[i][j];
        int otherBlock = blocks[otherI][otherJ];
        blocks[i][j] = otherBlock;
        blocks[otherI][otherJ] = block;
    }

    private int goalBlock(int i, int j) {
        int dim = dimension();
        int  goalBlock = dim * i + (j + 1);

        if ((i == (dim - 1)) && (j == (dim - 1))) {
            goalBlock = 0;
        }
        return goalBlock;
    }

    private int[] goalBlockIJ(int number) {
        int dim = dimension();
        int goalJ = (number - 1) % dim;
        int goalI = (number - (goalJ + 1)) / dim;

        if (number == 0) {
            goalI = dim - 1;
            goalJ = dim - 1;
        }

        return new int[] {goalI, goalJ};
    }


    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int dim = dimension();
        int value = 0;
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                int correctBlockNumber = goalBlock(i, j);

                if (blocks[i][j] != 0 && blocks[i][j] != correctBlockNumber) {
                    value++;
                }
            }
        }
        return value;  // Ignore empty block.
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int dim = dimension();
        int value = 0;
        for(int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int[] goalBlock = goalBlockIJ(blocks[i][j]);

                if (blocks[i][j] != 0) {
                    value += Math.abs(goalBlock[0] - i) + Math.abs(goalBlock[1] - j);
                }
            }
        }
        return value;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int dim = dimension();
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                int correctBlockNumber = goalBlock(i, j);

                if (blocks[i][j] != correctBlockNumber) {
                    return false;
                }
            }
        }
        return true;
    }

    // TODO: may be able to optimize by choosing swap that reduces manhattan distance.
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int dim = dimension();
        int[][] twin = copyBlocks();

        int randI;
        int randJ;
        int[] neighbor;
        do {
            randI = StdRandom.uniform(dim);
            randJ = StdRandom.uniform(dim);
            Direction dir = Direction.getDirection(StdRandom.uniform(4));
            neighbor = getNeighbor(randI, randJ, dir);
        } while (!inBounds(neighbor[0], neighbor[1]) || (blocks[randI][randJ] == 0) || (blocks[neighbor[0]][neighbor[1]] == 0));

        swapBlocks(twin, randI, randJ, neighbor[0], neighbor[1]);

        return new Board(twin);
    }

    // TODO BLOCK CAST
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (this == y) {  // Same reference?
            return true;
        }

        if (y instanceof String) {
            String other = (String) y;
            return (toString().compareTo(other) == 0);
        }
        else if (y instanceof Board) {
            int dim = dimension();
            Board other = (Board) y;
            if (dim != other.dimension()) {
                return false;
            }

            for(int i = 0; i < dim; i++) {
                for(int j = 0; j < dim; j++) {
                    if (blocks[i][j] != other.blocks[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    private class BoardNeighbors implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new BoardNeighborsIterator();
        }

        private class BoardNeighborsIterator implements Iterator<Board> {

            final Board[] neighbors;
            int current;

            public BoardNeighborsIterator() {
                int dim = dimension();
                int blankBlockI = 0;
                int blankBlockJ = 0;
                for(int i = 0; i < dim; i++) {
                    for(int j = 0; j < dim; j++) {
                        if(blocks[i][j] == 0) {
                            blankBlockI = i;
                            blankBlockJ = j;
                            break;  //TODO
                        }
                    }
                }

                ArrayList<Board> neighbors = new ArrayList<>();
                int[] neighborToBlank;
                for (Direction dir: Direction.values()) {
                    neighborToBlank = getNeighbor(blankBlockI, blankBlockJ, dir);
                    if (inBounds(neighborToBlank[0], neighborToBlank[1])) {
                        int[][] neighbor = copyBlocks();
                        swapBlocks(neighbor, blankBlockI, blankBlockJ, neighborToBlank[0], neighborToBlank[1]);
                        neighbors.add(new Board(neighbor));
                    }
                }
                this.neighbors = neighbors.toArray(new Board[neighbors.size()]);
            }

            @Override
            public boolean hasNext() {
                return current < neighbors.length;
            }

            @Override
            public Board next() {
                if (!hasNext()) { // TODO TEST
                    throw new NoSuchElementException("No more neighbors to iterate through.");
                }

                Board neighbor = neighbors[current];
                current++; //TODO
                return neighbor;
            }

            @Override
            public void remove() {  // TODO Test
                throw new UnsupportedOperationException();
            }
        }
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new BoardNeighbors();
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        int dim = dimension();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dim).append("\n");
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                stringBuilder.append(String.format("%2d ", blocks[i][j]));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
