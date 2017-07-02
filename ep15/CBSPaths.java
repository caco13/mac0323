import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; //DEBUG
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.BST;
import java.util.Scanner;
import java.util.Arrays; //DEBUG

public class CBSPaths {
    private EdgeWeightedDigraph ewd;
    private BST<String, Integer> cities;
    private String[] executiveCities;
    private String mp;
    
    public CBSPaths(Scanner stdin) {
        int n = stdin.nextInt();
        int m = stdin.nextInt();
        int k = stdin.nextInt();
        ewd = new EdgeWeightedDigraph(n);
        DirectedEdge e;
        int v = 0;
        cities = new BST<String,Integer>();
        // read the flies (edges) and corresponding taking hours (weights)
        for (int i = 0; i < m; i++) {
            String from = stdin.next();
            if (!cities.contains(from))
                cities.put(from, v++);
            String to = stdin.next();
            if (!cities.contains(to))
                cities.put(to, v++);
            double time = stdin.nextDouble();
            e = new DirectedEdge(cities.get(from), cities.get(to), time);
            ewd.addEdge(e);
        }
        // read the next k lines representing the cities that are the executives
        executiveCities = new String[k];
        for (int i = 0; i < k; i++)
            executiveCities[i] = stdin.next();
        // read the last line of StdIn representing Ministério Público intial city
        mp = stdin.next();
        
    }
    
    public static void main(String args[]) {
        // test constructor
        Scanner stdin = new Scanner(System.in);
        CBSPaths cbs = new CBSPaths(stdin);
        assert cbs.ewd.V() == 6;
        assert cbs.ewd.E() == 9;
//        for (DirectedEdge e : cbs.ewd.edges()) { //DEBUG
//            StdOut.println(e);
//        }
        assert Arrays.asList(cbs.executiveCities).contains("POA") == true;
        assert Arrays.asList(cbs.executiveCities).contains("GIG") == true;
        assert Arrays.asList(cbs.executiveCities).contains("GRU") == false;
        assert Arrays.asList(cbs.executiveCities).contains("CNF") == false;
        assert Arrays.asList(cbs.executiveCities).contains("BSB") == false;
        assert Arrays.asList(cbs.executiveCities).contains("CWB") == false;
        
        assert cbs.mp.equals("GRU");
    }
}