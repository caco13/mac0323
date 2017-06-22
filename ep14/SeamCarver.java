import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Stack;
import java.awt.Color;
import edu.princeton.cs.algs4.StdOut;  // DEBUG
import java.util.Arrays; // for unit tests

public class SeamCarver {
    // state variables
    private Picture picture; // we work with a copy of picture passed by client
    private double[][] energyMatrix;
    
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
        if ( (x < 0 || x > width() - 1) || (y < 0 || y > height() - 1) )
            throw new IndexOutOfBoundsException("energy() called with invalid index(es)");
       double xGrad = xGradient(x, y);
       double yGrad = yGradient(x, y);
       return Math.sqrt(xGrad + yGrad);
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
       transposePic();
       int[] vSeam = findVerticalSeam();
       transposePic();
       return vSeam;
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        setEnergyMatrix();
        double[][] pathsMatrix;
        pathsMatrix = relaxVertices();
        return seam(pathsMatrix);
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
    private void transposePic() {
        Picture picRotated = new Picture(height(), width());
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                picRotated.set(y, x, picture.get(x, y));
            }
        }
        picture = picRotated;
    }
    
    private void setEnergyMatrix() {
        energyMatrix = new double[height()][width()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                energyMatrix[i][j] = energy(j, i);
            }
        }
        
        // DEBUG: print energyMatrix
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                StdOut.print(energyMatrix[i][j] + " ");
            }
            StdOut.println();
        }
    }
    
    private double[][] relaxVertices() {
        double[][] pathsMatrix = new double[height()][width()];
        
        // Initialize pathsMatrix
        for (int j = 0; j < width(); j++)
            pathsMatrix[0][j] = energyMatrix[0][j];
        for (int i = 1; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                pathsMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        
        // Relax vertices (requires building energyMatrix before);
        int jIndexLeft = 0, jIndexRight = 0;
        for (int i = 0; i < height() - 1; i++) {
            for (int j = 0; j < width(); j++) {
                jIndexLeft = (j-1 == -1 ? width()-1 : j-1);
                jIndexRight = (j+1 == width() ? 0 : j+1);
                if (pathsMatrix[i][j] + energyMatrix[i+1][jIndexLeft] < pathsMatrix[i+1][jIndexLeft]) { 
                    pathsMatrix[i+1][jIndexLeft] = pathsMatrix[i][j] + energyMatrix[i+1][jIndexLeft];
                }
                if (pathsMatrix[i][j] + energyMatrix[i+1][j] < pathsMatrix[i+1][j]) {
                    pathsMatrix[i+1][j] = pathsMatrix[i][j] + energyMatrix[i+1][j];
                }
                if (pathsMatrix[i][j] + energyMatrix[i+1][jIndexRight] < pathsMatrix[i+1][jIndexRight]) {
                    pathsMatrix[i+1][jIndexRight] = pathsMatrix[i][j] + energyMatrix[i+1][jIndexRight];
                }
            }
        }
                
        // DEBUG: print pathsMatrix
//        for (int i = 0; i < height(); i++) {
//            for (int j = 0; j < width(); j++) {
//                StdOut.print(pathsMatrix[i][j] + " ");
//            }
//            StdOut.println();
//        }
        
        return pathsMatrix;

    }
    
    // requires bulding pathsMatrix in relaxVertices method before
    private int[] seam(double[][] pathsMatrix) {
        Stack<Integer> shortPath = new Stack<Integer>();
        BST<Double, Integer> lastLinePathsMatrix = new BST<Double, Integer>();
        int[] seam = new int[height()];
        for (int j = 0; j < width(); j++) {
            lastLinePathsMatrix.put(pathsMatrix[height()-1][j], j);
        }
        int minColLastLine = lastLinePathsMatrix.get(lastLinePathsMatrix.min());
        
        // Build short path array of indexes representing the seam
        shortPath.push(minColLastLine);
        int nextCol = minColLastLine;
        int minCol = 0;
        double minVal;
        for (int i = height() - 2; i >= 0; i--) {
            minVal = Double.POSITIVE_INFINITY;
            for (int j = nextCol - 1; j <= nextCol + 1; j++) {
                if (j != -1 && j != width()) {
                    if (pathsMatrix[i][j] < minVal) {
                        minVal = pathsMatrix[i][j];
                        minCol = j;
                    }
                }
            }
            nextCol = minCol;
            shortPath.push(minCol);
        }
        for (int i = 0; i < height(); i++) {
            seam[i] = shortPath.pop();
        }
        
        return seam;
    }
    
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
        
        // test energy calculation (with results from program assignment page)
        assert sc.energy(0, 0) == Math.sqrt(20808);
        assert sc.energy(1, 0) == Math.sqrt(52020);
        assert sc.energy(2, 0) == Math.sqrt(20808);
        assert sc.energy(1, 2) == Math.sqrt(52024);
        assert sc.energy(0, 2) == Math.sqrt(20809);
        assert sc.energy(2, 3) == Math.sqrt(21220);
        
        // test energyMatrix
        sc.setEnergyMatrix();
        assert sc.energyMatrix[0][0] == sc.energy(0, 0);
        assert sc.energyMatrix[0][1] == sc.energy(1, 0);
        assert sc.energyMatrix[0][2] == sc.energy(2, 0);
        assert sc.energyMatrix[2][1] == sc.energy(1, 2);
        assert sc.energyMatrix[2][0] == sc.energy(0, 2);
        assert sc.energyMatrix[3][2] == sc.energy(2, 3);
        
        // new SeamCarver
        Picture pic1 = new Picture("tests/6x5.png");
        SeamCarver sc1 = new SeamCarver(pic1);
        
        // test width's and height's picture
        assert sc1.width() == 6;
        assert sc1.height() == 5;
        
        // test findVerticalSeam
//        int[] result = {3, 4, 3, 2, 2};
//        assert Arrays.equals(sc1.findVerticalSeam(), result);
        
        // DEBUG
//        Picture pic2 = new Picture("tests/hjocean.png");
//        SeamCarver sc2 = new SeamCarver(pic2);
//        sc2.findHorizontalSeam();
        
        // test findHorizontalSeam
        int[] result1 = {2, 2, 1, 2, 1, 2};
        int[] vSeam = sc1.findHorizontalSeam();
        for (int i = 0; i < vSeam.length; i++) // DEBUG   
            StdOut.println(vSeam[i]); // DEBUG
        assert Arrays.equals(vSeam, result1);
    }

}