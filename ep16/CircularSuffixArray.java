public class CircularSuffixArray {
    private final int [] index;
    private CircularSuffix cs;
    private class CircularSuffix implements Comparable<CircularSuffix> {
        private CircularSuffix() {
        }
        public int compareTo(CircularSuffix that) {
            return -1; //implement it!
        };
    }
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        index = new int[s.length()];
    }
    
    // length of s
    public int length() {
        return -1; //implement it!
    }
    
    // returns index of ith sorted suffix
    public int index(int i) {
        return -1; //implement it!
    }
    
    // unit testing (not graded)
    public static void main(String[] args) {
        // test constructor
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        assert csa.index.length == 12;
    }
}