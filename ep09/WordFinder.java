public class WordFinder {
    // state variables
    String [] arr;
    
    // constructor
    public WordFinder(String[] arr) {
        this.arr = arr;
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
        String[] lines = {"Globo atira Michel Temer ao mar",
            "um ano depois de colocalo no poder por meio de um golpe mandrake",
            "emissora da familia Marinho deixa claro no Jornal Nacional",
            "que Temer virou bagaço de laranja noticiario tambem exibe os",
            "audios em que Temer avaliza a compra do silencio de Eduardo Cunha",
            "Globo ja prepara seu plano B para substituir Michel Temer e",
            "encontrar outro nome menos sujo para levar adiante sua agenda",
            "primeira fala de William Bonner foi sobre a fala nada republicana",
            "de Temer"};
        WordFinder wf = new WordFinder(lines);
        assert wf.arr[8] == "de Temer";
    }
}