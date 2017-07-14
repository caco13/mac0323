import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.ST;
import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        StringBuilder textSB = new StringBuilder(StdIn.readAll());
        textSB.deleteCharAt(textSB.length() - 1);
        String text = textSB.toString();
        CircularSuffixArray csa = new CircularSuffixArray(text);
        StringBuilder lastCharString = new StringBuilder(0);
        for (int i = 0; i < text.length(); i++) {
            if (csa.index(i) == 0) {
                StdOut.println(i);
                lastCharString.append(text.charAt(text.length() - 1));
                continue;
            }
            lastCharString.append(text.charAt(csa.index(i) - 1));
        }
        StdOut.println(lastCharString);
    }
    
    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first, n;
        String s;
        int [] next;
        char [] t, tSorted;
        ST<Character, Integer> alphabetST;
        
//        while (!StdIn.isEmpty()) {
            first = StdIn.readInt();
            StringBuilder textSB = new StringBuilder(StdIn.readAll());
            textSB.deleteCharAt(textSB.length() - 1);
            textSB.deleteCharAt(0);
//            StdOut.println(textSB.toString()); //DEBUG
            s = textSB.toString();
//            s = StdIn.readAll();
            n = s.length();
            // construct alphabet ST to count ocurrences of each alphabet element        
            t = new char[n];
            tSorted = new char[n];
            // construct t and tSorted arrays
            for (int i = 0; i < n; i++) {
                t[i] = s.charAt(i);
                tSorted[i] = t[i];
            }
            Arrays.sort(tSorted);
            // construct next array
            alphabetST = getAlphabet(s);
            next = new int[n];
            int i = 0;
            while (i < n) {
                char c = tSorted[i];
                int k = 0;
                int j = 0;
                while (k < s.length()) {
                    if (t[k] == c) {
                        next[i++] = k;
                        j++;
                        if (j == alphabetST.get(c)) break;
                    }
                    k++;
                }
            }
//            
//            for (i = 0; i < next.length; i++) //DEBUG
//                StdOut.println(next[i]);
            // print decode
            int k;
            StdOut.print(tSorted[first]);
            i = next[first];
            for (int count = 0; count < n - 1; count++) {
                StdOut.print(tSorted[i]);
                i = next[i];
            }
            StdOut.println();
//        }
    }
    
    
    /**
     * Private methods
     */
    private static ST<Character, Integer> getAlphabet(String s) {
        int n = s.length();
        ST<Character, Integer> a = new ST<Character, Integer>(); 
        for (int j = 0; j < n; j++) {
            if (a.contains(s.charAt(j)))
                a.put( s.charAt(j), a.get(s.charAt(j)) + 1 );
            else
                a.put( s.charAt(j), 1);
        }
        return a;
    }
    
    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) //TODO: if args[0] != "-" and != "+" show use
            BurrowsWheeler.transform();
        if (args[0].equals("+"))
            BurrowsWheeler.inverseTransform();
    }
}