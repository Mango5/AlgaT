package algat_mod;


import javafx.scene.paint.Color;

public class RedBlackTree {
	
    public Nodo root;
	//Colore per un nodo rosso
    protected static final Color RED = Color.RED;

    //Colore per un nodo nero
    protected static final Color BLACK = Color.BLACK;
    
	public RedBlackTree(){         
		this.root = null;
	}
	
	 public void setRoot(int chiave){
             this.root = new Nodo(chiave);   
	    }
	
	 
    //ritorna il nodo se nell'albero con radice root e' presente un nodo con chiave id
    public Nodo find(int id){
            Nodo current = root; //la ricerca parte dalla radice
            while(current!=null){
                    if(current.key==id){
                            return current;
                    }else if(id < current.key){
                            current = current.left;//scendo nel ramo sx se la chiave che stiamo cercando e' minore della chiave del nodo corrente
                    }else{
                            current =  current.right;//scendo nel ramo dx se la chiave che stiamo cercando e' maggiore della chiave del nodo corrente
                    }
            }
            return null;
    }
	
	
    public String treeFind(int key){
        Nodo find =  find(key);
       if(find != null){
           find.color=Color.GREEN;
           return "Il nodo con chiave " + key + " è stato trovato";
       }
       return "Nell'albero non esiste un nodo con chiave " + key;
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
			}else{
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
			if(current==root){ //se il nodo da cancellare e' la radice, viene posta a null --> viene cancellato tutto l'albero
				root = null;
			}
			if(isLeftChild == true){
				parent.left = null;
			}else{
				parent.right = null;
			}
		}
		//Caso 2 : se il nodo da cancellare ha un solo nodo figlio
        //se l'unico figlio e' il destro
		else if(current.right==null){
			if(current==root){
				root = current.left;
			}else if(isLeftChild){
				parent.left = current.left;
			}else{
				parent.right = current.left;
			}
		}
        //se l'unico figlio e' il sinistro
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
			Nodo successor = getSuccessor(current);
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
		    ( delNode.parent).color = RED;
		    leftRotate( delNode.parent);
		    w = delNode.parent.right;
		}

		if (( w.left).color == BLACK 
		    && ( w.right).color == BLACK) {
		    w.color = RED;
		    delNode = delNode.parent;
		}
		else {
		    if (( w.right).color == BLACK) {
			( w.left).color = BLACK;
			w.color = RED;
			rightRotate(w);
			w = delNode.parent.right;
		    }

		    w.color = (delNode.parent).color;
		    (delNode.parent).color = BLACK;
		    ( w.right).color = BLACK;
		    leftRotate( delNode.parent);
		    delNode = root;
		}
	    }
	    else {
		Nodo w = delNode.parent.left;

		if (w.color == RED) {
		    w.color = BLACK;
		    ( delNode.parent).color = RED;
		    rightRotate( delNode.parent);
		    w = delNode.parent.left;
		}

		if ((w.right).color == BLACK 
		    && ( w.left).color == BLACK) {
		    w.color = RED;
		    delNode = (Nodo) delNode.parent;
		}
		else {
		    if ((w.left).color == BLACK) {
			( w.right).color = BLACK;
			w.color = RED;
			leftRotate(w);
			w = delNode.parent.left;
		    }

		    w.color = (delNode.parent).color;
		    ( delNode.parent).color = BLACK;
		    ( w.left).color = BLACK;
		    rightRotate( delNode.parent);
		    delNode = root;
		}		
	    }
	}
	delNode.color = BLACK;
    }
	
	
	public Nodo getSuccessor(Nodo node){
		Nodo successsor =null;
		Nodo successsorParent =null;
		Nodo current =  node.right;
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
	
	
	//rotazione sx su un nodo (@param node); il figlio dx di node diventa il padre di node
    protected void leftRotate(Nodo node)
    {
	Nodo y = (Nodo) node.right;

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
    protected void rightRotate(Nodo node)
   {
	Nodo y = (Nodo) node.left;

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
	
    //inserimento nell'albero di un nuovo nodo con chiave id
	public Nodo insert(int id){
            String output= "";
		Nodo newNode = new Nodo(id);
                output += "nodo inserito " + id ;
		if(root==null){ //se l'albero e' vuoto, istanzio la radice con il nuovo nodo
                    output += "l'albero è vuoto, setto il nuovo nodo come la radice dell'albero";
			root = newNode;
			return newNode;
		}
                
		Nodo current = root;
                output += "nodo corrente: " + root.key;
		Nodo parent = null;
                output += "nodo parent : null" ;
		while(true){
			parent = current;
                        output += "nodo parent : " + current.key;
			if(id < current.key){	
                            output += " il nodo inserito: " + id + " è minore di nodo corrente: " + current.key;
				current = current.left;
                                output += "nodo corrente: " + current.key;
				if(current==null){                            
					parent.left = newNode;
                                         output += "figlio sinistro del nodo " + parent.key + " : " + newNode.key;
                                        newNode.parent = parent;
                                         output += "padre del nodo inserito: " + parent.key;
					return parent.left;
				}
			}else{
                             output += " il nodo inserito: " + id + " è maggiore di nodo corrente: " + current.key;
				current =  current.right;
				if(current==null){
					parent.right = newNode;
                                        output += "figlio destro del nodo " + parent.key + " : " + newNode.key;
                                        newNode.parent = parent;
                                         output += "padre del nodo inserito: " + parent.key;
					return parent.right;
				}
			}
		}
	}
	//Ripristiniamo le condizioni di colore (rosso-nero) dell'albero dopo l'inserimento di un nodo.
    protected void insertFixup(Nodo newNode){
	String output = "";
    	Nodo y = null;
        if(newNode.parent != root){
            output = "il nodo padre del nodo inserito non è la radice";
            while (newNode.parent.color == RED) {
                if (newNode.parent == newNode.parent.parent.left) {
                    y = newNode.parent.parent.right;
                    if (y.color == RED) {
                        newNode.parent.color = BLACK;
                        y.color = BLACK;
                        newNode.parent.parent.color = RED;
                        y = newNode.parent.parent;
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
                        ( newNode.parent).color = BLACK;
                        y.color = BLACK;
                        newNode.parent.parent.color = RED;
                        newNode =  newNode.parent.parent;
                    }
                    else {
                        if (newNode ==  newNode.parent.left) {
                            newNode =  newNode.parent;
                            rightRotate(newNode);
                        }

                        newNode.parent.color = BLACK;
                        newNode.parent.parent.color = RED;
                        leftRotate( newNode.parent.parent);
                    }
                }
            }   
        }
        root.color = BLACK;        
 }
	
	
	//inseriamo un nuovo nodo con chiave (key) nell'albero
    public String treeInsert(int key)
    { 
        //prima di chiamare la insert verificare che non sia già presente un nodo con medesima chiave
        Nodo newNode =  find(key);
       if(newNode == null){
            newNode = insert(key); 
            insertFixup(newNode);       
            return "Il nodo con chiave " + key + " è stato inserito con successo";
       }
       else return "Impossibile inserire il nodo con chiave " + key + "perchè nell'albero è già presente un nodo tale chiave";
    }
	
	
  //ALTEZZA NERA DELL'ALBERO
    //ritorna il numero di nodi black nel percorso dal nodo dato a qualsiasi foglia
    //BlackHeightException: se il numero di nodi black sul percorso lungo il figlio sx ad arivare a qualsiasi foglia è diverso da 
       //quello sul percorso lungo il figlio dx
     public int blackHeight(Nodo z)
    {
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
       
    //Restituisce il numero di nodi neri dalla radice fino a qualsiasi foglia. 
     //Il valore dovrebbe essere lo stesso per tutti i percorsi.
    public int blackHeight()
    {
	return blackHeight((Nodo) root);
    }	
    
    
    // Eccezione lanciata da se l'altezza nera di un nodo non e' definita correttamente
    public static class BlackHeightException extends RuntimeException
    {
    }
    
    public void display(Nodo root){
		if(root!=null){
			display(root.left);
			System.out.print(" " + root.key);
			display(root.right);
		}
	}
    
    public static void main(String arg[]){
		RedBlackTree b = new RedBlackTree();
		b.treeInsert(3);
		b.treeInsert(8);
		b.treeInsert(1);
		b.treeInsert(4);
		b.treeInsert(6);
		b.treeInsert(2);
		b.treeInsert(10);
		b.treeInsert(9);
		b.treeInsert(20);
		b.treeInsert(25);
		b.treeInsert(15);
		b.treeInsert(16);
		System.out.println("Original Tree : ");
		b.display(b.root);		
		System.out.println("");
		System.out.println("Check whether Node with value 4 exists : " + b.find(4));
		System.out.println("Delete Node with no children (2) : " + b.delete(2));		
		b.display(b.root);
		System.out.println("\n Delete Node with one child (4) : " + b.delete(4));		
		b.display(b.root);
		System.out.println("\n Delete Node with Two children (10) : " + b.delete(10));		
		b.display(b.root);
	}
}