import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MoveToFront {
    private final static int R = 256;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char [] ascii = asciiAlphabet();
//        char [] ascii = {'A', 'B', 'C', 'D', 'E', 'F'}; //DEBUG
//        while (!StdIn.isEmpty()) {
        StringBuilder textSB = new StringBuilder(StdIn.readAll());
        textSB.deleteCharAt(textSB.length() - 1);
        String s = textSB.toString();
//            String s = StdIn.readString();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int posChar = getPosCharAlphabet(c, ascii);
                StdOut.println(posChar);
                moveToFront(posChar, ascii);
            }
//        }
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char [] ascii = asciiAlphabet();
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            StdOut.print(ascii[i]);
            moveToFront(i, ascii);
        }
    }
    
    /**
     * Private methods
     */
    private static char [] asciiAlphabet() {
        char [] alphabet = new char[R];
        for (int i = 0; i < R; i++) {
            alphabet[i] = (char) i;
        }
        return alphabet;
    }
    
    private static int getPosCharAlphabet(char c, char [] arr) {
        for (int i = 0; i < R; i++)
            if (arr[i] == c)
                return i;
        return -1; // c char does not exist in ascii array
    }
    
    private static void moveToFront(int pos, char [] arr) {
        char tmp = arr[pos];
        for (int i = pos; i > 0; i--)
            arr[i] = arr[i - 1];
        arr[0] = tmp;
    }
    
    private static void showUsage() {
        StdOut.println("Wrong use.");
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args.length == 0) {
            showUsage();
            return;
        }
        if (args[0].equals("-"))
            MoveToFront.encode();
        else if (args[0].equals("+"))
            MoveToFront.decode();
        else {
            showUsage(); //TODO: show usage!
            return;
        }
    }
}