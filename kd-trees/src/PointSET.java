import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

/**
 * Created by Aydin on 27/05/2017.
 */
public class PointSET {
    private static final double IMPOSSIBLE_DISTANCE_SQUARED = 3.0;
    private final SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p: points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }

        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        for (Point2D p: points) {
            if (rect.contains(p)) {
                pointsInRange.add(p);
            }
        }
        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        Point2D closest = null;  // Don't need to do isEmpty check because of this with for loop.
        double distanceSquaredToClosest = IMPOSSIBLE_DISTANCE_SQUARED;
        for (Point2D point: points) {
            double distanceSquared = p.distanceSquaredTo(point);
            if (distanceSquared < distanceSquaredToClosest) {
                closest = point;
                distanceSquaredToClosest = distanceSquared;
            }
        }
        return closest;
    }
}
