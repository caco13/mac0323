import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTreeST<Value> {
    // state variables
    private int n; // KdTreeST length
    private Node root;

    private class Node {
        private Point2D p;      // the point
        private Value value;    // the symbol table maps the point to this value
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        public Node(Point2D p, Value val) {
            this.p = p;
            this.value = val;
        }
    }
    // construct an empty symbol table of points 
    public KdTreeST() {
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points
    public int size() {
        return n;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null) throw new IllegalArgumentException("called put() with a null key");
        root = put(root, p, val, true); //true represents vertical, false, horizontal
    }

    // value associated with point p
    public Value get(Point2D p) {
        return get(root, p, true);
    }

    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Queue<Point2D> qp = new Queue<Point2D>();
        return qp; //implement it!
    }

   // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> qr = new Queue<Point2D>();
        return qr; //implement it!
    }

    // a nearest neighbor to point p; null if the symbol table is empty 
    public Point2D nearest(Point2D p) {
        return p;
    }

    /**
     * Private methods
     */
    private Node put(Node node, Point2D p, Value val, boolean vertical) {
        if (node == null) {
            n++;
            return new Node(p, val);
        }
        if (p == node.p) node.value = val;
        if (vertical) {
            if (p.x() < node.p.x()) node.lb = put(node.lb, p, val, false);
            else node.rt = put(node.rt, p, val, false);
        } else {
            if (p.y() < node.p.y()) node.lb = put(node.lb, p, val, true);
            else node.rt = put(node.rt, p, val, true);
        }
        return node;
    }

    private Value get(Node node, Point2D p, boolean vertical) {
        if (p == null) throw new IllegalArgumentException("called get() with a null key");
        if (node == null) return null;
        if (p == node.p) return node.value;
        if (vertical) {
            if (p.x() < node.p.x()) return get(node.lb, p, false);
            else return get(node.rt, p, false);
        } else {
            if (p.y() < node.p.y()) return get(node.lb, p,true);
            else return get(node.rt, p, true);
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // construct KdTreeST and make default initial tests
        KdTreeST<Integer> kdt = new KdTreeST<Integer>();
        assert kdt.isEmpty() == true;
        assert kdt.size() == 0;
        
        // test put()
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);
        kdt.put(p1, 0);
        assert kdt.n == 1;
        kdt.put(p2, 1);
        assert kdt.n == 2;
        kdt.put(p3, 2);
        assert kdt.n == 3;
        kdt.put(p4, 3);
        assert kdt.n == 4;
        kdt.put(p5, 4);
        assert kdt.n == 5;
        assert kdt.get(p1) == 0;
        assert kdt.get(p2) == 1;
        assert kdt.get(p3) == 2;
        assert kdt.get(p4) == 3;
        assert kdt.get(p5) == 4;
        kdt.put(p1, 5);
        assert kdt.get(p1) == 5;
        assert kdt.contains(p1) == true;
        assert kdt.contains(p2) == true;
        assert kdt.contains(p3) == true;
        assert kdt.contains(p4) == true;
        assert kdt.contains(p5) == true;
        assert kdt.contains(new Point2D(-1.0, -1.0)) == false;
    }
}