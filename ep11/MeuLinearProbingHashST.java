/*************************************************************************
 *  Compilation:  javac MeuLinearProbingHashST.java
 *  Execution:    java MeuLinearProbingHashST <alfa inf> <alfa sup> <arquivo>
 *  
 *  Symbol table implementation with linear probing hash table.
 *
 *************************************************************************/

/**
   The LinearProbingHashST class represents a symbol table of generic
   key-value pairs. 

   It supports the usual put, get, contains, delete, size, and is-empty
   methods. It also provides a keys method for iterating over all of the
   keys. A symbol table implements the associative array abstraction:
   when associating a value with a key that is already in the symbol
   table, the convention is to replace the old value with the new
   value. 

   Unlike Map, this class uses the convention that values cannot
   be null—setting the value associated with a key to null is equivalent
   to deleting the key from the symbol table.
*/
import edu.princeton.cs.algs4.LinearProbingHashST; 

// The Queue class represents a first-in-first-out (FIFO) queue of generic items.
import edu.princeton.cs.algs4.Queue;

// Input. This class provides methods for reading strings and numbers from standard input,
// file input, URLs, and sockets.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/In.html
import edu.princeton.cs.algs4.In; // arquivo

// This class provides methods for printing strings and numbers to standard output.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdOut; 

// Stopwatch. This class is a data type for measuring the running time (wall clock) of a program.
// https://www.ime.usp.br/~pf/sedgewick-wayne/algs4/documentation/index.html
import edu.princeton.cs.algs4.Stopwatch; // arquivo


public class MeuLinearProbingHashST<Key extends Object, Value> {
    // largest prime <= 2^i for i = 3 to 31
    // not currently used for doubling and shrinking
    // NOTA: Esses valores são todas as possíveis dimensões da tabela de hash.
    private static final int[] PRIMES = {
        7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
        32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
        8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
        536870909, 1073741789, 2147483647
    };

    private static final int INIT_CAPACITY = PRIMES[0];

    // limite inferior default para o fator de carga
    private static final double ALFAINF_DEFAULT = 0.125;
    
    // limite superior default para o fator de carga
    private static final double ALFASUP_DEFAULT = 0.5;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values

    // NOTA: indice na tabela de primos correspondente ao valor de 'm'
    private int iPrimes = 0;

    // NOTA: alfa é o fator de carga (= load factor) n/m
    //       no caso do tratamento de colisão por sondagem linear alfa é
    //       a porcentagem da tabela que está ocupada.
    //       alfaSup é o limite superior para o fator de carga.
    //       Usado no método put().
    private final double alfaSup;

    // NOTA: alfa é o fator de carga (= load factor) n/m
    //       no caso do tratamento de colisão por sondagem linear alfa é
    //       a porcentagem da tabela que está ocupada.
    //       alfaSup é o limite superior para o fator de carga.
    //       Usado no método delete().
    private final double alfaInf;
    

    /** 
    * Construtor: cria uma tabela de espalhamento 
    * com resolução de colisões por encadeamento. 
    */
    public MeuLinearProbingHashST() {
        this(INIT_CAPACITY, ALFAINF_DEFAULT, ALFASUP_DEFAULT);
    }

   /** 
    * Construtor: cria uma tabela de espalhamento 
    * com (pelo menos) m posições.
    */
    public MeuLinearProbingHashST(int m) {
        this(m, ALFAINF_DEFAULT, ALFASUP_DEFAULT);
    }

   /** 
    * Construtor: cria uma tabela de espalhamento 
    * em que a maior porcentagem de posiçõs preenchidas é 
    * alfaSup e a menor porcetagem é alfaInf (bem, se a tabela for 
    * muito pequena pode ser menor que alfaInf).
    */
    public MeuLinearProbingHashST(double alfaInf, double alfaSup) {
        this(INIT_CAPACITY, alfaInf, alfaSup);
    } 

    /** 
     * Construtor.
     *
     * Cria uma tabela de hash vazia com PRIMES[iPrimes] posições sendo
     * que iPrimes >= 0 e
     *         PRIMES[iPrimes-1] < m <= PRIMES[iPrimes]
     * (suponha que PRIMES[-1] = 0).
     * 
     * Além disso a tabela criada será tal que o fator de carga alfa
     * respeitará
     *
     *            alfaInf <= alfa <= alfaSup
     *
     * A primeira desigualdade pode não valer quando o tamanho da tabela
     * é INIT_CAPACITY.
     *
     * Pré-condição: o método supõe que alfaInf < alfaSup.
     */
    public MeuLinearProbingHashST(int m, double alfaInf, double alfaSup) {
        // TAREFA: veja o método original e faça as adaptações necessárias
        // implement it!
        if (alfaInf > alfaSup)
            throw new IllegalArgumentException("alfaInf argument must be less then alfaSup argument");
        if (m < INIT_CAPACITY | m > PRIMES[PRIMES.length - 1])
            throw new IllegalArgumentException("argument m must be between " + INIT_CAPACITY + " and " + PRIMES[28]);
        this.m = initM(m);
        this.n = 0;
        keys = (Key[]) new Object[this.m];
        vals = (Value[]) new Object[this.m];
        this.alfaInf = alfaInf;
        this.alfaSup = alfaSup;
    }
    
