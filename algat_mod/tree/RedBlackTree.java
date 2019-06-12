package algat_mod.tree;


import javafx.scene.paint.Color;

public class RedBlackTree {
	
    public Nodo root;
	//Colore per un nodo rosso
    protected static final Color RED = Color.RED;

    //Colore per un nodo nero
    protected static final Color BLACK = Color.BLACK;
    
	public RedBlackTree(){         
		root = null;
	}
	
	 public void setRoot(int chiave){
        root = new Nodo(chiave);   
	 }
	
	 
    //ritorna il nodo se nell'albero con radice root e' presente un nodo con chiave id
    public Nodo find(int id){
    	Nodo current = root; //la ricerca parte dalla radice
        while(current!=null){
        	if(current.key==id)
        		return current;
        	else {
        		if(current.key<id)
        			current = current.left;//scendo nel ramo sx se la chiave che stiamo cercando e' minore della chiave del nodo corrente
        		else
        			current =  current.right;//scendo nel ramo dx se la chiave che stiamo cercando e' maggiore della chiave del nodo corrente
        	}
        }
        return null;
    }
	
	
    public boolean treeFind(int key){
    	Nodo find =  find(key);
    	if(find != null)
    		return true;
    	return false;
    }
    
    
    public Nodo getSuccessor(Nodo node){
		Nodo successor =null;
		Nodo successorParent =null;
		Nodo current =  node.right;
		while(current!=null){
			successorParent = successor;
			successor = current;
			current = current.left;
		}
		//verifichiamo che successor non abbia un figlio destro
		//se ce l'ha, lo aggiungiamo alla sinistra di successorparent
		if(successor!=node.right){
			successorParent.left = successor.right;
			successor.right = node.right;
		}
		return successor;
	}
        
	
    //cancello il nodo che ha chiave uguale al valore del parametro id
	public Nodo delete(int id){
		Nodo parent = root;
		Nodo current = root;
		boolean isLeftChild = false;
		while(current.key!=id){
			parent = current;
			if(current.key>id){
				isLeftChild = true;
				current = current.left;
			}
			else{
				isLeftChild = false;
				current = current.right;
			}
			if(current == null){
				return null; //ritorno null: il nodo non esiste
			}
		}
        //il ciclo while termina quando ho trovato il nodo
		//Caso 1: se il nodo da cancellare non ha nodi figli
		if(current.left==null && current.right==null){
			if(current==root) //se il nodo da cancellare e' la radice, viene posta a null --> viene cancellato tutto l'albero
				root = null;
			if(isLeftChild == true)
				parent.left = null;
			else
				parent.right = null;//TO-DO CANCELLARE I NODI NULL SOTTO IL NODO APPENA CANCELLATO
		}
		//Caso 2 : se il nodo da cancellare ha un solo nodo figlio
		else {
			//se l'unico figlio e' il destro
			if(current.right==null){
				if(current==root)
					root = current.left;
				else {
					if(isLeftChild)
						parent.left = current.left;
					else
						parent.right = current.left;
				}
			}
        	//se l'unico figlio e' il sinistro
			else {
				if(current.left==null){
					if(current==root)
						root = current.right;
					else {
						if(isLeftChild)
							parent.left = current.right;
						else
							parent.right = current.right;
					}
        //Caso 3: se il nodo da cancellare ha entrambi i figli
				}
				else {
					if(current.left!=null && current.right!=null){			
            //cerchiamo l'elemento minimo nel sotto-albero sinistro
						Nodo successor = getSuccessor(current);
						if(current==root)
							root = successor;
						else {
							if(isLeftChild)
								parent.left = successor;
							else
								parent.right = successor;		
						successor.left = current.left;
						}
					}
				}
			}
		}
		return current;
	}
	
	
    public void treeDelete(int key){
        Nodo delNode = delete(key);
        if (delNode.color == BLACK)
            deleteFixup(delNode);
    }
	
	
    protected void deleteFixup(Nodo delNode)
    {
	while (delNode != root && delNode.color == BLACK) {
	    if (delNode.parent.left == delNode) {
	    	Nodo w = delNode.parent.right;
	    	if (w.color == RED) {
	    		w.color = BLACK;
	    		delNode.parent.color = RED;
	    		leftRotate( delNode.parent);
	    		w = delNode.parent.right;
	    	}
	    	if (w.left.color == BLACK && w.right.color == BLACK) {
	    		w.color = RED;
	    		delNode = delNode.parent;
	    	}
	    	else {
	    		if (w.right.color == BLACK) {
	    			w.left.color = BLACK;
	    			w.color = RED;
	    			rightRotate(w);
	    			w = delNode.parent.right;
	    		}
	    		w.color = delNode.parent.color;
	    		delNode.parent.color = BLACK;
	    		w.right.color = BLACK;
	    		leftRotate(delNode.parent);
	    		delNode = root;
	    	}
	    }
	    else {
	    	Nodo w = delNode.parent.left;
	    	if (w.color == RED) {
	    		w.color = BLACK;
	    		delNode.parent.color = RED;
	    		rightRotate(delNode.parent);
	    		w = delNode.parent.left;
	    	}
	    	if (w.right.color == BLACK && w.left.color == BLACK) {
	    		w.color = RED;
	    		delNode = (Nodo) delNode.parent;
	    	}
	    	else {
	    		if (w.left.color == BLACK) {
	    			w.right.color = BLACK;
	    			w.color = RED;
	    			leftRotate(w);
	    			w = delNode.parent.left;
	    		}
	    		w.color = delNode.parent.color;
	    		delNode.parent.color = BLACK;
	    		w.left.color = BLACK;
	    		rightRotate(delNode.parent);
	    		delNode = root;
	    	}		
	    }
	}
	delNode.color = BLACK;
    }
	
	
	//rotazione sx su un nodo (node); il figlio dx di node diventa il padre di node
    protected void leftRotate(Nodo node){
    	Nodo y = (Nodo) node.right;
    	//scambiamo il sottoalbero esistente tra y e x
    	node.right = y.left;
    	if (y.left != null)
    		y.left.parent = node;
    	//y diventa la radice del sottoalbero di cui x era radice
    	y.parent = node.parent;
    	//se x era la radice dell'intero albero allora y ne diveta la radice
    	//altrimenti facciamo diventare y un figlio del sottoalbero di parent
    	if (node.parent == null)
    		root = y;
    	else {
    		if (node == node.parent.left)
    			node.parent.left = y;
    		else
    			node.parent.right = y;
    	}
    	// Relink x and y.
    	y.left = node;
    	node.parent = y;
    }
    
    
    //rotazione dx su un nodo (node); il figlio sx di node diventa il padre di node
    protected void rightRotate(Nodo node){
    	Nodo y = (Nodo) node.left;
    	node.left = y.right;
    	if (node.left != null)
    		y.right.parent = node;
    	y.parent = node.parent;
    	y.right = node;
    	node.parent = y;
    	if (root == node)
    		root = y;
    	else {
    		if (y.parent.left == node)
    			y.parent.left = y;
    		else
    			y.parent.right = y;
    	}
   }
	
