/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algat.tree;

import java.awt.Color;
/**
 *
 * @author chiaramengoli
 */
public class RedBlackTree extends BinarySearchTree{
    /** Colore per un nodo rosso. */
    protected static final Color RED = Color.red;

    /** Colore per un nodo nero. */
    protected static final Color BLACK = Color.black;
    
    /* Eccezione lanciata da {@link #blackHeight} se l'altezza nera di un nodo non è definita correttamente.*/
    public static class BlackHeightException extends RuntimeException
    {
    }
    
     protected class Node extends BinarySearchTree.Node
    {
         //un nodo può avere colore rosso o nero
         protected Color color;
         
         //inizializzo un nuovo nodo con chiave (@param data), di colore rosso e con i figli null
         public Node(int data)
	{
	    super(data);
	    this.color = RED;
	}
     }
     
     
    public RedBlackTree()
    {
	root = null;
    }
    
    //INSERIMENTO

    //inseriamo un nuovo nodo con chiave (@param key) nell'albero
      protected void treeInsert(int key)
    { 
	Node newNode = (Node)super.insert(key);
	insertFixup(newNode);
    }
      //Ripristiniamo le condizioni di colore (rosso-nero) dell'albero dopo l'inserimento di un nodo.
       protected void insertFixup(Node newNode)
    {
	Node y = null;

	while (((Node) newNode.parent).color == RED) {
	    if (newNode.parent == newNode.parent.parent.left) {
		y = (Node) newNode.parent.parent.right;
		if (y.color == RED) {
		    ((Node) newNode.parent).color = BLACK;
		    y.color = BLACK;
		    ((Node) newNode.parent.parent).color = RED;
		    newNode = (Node) newNode.parent.parent;
		}
		else {
		    if (newNode ==  newNode.parent.right) {
			newNode = (Node) newNode.parent;
			leftRotate(newNode);
		    }
		    
		    ((Node) newNode.parent).color = BLACK;
		    ((Node) newNode.parent.parent).color = RED;
		    rightRotate((Node) newNode.parent.parent);
		}
	    }
	    else {
		y = (Node) newNode.parent.parent.left;
		if (y.color == RED) {
		    ((Node) newNode.parent).color = BLACK;
		    y.color = BLACK;
		    ((Node) newNode.parent.parent).color = RED;
		    newNode = (Node) newNode.parent.parent;
		}
		else {
		    if (newNode ==  newNode.parent.left) {
			newNode = (Node) newNode.parent;
			rightRotate(newNode);
		    }
		    
		    ((Node) newNode.parent).color = BLACK;
		    ((Node) newNode.parent.parent).color = RED;
		    leftRotate((Node) newNode.parent.parent);
		}
	    }
	}
	((Node) root).color = BLACK;
    }
       
    //ROTAZIONE 
       
       //rotazione sx su un nodo (@param node); il figlio dx di node diventa il padre di node
    protected void leftRotate(Node node)
    {
	Node y = (Node) node.right;

	// Swap the in-between subtree from y to x.
	node.right = y.left;
	if (y.left != null)
	    y.left.parent = node;

	// Make y the root of the subtree for which x was the root.
	y.parent = node.parent;
	
	// If x is the root of the entire tree, make y the root.
	// Otherwise, make y the correct child of the subtree's
	// parent.
	if (node.parent == null)
	    root = y;
	else 
	    if (node == node.parent.left)
		node.parent.left = y;
	    else
		node.parent.right = y;

	// Relink x and y.
	y.left = node;
	node.parent = y;
    }
     
    //rotazione dx su un nodo (@param node); il figlio sx di node diventa il padre di node
     protected void rightRotate(Node node)
    {
	Node y = (Node) node.left;

	node.left = y.right;
	if (node.left != null)
	    y.right.parent = node;

	y.parent = node.parent;

	y.right = node;
	node.parent = y;

	if (root == node)
	    root = y;
	else
	    if (y.parent.left == node)
		y.parent.left = y;
	    else
		y.parent.right = y;
    }
     
