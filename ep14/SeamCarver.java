import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
    }
    
    // current picture
    public Picture picture() {
        return new Picture(4, 3);  // implement it!
    }
    
    // width of current picture
    public int width() {
        return -1;  // implement it!
    }
    
    // height of current picture
    public int height() {
        return -1;  // implement it!
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
    }

}