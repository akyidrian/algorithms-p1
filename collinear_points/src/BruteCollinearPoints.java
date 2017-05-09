import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Aydin
 */
//public class BruteCollinearPoints implements CollinearPoints {
public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null || hasNullPoint(points)) {
            throw new NullPointerException("Null constructor array argument or null points provided.");
        }

        if (hasRepeatedPoints(points)) {
            throw new IllegalArgumentException("Repeated points contained in constructor array argument.");
        }

        Point[] orderedPoints = points.clone();
        Arrays.sort(orderedPoints);

        ArrayList<LineSegment> tempLineSegments = new ArrayList<>();
        for (int i = 0; i < orderedPoints.length; i++) {
            for (int j = i + 1; j < orderedPoints.length; j++) {
                double slopeIJ = orderedPoints[i].slopeTo(orderedPoints[j]);

                for (int k = j + 1; k < orderedPoints.length; k++) {
                    double slopeJK = orderedPoints[j].slopeTo(orderedPoints[k]);

                    if (slopeIJ != slopeJK) {  // A minor optimization.
                        continue;
                    }

                    for (int l = k + 1; l < orderedPoints.length; l++) {
                        double slopeKL = orderedPoints[k].slopeTo(orderedPoints[l]);

                        if (slopeJK == slopeKL) {
                            tempLineSegments.add(new LineSegment(orderedPoints[i], orderedPoints[l]));
                        }
                    }
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
