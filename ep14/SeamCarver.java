import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import edu.princeton.cs.algs4.StdOut;  // DEBUG

public class SeamCarver {
    // state variables
    Picture picture;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new NullPointerException("Constructor called with null argument");
        this.picture = picture;
    }
    
    // current picture
    public Picture picture() {
        return picture;
    }
    
    // width of current picture
    public int width() {
        return picture.width();
    }
    
    // height of current picture
    public int height() {
        return picture.height();
    }
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if ( (x < 0 || x > width() - 1) || (y < 0 || y > height() - 1))
            throw new IndexOutOfBoundsException("energy() called with invalid index(es)");
       double xGrad = xGradient(x, y);
       double yGrad = yGradient(x, y);
       return Math.sqrt(xGrad + yGrad);
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] h = {-1};  // implement it!
        return h;  // implement it!
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] v = {-1};  // implement it!
        return v;  // implement it!
        
    }
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
    }
    
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
    }
    
    /**
     * Private methods
     */
    
    private double xGradient(int x, int y) {
        int x0 = x - 1;
        int x1 = x + 1;
        if (x == 0) x0 = width() - 1;
        if (x == width() - 1) x1 = 0;
        Color pixel0 = picture.get(x0, y);
        Color pixel1 = picture.get(x1, y);
        return gradient(pixel1, pixel0);
    }
    
    private double yGradient(int x, int y) {
        int y0 = y - 1;
        int y1 = y + 1;
        if (y == 0) y0 = height() - 1;
        if (y == height() - 1) y1 = 0;
        Color pixel0 = picture.get(x, y0);
        Color pixel1 = picture.get(x, y1);
        return gradient(pixel1, pixel0);
    }
    
    private int gradient(Color px0, Color px1) {
        int r = px1.getRed() - px0.getRed();
        int g = px1.getGreen() - px0.getGreen();
        int b = px1.getBlue() - px0.getBlue();
        return r*r + g*g + b*b;
    }
    
    // do unit testing of this class
    public static void main(String[] args) {
        // test constructor
        Picture pic = new Picture("tests/3x4.png");
        SeamCarver sc = new SeamCarver(pic);
        assert sc.picture == pic;
        
        // test get current picture
        assert sc.picture == sc.picture();
        
        // test width's and height's picture
        assert sc.width() == 3;
        assert sc.height() == 4;
        
        // test energy calculation
        assert sc.energy(0, 0) == Math.sqrt(20808);
        assert sc.energy(1, 0) == Math.sqrt(52020);
        assert sc.energy(2, 0) == Math.sqrt(20808);
        assert sc.energy(1, 2) == Math.sqrt(52024);
        assert sc.energy(0, 2) == Math.sqrt(20809);
        assert sc.energy(2, 3) == Math.sqrt(21220);
    }

}