/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algat.tree;

/**
 *
 * @author chiaramengoli
 */
public class BinarySearchTree {
 
	public   Node root;
        
	public BinarySearchTree(){
		this.root = null;
	}
	
        //ritorna true se nell'albero con radice root è presente un nodo con chiave id
	public Node find(int id){
		Node current = root; //la ricerca parte dalla radice
		while(current!=null){
			if(current.key==id){
				return current;
			}else if(current.key<id){
				current = current.left;//scendo nel ramo sx se la chiave che stiamo cercando è minore della chiave del nodo corrente
			}else{
				current = current.right;//scendo nel ramo dx se la chiave che stiamo cercando è maggiore della chiave del nodo corrente
			}
		}
		return null;
	}
        
        //cancello il nodo che ha chiave uguale al valore del parametro id
	public Node delete(int id){
		Node parent = root;
		Node current = root;
		boolean isLeftChild = false;
		while(current.key!=id){
			parent = current;
			if(current.key>id){
				isLeftChild = true;
				current = current.left;
			}else{
				isLeftChild = false;
				current = current.right;
			}
			if(current ==null){
				return null; //ritorno false: il nodo non esiste
			}
		}
                //il ciclo while termina quando ho trovato il nodo
		//Caso 1: se il nodo da cancellare non ha nodi figli
		if(current.left==null && current.right==null){
			if(current==root){ //se il nodo da cancellare è la radice, viene posta a null --> viene cancellato tutto l'albero
				root = null;
			}
			if(isLeftChild == true){
				parent.left = null;
			}else{
				parent.right = null;
			}
		}
		//Caso 2 : se il nodo da cancellare ha un solo nodo figlio
                    //se l'unico figlio è il destro
		else if(current.right==null){
			if(current==root){
				root = current.left;
			}else if(isLeftChild){
				parent.left = current.left;
			}else{
				parent.right = current.left;
			}
		}
                //se l'unico figlio è il sinistro
		else if(current.left==null){
			if(current==root){
				root = current.right;
			}else if(isLeftChild){
				parent.left = current.right;
			}else{
				parent.right = current.right;
			}
                //Caso 3: se il nodo da cancellare ha entrambi i figli
		}else if(current.left!=null && current.right!=null){			
                        //cerchiamo l'elemento minimo nel sotto-albero sinistro
			Node successor	 = getSuccessor(current);
			if(current==root){
				root = successor;
			}else if(isLeftChild){
				parent.left = successor;
			}else{
				parent.right = successor;
			}			
			successor.left = current.left;
		}		
		return current;		
	}
	
	public Node getSuccessor(Node node){
		Node successsor =null;
		Node successsorParent =null;
		Node current = node.right;
		while(current!=null){
			successsorParent = successsor;
			successsor = current;
			current = current.left;
		}
		//check if successor has the right child, it cannot have left child for sure
		// if it does have the right child, add it to the left of successorParent.
                //successsorParent
		if(successsor!=node.right){
			successsorParent.left = successsor.right;
			successsor.right = node.right;
		}
		return successsor;
	}
        
        //inserimento nell'albero di un nuovo nodo con chiave id
	public Node insert(int id){
		Node newNode = new Node(id);
		if(root==null){ //se l'albero è vuoto, istanzio la radice con il nuovo nodo
			root = newNode;
			return newNode;
		}
		Node current = root;
		Node parent = null;
		while(true){
			parent = current;
			if(id<current.key){				
				current = current.left;
				if(current==null){
					parent.left = newNode;
					return parent.left;
				}
			}else{
				current = current.right;
				if(current==null){
					parent.right = newNode;
					return parent.right;
				}
			}
		}
	}
	public void display(Node root){
		if(root!=null){
			display(root.left);
			System.out.print(" " + root.key);
			display(root.right);
		}
	}
	public static void main(String arg[]){
		BinarySearchTree b = new BinarySearchTree();
		b.insert(3);b.insert(8);
		b.insert(1);b.insert(4);b.insert(6);b.insert(2);b.insert(10);b.insert(9);
		b.insert(20);b.insert(25);b.insert(15);b.insert(16);
		System.out.println("Original Tree : ");
		b.display(b.root);		
		System.out.println("");
		System.out.println("Check whether Node with value 4 exists : " + b.find(4));
		System.out.println("Delete Node with no children (2) : " + b.delete(2));		
		b.display(root);
		System.out.println("\n Delete Node with one child (4) : " + b.delete(4));		
		b.display(root);
		System.out.println("\n Delete Node with Two children (10) : " + b.delete(10));		
		b.display(root);
	}

    public class Node {
       public int key;
	public Node left;
       public Node right;
       public Node parent;
        public Node(int data) {
            this.key=data;
            left = null;
            right = null;
            parent = null;
        }
    }
}
