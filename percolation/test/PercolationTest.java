import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test client used during the initial development of the Percolation class.
 */
class PercolationTest {
    private Percolation p;
    private static final int n = 3;  // Height/width of grid.

    @BeforeEach
    void setUpModel() {
        p = new Percolation(n);
    }

    @Test
    void checkCorrectInitialisation() {
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= n; i++) {
                assertFalse(p.isOpen(i, j));
                assertFalse(p.isFull(i, j));
            }
        }
        assertEquals(0, p.numberOfOpenSites());
        assertFalse(p.percolates());
    }

    @Test
    void openCentreSite() {
        int row = 2;
        int col = 2;

        p.open(row, col);
        assertTrue(p.isOpen(row, col));
        assertFalse(p.isFull(row, col));
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= n; i++) {
                if (i != row && j != col) {
                    assertFalse(p.isOpen(i, j));
                    assertFalse(p.isFull(i, j));
                }
            }
        }
        assertEquals(1, p.numberOfOpenSites());
        assertFalse(p.percolates());
    }

    @Test
    void isFull() {
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 3);
        p.open(3, 3);
        assertTrue(p.isFull(2, 1));
        assertFalse(p.isFull(2, 2));
        assertFalse(p.isFull(3, 2));
    }

    @Test
    void percolates() {
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 3);
        p.open(3, 3);

        assertFalse(p.percolates());
        p.open(2, 2);
        assertTrue(p.percolates());
    }
}