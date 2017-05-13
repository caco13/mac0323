import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {
    // State variables
    private final int side;
    private int openSites;
    private boolean sitesOpened [];
    private QuickFindUF uF;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("Percolation(): n must be > 0.");
        }
        side = n;
        openSites = 0;
        initializeGrid(side, side);
        uF = new QuickFindUF(linearIndex(side, side) + 1);
    }
    
    public void open(int row, int col) {
        testOutOfBounds(row, col);
        int n = linearIndex(row, col);
        sitesOpened[n] = true;
        openSites++;
        
        // Faz conexão com sítios vizinhos
        int p, q;
        p = linearIndex(row, col);
        if (row - 1 >= 1) { //sítio não está na primeira linha
            q = linearIndex(row - 1, col);
            if (isOpen(row - 1, col)) uF.union(p, q);
        }
        if (col + 1 <= side) { //sítio não está na última coluna
            q = linearIndex(row, col + 1);
            if (isOpen(row, col + 1)) uF.union(p, q);
        }
        if (row + 1 <= side) { //sítio não está na última linha
            q = linearIndex(row + 1, col);
            if (isOpen(row + 1, col)) uF.union(p, q);
        }
        if (col - 1 >= 1) { //sítio não setá n primeira coluna
            q = linearIndex(row, col - 1);
            if (isOpen(row, col - 1)) uF.union(p, q);
        }
                
    }
    
    public boolean isOpen(int row, int col) {
        int n = linearIndex(row, col);
//        StdOut.println("site " + n + " opened? " + sitesOpened[n]); //DEBUG
        return sitesOpened[n];
    }
    
    public boolean isFull(int row, int col) {
        if (row == 1) { //sítios abertos na primeira linha estão cheios
//            StdOut.println(row + ", " + col + " " + isOpen(row, col));//DEBUG
            if (isOpen(row, col)) return true;
        }
        
        int p, q;
        p = linearIndex(row, col);
        for (int j = 1; j <= side; j++) {
//            StdOut.println(row + ", " + col + " " + isOpen(row, col));//DEBUG
            if (isOpen(1, j)) {        //verificamos se sítio (row, col) está 
                q = linearIndex(1, j); //conectado com sítio na primeira linha
//                StdOut.println("uF.connected(" + p + ", " + q + "): " + uF.connected(p, q)); //DEBUG
//                StdOut.println(uF.count()); //DEBUG
                if (uF.connected(p, q)) return true;
            }
        }
        
        return false;
    }
    
    public int numberOfOpenSites() {
        return openSites;
    }
    
    public boolean percolates() {
        int p, q;
        for (int firstRowCol = 1; firstRowCol <= side; firstRowCol++) {
            p = linearIndex(1, firstRowCol);
            for (int lastRowCol = 1; lastRowCol <= side; lastRowCol++) {
                q = linearIndex(side, lastRowCol);
                if (isOpen(firstRowCol, lastRowCol) && uF.connected(p, q)) {
//                    StdOut.println(p + ", " + q); //DEBUG
                    return true;
                }
            }
        }
        return false;
    }
    
    // Private methods
    /**
     * linearIndex
     * -----------
     * Returns the one-dimensional index for Union-Find ADT.
     * Indexes for Union Find starts in 0: (0, 0).
     */
    private int linearIndex(int row, int col) {
        return (row - 1) * side + (col - 1);
    }
    
    /**
     * testOutOfBounds
     * ---------------
     * Returns exception if row or col is out of range
     * defined in constructor.
     */
    private void testOutOfBounds(int row, int col) {
        if (row < 1 || row > side || col < 1 || col > side) {
            // TODO: include Class.method in exception message
            throw new java.lang.IndexOutOfBoundsException("(row,col) out of bounds.");
        }
    }
    
    /**
     * initializeGrid
     * --------------
     * Initializes the grid with all sites closed giving side
     * dimension to openSites and initializing it with false
     * to all positions
     */
    private void initializeGrid(int row, int col) {
        int maxIndexUF = linearIndex(row, col);
        boolean [] tmp = new boolean [maxIndexUF + 1];
        for (int i = 0; i <= maxIndexUF; i++) {
            tmp[i] = false;
        }
        sitesOpened = tmp;
    }
    
    // Testes unitários
    public static void main(String[] args) {
        
        // Cria dois objetos Percolation
        Percolation p = new Percolation(2);
        Percolation p1 = new Percolation(4);
        
        // Testa construtor com argumento ilegal
        // TODO: testar exceção sem gerá-la
        //Percolation p2 = new Percolation(-1);
        
        // Testa linearização do índice
        assert p.linearIndex(1, 1) == 0;
        assert p.linearIndex(1, 2) == 1;
        assert p.linearIndex(2, 1) == 2;
        assert p.linearIndex(2, 2) == 3;
        assert p1.linearIndex(4, 3) == 14;
        
        // Testa ponto fora da chapa
        // TODO: testar exceção
        //p.open(-1, -1);
        
        // Testa se todos os sítios foram inicializados fechados
        for (int i = 0; i < p1.linearIndex(p1.side, p1.side); i++) {
            assert p1.sitesOpened[i] == false;
        }
        
        // Testa abertura de sítios
        int n = p1.linearIndex(2, 2);
        assert p1.sitesOpened[n] == false;
        p1.open(2, 2);
        assert p1.sitesOpened[n] == true;
        int n1 = p1.linearIndex(3, 1);
        assert p1.sitesOpened[n1] == false;
        p1.open(3, 1);
        assert p1.sitesOpened[n1] == true;
        
        // Testa isOpened
        assert p1.isOpen(1, 1) == false;
        p1.open(1, 1);
        n = p1.linearIndex(1, 1);
        assert p1.isOpen(1, 1) == true;
        
        // Testa número de sítios abertos (p1 já tem 3 sítios abertos).
        assert p1.numberOfOpenSites() == 3;
        
        // Leitura do aquivo passado no argumento
        In in = new In(args[0]);
        
        // Testa leitura da primeira linha do arquivo input2.txt.
        // Executar:
        // $ java-algs4 -ea Percolation percolation_tests/dados/input2.txt
        // TODO: colocar condição para reconhecer leitura do arquivo
        // do arquivo input2.txt.
        // Lê primeira linha contendo número de sítios e testa
        int gridLength = in.readInt();
        assert gridLength == 2;
        
        Percolation p3 = new Percolation(gridLength);
        // Testa QuickFindUF para arquivo input2.txt
        // Executar:
        // $ java-algs4 -ea Percolation percolation_tests/dados/input2.txt
        // TODO: colocar condição para reconhecer leitura do arquivo
        // do arquivo input2.txt.
        assert p3.uF.count() == 4;
        assert p3.uF.find(0) == 0;
        assert p3.uF.find(1) == 1;
        assert p3.uF.find(2) == 2;
        assert p3.uF.find(3) == 3;
        
        // Cada dois inteiros lidos em uma linha
        // representam o sítio que é aberto.
        int row = 0, col = 0;
        while (!in.isEmpty()) {
            row = in.readInt();
            col = in.readInt();
            p3.open(row, col);
        }
        // Testa se linha e coluna da segunda linha do arquivo foi
        // lida corretamente.
        // Executar:
        // $ java-algs4 -ea Percolation percolation_tests/dados/input2.txt
        // TODO: colocar condição para reconhecer leitura do arquivo
        // do arquivo input2.txt.
        assert row == 1;
        assert col == 2;
        
        // Testa se sítios abertos foram conectados corretamente
        // Executar:
        // $ java-algs4 -ea Percolation percolation_tests/dados/input2.txt
        // TODO: colocar condição para reconhecer leitura do arquivo
        // do arquivo input2.txt.
        // Índices do objeto
        assert p3.uF.connected(p3.linearIndex(1, 1), p3.linearIndex(1, 2)) == true;
        assert p3.uF.connected(p3.linearIndex(1, 2), p3.linearIndex(1, 1)) == true;
        assert p3.uF.connected(p3.linearIndex(1, 2), p3.linearIndex(2, 2)) == true;
        assert p3.uF.connected(p3.linearIndex(2, 2), p3.linearIndex(1, 2)) == true;
        assert p3.uF.connected(p3.linearIndex(1, 1), p3.linearIndex(2, 1)) == false;
        assert p3.uF.connected(p3.linearIndex(2, 1), p3.linearIndex(1, 1)) == false;
        assert p3.uF.connected(p3.linearIndex(2, 1), p3.linearIndex(2, 2)) == false;
        assert p3.uF.connected(p3.linearIndex(2, 2), p3.linearIndex(2, 1)) == false;
        
        //Testa se sítios estão cheio
        // Executar:
        // $ java-algs4 -ea Percolation percolation_tests/dados/input2.txt
        // TODO: colocar condição para reconhecer leitura do arquivo
        // do arquivo input2.txt.
        assert p3.isFull(1, 1) == true;
        assert p3.isFull(1, 2) == true;
        assert p3.isFull(2, 1) == false;
        assert p3.isFull(2, 2) == true;
        
        // Testa número de componentes após conexões no arquivo input2.txt
        // Executar:
        // $ java-algs4 -ea Percolation percolation_tests/dados/input2.txt
        // TODO: colocar condição para reconhecer leitura do arquivo
        // do arquivo input2.txt.
        // São dois componentes, 1 resultante das conexões efetuadas acima,
        // outro representando o sítio fechado.
        assert p3.uF.count() == 2;
        
        // Testa percolação com dados do arquivo input2.txt
        // $ java-algs4 -ea Percolation percolation_tests/dados/input2.txt
        // TODO: colocar condição para reconhecer leitura do arquivo
        // do arquivo input2.txt.
        assert p3.percolates() == true;
    }

}