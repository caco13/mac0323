import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.BST;
import java.util.Arrays;

public class PointST<Value> {
    private RedBlackBST<Point2D, Value> root;
    
    // construct an empty symbol table of points
    public PointST() {
        root = new RedBlackBST<Point2D, Value>();
    }
    
    // is the symbol table empty?
    public boolean isEmpty() {
        return this.root.isEmpty(); //implement it!
    }
    
    // number of points
    public int size() {
        return root.size(); //implement it!
    }
    
    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null | val == null)
            throw new java.lang.NullPointerException("put(): null argument given");
        root.put(p, val);
    }
    
    // value associated with point p
    public Value get(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("get(): null argument given");
        return root.get(p);
    }
    
    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("contains(): null argument given");
        return root.contains(p);
    }
    
    // all points in the symbol table
    public Iterable<Point2D> points() {
        return root.keys();
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.NullPointerException("range(): null argument given");
        Queue<Point2D> pointsIn = new Queue<Point2D>();
        for (Point2D point : points())
            if (rect.contains(point))
                pointsIn.enqueue(point);
        return pointsIn;
    }
    
    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("nearest(): null argument given");
        if (this.isEmpty()) return null;
        BST<Double, Point2D> dist = new BST<Double, Point2D>();
        for (Point2D point : points()){
            // does not include distance of the point to be compared: it's 0.
            if (p == point) continue;
            double distSquared = p.distanceSquaredTo(point);
            dist.put(distSquared, point);
        }
        return dist.get(dist.min());
    }

    // unit testing (required)
    public static void main(String[] args) {
        // construct PointST and make default tests
        PointST<Integer> pst = new PointST<Integer>();
        assert RedBlackBST.class == pst.root.getClass();
        assert pst.isEmpty() == true;
        assert pst.size() == 0;
        
        // test putting a new point
        Point2D p = new Point2D(0, 0);
        pst.put(p, 0);
        assert pst.isEmpty() == false;
        assert pst.size() == 1;
        
        // test getting value point
        int val = pst.get(p);
        assert val == 0;
        
        // test symbol table contains point
        assert pst.contains(p) == true;
        
        // test points inside rectangle (0, 0)X(1, 1)
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        Point2D p1 = new Point2D(0.5, 0.5);
        Point2D p2 = new Point2D(0.3, 0.3);
        Point2D p3 = new Point2D(0.7, 0.7);
        Point2D p4 = new Point2D(1.0, 0.3);
        Point2D p5 = new Point2D(0.2, 1.0);
        Point2D p6 = new Point2D(0.5, 1.5);
        Point2D p7 = new Point2D(1.5, 0.5);
        Point2D p8 = new Point2D(-0.5, 0.3);
        Point2D p9 = new Point2D(0.7, -1.0);
        pst.put(p1, 1); // inside
        pst.put(p2, 2); // inside
        pst.put(p3, 3); // inside
        pst.put(p4, 4); // inside
        pst.put(p5, 5); // inside
        pst.put(p6, 6); // outside
        pst.put(p7, 7); // outside
        pst.put(p8, 8); // outside
        pst.put(p9, 9); // outside
        Iterable iterable = pst.range(rect);
        String pointString[] = new String[6];
        int i = 0;
        for (Point2D point : pst.range(rect))
            pointString[i++] = point.toString();
        assert Arrays.asList(pointString).contains(p.toString()) == true;
        assert Arrays.asList(pointString).contains(p1.toString()) == true;
        assert Arrays.asList(pointString).contains(p2.toString()) == true;
        assert Arrays.asList(pointString).contains(p3.toString()) == true;
        assert Arrays.asList(pointString).contains(p4.toString()) == true;
        assert Arrays.asList(pointString).contains(p5.toString()) == true;
        assert Arrays.asList(pointString).contains(p6.toString()) == false;
        assert Arrays.asList(pointString).contains(p7.toString()) == false;
        assert Arrays.asList(pointString).contains(p8.toString()) == false;
        assert Arrays.asList(pointString).contains(p9.toString()) == false;
        
        // test if nearest() return null when symbol is empty
        PointST<Integer> pst1 = new PointST<Integer>();
        assert pst1.nearest(p) == null;
        // test nearest point
        assert pst.nearest(p) == p2;
        assert pst.nearest(p2) == p1;
        assert pst.nearest(p1) == p3;
        assert pst.nearest(p3) == p1;
        assert pst.nearest(p4) == p3;
        assert pst.nearest(p5) == p6;
        assert pst.nearest(p6) == p5;
        assert pst.nearest(p7) == p4;
        assert pst.nearest(p8) == p;
        assert pst.nearest(p9) == p;
    }
}