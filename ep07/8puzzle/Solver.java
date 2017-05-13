import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    // state variables
    private SearchNode root;
    
    private class SearchNode {
        SearchNode previous;
        int moves;
        Board board;
        int manhattanPF;
        
        // constructor
        public SearchNode (Board board, SearchNode previous) {
            this.previous = previous;
            this.board = board;
            manhattanPF = this.board.manhattan() + moves;
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        root = new SearchNode(initial, null);
    }
    
    // min number of moves to solve initial board
    public int moves() {
        return -1;
    }
    
    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<Board>();
        return solution;
    }
    
    //unit testing
    public static void main(String[] args) {
        // create new Solver
        int[][] t = { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} };
        Board b = new Board(t);
        Solver s = new Solver(b);
        
        // test SearchNode constructor
        assert s.root.previous == null;
        assert s.root.moves == 0;
        assert s.root.board.equals(b);
        assert s.root.manhattanPF == 4;
    }
}