package algat_mod.tree;


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
	
	 public void setRoot(Nodo root){
             this.root = root;   
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
           return "Il nodo con chiave " + key + " è stato trovato";
       }
       return "Nell'albero non esiste un nodo con chiave " + key;
    }  
        
	
     
        
   
    
    public void link(Nodo p, Nodo u, int x) {
    	if (u != null)
    		u.parent = p;
    	if (p != null)
    		if (x < p.key)
    			p.left = u;
    		else
    			p.right = u;
    }
    
    public void treeDelete(int x) {
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
    
   
   public void treeInsert(int x) {
	   Nodo p = null;
           Nodo n = new Nodo(x);
           if(this.root == null) this.setRoot(n);
	   Nodo u = this.root;
	   
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
	   
   }
   
   public void insertFixup(Nodo t) {
	   t.color = RED;
           if (t == this.root || t.parent == this.root)
               this.root.color = BLACK;
           else{
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
    
   
    
}