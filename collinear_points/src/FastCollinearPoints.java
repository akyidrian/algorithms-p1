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
            int end = others.length - 1;
            int i = start;
            while (i <= end) {
                Comparator<Point> slopeOrder = origin.slopeOrder();
                boolean sameSlopes = (slopeOrder.compare(others[i - 1], others[i]) == 0);
                boolean lineSegment = ((i - start) >= 3) && (origin.compareTo(others[start]) < 0);
                if (sameSlopes) {
                    if ((i == end) && ((i - start) >= 2) && (origin.compareTo(others[start]) < 0)) { // 2 because we don't get the next iteration.
                        tempLineSegments.add(new LineSegment(origin, others[end]));
                    }
                } else { // Different slopes
                    if (lineSegment) {
                        tempLineSegments.add(new LineSegment(origin, others[i - 1]));
                    }

                    start = i;
                }
                i++;
            }
        }

        this.lineSegments = tempLineSegments.toArray(new LineSegment[tempLineSegments.size()]);
    }

    private int hello(Point origin, Point[] others, int i, double currSlope) {
        double slope = origin.slopeTo(others[i]);
        int j;
        if (currSlope == slope) {
            j = hello(origin, others, ++i, slope);
        }
        else {
            j = i;
        }
        return j;
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
