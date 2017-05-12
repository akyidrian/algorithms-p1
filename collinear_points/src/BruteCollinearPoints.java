import java.util.ArrayList;
import java.util.Arrays;

/**
 * A brute force method of examining 4 points at a time and checking whether they all lie on the same line segment. All
 * such line segments are stored/returned.
 *
 * @author Aydin
 */
public class BruteCollinearPoints {

    // Holds all line segments found
    private LineSegment[] lineSegments;

    /**
     * Instantiate by finding all line segments which are collinear with four points from a valid set of points.
     *
     * @param points array to find line segments in.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null || hasNullPoint(points)) {
            throw new NullPointerException("Null array or null points in array provided.");
        }

        if (hasRepeatedPoints(points)) {
            throw new IllegalArgumentException("Repeated points are contained in the array.");
        }

        lineSegments = findLineSegments(points.clone());  // cloning to prevent mutation of user input.
    }

    /**
     * Finds all line segments which are collinear with four points using brute force.
     *
     * @param points array to find line segments in.
     * @return array of all line segments found.
     */
    private LineSegment[] findLineSegments(Point[] points) {
        Arrays.sort(points);

        // To check 4 points p, q, r, and s are collinear, we check whether the three slopes between i and j,
        // between j and k, and between k and l are all equal.
        ArrayList<LineSegment> tempLineSegments = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double slopeIJ = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < points.length; k++) {
                    double slopeJK = points[j].slopeTo(points[k]);

                    if (slopeIJ != slopeJK) {  // A minor optimization.
                        continue;
                    }

                    for (int l = k + 1; l < points.length; l++) {
                        double slopeKL = points[k].slopeTo(points[l]);

                        // Already checked that slopeIJ == slopeJK through the minor optimization.
                        if (slopeJK == slopeKL) {
                            tempLineSegments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }

        return tempLineSegments.toArray(new LineSegment[tempLineSegments.size()]);
    }

    /**
     * Number of line segments that were found based on given points.
     *
     * @return number of line segments.
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * Line segments that were found based on given points.
     *
     * @return array of line segments found.
     */
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    /**
     * Checks for any null items in points array.
     * Useful for exception handling.
     *
     * @param points array to check.
     * @return true if null point found, false if not.
     */
    private boolean hasNullPoint(Point[] points) {
        for (Point point: points) {
            if (point == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check for repeated/duplicate points in points array.
     * Useful for exception handling.
     *
     * @param points array to check.
     * @return true if there are repeated points, false if not.
     */
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
