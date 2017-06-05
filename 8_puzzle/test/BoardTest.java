import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test client for the Board class that does checks for correctness and proper usage of exceptions.
 * We use the Solver class as inspiration for the tests conducted below.
 *
 *
 * @author Aydin
 */
public class BoardTest {

    /**
     * Does the dimension() method give the correct dimensions?
     */
    @Test
    void dimensionsCheck() {
        int[][] threeByThreeBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        assertEquals(new Board(threeByThreeBlocks).dimension(), 3);

        int[][] fourByFourBlocks = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        assertEquals(new Board(fourByFourBlocks).dimension(), 4);
    }

    /**
     * Does isGoal() method work as expected for a known sequence of boards?
     */
    @Test
    void isGoalCheck() {
        int[][] initialBlocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] goalBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][][] sequenceToGoal = {
                initialBlocks,
                {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}}
        };

        Board board;
        for (int[][] blocks: sequenceToGoal) {
            board = new Board(blocks);
            assertFalse(board.isGoal());
        }

        board = new Board(goalBlocks);
        assertTrue(board.isGoal());
    }

    /**
     * Confirm hamming() and manhattan() values are correct for a sequence of boards.
     */
    @Test
    void priorityResultsCheck() {
        int[][] initialBlocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] goalBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][][] completeSequenceToGoal = {
                initialBlocks,
                {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}},
                goalBlocks
        };
        int[] correctHamming = {4, 3, 2, 1, 0};
        int[] correctManhattan = {4, 3, 2, 1, 0};

        for (int i = 0; i < completeSequenceToGoal.length; i++) {
            Board correct = new Board(completeSequenceToGoal[i]);

            assertEquals(correct.hamming(), correctHamming[i]);
            assertEquals(correct.manhattan(), correctManhattan[i]);
        }
    }

    /**
     * Ensuring valid twin board given by twin() method. Assuming Hamming and Manhattan work as expected.
     */
    @Test
    void validTwinBoardReturned() {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        Board twinBoard = board.twin();

        assertEquals(Math.abs(board.hamming() - twinBoard.hamming()), 2);
        assertEquals(Math.abs(board.manhattan() - twinBoard.manhattan()), 2);

    }

    /**
     * Checking that equals() method works as expected by going through a few specific cases.
     */
    @Nested
    class equalsCheck {
        /**
         * Checking three cases where two boards can and can't be equal to each other.
         */
        @Test
        void usingAVariationOfBoards() {
            int[][] blocks1 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
            int[][] blocks2 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
            int[][] blocks3 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};

            Board board1 = new Board(blocks1);
            Board board2 = new Board(blocks2);
            Board board3 = new Board(blocks3);

            assertTrue(board1.equals(board1));  // Same reference.
            assertTrue(board1.equals(board2));  // Same board, but different reference.
            assertFalse(board1.equals(board3)); // Different boards.
        }

        /**
         * Checking unusual cases for when equals() method is used.
         */
        @Test
        void usingUnusualArgs() {
            int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
            Board board = new Board(blocks);
            assertFalse(board.equals(null));  // Comparing to a null object.
            assertFalse(board.equals(new Integer(0)));  // Comparing to a object with different type.
        }
    }

    /**
     * Checking that all neighboring boards are found for a particular board. We assume equals() is functioning
     * correctly.
     */
    @Test
    void findAllNeighboringBoards() {
        int[][] blocks = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
        int[][][] correctNeighboringBlocks = {
                {{8, 1, 0}, {4, 2, 3}, {7, 6, 5}},
                {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}},
                {{8, 1, 3}, {4, 2, 5}, {7, 6, 0}}
        };

        Board board = new Board(blocks);
        Iterable<Board> foundBoardStepsIterable = board.neighbors();
        assertNotNull(foundBoardStepsIterable);

        Board[] correctNeighboringBoards = new Board[correctNeighboringBlocks.length];
        for (int i = 0; i < correctNeighboringBoards.length; i++) {
            correctNeighboringBoards[i] =  new Board(correctNeighboringBlocks[i]);
        }

        boolean[] checkedOff = new boolean[correctNeighboringBlocks.length];
        for(Board found: foundBoardStepsIterable) {
            for (int i = 0; i < correctNeighboringBoards.length; i++) {
                if (correctNeighboringBoards[i].equals(found)) {
                    if (!checkedOff[i]) {
                        checkedOff[i] = true;
                        break;  // There can't be any others.
                    }
                    else {
                        fail("Duplicate neighbors found!");
                    }
                }
            }
        }

        // All correct neighboring blocks were found?
        for (boolean b: checkedOff) {
            assertTrue(b);
        }
    }

    /**
     * Make sure the constructor doesn't accept null arrays or arrays with null elements.
     */
    @Test
    void constructorNullArgsException() {
        assertThrows(NullPointerException.class, ()->{
            new Board(null);
        });

        int[][] blocks = {{1, 2, 3}, null, {7, 8, 0}};
        assertThrows(NullPointerException.class, ()->{
            new Board(blocks);
        });
    }
}
