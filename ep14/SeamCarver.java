import edu.princeton.cs.algs4.Picture;
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
        return -1.0;  // implement it!
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
    }

}