    // return the number of key-value pairs in the symbol table
    public int size() {
        return n;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // does a key-value pair with the given key exist in the symbol table?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /* return the value associated with the given key, null if no such value
     *
     * Supõe que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     */
    public Value get(Key key) {
        for (int i = hash(key); vals[i] != null; i = (i + 1) % m) 
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }
    
    /** 
     *
     * Redimensiona a tabela de hash de modo que ela tenha PRIMES[k]
     * listas e reinsere todos os itens na nova tabela.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     * Assim, o índice k corresponde ao valor PRIMES[k] que será o novo 
     * tamanho da tabela.
     */
    private void resize(int k) {
        // TAREFA: veja o método original e faça adaptação para que
        //         o tamanho da nova tabela seja PRIMES[k].
    }

    /**
     * put(): insert the key-value pair into the symbol table
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     */
    public void put(Key key, Value val) {
        // TAREFA: veja o método original e faça adaptação para que
        //         a tabela seja redimensionada se o fator de carga
        //         passar de alfaSup.
    }


    // delete the key (and associated value) from the symbol table
    public void delete(Key key) {
        // TAREFA: veja o método original e adapte para que a tabela 
        //         seja redimensionada sempre que o fator de carga for menor que
        //         alfaInf.
    }

    /**
     * return all of the keys as in Iterable
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (vals[i] != null) queue.enqueue(keys[i]);
        return queue;
    }

    /**
     * check():
     * integrity check - don't check after each put() because
     * integrity not maintained during a delete()
     */
    private boolean check() {
        // TAREFA: veja o método original e adapte para verificar que
        //         a tabela de hash está no máximo alfaSup% cheia.
        // implement it!
        return false;
    }

    /********************************************************************
     *  ALGUNS MÉTODOS NOVOS
     *
     *********************************************************************/

    // retorna o tamanha da tabela de hash
    public int sizeST() {
        return m;
    } 

    /** 
     * maxCluster(): retorna o maior comprimento de um cluster.
     *
     * O custo médio de sondagem linear depende da maneira em que
     * as chaves são agrupadas em pedaços contínuos da tabela.
     * Esses pedaços são chamados de clusters.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */ 
    public int maxCluster() {
        // TAREFA
        // implement it!
        return -1;
    }

    /** 
     * numCluster(): retorna o número de clusters.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     */ 
    public int numClusters() {
        // TAREFA
        // implement it!
        return -1;
    }
    
    
    /**
     * NOTA:
     * Proposição: Em uma tabela de hash com sondagem linear, m posições e
     *   n = alfa*m chaves, o número médio de sondagens (probes), supondo
     *   que a função de hashing satisfaz a hipótese do hashing uniforme, é
     *
     *            0.5 * (1 + 1/(1-alfa)) 
     *
     *   para buscas bem-sucedidas e
     *
     *            0.5 * (1 + a/(1-alfa)^2) 
     *
     *   para buscas malsucedidas.
     */


    /**
     * averageSearchHit(): retorna o custo médio de uma busca
     * bem-sucedida na tabela supondo que cada chave da tabela tem a
     * mesma probabilidade de ser buscada.
     *
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */
    public double averageSearchHit() {
        // TAREFA
        // implement it!
        return -1.0;
    }

    /**
     * averageSearchMiss(): retorna o custo médio de uma busca malsucedida 
     * (que é também o custo de uma inserção) na tabela supondo que a função de hashing 
     * satisfaz a hipótese de hashing uniforme.
     *
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */
    public double averageSearchMiss() {
        // TAREFA
        // implement it!
        return -1.0;
    }

    /**
     * My private methods
     */
    private int initM(int m) {
        for (int i = 0; i < PRIMES.length; i++) {
            if (m <= PRIMES[i])
                return PRIMES[i];
        }
        return -1; // TODO: return -1?
    }

        
   /***********************************************************************
    *  Unit test client.
    *  Altere à vontade, pois este método não será corrigido.
    ***********************************************************************/
    public static void main(String[] args) {
        if (args.length != 3) {
            showUse();
            return;
        }
        
        String s;
        double alfaInf = Double.parseDouble(args[0]);
        double alfaSup = Double.parseDouble(args[1]);
        String fileName = args[2];
        
        /**
         * Meus testes
         */
        // Testa construtor
        In in = new In(fileName);
        MeuLinearProbingHashST<String, Integer> meuST1 = new MeuLinearProbingHashST<String, Integer>(alfaInf, alfaSup);
        assert meuST1.m == PRIMES[0];
        assert meuST1.alfaInf == alfaInf;
        assert meuST1.alfaSup == alfaSup;
        assert meuST1.n == 0;
        assert ((Object[]) meuST1.keys).length == PRIMES[0];
        assert ((Object[]) meuST1.vals).length == PRIMES[0];
        in.close();
        
    }
    
    private static void showUse() {
        String msg = "Uso: meu_prompt> java MeuLinearProbingingHashST <alfa inf> <alfa sup> <nome arquivo>\n"
            + "    <alfa inf>: limite inferior para o comprimento médio das listas (= fator de carga)\n"
            + "    <alfa sup>: limite superior para o comprimento médio das listas (= fator de carga)\n"
            + "    <nome arquivo>: nome de um arquivo com um texto para que uma ST seja\n"
            + "                    criada com as palavras nesse texto.";
        StdOut.println(msg);
    }

}

