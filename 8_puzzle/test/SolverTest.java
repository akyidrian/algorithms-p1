import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Aydin on 13/05/2017.
 */
public class SolverTest {

    /**
     * Checking Solver with minimal usage of Board.
     */
    @Test
    void unsolvableBoardsCheck() {
        int[][] threeByThreeBlocks = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        int[][] fourByFourBlocks = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 15, 14, 0}};

        Solver solver = new Solver(new Board(threeByThreeBlocks));
        assertFalse(solver.isSolvable());
        assertEquals(solver.moves(), -1);
        assertNull(solver.solution());

        solver = new Solver(new Board(fourByFourBlocks));
        assertFalse(solver.isSolvable());
        assertEquals(solver.moves(), -1);
        assertNull(solver.solution());
    }

    @Test
    void solvableThreeByThreeBoardCheck() {
        int[][] initialBlocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] goalBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][][] correctBlockSteps = {
                initialBlocks,
                {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}},
                {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}},
                goalBlocks
        };

        Board[] correctBoardSteps = new Board[correctBlockSteps.length];
        for (int i = 0; i < correctBlockSteps.length; i++) {
            correctBoardSteps[i] = new Board(correctBlockSteps[i]);
        }

        Solver solver = new Solver(new Board(initialBlocks));
        assertTrue(solver.isSolvable());
        assertEquals(solver.moves(), 4);

        Iterable<Board> foundBoardStepsIterable = solver.solution();
        assertNotNull(foundBoardStepsIterable);

        Iterator<Board> foundBoardStepsIter = foundBoardStepsIterable.iterator();
        for(Board correct: correctBoardSteps) {
            assertTrue(foundBoardStepsIter.hasNext());

            String correctStr = correct.toString();
            String foundStr = foundBoardStepsIter.next().toString();

            assertTrue(correctStr.equals(foundStr));
        }
        assertFalse(foundBoardStepsIter.hasNext());  // No more boards
    }

    @Test
    void constructorNullArgsException() {
        assertThrows(NullPointerException.class, ()->{
           new Solver(null);
        });
    }
}
