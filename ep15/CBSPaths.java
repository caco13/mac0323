import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; //DEBUG
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Bag;
import java.util.Scanner;
import java.util.Arrays; //DEBUG

public class CBSPaths {
    private EdgeWeightedDigraph ewd;
    private BST<String, Integer> cities;
    private String[] executiveCities;
    private String mpCity;
    
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
        mpCity = stdin.next();
        
    }
    
    public Bag<String> safeCities() {
        Bag<String> safeCities = new Bag<String>();
        // If some executives city is the same as MP city, game over
        if (Arrays.asList(executiveCities).contains(mpCity))
            return safeCities;
        
        DijkstraSP sp;
        int mpCityV = cities.get(mpCity);
        for (String city : cities.keys()) {
            int cityV = cities.get(city);
            if (city.equals(mpCity)) continue; // the MP city is not secure by definition
            
            // City that CBS executives are before the flies are secure only if
            // MP city has no edge to it.
            if (Arrays.asList(executiveCities).contains(city)) {
                if (mpCityHasEdgeTo(cityV)) continue;
            }
            sp = new DijkstraSP(ewd, mpCityV);
            double mpDistTo = sp.distTo(cityV);
            for (int i = 0; i < executiveCities.length; i++) {
                sp = new DijkstraSP(ewd, cities.get(executiveCities[i]));
                if (sp.distTo(cityV) >= mpDistTo) continue;
            }
            safeCities.add(city);
        }
        return safeCities;
    }
    
    /**
     * Private methods
     */
    private boolean mpCityHasEdgeTo(int cityV) {
        for ( DirectedEdge c : ewd.adj(cities.get(mpCity)) ) {
            if (cityV == c.to()) {
                return true;
            }
        }
        return false;
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
        // executive's cities
        assert Arrays.asList(cbs.executiveCities).contains("POA") == true;
        assert Arrays.asList(cbs.executiveCities).contains("GIG") == true;
        assert Arrays.asList(cbs.executiveCities).contains("GRU") == false;
        assert Arrays.asList(cbs.executiveCities).contains("CNF") == false;
        assert Arrays.asList(cbs.executiveCities).contains("BSB") == false;
        assert Arrays.asList(cbs.executiveCities).contains("CWB") == false;
        // Ministério Público city
        assert cbs.mpCity.equals("GRU");
        
        // test safe cities
        String [] safeCities = {"CNF", "BSB", "CWB"};
        for (String city : cbs.safeCities())
//            StdOut.println(city); //DEBUG
            assert Arrays.asList(safeCities).contains(city) == true;
        assert Arrays.asList(safeCities).contains("POA") == false;
        assert Arrays.asList(safeCities).contains("GIG") == false;
        assert Arrays.asList(safeCities).contains("GRU") == false;
    }
}