import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.ST;
import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            CircularSuffixArray csa = new CircularSuffixArray(s);
            StringBuilder lastCharString = new StringBuilder(0);
            for (int i = 0; i < s.length(); i++) {
                if (csa.index(i) == 0) {
                    StdOut.println(i);
                    lastCharString.append(s.charAt(s.length() - 1));
                    continue;
                }
                lastCharString.append(s.charAt(csa.index(i) - 1));
            }
            StdOut.println(lastCharString);
            while (StdIn.hasNextChar()) {
                char c = StdIn.readChar();
                if (Character.isWhitespace(c))
                    StdOut.print(c);
                else
                    break;
            }
            
        }
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first, n;
        String s;
        int [] next;
        char [] t, tSorted;
        ST<Character, Integer> alphabetST;
        
        while (!StdIn.isEmpty()) {  
            first = StdIn.readInt();
            s = StdIn.readString();
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
            // print decode
            int k;
            StdOut.print(tSorted[first]);
            i = next[first];
            for (int count = 0; count < n - 1; count++) {
                StdOut.print(tSorted[i]);
                i = next[i];
;
//                StdOut.println(i + " " + k); //DEBUG
            }
            StdOut.println();
        }
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
    
//    private static boolean isWhiteSpace(char c) {
//        return c == ' ' || c == '\n';
//    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) //TODO: if args[0] != "-" and != "+" show use
            BurrowsWheeler.transform();
        if (args[0].equals("+"))
            BurrowsWheeler.inverseTransform();
    }
}