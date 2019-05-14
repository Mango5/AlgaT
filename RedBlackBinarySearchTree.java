import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

public class RedBlackBinarySearchTree<Key extends Comparable<Key>, Value> {
    // <Key extends Comparable<Key>, Value> tipo di dato parametrizzato
    // - final static- significa che il campo è una costante e associato solamente
    // alla seguente classe ()
    private static final boolean Red = true;
    private static final boolean Black = false;
    // radice dell'albero
    private Node root;

    private class Node {
        private Key key;
        private Node left;
        private Node right;
        private Value value;
        private boolean color;
        private int size;

        public Node(Key key, Value value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    // costruttore della classe RedBlackBinarySearchTree, inizializzazione vuota
    public RedBlackBinarySearchTree() {

    }

    // ritorna la dimensione del sotto-albero con nodo radice x
    private int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }

    // ritorna la dimensione dell'intero albero (radice root)
    private int size() {
        return size(root);
    }

    // ritorna true se se la symbol table è vuota, false altrimenti
    public boolean isEmpty() {
        return root == null;
    }

    private boolean isRed(Node node) {
        if (node == null)
            return false;
        return node.color == Red;
    }

    /***********************************************
     * Ricerca
     ***********************************************/
    // ritorna il valore associato alla chiave data in input, se la chiave è
    // presente nella symbol table, se no ritorna null
    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("l'argomento to  get() è  null");
        return get(root, key);
    }

    // ritorna il valore associato alla chiave nel sotto-albero radicato in node; se
    // la chiave non è presente ritorna null
    private Value get(Node node, Key key) {
        int cmp = 0;
        while (node != null) {
            // compareTo restituisce:
            // - valore negativo se key è minore di node.key
            // - zero se key è uguale a node.key
            // - valore positivo se key è maggiore di node.key
            cmp = key.compareTo(node.key);
            if (cmp < 0)
                node = node.left;
            else if (cmp > 0)
                node = node.right;
            else
                return node.value;
        }
        return null;
    }

    // ritorna true se la chiave in input è contenuta nella symbol table, false
    // altrimenti
    // se la chiave in input è null: throws IllegalArgumentException
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /*************************************************
     * Inserimento Albero Red-Black
     ************************************************/
    // inserisce la coppia chiave-valore nella symbol table
    // se la symbol table contiene già la chiave in input il vecchio valore viene
    // sovrascritto con quello nuovo (@param value)
    // se (@param value) è null, la chiave e il suo valore associato vengono
    // eliminati dalla symbol table.
    public void put(Key key, Value value) {
        // se key è null: throws IllegalArgumentException
        if (key == null)
            throw new IllegalArgumentException("il primo argomento to put() è null");
        // se value è null, la chiave specificata viene eliminata dalla symbol table
        // insieme al valore associato
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
        root.color = Black;
    }

    private Node put(Node node, Key key, Value value) {
        // ritorno un nodo, con chiave= key, valore= value, colore = red e dimensione=1
        if (node == null)
            return new Node(key, value, Red, 1);
        int cmp = 0;
        cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = put(node.left, key, value);
        else if (cmp > 0)
            node.right = put(node.right, key, value);
        else
            node.value = value;

        // sistemazione dei collegamenti tra i nodi per soddisfare la proprietà del
        // colore
        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);
        node.size = size(node.left) + size(node.right) + 1;

