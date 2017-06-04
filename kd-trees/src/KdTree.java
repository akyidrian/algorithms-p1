import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

/**
 * Created by Aydin on 27/05/2017.
 */
public class KdTree {
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        root = insert(null, root, p, 0);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return contains(root, p, 0);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return nearest(root, p, null, 0);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        ArrayList<Point2D> points = new ArrayList<>();
        range(root, rect, points);
        return points;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0);
    }

    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
        }

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }
    }

    private RectHV createRectForNewNode(Node prev, Point2D p, int depth) {
        int cmp;
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        if (prev != null) {
            if (depth % 2 == 0) {  // Current is vertical, parent is horizontal
                cmp = Double.compare(p.y(), prev.p.y());

                if (cmp < 0) {
                    rect = new RectHV(prev.rect.xmin(), prev.rect.ymin(), prev.rect.xmax(), prev.p.y());
                } else if (cmp > 0) {
                    rect = new RectHV(prev.rect.xmin(), prev.p.y(), prev.rect.xmax(), prev.rect.ymax());
                }
            } else {  // Horizontal
                cmp = Double.compare(p.x(), prev.p.x());

                if (cmp < 0) {  // Current is horizontal, parent is vertical
                    rect = new RectHV(prev.rect.xmin(), prev.rect.ymin(), prev.p.x(), prev.rect.ymax());
                } else if (cmp > 0) {
                    rect = new RectHV(prev.p.x(), prev.rect.ymin(), prev.rect.xmax(), prev.rect.ymax());
                }
            }
        }

        return rect;
    }

    private Node insert(Node prev, Node n, Point2D p, int depth) {
        if (n == null) {
            size++;
            return new Node(p, createRectForNewNode(prev, p, depth));
        }

        int cmpX = Double.compare(p.x(), n.p.x());
        int cmpY = Double.compare(p.y(), n.p.y());
        if ((cmpX == 0) && (cmpY == 0)) { return n; }

        int cmp;
        if (depth % 2 == 0) {  // Vertical
            cmp = cmpX;
        } else {  // Horizontal
            cmp = cmpY;
        }

        if (cmp < 0) {
            n.lb = insert(n, n.lb, p, depth + 1);
        } else {
            n.rt = insert(n, n.rt, p, depth + 1);
        }

        return n;
    }

    private boolean contains(Node n, Point2D p, int depth) {
        if (n == null) { return false; }

        int cmpX = Double.compare(p.x(), n.p.x());
        int cmpY = Double.compare(p.y(), n.p.y());
        if ((cmpX == 0) && (cmpY == 0)) { return true; }

        int cmp;
        if (depth % 2 == 0) {  // Vertical
            cmp = cmpX;
        } else {  // Horizontal
            cmp = cmpY;
        }

        if (cmp < 0) {
            return contains(n.lb, p, depth + 1);
        } else {
            return contains(n.rt, p, depth + 1);
        }
    }

    // Post Order traversal?
    private void draw(Node n, int depth) {
        if (n == null) { return; }

        draw(n.lb, depth + 1);
        draw(n.rt, depth + 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();
        if (depth % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }
    }

    private void range(Node n, RectHV query, ArrayList<Point2D> points) {
        if (n == null || !query.intersects(n.rect)) { return; }
        if (query.contains(n.p)) { points.add(n.p); }
        range(n.lb, query, points);
        range(n.rt, query, points);
    }

    private Point2D nearest(Node n, Point2D query, Point2D nearest, int depth) {
        if (n == null) { return nearest; }
        if (nearest == null || (query.distanceSquaredTo(n.p) < query.distanceSquaredTo(nearest))) { nearest = n.p; }

        int cmp;
        if (depth % 2 == 0) {  // Vertical
            cmp = Double.compare(query.x(), n.p.x());
        } else {  // Horizontal
            cmp = Double.compare(query.y(), n.p.y());
        }

        if (cmp < 0){
            nearest = nearest(n.lb, query, nearest, depth + 1);
            if ((n.rt != null) && n.rt.rect.distanceSquaredTo(query) < query.distanceSquaredTo(nearest)) {
                nearest = nearest(n.rt, query, nearest, depth + 1);
            }
        } else {
            nearest = nearest(n.rt, query, nearest, depth + 1);
            if (n.lb != null && n.lb.rect.distanceSquaredTo(query) < query.distanceSquaredTo(nearest)) {
                nearest = nearest(n.lb, query, nearest, depth + 1);
            }
        }

        return nearest;
    }
}
