import edu.princeton.cs.algs4.StdOut; // DEBUG
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.BST;

public class WordFinder {
    // state variables
    String [] arr; // array containing strings
    String maxFrequencyWord; // word that appear more in arr (ignore repetition inside arr item)
    SeparateChainingHashST<String, BST<Integer, Integer>> st;  // hash table to store index vectors
    
    // constructor
    public WordFinder(String[] arr) {
        this.arr = arr;
        st = new SeparateChainingHashST<String, BST<Integer, Integer>>();
        makeHashTable(this.arr, st);
    }
    
    // word that appear the most in given strings
    public String getMax() {
        return maxFrequencyWord; // implement it!
    }
    
    // word that appear in a and b indexes strings
    public String containedIn(int a, int b) {
        String[] wordsInA = this.arr[a].split("\\W+");
        for (int i = 0; i < wordsInA.length; i++) {
            if (st.get(wordsInA[i]) == null)
                throw new NullPointerException("got null BST");
            if (st.get(wordsInA[i]).contains(b))
                return wordsInA[i];
        }
        return null;
    }
    
    // array of string array indexes where string appears
    public int[] appearsIn(String s) {
        return new int[1]; // implement it!
    }
    
    /**
     * Private methods
     */
    private void makeHashTable(String[] arr,
                               SeparateChainingHashST<String, BST<Integer, Integer>> st) {
        int wordsInLine = 0;
        int maxCountWord = 0;
        for (int i = 0; i < this.arr.length; i++) {
            String[] words = this.arr[i].split("\\W+");
            for (int j = 0; j < words.length; j++) {
                BST<Integer, Integer> bst = st.get(words[j]);
                if (bst == null) bst = new BST<Integer, Integer>();
                wordsInLine = (bst.get(i) == null ? 1 : bst.get(i) + 1);
                // Updates maxCountWord and give the word that appears more
                // frequently until now
                if (wordsInLine == 1) {
                    if (bst.size() > maxCountWord) {
                        maxCountWord = bst.size();
                        maxFrequencyWord = words[j];
                    }
                }
                bst.put(i, wordsInLine);
                st.put(words[j], bst);
            }
        }
    }
    
    // unit testing
    public static void main(String args[]) {
        // construct WordFinder and make default constructor tests
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
        assert wf.st.get("Fora").keys().toString().equals("0 5 8 ");
        assert wf.st.get("Globo").keys().toString().equals("0 5 ");
        assert wf.st.get("Temer").keys().toString().equals("0 3 4 5 8 ");
        assert wf.st.get("Fora").get(0) == 1;
        assert wf.st.get("Globo").get(0) == 1;
        assert wf.st.get("Globo").get(5) == 1;
        assert wf.st.get("Temer").get(0) == 1;
        assert wf.st.get("Temer").get(4) == 1;
        assert wf.st.get("um").get(0) == null;
        assert wf.st.get("um").get(1) == 2;
        assert wf.st.get("fala").get(7) == 2;
        assert wf.st.get("de").get(0) == null;
        assert wf.st.get("de").get(1) == 2;
        
        // test getMax
        assert wf.getMax().equals("de");
        
        // test containedIn
        assert wf.containedIn(0, 5).equals("Globo");
        assert wf.containedIn(7, 8).equals("de");
        assert wf.containedIn(5, 8).equals("Fora");
        assert wf.containedIn(2, 3) == null;
        assert wf.containedIn(1, 2).equals("no");
        
    }
}