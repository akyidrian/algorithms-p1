import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test client for FastCollinearPoints class.
 * Checks for correctness and proper usage of exceptions.
 *
 * @author Aydin
 */
public class FastCollinearPointsTest {
    Point[] points;  // test points

    /**
     * Contains tests to ensure the operation of finding line segments from a set of points is functionally correct.
     */
    @Nested
    class LineSegmentsCheck {
        /**
         * If there are fewer than four collinear points then no line segment should be found.
         */
        @Test
        void fewerThanFourCollinearPointsTest() {
            points = new Point[4];
            points[0] = new Point(1, 1);
            points[1] = new Point(2, 1);
            points[2] = new Point(3, 1);
            points[3] = new Point(3, 2);
            String[][] correctLineSegments = {};  // No line segments should be found.

            assertCorrectLineSegments(new FastCollinearPoints(points), correctLineSegments);
        }

        /**
         * Do four collinear points return a correct line segment?
         */
        @Test
        void fourCollinearPointsTest() {
            points = new Point[4];
            points[0] = new Point(1, 1);
            points[1] = new Point(2, 1);
            points[2] = new Point(3, 1);
            points[3] = new Point(4, 1);
            String[][] correctLineSegments = {{"(1, 1)", "(4, 1)"}};

            assertCorrectLineSegments(new FastCollinearPoints(points), correctLineSegments);
        }

        /**
         * Do five collinear points return a correct line segment?
         */
        @Test
        void fiveCollinearPointsTest() {
            points = new Point[4];
            points[0] = new Point(1, 1);
            points[1] = new Point(2, 1);
            points[2] = new Point(3, 1);
            points[3] = new Point(4, 1);
            points[3] = new Point(5, 1);
            String[][] correctLineSegments = {{"(1, 1)", "(5, 1)"}};

            assertCorrectLineSegments(new FastCollinearPoints(points), correctLineSegments);
        }
    }

    /**
     * Make sure constructor doesn't accept null arrays or null items arrays.
     */
    @Test
    void constructorNullArgsException() {
        points = null;
        assertThrows(NullPointerException.class, ()->{
            new FastCollinearPoints(points);
        });

        points = new Point[1];
        points[0] = null;
        assertThrows(NullPointerException.class, ()->{
            new FastCollinearPoints(points);
        });
    }

    /**
     * Ensure constructor doesn't accept arrays that have repeated elements in them.
     */
    @Test
    void constructorRepeatedPointsException() {
        points = new Point[2];
        points[0] = new Point(1000, 1000);
        points[1] = new Point(1000, 1000);

        assertThrows(IllegalArgumentException.class, ()->{
            new FastCollinearPoints(points);
        });
    }

    /**
     * Helper method to assert the correctness in the number of line segments found.
     *
     * @param collinearPoints object to perform assertions on.
     * @param n number of line segments there should be.
     */
    static private void assertNLineSegments(FastCollinearPoints collinearPoints, int n) {
        assertTrue(collinearPoints.numberOfSegments() == n);
        assertTrue(collinearPoints.segments().length == n);
    }

    /**
     * Helper method to assert the correctness in the line segment finding operation.
     *
     * Specifically, makes sure the number of line segments found is correct, sub-segments are not found and that there
     * are no missing or repeated line segments. This method is also agnostic to the start and end points chosen for a
     * line segment.
     *
     * @param collinearPoints object to perform assertions on.
     * @param correctLineSegments the expected line segments to be found.
     */
    static private void assertCorrectLineSegments(FastCollinearPoints collinearPoints, String[][] correctLineSegments) {
        assertNLineSegments(collinearPoints, correctLineSegments.length);

        LineSegment[] foundLineSegments = collinearPoints.segments();
        boolean[] checkedOff = new boolean[correctLineSegments.length];

        // For each line segment found check it off against the list of correct line segments expected.
        for (int i = 0; i < foundLineSegments.length; i++) {
            String foundLineSegmentPoints[] = foundLineSegments[i].toString().split(" -> ");
            for (int j = 0; i < correctLineSegments.length; j++) {
                String[] cls = correctLineSegments[j];  // standard order as provided
                String[] rcls = {cls[1], cls[0]};  // reverse order to what is provided

                // The line segment has to be one or the other ordering, or neither.
                boolean isCorrectLineSegment =
                        Arrays.equals(foundLineSegmentPoints, cls) || Arrays.equals(foundLineSegmentPoints, rcls);

                if (isCorrectLineSegment) {  // Confirming a correct line segment has been found
                    if (!checkedOff[j]) {
                        checkedOff[j] = true;
                        break;
                    } else {
                        fail("Repeated correct line segment were found: " + foundLineSegments[i].toString());
                    }
                }
            }
        }

        // All correct line segments were found?
        for (boolean b: checkedOff) {
            assertTrue(b);
        }
    }
}
