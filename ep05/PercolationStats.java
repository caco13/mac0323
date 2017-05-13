import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    // state variables
    private final int n;
    private final int trials;
    private Percolation perc;
    
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new java.lang.IllegalArgumentException("PercolationStats(): n and trials must be greater then or equal 1.");
        }
        this.n = n;
        this.trials = trials;
        perc = new Percolation(this.n);
    }
    
    public double mean() {
        //TODO: implementar!
        return -1;
    }
   
    public double stddev() {
        //TODO: implementar!
        return -1;
    }
    
    public double confidenceLow() {
        //TODO: implementar!
        return -1;
    }
    
    public double confidenceHigh() {
        //TODO: implementar!
        return -1;
    }
    
    // private methods
    private void createRandomGrid() {
        // TODO: para cada trails faça
        int randRow, randCol;
        while (! this.perc.percolates()) {
            randRow = StdRandom.uniform(1, this.n+1);
            randCol = StdRandom.uniform(1, this.n+1);
            if (! this.perc.isOpen(randRow, randCol)) {
                this.perc.open(randRow, randCol);
            }
        }
    }
    
    // unit testing
    public static void main (String [] args) {
        // Testa exceção na instanciação de objeto PercolationStats
        // com argumentos ilegais.
        //TODO: makes assertion.
//        PercolationStats psIllegal = new PercolationStats(-1, 1);
//        PercolationStats psIllegal1 = new PercolationStats(1, -1);
        
        // Instancia objeto PercolationStats
        PercolationStats ps = new PercolationStats(5, 1);
        
        // Testa atribuição de variáveis de estado
        assert ps.n == 5;
        assert ps.trials == 1;
        
        // Testa objeto Percolates criado no construtor.
        // Todos os sítios devem estar bloqueados.
        for (int row = 1; row < ps.n; row++) {
            for (int col = 1; col < ps.n; col++) {
                assert ps.perc.isOpen(row, col) == false;
            }
        }
        
        // Testa geração de número aleatório
        // dentro do intervalo 1, 5 do grid criado
        // a partir do objeto ps.
        //TODO: assert between [1, 6);
        StdRandom.uniform(1, 6);
        
        // Testa criação de _grid_ com sítios abertos aleatoriamente
        // até que o sistema percole.
        // Descomentar as duas linhas abaixo.
        //ps.createRandomGrid();
        //StdOut.println((double) ps.perc.numberOfOpenSites() / (ps.n*ps.n));
        
    }
    
}