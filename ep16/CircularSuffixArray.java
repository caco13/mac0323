public class CircularSuffixArray {
    private final int [] arr;
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        arr = new int[1];
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
//        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
//        assert csa.arr.length == 12;
    }
}