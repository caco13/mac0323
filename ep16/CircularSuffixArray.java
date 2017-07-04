import edu.princeton.cs.algs4.StdOut;  // DEBUG
import edu.princeton.cs.algs4.LSD;

public class CircularSuffixArray {
    private final String [] index;
    private CircularSuffix cs;
    private static class CircularSuffix implements Comparable<CircularSuffix> {
        // do not instantiate
        private CircularSuffix() { }
        
        public static int getIthSortedSuffix(String [] suffix, int i) {
            String [] circularSuffix = new String[suffix.length];
            System.arraycopy(suffix, 0, circularSuffix, 0, suffix.length);
            LSD.sort(circularSuffix, circularSuffix.length);
            for (int j = 0; j < suffix.length; j++) {
                if (circularSuffix[i].equals(suffix[j]))
                    return j;
            }
            return -1; // Something went wrong
        }
        public int compareTo(CircularSuffix that) {
            return -1; //implement it! Será?
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
        return index.length;
    }
    
    // returns index of ith sorted suffix
    public int index(int i) {
        return CircularSuffix.getIthSortedSuffix(index, i);
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
        
        assert csa.length() == csa.index.length;
        
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
        
        // test index method with longer word
        CircularSuffixArray csa1 = new CircularSuffixArray("INCONSTITUCIONABILISSIMAMENTE!");
        assert csa1.index(0) == 29;
        assert csa1.index(1) == 14;
        
    }
}