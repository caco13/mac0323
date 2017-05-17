import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTreeST<Value> {
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
        return false; //implement it!
    }
    
    // number of points
    public int size() {
        return -1; //implement it!
    }
    
    // associate the value val with point p
    public void put(Point2D p, Value val) {
    }
    
    // value associated with point p
    public Value get(Point2D p) {
        return root.value; //implement it!
    }
    
    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        return false; //implement it!
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
    
    // unit testing (required)
    public static void main(String[] args) {
    }
}