     //CANCELLAZIONE 
    /*  public void delete(Node node)
    {
        Node corrente = node;
	Node x = (Node) null;

	if (node == null)
	    throw new IllegalArgumentException("il nodo da cancellare è  null");
	    
	if (node.left != null && node.right != null)
	    corrente = (Node) successor(node);

	if (node.left != null)
	    x = (Node) corrente.left;
	else
	    x = (Node) corrente.right;

	x.parent = corrente.parent;

	if (corrente.parent == null)
	    root = x;
	else
	    if (corrente == corrente.parent.left)
		corrente.parent.left = x;
	    else
		corrente.parent.right = x;

	if (corrente != node) {
	    corrente.left = node.left;
	    corrente.left.parent = corrente;
	    corrente.right = node.right;
	    corrente.right.parent = corrente;
	    corrente.parent = node.parent;
	    if (node == root)
		root = corrente;
	    else
		if (node == node.parent.left)
		    node.parent.left = corrente;
		else
		    node.parent.right = corrente;
	}

	if (corrente.color == BLACK)
	    deleteFixup(x);
    }*/
     
     public void treeDelete(int key){
         Node delNode = (Node) super.delete(key);
         if (delNode.color == BLACK)
                deleteFixup(delNode);
     }
      
       protected void deleteFixup(Node delNode)
    {
	while (delNode != root && delNode.color == BLACK) {
	    if (delNode.parent.left == delNode) {
		Node w = (Node) delNode.parent.right;

		if (w.color == RED) {
		    w.color = BLACK;
		    ((Node) delNode.parent).color = RED;
		    leftRotate((Node) delNode.parent);
		    w = (Node) delNode.parent.right;
		}

		if (((Node) w.left).color == BLACK 
		    && ((Node) w.right).color == BLACK) {
		    w.color = RED;
		    delNode = (Node) delNode.parent;
		}
		else {
		    if (((Node) w.right).color == BLACK) {
			((Node) w.left).color = BLACK;
			w.color = RED;
			rightRotate(w);
			w = (Node) delNode.parent.right;
		    }

		    w.color = ((Node) delNode.parent).color;
		    ((Node) delNode.parent).color = BLACK;
		    ((Node) w.right).color = BLACK;
		    leftRotate((Node) delNode.parent);
		    delNode = (Node) root;
		}
	    }
	    else {
		Node w = (Node) delNode.parent.left;

		if (w.color == RED) {
		    w.color = BLACK;
		    ((Node) delNode.parent).color = RED;
		    rightRotate((Node) delNode.parent);
		    w = (Node) delNode.parent.left;
		}

		if (((Node) w.right).color == BLACK 
		    && ((Node) w.left).color == BLACK) {
		    w.color = RED;
		    delNode = (Node) delNode.parent;
		}
		else {
		    if (((Node) w.left).color == BLACK) {
			((Node) w.right).color = BLACK;
			w.color = RED;
			leftRotate(w);
			w = (Node) delNode.parent.left;
		    }

		    w.color = ((Node) delNode.parent).color;
		    ((Node) delNode.parent).color = BLACK;
		    ((Node) w.left).color = BLACK;
		    rightRotate((Node) delNode.parent);
		    delNode = (Node) root;
		}		
	    }
	}
	delNode.color = BLACK;
    }
//RICERCA
     public boolean treeFind(int key){
         Node find = (Node) super.find(key);
        if(find != null)
            return true;
        return false;
     }  
      
//ALTEZZA NERA DELL'ALBERO
    //ritorna il numero di nodi black nel percorso dal nodo dato a qualsiasi foglia
    //BlackHeightException: se il numero di nodi black sul percorso lungo il figlio sx ad arivare a qualsiasi foglia è diverso da 
       //quello sul percorso lungo il figlio dx
     public int blackHeight(Node z)
    {
	if (z == null)
	    return 0;

	int left = blackHeight((Node) z.left);
	int right = blackHeight((Node) z.right);
	if (left == right)
	    if (z.color == BLACK)
		return left + 1;
	    else
		return left;
	else
	    throw new BlackHeightException();
    }
       
    //Restituisce il numero di nodi neri dalla radice fino a qualsiasi foglia. 
     //Il valore dovrebbe essere lo stesso per tutti i percorsi.
    public int blackHeight()
    {
	return blackHeight((Node) root);
    }
}
