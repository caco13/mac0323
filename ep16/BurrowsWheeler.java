import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

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
        }
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-"))
            BurrowsWheeler.transform();
        if (args[0].equals("+"))
            BurrowsWheeler.inverseTransform();
    }
}