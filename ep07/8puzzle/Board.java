import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;

public class Board {
    // state variables
    private final int n;
    private int [][] tiles;
    
    // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = tiles;
    }
    
    // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }
    
    // board size N
    public int size() {
        return n;
    }
    
    // number of tiles out of place
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    if (tileAtGoalPosition(i, j) != tiles[i][j]) sum++;
                }
            }
        }
        return sum;
    }
    
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    sum += manhattanDist(i, j);
                }
            }
        }
        return sum;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != tileAtGoalPosition(i, j)) return false;
            }
        }
        return true;
    }
    
    // is this board solvable?
    public boolean isSolvable() {
        if (n % 2 == 0) {
            return (blankRowBoard() + inversions()) % 2 == 1 ? true : false;
        }
        return inversions() % 2 == 0 ? true : false;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();
        // find empty node
        int emptyRow = 0, emptyCol = 0;
        // we consider that the board is well formed so exists empty node
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    emptyRow = i; emptyCol = j;
                    break;
                }
            }
        }
        if (emptyCol != n - 1) {
            int[][] tilesN0 = new int[n][n];
            copy2DArray(tiles, tilesN0);
            tilesN0[emptyRow][emptyCol] = tilesN0[emptyRow][emptyCol + 1];
            tilesN0[emptyRow][emptyCol + 1] = 0;
            Board boardN0 = new Board(tilesN0);
            neighbors.enqueue(boardN0);
        }
        if (emptyRow != 0) {
            int[][] tilesN1 = new int[n][n];
            copy2DArray(tiles, tilesN1);
            tilesN1[emptyRow][emptyCol] = tilesN1[emptyRow - 1][emptyCol];
            tilesN1[emptyRow - 1][emptyCol] = 0;
            Board boardN1 = new Board(tilesN1);
            neighbors.enqueue(boardN1);
        }
        if (emptyCol != 0) {
            int[][] tilesN2 = new int[n][n];
            copy2DArray(tiles, tilesN2);
            tilesN2[emptyRow][emptyCol] = tilesN2[emptyRow][emptyCol - 1];
            tilesN2[emptyRow][emptyCol - 1] = 0;
            Board boardN2 = new Board(tilesN2);
            neighbors.enqueue(boardN2);
        }
        if (emptyRow != n) {
            int[][] tilesN3 = new int[n][n];
            copy2DArray(tiles, tilesN3);
            tilesN3[emptyRow][emptyCol] = tilesN3[emptyRow + 1][emptyCol];
            tilesN3[emptyRow + 1][emptyCol] = 0;
            Board boardN3 = new Board(tilesN3);
            neighbors.enqueue(boardN3);
        }   
        return neighbors;           
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    /*
     * Private methods
     */
    
    /**
     * tileAtGoalPosition
     * ------------------
     * Returns the right title at position (i,j)
     * when the tile is in goal position board.
     */
    private int tileAtGoalPosition(int i, int j) {
        if (i == n - 1 & j == n - 1) return 0;
        return i * n + j + 1;
    }
    
    /**
     * manhattanDist
     * -------------
     * Returns the Manhattan Distance (sum of
     * the horizontal end vertical distances) of
     * a tile.
     */
    private int manhattanDist(int i, int j) {
        int tileRow = goalTileRow(i, j);
        int tileCol = goalTileCol(i, j, tileRow);
        return Math.abs(i - tileRow) + Math.abs(j - tileCol);
    }
    
    /**
     * goalTileRow
     * ------------
     * Returns the board row a tile stands when
     * it's the board goal.
     */
    private int goalTileRow(int i, int j) {
        if (tiles[i][j] == 0) return (n - 1);
        return ( (tiles[i][j] - 1) / n );
    }
    
    /**
     * goalTileCol
     * -----------
     * Returns the board col a tile stands when
     * it's the board goal.
     */
    private int goalTileCol(int i, int j, int tileRow) {
        if (tiles[i][j] == 0) return (n - 1);
        return tiles[i][j] - tileRow * n - 1; 
    }
    
    /**
     * inversions
     * ----------
     * Returns de number of inversions of a board.
     */
    private int inversions() {
        int inversions = 0;
        int linearTiles[] = new int[n*n];
        linearizeBoard(linearTiles);
        for (int i = 0; i < n*n - 1; i++) {
            for (int j = i + 1; j < n*n; j++) {
                if (linearTiles[j] != 0 & linearTiles[i] != 0 &
                    linearTiles[j] - linearTiles[i] < 0) {
                    inversions++;
                }
            }
        }
        return inversions;
    }
    
    /**
     * linearizeBoard
     * --------------
     * Create a row-major order linear array from tiles.
     */
    private void linearizeBoard(int [] arr) {
        if (arr.length < n*n) {
            // TODO: generates exception - array size less then necessary.
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i * n + j] = tiles[i][j];
            }
        }
    }
    
    /**
     * blankRowBoard
     * -------------
     * Returns the row where the blank space stands.
     */
    private int blankRowBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) return i;
            }
        }
        
        return -1; // malformed board
    }
    
    /**
     * copy2DArray
     * -----------
     * Efficently copy 2D array arr0 to arr1.
     */
    private void copy2DArray(int[][] arr0, int[][] arr1) {
        for (int i = 0; i < arr0.length; i++) {
            System.arraycopy(arr0[i], 0, arr1[i], 0, arr0[0].length);
        }
    }
    
    //unit testing
    public static void main(String[] args) {
        
        // construct a board
        int[][] tiles = { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} };
        Board board = new Board(tiles);
        
        // construct a board
        int[][] tiles1 = { {8, 1, 3}, {4, 0, 2}, {7, 6, 5} };
        Board board1 = new Board(tiles1);
        
        //construct a goal board
        int[][] tiles2 = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
        Board board2 = new Board(tiles2);
        
        // construct a board
        int[][] tiles3 = { {1, 2, 3}, {4, 5, 6}, {8, 7, 0} };
        Board board3 = new Board(tiles3);
        
        // construct a board
        int[][] tiles4 = { {1, 2, 3, 4}, {5, 6, 7, 8},
            {9, 10, 11, 12}, {13, 15, 14, 0} };
        Board board4 = new Board(tiles4);
        
        // construct a board
        int[][] tiles5 = { {1, 2, 3}, {4, 6, 7}, {8, 5, 0} };
        Board board5 = new Board(tiles5);
        
        // test tilesAt()
        assert board.tileAt(0, 0) == 0;
        assert board.tileAt(1, 0) == 4;
        assert board.tileAt(2, 2) == 6;
        
        // test toString()
        assert board.toString().equals("3\n 0  1  3 \n 4  2  5 \n 7  8  6 \n");
        
        // test size()
        assert board.size() == 3;
        
        // test tilePostionRight()
        assert board.tileAtGoalPosition(0, 0) == 1;
        assert board.tileAtGoalPosition(0, 1) == 2;
        assert board.tileAtGoalPosition(1, 1) == 5;
        assert board.tileAtGoalPosition(2, 0) == 7;
        assert board.tileAtGoalPosition(2, 2) == 0;
        
        // test hamming()
        assert board.hamming() == 4;
        assert board1.hamming() == 5;
        
        // test manhattan()
        assert board.manhattan() == 4;
        assert board1.manhattan() == 10;
        
        // test isGoal
        assert board.isGoal() == false;
        assert board1.isGoal() == false;
        assert board2.isGoal() == true;
        
        // test linearizeBoard
        int[] linearTiles = new int[3*3];
        board.linearizeBoard(linearTiles);
        assert board.tileAt(0, 0) == linearTiles[0];
        assert board.tileAt(0, 1) == linearTiles[1];
        assert board.tileAt(0, 2) == linearTiles[2];
        assert board.tileAt(1, 0) == linearTiles[3];
        assert board.tileAt(1, 1) == linearTiles[4];
        assert board.tileAt(1, 2) == linearTiles[5];
        assert board.tileAt(2, 0) == linearTiles[6];
        assert board.tileAt(2, 1) == linearTiles[7];
        assert board.tileAt(2, 2) == linearTiles[8];
        
        // test inversions
        assert board3.inversions() == 1;
        assert board4.inversions() == 1;
        assert board5.inversions() == 3;
        
        // test blankRowBoard
        assert board.blankRowBoard() == 0;
        assert board1.blankRowBoard() == 1;
        assert board2.blankRowBoard() == 2;
        assert board3.blankRowBoard() == 2;
        assert board4.blankRowBoard() == 3;
        
        // test isSolvable
        assert board.isSolvable() == true;
        assert board1.isSolvable() == true;
        assert board2.isSolvable() == true;
        assert board3.isSolvable() == false;
        assert board4.isSolvable() == false;
        
        // test equals
        assert board.equals(board) == true;
        assert board.equals(null) == false;
        assert board.equals(board1) == false;
        Board board6 = board;
        assert board.equals(board6) == true;
        int[][] tiles7 = { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} };
        Board board7 = new Board(tiles7);
        assert board.equals(board7) == true;
        
        // test neighbors
        int[][] tilesN1 = { {1, 0, 3}, {4, 2, 5}, {7, 8, 6} };
        Board boardN1 = new Board(tilesN1);
        int[][] tilesN2 = { {4, 1, 3}, {0, 2, 5}, {7, 8, 6} };
        Board boardN2 = new Board(tilesN2);
        Iterator<Board> i = board.neighbors().iterator();
        Board board8 = i.next();
        assert board8.equals(boardN1) == true;
        Board board9 = i.next();
        assert board9.equals(boardN2) == true; 
    }
    
}
