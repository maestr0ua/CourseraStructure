import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

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
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {

            root = new Node(p, true, (char) ('A' + size));
            root.rect = new RectHV(0, 0, 1, 1);
            size++;
            return;
        }

        if (!contains(p)) {

            Node current = root;
            while (true) {

                if (compare(p, current.point, current.horizontal) < 0) {
                    if (current.left == null) {
                        current.left = new Node(p, !current.horizontal, (char) ('A' + size));
                        current.left.rect = getRect(current, p);
                        size++;
                        return;
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null) {
                        current.right = new Node(p, !current.horizontal, (char) ('A' + size));
                        current.right.rect = getRect(current, p);
                        size++;
                        return;
                    } else {
                        current = current.right;
                    }
                }
            }
        }
    }

    private RectHV getRect(Node parent, Point2D p) {
        if (parent.horizontal) {
            if (Double.compare(p.x(), parent.point.x()) < 0) {
                return new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.point.x(), parent.rect.ymax());
            } else {
                return new RectHV(parent.point.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
            }

        } else {
            if (Double.compare(p.y(), parent.point.y()) < 0) {
                return new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.point.y());
            } else {
                return new RectHV(parent.rect.xmin(), parent.point.y(), parent.rect.xmax(), parent.rect.ymax());
            }

        }
    }

    private int compare(Point2D p1, Point2D p2, boolean horizontal) {
        if (horizontal) {
            return Double.compare(p1.x(), p2.x());
        } else {
            return Double.compare(p1.y(), p2.y());
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node current = root;
        while (true) {
            if (current == null) return false;

            int comparisson = current.point.compareTo(p);
            if (comparisson == 0) {
                return true;
            }
            current = comparisson > 0 ? current.right : current.left;
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;

        node.point.draw();
        draw(node.left);
        draw(node.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Queue<Point2D> queue = new Queue<>();

        if (!isEmpty()) {
            findPoints(queue, rect, root);
        }
        return queue;
    }

    private void findPoints(Queue<Point2D> queue, RectHV rect, Node root) {
        if (!rect.intersects(root.rect)) {
            return;
        }
        if (rect.contains(root.point)) {
            queue.enqueue(root.point);
        }
        if (root.left != null) {
            findPoints(queue, rect, root.left);
        }
        if (root.right != null) {
            findPoints(queue, rect, root.right);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            return null;
        }

        return nearest(root, root, p).point;
    }

    private Node nearest(Node root, Node nearest, Point2D p) {

        if (root.point.distanceSquaredTo(p) < nearest.point.distanceSquaredTo(p)) {
            nearest = root;
        }

        Node first;
        Node second;
        if (compare(p, root.point, root.horizontal) < 0) {
            first = root.left;
            second = root.right;
        } else {
            first = root.right;
            second = root.left;
        }

        if (first != null && first.rect.distanceSquaredTo(p) < nearest.point.distanceTo(p)) {
            nearest = nearest(first, nearest, p);
        }

        if (second != null && second.rect.distanceSquaredTo(p) < nearest.point.distanceTo(p)) {
            nearest = nearest(second, nearest, p);
        }

        return nearest;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // empty method
    }

    private static class Node {

        Point2D point;
        RectHV rect;
        Node left;
        Node right;
        boolean horizontal = false;
        char name;

        public Node(Point2D point, boolean horizontal, char name) {
            this.point = point;
            this.horizontal = horizontal;
            this.name = name;
        }

        @Override
        public String toString() {
            return name + " (" + point.x() + ", " + point.y() + ")";
        }
    }

}
