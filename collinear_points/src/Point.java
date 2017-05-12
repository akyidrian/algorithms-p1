/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * Note, it is okay to compare slopes (floating point numbers) since Points
     * are defined at integer x, y coordinates only.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        int delX = (that.x - x);
        int delY = (that.y - y);
        double slope;

        if (delY == 0 && delX == 0) {  // Degenerate slope
            slope = Double.NEGATIVE_INFINITY;
        }
        else if (delX == 0) {  // Vertical slope (delY != 0)
            slope = Double.POSITIVE_INFINITY;
        }
        else if (delY == 0) {  // Horizontal slope (delX != 0)
            // double type has two representations of 0 (-0 and +0). We need only one.
            slope = +0;
        }
        else {  // Uniquely calculable slope (delY != 0 && delX != 0)
            slope = (double) delY / (double) delX;
        }

        return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (y < that.y) {
            return -1;
        }
        else if (y > that.y) {
            return +1;
        }
        else if (x < that.x) {  // y0==y1
            return -1;
        }
        else if (x > that.x) {  // y0==y1
            return +1;
        }
        return 0;  // y0==y1 and x0==x1
    }

    /**
     * Contains method to help compare two points by the slope they make with this point.
     */
    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);

            // Comparing floating point numbers here is okay since Points are defined at integer x, y coordinates only.
            if (s1 < s2) {
                return -1;
            }
            else if (s1 > s2) {
                return +1;
            }
            return 0;  // s1 == s2
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}
