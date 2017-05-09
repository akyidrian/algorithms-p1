import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by Aydin on 29/04/2017.
 */
public class CollinearPointsTestHelper {
    static public void assertNLineSegments(CollinearPoints collinearPoints, int n) {
        assertTrue(collinearPoints.numberOfSegments() == n);
        assertTrue(collinearPoints.segments().length == n);
    }

    static public void assertCorrectLineSegments(CollinearPoints collinearPoints, String[][] correctLineSegments) {
        assertNLineSegments(collinearPoints, correctLineSegments.length);

        LineSegment[] lineSegments = collinearPoints.segments();
        boolean[] checkedOff = new boolean[correctLineSegments.length];
        for (int i = 0; i < lineSegments.length; i++) {
            String lineSegment[] = lineSegments[i].toString().split(" -> ");
            for (int j = 0; i < correctLineSegments.length; j++) {
                String[] cls = correctLineSegments[j];
                String[] rcls = {cls[1], cls[0]};  // reverse order

                // has to be one or the other ordering
                boolean isCorrectLineSegment =
                        Arrays.equals(lineSegment, cls) || Arrays.equals(lineSegment, rcls);

                if (isCorrectLineSegment && !checkedOff[j]) {
                    checkedOff[j] = true;  // check it off
                    break;
                } else if (isCorrectLineSegment && checkedOff[j]) {  // Need to check this in-case there is two of the same line segments present
                    fail("Duplicate line segment detected: " + "("  + lineSegment[0] + " -> " + lineSegment[1] + ")");
                }
            }
        }
        assertArrayEquals(checkedOff, new boolean[]{true, true, true});  // All checked off?
    }
}
