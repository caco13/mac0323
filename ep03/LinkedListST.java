/** ***********************************************************************
 *  Compilation:  javac LinkedListST.java
 *  Execution:    java LinkedListST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt  
 *  
 *  Symbol table implementation with an ordered linked list.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java LinkedListST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 *************************************************************************/

// The StdIn class provides static methods for reading strings and numbers from standard input.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdIn.html
import edu.princeton.cs.algs4.StdIn; 

// This class provides methods for printing strings and numbers to standard output.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdOut; 

// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html 
// http://codereview.stackexchange.com/questions/48109/simple-example-of-an-iterable-and-an-iterator-in-java
import java.util.Iterator; 

/** This is an implementation of a symbol table whose keys are comparable.
 * The keys are kept in increasing order in a linked list.
 * Following our usual convention for symbol tables, 
 * the keys are pairwise distinct.
 * <p>
 * For additional documentation, see 
 * <a href="http://algs4.cs.princeton.edu/31elementary/">Section 3.1</a> 
 * of "Algorithms, 4th Edition" (p.378 of paper edition), 
 * by Robert Sedgewick and Kevin Wayne.
 *
 */

public class LinkedListST<Key extends Comparable<Key>, Value> {
    // atributos de estado
    private Node first;
    public int n; //voltar com private
    private class Node {
        Key key;
        Value value;
        Node next;
    }

    
    /** Constructor.
     * Creates an empty symbol table with default initial capacity.
     */
    public LinkedListST() {
        this.first = null; // Java já inicializa com null?
        this.n = 0;
    }   

    /** Is the key in this symbol table?
     */
    public boolean contains(Key key) {
        // escreva seu método a seguir
        // TODO
        return false;
    }

    /** Returns the number of (key,value) pairs in this symbol table.
     */
    public int size() {
        // escreva seu método a seguir
        return n;
    }

    /** Is this symbol table empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /** Returns the value associated with the given key, 
     *  or null if no such key.
     *  Argument key must be nonnull.
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        if (this.isEmpty()) return null; //TODO: generates exception?
        // Busca nó pela chave
        Node current = this.first;
        while(current != null) {
            if (current.key.compareTo(key) == 0) return current.value;
            current = current.next;
        }
        return null;
    } 
    
    /** Returns the number of keys in the table 
     *  that are strictly smaller than the given key.
     *  Argument key must be nonnull.
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        // escreva seu método a seguir
        Node current = this.first;
        int n = 0;
        while(current.key != key) {
            current = current.next;
            n++;
        }
        return n;
    } 

    
    /** Search for key in this symbol table. 
     * If key is in the table, update the corresponding value.
     * Otherwise, add the (key,val) pair to the table.
     * Argument key must be nonnull.
     * If argument val is null, the key must be deleted from the table.
     */
    public void put(Key key, Value val)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        if (this.isEmpty()) {
            this.first = new Node();
            this.first.key = key;
            this.first.value = val;
            this.n++;
            return;
        }

        // Percorre tabela de símbolos. Se encontrar key
        // atribui val. Se não encontrar, insere par key-value
        // e rearranja tabela.
        Node current = this.first;
        Node previous = null;
        while (current.key.compareTo(key) < 0) {
            previous = current;
            if (current.next == null) {
                Node newKey = new Node();
                newKey.key = key;
                newKey.value = val;
                this.n++;
                previous.next = newKey;
                newKey.next = null;
                return;
            }
            current = current.next;
        }
        if (current.key.compareTo(key) == 0) {
            // Altera current.value com novo val
            current.value = val;
            return;
        }
        
