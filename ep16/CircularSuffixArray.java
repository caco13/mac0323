import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final String [] index;
    private CircularSuffix cs;
    private class CircularSuffix implements Comparable<CircularSuffix> {
        private CircularSuffix(String suffix) {
        }
        public int compareTo(CircularSuffix that) {
            return -1; //implement it!
        };
    }
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        index = new String[s.length()];
        index[0] = s;
        StringBuilder sb;
        for (int i = 1; i < index.length; i++) {
            sb = new StringBuilder();
            for (int j = i; j < i + index.length; j++) {
                sb.append(s.charAt(j % index.length));
            }
            index[i] = sb.toString();
        }
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
        assert csa.index[0].equals("ABRACADABRA!");
        assert csa.index[1].equals("BRACADABRA!A");
        assert csa.index[2].equals("RACADABRA!AB");
        assert csa.index[3].equals("ACADABRA!ABR");
        assert csa.index[4].equals("CADABRA!ABRA");
        assert csa.index[5].equals("ADABRA!ABRAC");
        assert csa.index[6].equals("DABRA!ABRACA");
        assert csa.index[7].equals("ABRA!ABRACAD");
        assert csa.index[8].equals("BRA!ABRACADA");
        assert csa.index[9].equals("RA!ABRACADAB");
        assert csa.index[10].equals("A!ABRACADABR");
        assert csa.index[11].equals("!ABRACADABRA");

    }
}