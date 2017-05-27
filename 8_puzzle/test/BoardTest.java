import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Aydin on 13/05/2017.
 */
public class BoardTest {
    @Test
    void correctDimensions() {
        int[][] threeByThreeBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        assertEquals(new Board(threeByThreeBlocks).dimension(), 3);

        int[][] fourByFourBlocks = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        assertEquals(new Board(fourByFourBlocks).dimension(), 4);
    }

    @Test
    void isGoalCheck() {
        int[][] initialBlocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] goalBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][][] correctBlockStepsTowardsGoal = {
                initialBlocks,
                {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}}
        };

        Board board;
        for (int[][] blocks: correctBlockStepsTowardsGoal) {
            board = new Board(blocks);
            assertFalse(board.isGoal());
        }

        board = new Board(goalBlocks);
        assertTrue(board.isGoal());
    }

    @Test
    void correctPriorityResults() {
        int[][] initialBlocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] goalBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][][] correctBlockSteps = {
                initialBlocks,
                {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}},
                goalBlocks
        };
        int[] correctHamming = {4, 3, 2, 1, 0};  // Number of blocks in the wrong position.
        int[] correctManhattan = {4, 3, 2, 1, 0};

        for (int i = 0; i < correctBlockSteps.length; i++) {
            Board correct = new Board(correctBlockSteps[i]);

            assertEquals(correct.hamming(), correctHamming[i]);
            assertEquals(correct.manhattan(), correctManhattan[i]);
        }
    }

    // Assuming correct priority functions for both hamming and manhattan.
    @Test
    void correctTwinBoardGiven() {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        Board twinBoard = board.twin();

        assertEquals(Math.abs(board.hamming() - twinBoard.hamming()), 2);
        assertEquals(Math.abs(board.manhattan() - twinBoard.manhattan()), 2);

    }

    @Test
    void areBoardsEquals() {
        int[][] blocks1 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] blocks2 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] blocks3 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};

        Board board1 = new Board(blocks1);
        Board board2 = new Board(blocks2);
        Board board3 = new Board(blocks3);

        assertTrue(board1.equals(board1));
        assertTrue(board1.equals(board2));
        assertFalse(board1.equals(board3));
    }

    //Assume boards equals() works.
    @Test
    void findAllNeighbourBoards() {
        int[][] blocks = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
        int[][][] correctNeighbourBlocks = {
                {{8, 1, 0}, {4, 2, 3}, {7, 6, 5}},
                {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}},
                {{8, 1, 3}, {4, 2, 5}, {7, 6, 0}}
        };

        Board board = new Board(blocks);
        Iterable<Board> foundBoardStepsIterable = board.neighbors();
        assertNotNull(foundBoardStepsIterable);

        Board[] correctNeighbourBoards = new Board[correctNeighbourBlocks.length];
        for (int i = 0; i < correctNeighbourBoards.length; i++) {
            correctNeighbourBoards[i] =  new Board(correctNeighbourBlocks[i]);
        }

        boolean[] checkedOff = new boolean[correctNeighbourBlocks.length];
        for(Board found: foundBoardStepsIterable) {
            for (int i = 0; i < correctNeighbourBoards.length; i++) {
                if (correctNeighbourBoards[i].equals(found)) {
                    if (!checkedOff[i]) {
                        checkedOff[i] = true;
                        break;  // There can't be any others.
                    }
                    else {
                        fail("Duplicate neighbours found!");
                    }
                }
            }
        }

        // All correct neighbouring blocks were found?
        for (boolean b: checkedOff) {
            assertTrue(b);
        }
    }

    @Test
    void equalsNullArgsException() {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        assertFalse(board.equals(null));
    }
}
