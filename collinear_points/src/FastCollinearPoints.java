import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Aydin
 */
//public class FastCollinearPoints implements CollinearPoints {
public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null || hasNullPoint(points)) {
            throw new NullPointerException("Null constructor array argument or null points provided.");
        }

        if (hasRepeatedPoints(points)) {
            throw new IllegalArgumentException("Repeated points contained in constructor array argument.");
        }

        ArrayList<LineSegment> tempLineSegments = new ArrayList<>();
        Point[] pointsCopy = points.clone();
        Point[] others = pointsCopy.clone();
        for (Point origin: pointsCopy) {
            Arrays.sort(others);
            Arrays.sort(others, origin.slopeOrder());  // Lowest element in other is the origin point/degenerate point.

            int start = 1;
            int end = start;
            Comparator<Point> slopeOrder = origin.slopeOrder();
            for (int i = start; i < others.length; i++) {
                boolean slopesEqual = (slopeOrder.compare(others[i - 1], others[i]) == 0);
                boolean originIsStart = (origin.compareTo(others[start]) < 0);
                if (slopesEqual) {
                    end++;
                    if ((i == (others.length - 1)) && ((end - start) >= 2) && originIsStart) {
                        tempLineSegments.add(new LineSegment(origin, others[others.length - 1]));
                    }
                } else { // Different slopes
                    if (((end - start) >= 2) && originIsStart) {
                        tempLineSegments.add(new LineSegment(origin, others[i - 1]));
                    }
                    start = i;
                    end = i;
                }
            }
        }
        this.lineSegments = tempLineSegments.toArray(new LineSegment[tempLineSegments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    private boolean hasNullPoint(Point[] points) {
        for (Point point: points) {
            if (point == null) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRepeatedPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                // Same reference or same point in terms of (x, y) location?
                if (points[i] == points[j] || points[i].compareTo(points[j]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
