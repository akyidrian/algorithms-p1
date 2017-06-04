import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

/**
 * A mutable data type that utilises a red-black BST to represent a set of points inside a unit square. The unit square
 * has vertices at (0,0), (0,1), (1,0) and (1,1). Points can lie on the perimeter of the unit square.
 *
 * This API supports nearest() and range() operations in time proportional to the number of points in the set.
 *
 * @author Aydin
 */
public class PointSET {

    // An integer quantity greater than any possible distance squared value between two points in a unit square.
    private static final double IMPOSSIBLE_DISTANCE_SQUARED = 3.0;

    // The set of all points inside the unit square.
    private final SET<Point2D> points;

    /**
     * Setup PointSET object.
     */
    public PointSET() {
        points = new SET<>();
    }

    /**
     * Is there zero points in the set?
     *
     * @return true if zero points, false if there do exist points.
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Number of points in the set.
     *
     * @return total number of points.
     */
    public int size() {
        return points.size();
    }

    /**
     * Add the point p to the set (if it is not already in the set).
     *
     * @param p point to add.
     */
    public void insert(Point2D p) {
        if (p == null) { throw new NullPointerException(); }
        else if (!inBounds(p)) { throw new IllegalArgumentException("Point must be inside the unit square."); }
        points.add(p);
    }

    /**
     * Does the set have a point p?
     *
     * @param p point to check for in the set.
     * @return true if p is in the set, false otherwise.
     */
    public boolean contains(Point2D p) {
        if (p == null) { throw new NullPointerException(); }
        else if (!inBounds(p)) { throw new IllegalArgumentException("Point must be inside the unit square."); }
        return points.contains(p);
    }

    /**
     * Draw all points in the set to standard draw.
     */
    public void draw() {
        for (Point2D p: points) {
            p.draw();
        }
    }

    /**
     * Find all points contained inside the query rectangle. Query rectangle can be outside the unit square.
     *
     * @param rect query rectangle.
     * @return iterable of points inside the query rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new NullPointerException(); }

        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        for (Point2D p: points) {
            if (rect.contains(p)) {
                pointsInRange.add(p);
            }
        }
        return pointsInRange;
    }

    /**
     * Find the closest point to the query point p. Query point p can be outside the unit square.
     * Null is returned if the set is empty.
     *
     * @param p query point.
     * @return closest point to query point p.
     */
    public Point2D nearest(Point2D p) {
        if (p == null) { throw new NullPointerException(); }
        if (isEmpty()) { return null; }

        Point2D nearest = null;
        double distanceSquaredToNearest = IMPOSSIBLE_DISTANCE_SQUARED;
        for (Point2D point: points) {
            double distanceSquared = p.distanceSquaredTo(point);
            if (distanceSquared < distanceSquaredToNearest) {
                nearest = point;
                distanceSquaredToNearest = distanceSquared;
            }
        }
        return nearest;
    }

    /**
     * Is point p inside the unit square defined with vertices at (0,0), (0,1), (1,0) and (1,1)? Point p can be on the
     * perimeter of the unit square.
     *
     * @param p point to check.
     * @return true if inside unit square, false otherwise.
     */
    private boolean inBounds(Point2D p) {
        return ((p.x() >= 0.0) && (p.x() <= 1.0) && (p.y() >= 0.0) && (p.y() <= 1.0));
    }
}
