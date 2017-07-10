import edu.princeton.cs.algs4.StdOut;  // DEBUG
import java.util.Arrays;

public class CircularSuffixArray {
    private CircularSuffix[] circularSuffixes;
    
    public CircularSuffixArray(String text) {
        int n = text.length();
        this.circularSuffixes = new CircularSuffix[n];
        for (int i = 0; i < n; i++)
            circularSuffixes[i] = new CircularSuffix(text, i);
        Arrays.sort(circularSuffixes);
    }
    
    private static class CircularSuffix implements Comparable<CircularSuffix> {
        private final String text;
        private final int index;
        
        private CircularSuffix(String text, int index) {
            this.text = text;
            this.index = index;
        }
        
        private int length() {
            return text.length() - index;
        }
        
        private char charAt(int i) {
            return text.charAt(index + i);
        }
       
        public int compareTo(CircularSuffix that) {
            if (this == that) return 0;
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return this.length() - that.length();
        }
    }
    
    // length of s
    public int length() {
        return circularSuffixes.length;
    }
    
    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= circularSuffixes.length) throw new IllegalArgumentException();
        return circularSuffixes[i].index;
    }
    
    // unit testing (not graded)
    public static void main(String[] args) {
        // test constructor
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        
        // test index method
        assert csa.index(0) == 11;
        assert csa.index(1) == 10;
        assert csa.index(2) == 7;
        assert csa.index(3) == 0;
        assert csa.index(4) == 3;
        assert csa.index(5) == 5;
        assert csa.index(6) == 8;
        assert csa.index(7) == 1;
        assert csa.index(8) == 4;
        assert csa.index(9) == 6;
        assert csa.index(10) == 9;
        assert csa.index(11) == 2;
    }
}