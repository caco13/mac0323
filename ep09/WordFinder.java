import edu.princeton.cs.algs4.StdOut; // DEBUG
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.BST;

public class WordFinder {
    // state variables
    String [] arr; // array containing strings
    SeparateChainingHashST<String, BST<Integer, Integer>> st;  // hash table to store index vectors
    
    // constructor
    public WordFinder(String[] arr) {
        this.arr = arr;
        st = new SeparateChainingHashST<String, BST<Integer, Integer>>();
        for (int i = 0; i < this.arr.length; i++) {
            String[] words = this.arr[i].split("\\W+");
            for (int j = 0; j < words.length; j++) {
                BST<Integer, Integer> bst = st.get(words[j]);
                if (bst == null) bst = new BST<Integer, Integer>();
                int wordsInLine = (bst.get(i) == null ? 1 : bst.get(i));
                bst.put(i, wordsInLine++);
                st.put(words[j], bst);
            }
        }
    }
    
    // word that appear the most in given strings
    public String getMax() {
        return "bla"; // implement it!
    }
    
    // word that appear in a and b indexes strings
    public String containedIn(int a, int b) {
        return "bla"; // implement it!
    }
    
    // array of string array indexes where string appears
    public int[] appearsIn(String s) {
        return new int[1]; // implement it!
    }
    
    // unit testing
    public static void main(String args[]) {
        // construct WordFinder and make default tests
        String[] lines = {"Globo atira Michel Fora Temer ao mar",
            "um ano depois de colocalo no poder por meio de um golpe mandrake",
            "emissora da familia Marinho deixa claro no Jornal Nacional",
            "que Temer virou bagaço de laranja noticiario tambem exibe os",
            "audios em que Temer avaliza a compra do silencio de Eduardo Cunha",
            "Globo ja prepara seu plano B para substituir Michel Fora Temer e",
            "encontrar outro nome menos sujo para levar adiante sua agenda",
            "primeira fala de William Bonner foi sobre a fala nada republicana",
            "de Fora Temer"};
        WordFinder wf = new WordFinder(lines);
        assert SeparateChainingHashST.class == wf.st.getClass();
        assert BST.class == wf.st.get("Fora").getClass();
        assert BST.class == wf.st.get("Globo").getClass();
        
    }
}