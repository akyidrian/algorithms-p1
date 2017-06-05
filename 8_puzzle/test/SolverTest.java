import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test client for the Solver class. Check for correctness and proper usage of exceptions.
 */
public class SolverTest {

    /**
     * Checks Solver behaves as expected when using an example three-by-three unsolvable board.
     */
    @Test
    void unsolvableThreeByThreeBoardCheck() {
        int[][] threeByThreeBlocks = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        Solver solver = new Solver(new Board(threeByThreeBlocks));
        assertFalse(solver.isSolvable());
        assertEquals(solver.moves(), -1);
        assertNull(solver.solution());
    }

    /**
     * Checks Solver behaves as expected when using an example four-by-four unsolvable board.
     *
     * Note, this test is currently disabled since it can take a reasonable amount of time to complete.
     */
    @Disabled()
    @Test
    void unsolvableFourByFourBoardCheck() {
        int[][] fourByFourBlocks = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 15, 14, 0}};
        Solver solver = new Solver(new Board(fourByFourBlocks));
        assertFalse(solver.isSolvable());
        assertEquals(solver.moves(), -1);
        assertNull(solver.solution());
    }

    /**
     * Checks Solver behaves as expected when using an example three-by-three solvable board.
     */
    @Test
    void solvableThreeByThreeBoardCheck() {
        int[][] initialBlocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] goalBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][][] completeBlocksSeqToGoal = {
                initialBlocks,
                {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}},
                goalBlocks
        };

        // Create boards for the blocks in the sequence.
        Board[] completeBoardSeqToGoal = new Board[completeBlocksSeqToGoal.length];
        for (int i = 0; i < completeBlocksSeqToGoal.length; i++) {
            completeBoardSeqToGoal[i] = new Board(completeBlocksSeqToGoal[i]);
        }

        Solver solver = new Solver(new Board(initialBlocks));
        assertTrue(solver.isSolvable());
        assertEquals(solver.moves(), 4);

        Iterable<Board> foundBoardStepsIterable = solver.solution();
        assertNotNull(foundBoardStepsIterable);

        // Checks correctness of Solver for each board in the sequence.
        Iterator<Board> foundBoardStepsIter = foundBoardStepsIterable.iterator();
        for(Board correct: completeBoardSeqToGoal) {
            assertTrue(foundBoardStepsIter.hasNext());

            String correctStr = correct.toString();
            String foundStr = foundBoardStepsIter.next().toString();

            assertTrue(correctStr.equals(foundStr));
        }
        assertFalse(foundBoardStepsIter.hasNext());  // There should be no more boards.
    }

    /**
     * Make sure the constructor doesn't accept null references.
     */
    @Test
    void constructorNullArgsException() {
        assertThrows(NullPointerException.class, ()->{
           new Solver(null);
        });
    }
}
