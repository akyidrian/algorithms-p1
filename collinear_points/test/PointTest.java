import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author Aydin
 */
public class PointTest {
    private final int X = 1000;
    private final int Y = 1000;
    private final Point POINT = new Point(X, Y);
    private final int LOWER_BOUND = 0;      //TODO: Inclusive?
    private final int UPPER_BOUND = 32767;  //TODO: Inclusive?

    @Test
    void compareToTest() {
        assertEquals(POINT.compareTo(new Point(X, Y + 1)), -1);  // point above
        assertEquals(POINT.compareTo(new Point(X, Y - 1)), +1);  // point below
        assertEquals(POINT.compareTo(new Point(X, Y)), 0);  // point equal
        assertEquals(POINT.compareTo(new Point(X + 1, Y)), -1);  // point to the right
        assertEquals(POINT.compareTo(new Point(X - 1, Y)), +1);  // point to the left
    }

    @Test
    void slopeToTest() {
        assertEquals(POINT.slopeTo(new Point(X + 1, Y + 1)), 1);
        assertEquals(POINT.slopeTo(new Point(X - 1, Y - 1)), 1);
        assertEquals(POINT.slopeTo(new Point(X - 1, Y + 1)), -1);
        assertEquals(POINT.slopeTo(new Point(X + 1, Y - 1)), -1);

        assertEquals(POINT.slopeTo(new Point(X + 1, Y)), +0);  // horizontal line segment
        assertEquals(POINT.slopeTo(new Point(X, Y + 1)), Double.POSITIVE_INFINITY);  // vertical line segment
        assertEquals(POINT.slopeTo(new Point(X, Y)), Double.NEGATIVE_INFINITY);  // degenerate line segment
    }

    @Test
    void slopeOrderTest() {
        Comparator<Point> pointComparator = POINT.slopeOrder();
        int compareResult = 0;

        compareResult = pointComparator.compare(new Point(X, Y), new Point(X, Y));
        assertTrue(compareResult == 0);

        compareResult = pointComparator.compare(new Point(X, Y), new Point(X, Y + 1));
        assertTrue(compareResult < 0);

        compareResult = pointComparator.compare(new Point(X + 1, Y), new Point(X, Y));
        assertTrue(compareResult > 0);
    }

    @Disabled
    @Test
    void intCoordBoundsAssumption() {
        assertThrows(IllegalArgumentException.class, ()->{
            new Point(LOWER_BOUND - 1, LOWER_BOUND);
            new Point(LOWER_BOUND, LOWER_BOUND - 1);
            new Point(LOWER_BOUND - 1, LOWER_BOUND - 1);
            new Point(UPPER_BOUND, UPPER_BOUND + 1);
            new Point(UPPER_BOUND + 1, UPPER_BOUND);
            new Point(UPPER_BOUND + 1, UPPER_BOUND + 1);
        });
    }
}