        return node;
    }

    /****************************************************
     * Cancellazione Albero Red-Black
     ****************************************************/
    // rimuove la chiave in input, se presente nella symbol table, e il suo valore
    // associato
    // se la key è null: throws IllegalArgumentException
    public void delete(Key key) {
        if (key == null)
            throw new IllegalArgumentException("l'argomento da eliminare è null");
        // se la chiave non è presente nella symbol table
        if (!contains(key))
            return;

        // se entrambi i figli della radice sono neri, settiamo il colore della radice a
        // rosso
        if (!isRed(root.left) && !isRed(root.right))
            root.color = Red;
        root = delete(root, key);
        if (!isEmpty())
            root.color = Black;

    }

    private Node delete(Node node, Key key) {
        // se la chiave in input (key) è minore della chiave del nodo in input
        if (key.compareTo(node.key) < 0) {
            // se il figlio sx di node e il figlio sx del figlio sx del node sono entrambi
            // Black
            if (!isRed(node.left) && !isRed(node.left.left))
                node = moveRedLeft(node);
            node.left = delete(node.left, key);
        } else { // se la chiave in input è uguale o maggiore delle chiave del nodo in input
                 // se il figlio sx di node è rosso applico una rotazione a destra
            if (isRed(node.left))
                node = rotateRight(node);
            if (key.compareTo(node.key) == 0 && (node.right == null))
                return null;
            if (!isRed(node.right) && !isRed(node.right.left))
                node = moveRedRight(node);
            if (key.compareTo(node.key) == 0) {
                Node x = min(node.right);
                node.key = x.key;
                node.value = x.value;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                node.right = deleteMin(node.right);
            } else {
                node.right = delete(node.right, key);
            }
        }
        return balance(node);
    }

    // elimina dalla symbol table la chiave più piccola e il suo valore associato
    public void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = Red;

        root = deleteMin(root);
        if (!isEmpty())
            root.color = Black;
        // assert check();
    }

    // elimina la coppia <chiave,valore> contenente la chiave più piccola nel
    // sotto-albero radicato in node

    private Node deleteMin(Node node) {
        if (node.left == null)
            return null;

        if (!isRed(node.left) && !isRed(node.left.left))
            node = moveRedLeft(node);

        node.left = deleteMin(node.left);
        return balance(node);
    }

    // elimina dalla symbol table la chiave più grande e il suo valore associato
    public void deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = Red;

        root = deleteMax(root);
        if (!isEmpty())
            root.color = Black;
        // assert check();
    }

    // elimina la coppia <chiave,valore> contenente la chiave più grande nel
    // sotto-albero radicato in node
    private Node deleteMax(Node h) {
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    /****************************************************
     * Funzioni ausiliare di aiuto Albero Red-Black
     ****************************************************/

    private Node rotateRight(Node node) {
        // assert (h != null) && isRed(h.left);
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = x.right.color;
        x.right.color = Red;
        x.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        return x;
    }

    private Node rotateLeft(Node node) {
        // assert (h != null) && isRed(h.right);
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = x.left.color;
        x.left.color = Red;
        x.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        return x;
    }

    // ribalta i colori del nodo in input e dei suoi figli
    private void flipColors(Node node) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) && isRed(h.left) && isRed(h.right))
        // || (isRed(h) && !isRed(h.left) && !isRed(h.right));
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    // Assumiamo che node.color = Red ed entrambi: node.left e node.left.left sono
    // Black
    // impostiamo il colore di node.left o uno dei suoi figli a Red
    private Node moveRedLeft(Node node) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    private Node balance(Node node) {
        if (isRed(node.right))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /****************************************************
     * Altre Funzioni
     ****************************************************/

    // ritorna l'altezza dell'albero binario di ricerca
    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null)
            return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /*************************************************************
     * Verifica dell'integrità della strutta ad albero Red-Black
     *************************************************************/

    private boolean check() {
        if (!isBST())
            System.out.println("Not in symmetric order");
        /*
         * if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent"); if
         * (!isRankConsistent()) StdOut.println("Ranks not consistent"); if (!is23())
         * StdOut.println("Not a 2-3 tree");
         */
        if (!isBalanced())
            System.out.println("Not balanced");
        return isBST() && isBalanced();
    }

    // mi chiedo se questo albero binario soddisfa l'ordine simmetrico
    // questo test assicura anche che la struttura dei dati è un albero binario
    // poiché l'ordine è rigoroso.
    private boolean isBST() {
        return isBST(root, null, null);
    }

    /*** ci serve????? */
    // mi chiedo se l'albero BST radicato in x ha tutte le chiavi strettamente tra
    // min e max
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null)
            return true;
        if (min != null && x.key.compareTo(min) <= 0)
            return false;
        if (max != null && x.key.compareTo(max) >= 0)
            return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // mi chiedo se tutti i percorsi dalla radice ad una foglia hanno lo stesso
    // numeri di nodi Black
    private boolean isBalanced() {
        int black = 0; // numero di nodi color Black presenti nel percorso
        Node x = root;
        while (x != null) {
            if (!isRed(x))
                black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    // mi chiedo se tutti i percorsi dalla radice ad una foglia hanno lo stesso
    // numero di nodi Black (@param black)
    private boolean isBalanced(Node x, int black) {
        if (x == null)
            return black == 0;
        if (!isRed(x))
            black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    /*************************************************************
     * Range per il conteggio e la ricerca
     *************************************************************/

    // ritorna tutte le chiavi presenti nella symbol table
    public Iterable<Key> keys() {
        if (isEmpty()) {
            return new LinkedList<>();
        }
        return keys(min(), max());
    }

    // ritorna tutte le chiavi nella symbol table che stanno all'interno del range
    // indicato
    // (@param min ) e (@param max) estremi inclusi
    public Iterable<Key> keys(Key min, Key max) {
        if (min == null)
            throw new IllegalArgumentException("first argument to keys() is null");
        if (max == null)
            throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new LinkedList<>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        keys(root, queue, min, max);
        return queue;
    }

    // tutte le chiavi comprese tra min e max, nel sotto-albero radicato in (@param
    // node)
    private void keys(Node node, Queue<Key> queue, Key min, Key max) {
        if (node == null)
            return;
        int cmplo = min.compareTo(node.key);
        int cmphi = max.compareTo(node.key);
        if (cmplo < 0)
            keys(node.left, queue, min, max);
        if (cmplo <= 0 && cmphi >= 0)
            queue.add(node.key);
        if (cmphi > 0)
            keys(node.right, queue, min, max);
    }

    /*************************************************************
     * ordinamento symbol table
     *************************************************************/

    // ritorna la chiave più piccola nella symbol table
    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    // ritorna la chiave più piccola nel sotto-albero radicato in node, null se non
    // ci sono chiavi
    private Node min(Node node) {
        // assert x != null;
        if (node.left == null)
            return node;
        else
            return min(node.left);
    }

    // ritorna la chiave più grande nella symbol table
    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    // ritorna la chiave più grande nel sotto-albero radicato in node, null se non
    // ci sono chiavi
    private Node max(Node x) {
        // assert x != null;
        if (x.right == null)
            return x;
        else
            return max(x.right);
    }

    // Test
    public static void main(String[] args) {
        RedBlackBinarySearchTree<String, Integer> st = new RedBlackBinarySearchTree<String, Integer>();
        st.put("A", 27);
        st.put("B", 10);
        st.put("C", 2);
        st.put("D", 6);
        st.put("E", 11);
        st.put("F", 30);
        /*
         * Scanner scanner = new Scanner(System.in); // per leggere la stringa da
         * console for (int i = 0; i < 10; i++) {
         * System.out.println("Please enter a key:"); String key = scanner.nextLine();
         * st.put(key, i); } scanner.close(); for (String s : st.keys())
         * System.out.println(s + " " + st.get(s)); System.out.println();
         */

        for (String key : st.keys()) {
            System.out.println(key + " " + st.get(key) + " " + );
            System.out.println();
        }

    }
}
