import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Simple test client for the Point class.
 *
 * @author Aydin
 */
public class PointTest {

    // Arbitrarily chosen point in bounds.
    private static final int X = 1000;
    private static final int Y = 1000;
    private static final Point POINT = new Point(X, Y);

    private static final int LOWER_BOUND = 0;      // Inclusive.
    private static final int UPPER_BOUND = 32767;  // Inclusive.

    /**
     * Checking compareTo() method.
     */
    @Test
    void compareToTest() {
        assertTrue(POINT.compareTo(new Point(X, Y)) == 0);        // new point equal
        assertTrue(POINT.compareTo(new Point(X + 1, Y)) < 0);  // new point to the right
        assertTrue(POINT.compareTo(new Point(X - 1, Y)) > 0);  // new point to the left
        assertTrue(POINT.compareTo(new Point(X, Y + 1)) < 0);  // new point above
        assertTrue(POINT.compareTo(new Point(X, Y - 1)) > 0);  // new point below

        assertTrue(POINT.compareTo(new Point(X + 1, Y + 1)) < 0);  // new point to the North-East
        assertTrue(POINT.compareTo(new Point(X - 1, Y - 1)) > 0);  // new point to the South-West
        assertTrue(POINT.compareTo(new Point(X - 1, Y + 1)) < 0);  // new point to the North-West
        assertTrue(POINT.compareTo(new Point(X + 1, Y - 1)) > 0);  // new point to the South-East
    }

    /**
     * Checking slopeTo() method.
     */
    @Test
    void slopeToTest() {
        assertEquals(POINT.slopeTo(new Point(X, Y)), Double.NEGATIVE_INFINITY);  // Degenerate line
        assertEquals(POINT.slopeTo(new Point(X + 1, Y)), +0);  // Horizontal line
        assertEquals(POINT.slopeTo(new Point(X - 1, Y)), +0);  // Horizontal line
        assertEquals(POINT.slopeTo(new Point(X, Y + 1)), Double.POSITIVE_INFINITY);  // Vertical line
        assertEquals(POINT.slopeTo(new Point(X, Y - 1)), Double.POSITIVE_INFINITY);  // Vertical line

        assertEquals(POINT.slopeTo(new Point(X + 1, Y + 1)), 1);   // North-East line
        assertEquals(POINT.slopeTo(new Point(X - 1, Y - 1)), 1);   // South-West line
        assertEquals(POINT.slopeTo(new Point(X - 1, Y + 1)), -1);  // North-West line
        assertEquals(POINT.slopeTo(new Point(X + 1, Y - 1)), -1);  // South-East line
    }

    /**
     * Checking compare() method inside Comparator.
     */
    @Test
    void slopeOrderTest() {
        Comparator<Point> pointComparator = POINT.slopeOrder();
        int compareResult;

        compareResult = pointComparator.compare(new Point(X, Y), new Point(X, Y));
        assertTrue(compareResult == 0);

        compareResult = pointComparator.compare(new Point(X, Y), new Point(X, Y + 1));
        assertTrue(compareResult < 0);

        compareResult = pointComparator.compare(new Point(X, Y), new Point(X, Y - 1));
        assertTrue(compareResult < 0);

        compareResult = pointComparator.compare(new Point(X + 1, Y), new Point(X, Y));
        assertTrue(compareResult > 0);

        compareResult = pointComparator.compare(new Point(X - 1, Y), new Point(X, Y));
        assertTrue(compareResult > 0);
    }

    /**
     * Currently disabled. An assumption which is not considered too important.
     */
    @Disabled
    @Test
    void coordBoundsAssumption() {
        assertThrows(IllegalArgumentException.class, ()->{
            new Point(LOWER_BOUND - 1, LOWER_BOUND);  // point left of the lower bound
            new Point(LOWER_BOUND, LOWER_BOUND - 1);  // point below the lower bound
            new Point(LOWER_BOUND - 1, LOWER_BOUND - 1);  // point South-West of lower bound
            new Point(UPPER_BOUND, UPPER_BOUND + 1);  // point above the upper bound
            new Point(UPPER_BOUND + 1, UPPER_BOUND);  // point right of the upper bound
            new Point(UPPER_BOUND + 1, UPPER_BOUND + 1);  // point North-East of upper bound
        });
    }
}