    //inserimento nell'albero di un nuovo nodo con chiave id
	public Nodo insert(int id){
		Nodo newNode = new Nodo(id);
		if(root==null){ //se l'albero e' vuoto, istanzio la radice con il nuovo nodo
			root = newNode;
			return newNode;
		}
		Nodo current = root;
		Nodo parent = null;
		while(true){
			parent = current;
			if(id<current.key){				
				current = current.left;
				if(current==null){
					parent.left = newNode;
                    newNode.parent = parent;
					return parent.left;
				}
			}
			else{
				current =  current.right;
				if(current==null){
					parent.right = newNode;
                    newNode.parent = parent;
					return parent.right;
				}
			}
		}
	}
	
	
	//Ripristiniamo le condizioni di colore (rosso-nero) dell'albero dopo l'inserimento di un nodo.
    protected void insertFixup(Nodo newNode){
    	Nodo y = null;
        if(newNode.parent != root){
            while (newNode.parent.color == RED) {
                if (newNode.parent == newNode.parent.parent.left) {
                    y = newNode.parent.parent.right;
                    if (y.color == RED) {
                        newNode.parent.color = BLACK;
                        y.color = BLACK;
                        newNode.parent.parent.color = RED;
                        newNode = newNode.parent.parent;
                    }
                    else {
                        if (newNode ==  newNode.parent.right) {
                            newNode =  newNode.parent;
                            leftRotate(newNode);
                        }
                        newNode.parent.color = BLACK;
                        newNode.parent.parent.color = RED;
                        rightRotate( newNode.parent.parent);
                    }
                }
                else {
                    y =  newNode.parent.parent.left;
                    if (y.color == RED) {
                        newNode.parent.color = BLACK;
                        y.color = BLACK;
                        newNode.parent.parent.color = RED;
                        newNode = newNode.parent.parent;
                    }
                    else {
                        if (newNode ==  newNode.parent.left) {
                            newNode =  newNode.parent;
                            rightRotate(newNode);
                        }
                        newNode.parent.color = BLACK;
                        newNode.parent.parent.color = RED;
                        leftRotate(newNode.parent.parent);
                    }
                }
            }   
        }
        root.color = BLACK;        
    }
	
	
	//inseriamo un nuovo nodo con chiave (key) nell'albero
    public void treeInsert(int key){ 
    	Nodo newNode = insert(key);
    	insertFixup(newNode);
    }
	
	
    //ALTEZZA NERA DELL'ALBERO
    //ritorna il numero di nodi black nel percorso dal nodo dato a qualsiasi foglia
    //BlackHeightException: se il numero di nodi black sul percorso lungo il figlio sx ad arivare a qualsiasi
    //foglia e' diverso da quello sul percorso lungo il figlio dx
    /* public int blackHeight(Nodo z){
    	 if (z == null)
    		 return 0;
    	 int left = blackHeight((Nodo) z.left);
    	 int right = blackHeight((Nodo) z.right);
    	 if (left == right)
    		 if (z.color == BLACK)
    			 return left + 1;
    		 else
    			 return left;
    	 else
    		 throw new BlackHeightException();
    }
       
    //Restituisce il numero di nodi neri dalla radice fino a qualsiasi foglia 
    //Il valore dovrebbe essere lo stesso per tutti i percorsi
    public int blackHeight(){
    	return blackHeight((Nodo) root);
    }	
    
    
    // Eccezione lanciata se l'altezza nera di un nodo non e' definita correttamente
    public static class BlackHeightException extends RuntimeException{
    	
    }*/
    
    public void display(Nodo root){
		if(root!=null){
			display(root.left);
			System.out.print(" " + root.key);
			display(root.right);
		}
	}
    
    public static void main(String arg[]){
		RedBlackTree b = new RedBlackTree();
		b.setRoot(10);
		b.treeInsert(8);
		b.treeInsert(12);
		b.treeInsert(7);
		b.treeInsert(9);
		b.treeInsert(11);
		b.treeInsert(13);
		System.out.println("Original Tree : ");
		b.display(b.root);		
		System.out.println("");
		System.out.println("Check whether Node with value 4 exists : " + b.treeFind(4));
		System.out.println("Check whether Node with value 4 exists : " + b.treeFind(10));
		b.treeDelete(2);
		b.treeDelete(7);
		b.display(b.root);
	}
}