        // Insere o novo nó
        Node newKey = new Node();
        newKey.key = key;
        newKey.value = val;
        this.n++;
        // Se o novo nó é o primeiro da lista
        if (previous == null) {
            this.first = newKey;
            newKey.next = current;
            return;
        }
        previous.next = newKey;
        newKey.next = current;
        return;
    }

    /** Remove key (and the corresponding value) from this symbol table.
     * If key is not in the table, do nothing.
     */
    public void delete(Key key)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        if (this.isEmpty()) throw new java.util.NoSuchElementException("delete(): Symbol table underflow error");
        // Se é o primeiro da lista
        if (this.first.key.compareTo(key) == 0) {
            this.deleteMin();
            return;
        }
        Node previous = this.first;
        Node current = this.first.next;
        while (current != null) {
            if (current.key.compareTo(key) == 0) {
                if (current.next == null) {
                    this.deleteMax();
                    return;
                } else {
                    previous.next = current.next;
                }
            }
        }
        return;
    } 

    /** Delete the minimum key and its associated value
     * from this symbol table.
     * The symbol table must be nonempty.
     */
    public void deleteMin() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMin(): Symbol table underflow error");
        // Se só tem um elemento na lista
        if (this.n == 1) {
            this.first = null;
            n = 0;
        } else {
            this.first = this.first.next;
        }
    }

    /** Delete the maximum key and its associated value
     * from this symbol table.
     */
    public void deleteMax() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMax(): Symbol table underflow error");
        Node previous = this.first;
        Node current = this.first.next;
        // Se só há um elemento na lista
        if (current == null) {
            this.first = null;
            n = 0;
            return;
        }
        while(current != null) {
            previous = current;
            current = current.next;
        }
        previous = null;
        return;
    }


   /***************************************************************************
    *  Ordered symbol table methods
    **************************************************************************/

    /** Returns the smallest key in this table.
     * Returns null if the table is empty.
     */
    public Key min() {
        return this.first.key;
    }

   
    /** Returns the greatest key in this table.
     * Returns null if the table is empty.
     */
    public Key max() {
        Node current = this.first;
        while (current.next != null) {
            current = current.next;
        }
        return current.key;
    }

    /** Returns a key that is strictly greater than 
     * (exactly) k other keys in the table. 
     * Returns null if k < 0.
     * Returns null if k is greater that or equal to 
     * the total number of keys in the table.
     */
    public Key select(int k) {
        if (k < 0 || k >= n) return null;
        Node current = this.first;
        for (int i = 0; i < k; i++) {
            current = current.next;
        }
        return current.next.key;
    }

    /** Returns the greatest key that is 
     * smaller than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is smaller than any key in the table), 
     * returns null.
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (this.first.key.compareTo(key) < 0) return null;
        Node current = this.first;
        while (current.next.key.compareTo(key) < 0) {
            current = current.next;
        }
        return current.key;
    }

    /** Returns the smallest key that is 
     * greater than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is greater than any key in the table), 
     * returns null.
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (this.first.key.compareTo(key) < 0) return null;
        Node current = this.first;
        while (current.key.compareTo(key) < 0) {
            current = current.next;
        }
        return current.key;
    }

    /** Returns all keys in the symbol table as an Iterable.
     * To iterate over all of the keys in the symbol table named st, use the
     * foreach notation: for (Key key : st.keys()).
     */
    public Iterable<Key> keys() {
        return new ListKeys();
    }

    
    
//    public class GenSet<Key> {   
//        private Key[] a;
//        public GenSet(Class<Key> c, int s) {
//            // Use Array native method to create array
//            // of a type only known at run time
//            @SuppressWarnings("unchecked")
//            final Key[] a = (E[]) Array.newInstance(c, s);
//            this.a = a;
//        }
//        
//        E get(int i) {
//            return a[i];
//        }
//    }
//    
    
    
    /**
     * implements Iterable<Key> significa que essa classe deve 
     * ter um método iterator(), acho...
     */
    private class ListKeys implements Iterable<Key> {
        /** 
         * Devolve um iterador que itera sobre os itens da ST 
         * da menor até a maior chave.<br>
         */
        public Iterator<Key> iterator() {
            return new KeysIterator();
        }
        
        private class KeysIterator implements Iterator<Key> {
            // variáveis do iterador
//            Key[] arrayKeys = (Key[]) new Object[n];
            String arrayKeys [] = new String [n];
            int i;
            
            public KeysIterator() {
                Node current = first;
//                StdOut.println(current.next); //DEBUG
                while(current.next != null) {
                    arrayKeys[i++] = current.key.toString();
//                    StdOut.println(current.key.toString()); //DEBUG
                    current = current.next;
                }
                i = 0;
                StdOut.println(n);
            }    
            
            public boolean hasNext() {
                return i >= n;
            }

            public Key next() {
                return (Key) arrayKeys[i++];
            }
                    
            public void remove() { 
                throw new UnsupportedOperationException(); 
            }
        }
    }


   /***************************************************************************
    *   Check internal invariants: pode ser útil durante o desenvolvimento 
    **************************************************************************/
    
    // are the items in the linked list in ascending order?
    private boolean isSorted() {
        // escreva seu método a seguir
        Node current = this.first;
        Node next = this.first.next;
        for (int i = 0; i < n; i++) {
            if (next.key.compareTo(current.key) > 0) return false;
            current = next;
            next = next.next;
        }
        return true;
    }

   /** Test client.
    * Reads a sequence of strings from the standard input.
    * Builds a symbol table whose keys are the strings read.
    * The value of each string is its position in the input stream
    * (0 for the first string, 1 for the second, and so on).
    * Then prints all the (key,value) pairs.
    */
    public static void main(String[] args) { 
        LinkedListST<String, Integer> st;
        st = new LinkedListST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
            
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}

