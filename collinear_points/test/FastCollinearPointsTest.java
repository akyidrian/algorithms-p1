import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Aydin
 */
public class FastCollinearPointsTest {
    Point[] points;

    @Nested
    class LineSegmentsCheck {
        @Test
        void insufficientPointsInSubsetTest() {
            points = new Point[4];
            points[0] = new Point(1, 1);
            points[1] = new Point(2, 1);
            points[2] = new Point(3, 1);
            points[3] = new Point(3, 2);

            FastCollinearPoints fcp = new FastCollinearPoints(points);
            CollinearPointsTestHelper.assertNLineSegments(fcp, 0);
        }

        /**
         * Checks for correctness, including duplicates
         */
        @Test
        void simpleLineSegmentTest() {
            points = new Point[4];
            points[0] = new Point(1, 1);
            points[1] = new Point(2, 1);
            points[2] = new Point(3, 1);
            points[3] = new Point(4, 1);
            String[][] correctLineSegments = {{"(1, 1)", "(2, 1)"}, {"(2, 1)", "(3, 1)"}, {"(3, 1)", "(4, 1)"}};

            FastCollinearPoints fcp = new FastCollinearPoints(points);
            CollinearPointsTestHelper.assertCorrectLineSegments(fcp, correctLineSegments);
        }
    }

    @Test
    void constructorNullArgsException() {
        points = null;
        assertThrows(NullPointerException.class, ()->{
            new BruteCollinearPoints(points);
        });

        points = new Point[1];
        points[0] = null;
        assertThrows(NullPointerException.class, ()->{
            new BruteCollinearPoints(points);
        });
    }

    @Test
    void constructorRepeatedPointsException() {
        points = new Point[2];
        points[0] = new Point(1000, 1000);
        points[1] = new Point(1000, 1000);

        assertThrows(IllegalArgumentException.class, ()->{
            new BruteCollinearPoints(points);
        });
    }
}
