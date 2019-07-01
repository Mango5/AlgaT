package algat_modChiara.tree;


import javafx.scene.paint.Color;

public class RedBlackTree {
	
    public Nodo root;
    public String messaggio;
	//Colore per un nodo rosso
    protected static final Color RED = Color.RED;

    //Colore per un nodo nero
    protected static final Color BLACK = Color.BLACK;
    
	public RedBlackTree(){         
		this.root = null;
	}
	
	 public void setRoot(Nodo root){
             this.root = root;   
	    }
	
	 
    //ritorna il nodo se nell'albero con radice root e' presente un nodo con chiave id
    public Nodo find(int id){
            Nodo current = root; //la ricerca parte dalla radice
            //messaggio = "visito il nodo: " + current.key;
            while(current!=null){
                    if(current.key==id){
                          //  messaggio = "il nodo che sto visitando è il nodo che cerco";
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
           return "Il nodo con chiave " + key + " è stato trovato";
       }
       return "Nell'albero non esiste un nodo con chiave " + key;
    }  
        
	
        /*public String treeDelete(int key){
        //prima di chiamare la delete verificare che  sia  presente un nodo con medesima chiave
        Nodo delNode =  find(key); //delNode è il nodo dell'albero con chiave key
       if(delNode != null){
            Nodo root = delete(delNode);         
            if (root != null ){
                    deleteFixup(root);
                 return "";
            }else return "Impossibile cancellare, si andrebbe a cancellare tutto l'albero";
       }
       else return "impossibile eliminare il nodo con chiave" + delNode.key + "perchè non è presente nell'albero";
    }*/
        
    //cancello il nodo che ha chiave uguale al valore del parametro id
	/*public  Nodo delete(Nodo delNode){
		Nodo parent = delNode.parent;
		Nodo current = delNode;
                //Caso 0: se il nodo da cancellare è la radice dell'albero
                //if(current != root){
                    //Caso 1: se il nodo da cancellare non ha nodi figli
                    if(current.left==null && current.right==null){
                            if(current==root)
                                    return null;  //l'unico caso in cui ritorno null è se voglio cancellare la radice e la radice non aveva figli (ho un albero vuoto)
                            if(parent.left == current){ //se sono il figlio sx di mio padre
                                    parent.left = null;
                            }else { //se sono il figlio dx di mio padre
                                    parent.right = null;
                            }
                    }
                    
                    //Caso 2 : se il nodo da cancellare ha un solo nodo figlio
                    //se l'unico figlio e' il sinistro
                    else if(current.right==null){
                            if(current==root){
                                    root = current.left;
                                     root.parent = null;
                            }else if(parent.left == current){
                                    parent.left = current.left;
                                    parent.left.parent = parent;
                            }else{
                                    parent.right = current.right;
                                    parent.right.parent = parent;
                            }
                    }
                    //se l'unico figlio e' il destro
                    else if(current.left==null){
                            if(current==root){
                                    root = current.right;
                                    root.parent = null;
                            }else if(parent.left == current){
                                    parent.left = current.right;
                                    parent.left.parent = parent;
                            }else{
                                    parent.right = current.right;
                                    parent.right.parent = parent;
                            }
                    //Caso 3: se il nodo da cancellare ha entrambi i figli
                    }else if(current.left!=null && current.right!=null){			
                         //cerchiamo l'elemento minimo nel sotto-albero sinistro
                            Nodo successor = getSuccessor(current);
                            if(current==root){
                                    root = successor;
                                    root.parent= null;
                            }else if(parent.left == current){
                                    parent.left = successor;
                                    successor.parent = parent;
                            }else{
                                    parent.right = successor;
                                    successor.parent = parent;
                            }			
                            successor.left = current.left;
                            current.left.parent = successor;
                    } 
             return root;  
	}*/
    
    public void link(Nodo p, Nodo u, int x) {
    	if (u != null)
    		u.parent = p;
    	if (p != null)
    		if (x < p.key)
    			p.left = u;
    		else
    			p.right = u;
    }
    
    public String treeDelete(int x) {
    	Nodo u = this.find(x);
    	if (u!= null) {
    		if (u.left != null && u.right != null) {
    			Nodo s = u.right;
    			while (s.left != null)
    				s = s.left;
    			u.key = s.key;
    			x = s.key;
    			u = s;
    		}
    		Nodo t;
    		if (u.left != null && u.right == null)
    			t = u.left;
    		else
    			t = u.right;
    		link (u.parent, t, x);
    		if (u.color == BLACK)
    			deleteFixup(t);
    		if (u.parent == null)
    			this.root = t;
    		u = null;
    	}
    	while (this.root.parent != null)
    		this.root = this.root.parent;
    	return "ciao";
    }
	
    protected void deleteFixup(Nodo t){
    	while (t != this.root && t.color == BLACK) {
    		Nodo p = t.parent;
    		if (t == p.left) {
    			Nodo f = p.right;
    			Nodo ns = f.left;
    			Nodo nd = f.right;
    			if (f.color == RED) {
    				p.color = RED;
    				f.color = BLACK;
    				leftRotate(p);
    			}
    			else
    				if (ns.color == nd.color && nd.color == BLACK) {
    					f.color = RED;
    					t = p;
    				}
    				else
    					if (ns.color == RED && nd.color == BLACK) {
    						ns.color = BLACK;
    						f.color = RED;
    						rightRotate(f);
    					}
    					else
    						if(nd.color == RED) {
    							f.color = p.color;
    							p.color = BLACK;
    							nd.color = BLACK;
    							leftRotate(p);
    							t = this.root;
    						}
    		}
    		else{
    			Nodo f = p.left;
    			Nodo ns = f.right;
    			Nodo nd = f.left;
    			if (f.color == RED) {
    				p.color = RED;
    				f.color = BLACK;
    				leftRotate(p);
    			}
    			else
    				if (ns.color == nd.color && nd.color == BLACK) {
    					f.color = RED;
    					t = p;
    				}
    				else
    					if (ns.color == RED && nd.color == BLACK) {
    						ns.color = BLACK;
    						f.color = RED;
    						rightRotate(f);
    					}
    					else
    						if(nd.color == RED) {
    							f.color = p.color;
    							p.color = BLACK;
    							nd.color = BLACK;
    							leftRotate(p);
    							t = this.root;
    						}
    		}
    			
    	}
    	if(t != null)
    		t.color = BLACK;
    }
		
    /*protected void deleteFixup(Nodo root)
    {
        Nodo tmp = null;
        Nodo current = root;
        if(root.color != BLACK)
            root.color = BLACK;
        if(root.left != null && root.right != null){
            if(root.left != null){
                current = root.left;
                while(current.left != null){
                    current = current.left;
                }
            }//termina quando il nodo che sto visitando current, ha il nodo sinistro a null --> current è una foglia
            else {
                 current = root.right;
                while(current.right != null){
                  
            }
            insertFixup(current);
           }
        }
    }*/
	
	
	/*public Nodo getSuccessor(Nodo node){
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
	}*/
	
	
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
    
   
   public String treeInsert(int x) {
	   Nodo p = null;
	   Nodo u = this.root;
	   Nodo n = new Nodo(x);
	   while (u != null && u.key != x) {
		   p = u;
		   if (x < u.key)
			   u = u.left;
		   else
			   u = u.right;
	   }
	   if (u != null && u.key == x)
		   u.key = x;
	   else {
		   link(p, n, x);
		   insertFixup(n);
	   }
	   while (n.parent != null)
		   n = n.parent;
	   return "ciao";
   }
   
   public void insertFixup(Nodo t) {
	   t.color = RED;
	   while (t != null) {
		   Nodo p = t.parent;
		   Nodo n;
		   if (p != null)
			   n = p.parent;
		   else
			   n = null;
		   Nodo z;
		   if (n == null)
			   z = null;
		   else
			   if (n.left == p)
				   z = n.right;
			   else
				   z = n.left;
		   if (p == null) {
			   t.color = BLACK;
			   t = null;
		   }
		   else
			   if (p.color == BLACK)
				   t = null;
			   else
				   if (z.color == RED) {
					   p.color = BLACK;
					   z.color = BLACK;
					   n.color = RED;
					   t = n;
				   }
				   else
					   if (t == p.right && p == n.left) {
						   leftRotate(p);
						   t = p;
					   }
					   else
						   if (t == p.left && p == n.right) {
							   rightRotate(p);
							   t = p;
						   }
						   else {
							   if (t == p.left && p == n.left)
								   rightRotate(n);
							   else
								   if (t == p.right && p == n.right)
									   leftRotate(n);
							   p.color = BLACK;
							   n.color = RED;
							   t = null;
						   }
	   }
   }
      
    	//inseriamo un nuovo nodo con chiave (key) nell'albero
    /*public String treeInsert(int key)
    { 
        //prima di chiamare la insert verificare che non sia già presente un nodo con medesima chiave
        Nodo newNode =  find(key);
       if(newNode == null){ //se non l'ho trovato
            newNode = insert(key); //inserisco il nodo nell'albero
            insertFixup(newNode); // ripristino le condizioni di colore    
            return "Il nodo con chiave " + key + " è stato inserito con successo";
       }
       else return "Impossibile inserire il nodo con chiave " + key + "perchè nell'albero è già presente un nodo tale chiave";
    }*/
	
    //inserimento nell'albero di un nuovo nodo con chiave id
	/*public Nodo insert(int id){
		String output= "";
		Nodo newNode = new Nodo(id);
                output += "nodo inserito " + id ;
		if(root==null){ //se l'albero e' vuoto, istanzio la radice con il nuovo nodo
                    output += "l'albero e' vuoto, setto il nuovo nodo come la radice dell'albero";
                    setRoot(newNode);
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
                        output += " il nodo inserito: " + id + " e' minore di nodo corrente: " + current.key;
                        current = current.left;                            
                        if(current==null){                            
                            parent.left = newNode;
                            output += "figlio sinistro del nodo " + parent.key + " : " + newNode.key;
                            newNode.parent = parent;
                            output += "padre del nodo inserito: " + parent.key;
                            return parent.left;
                        }
                    }
                    else{
                            output += " il nodo inserito: " + id + " e' maggiore di nodo corrente: " + current.key;
                            current =  current.right;
                            if(current==null){
                                parent.right = newNode;
                                output += "figlio destro del nodo " + parent.key + " : " + newNode.key;
                                newNode.parent = parent;
                                output += "padre del nodo inserito: " + parent.key;
                                return parent.right;
                            }
			}
                    output += "nodo corrente: " + current.key;
		}
	}*/
        
        
	//Ripristiniamo le condizioni di colore (rosso-nero) dell'albero dopo l'inserimento di un nodo.
        /*protected void insertFixup(Nodo newNode){
    	String output = "";
    	Nodo y = null;
    	//casi da distinguere: 
        //il nodo inserito e' la radice
        //il nodo inserito e' un figlio della radice
        //altrimenti
    	//se il nodo inserito e' la radice --> root.color = BLACK
        //se il nodo inserito e' un figlio della radice --> root.color = BLACK (i nodi sono gia'  settati a rosso quando inseriti)
        //ciclo finche' dal basso verso l'alto finche' non arrivo alla radice, esco e setto root.color = BLACK
        if(newNode == root || newNode.parent ==  root){
        	root.color = BLACK;
        }
        else {
        	//la condizione nel while 'newNode.parent != null' in realta' non serve se si ha un albero redblack bilanciato
        	//correttamente, capita se inserendo un nodo viene applicata la rotazione e un nodo finisce figlio di un nodo
        	//nil 
            while (newNode.parent != root && newNode.parent != null) {
            	if (newNode.parent == newNode.parent.parent.left //&& newNode.parent.parent.right != null
            	) {
            		y = newNode.parent.parent.right; //y va nel ramo destro dell'albero
            		if (y!= null) {
            			if (y.color == RED) {
            				newNode.parent.color = BLACK;
            				y.color = BLACK;
            				newNode.parent.parent.color = RED;
            				newNode = newNode.parent;
            			}
            			else {
            				if (newNode ==  newNode.parent.right) {
            					newNode =  newNode.parent;
            					leftRotate(newNode);
            				}
            				newNode.parent.color = BLACK;
            				newNode.parent.parent.color = RED;
            				rightRotate( newNode.parent.parent);
            				newNode = newNode.parent;
            			}
            		}
            		else {
        				if (newNode ==  newNode.parent.right) {
        					newNode =  newNode.parent;
        					leftRotate(newNode);
        				}
        				newNode.parent.color = BLACK;
        				newNode.parent.parent.color = RED;
        				rightRotate( newNode.parent.parent);
        				newNode = newNode.parent;
        			}
            	}
                else
                	if(newNode.parent == newNode.parent.parent.right) {
                        y =  newNode.parent.parent.left;
                        if (y!= null) {
                        	if (y.color == RED ) {
                        		newNode.parent.color = BLACK;
                        		y.color = BLACK;
                        		newNode.parent.parent.color = RED;
                        		newNode =  newNode.parent;
                        	}                    
                        	else {
                        		if (newNode ==  newNode.parent.left) {
                        			newNode =  newNode.parent;
                        			rightRotate(newNode);
                        		}
                        		newNode.parent.color = BLACK;
                        		newNode.parent.parent.color = RED;
                        		leftRotate( newNode.parent.parent);
                        		newNode = newNode.parent;
                        	}
                        }
                        else {
                    		if (newNode ==  newNode.parent.left) {
                    			newNode =  newNode.parent;
                    			rightRotate(newNode);
                    		}
                    		newNode.parent.color = BLACK;
                    		newNode.parent.parent.color = RED;
                    		leftRotate( newNode.parent.parent);
                    		newNode = newNode.parent;
                    	}
                	}
            }   
            root.color = BLACK;     
        }              
    }*/
	
  //ALTEZZA NERA DELL'ALBERO
    //ritorna il numero di nodi black nel percorso dal nodo dato a qualsiasi foglia
    //BlackHeightException: se il numero di nodi black sul percorso lungo il figlio sx ad arivare a qualsiasi foglia è diverso da 
       //quello sul percorso lungo il figlio dx
     /*public int blackHeight(Nodo z)
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
    }*/
       
    //Restituisce il numero di nodi neri dalla radice fino a qualsiasi foglia. 
     //Il valore dovrebbe essere lo stesso per tutti i percorsi.
    /*public int blackHeight()
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
	}*/
    
}