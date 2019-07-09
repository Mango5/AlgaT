package algat_mod.tree;


import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RedBlackTree {
	
    public Nodo root;
    //Colore per un nodo rosso
    protected static final Color RED = Color.RED;
    //Colore per un nodo nero
    protected static final Color BLACK = Color.BLACK;
    
	public RedBlackTree(){         
		this.root = null;
	}
	//setto la radice e imposto il colore a BLACK
	public void setRoot(Nodo root){
             this.root = root;
             this.root.color = BLACK;
        }
	
	 
    //ritorna il nodo se nell'albero con radice root e' presente un nodo con chiave id
    public Nodo find(int id){
            Nodo current = root; //la ricerca parte dalla radice
            while(current!=null){
                    if(current.key==id){ //se il nodo corrente ha chiave id; ritorno il nodo corrente
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
        //cerco se nell'albero è presente un nodo con chiave key
        Nodo find =  find(key);
       if(find != null){
           return "Il nodo con chiave " + key + " è stato trovato";
       }
       return "Nell'albero non esiste un nodo con chiave " + key;
    }  
        
	
     
        
   
    
    public String link(Nodo p, Nodo u, int x) {
    	String messaggio = "";
    	if (u != null) {
    		u.parent = p;
    		if (p != null)
    			messaggio += "\nil padre di " + u.key + " diventa " + p.key;
    		else
    			messaggio += "\nil padre di " + u.key + " diventa null";
    	}
    	if (p != null)
    		if (x < p.key) {
    			p.left = u;
    			if (u != null)
    				messaggio += "\nil figlio sinistro di " + p.key + " diventa " + u.key;
    			else
    				messaggio += "\nil figlio sinistro di " + p.key + " diventa null";
    		}
    		else {
    			p.right = u;
    			if (u != null)
    				messaggio += "\nil figlio destro di " + p.key + " diventa " + u.key;
    			else
    				messaggio += "\nil figlio destro di " + p.key + " diventa null";
    		}
    	return messaggio;
    }
    
    public void treeDelete(int x,Text txtComments,Text txtCommentsHidden) {
    	txtComments.setText("");
         //cerco se nell'albero è presente un nodo con chiave key
    	Nodo u = this.find(x);
        //se ho trovato un nodo 
    	if (u!= null) {
    		if (u == this.root && u.left == null && u.right == null) { //se u è il nodo radice di un albero con un solo nodo
    			this.root = null;
    			txtComments.setText("la radice viene settata a null e l'albero non esiste piu'");
    		}
    		else {
    			if (u.left != null && u.right != null) { //se u ha entrambi i figli diversi da null
    				Nodo s = u.right;
    				while (s.left != null)
    					s = s.left;
    				u.key = s.key;
    				x = s.key;
    				u = s;
    			}
    			Nodo t;
    			if (u.left != null && u.right == null) { //se u ha il figlio sx mentre il figlio dx è null
    				t = u.left;
    			}else {  //se u ha il figlio dx mentre il figlio sx è null
    				t = u.right;
    				txtComments.setText(txtComments.getText()+link(u.parent, t, x));
                   //ribilancio l'albero tramite la chiamata alla funzione deleteFixup()
    			}
    			/*final Nodo k=u;
    			Thread five=new Thread() {
    	    		  public void run(){
    	    			  try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    	    			  if (k.color == BLACK)
    	    	    		 txtCommentsHidden.setText(txtCommentsHidden.getText()+deleteFixup(k)+"nigga");
    	    			  }
    	    		  };*/
    	    		  if (u.color == BLACK) {
 	    	    		 txtCommentsHidden.setText(txtCommentsHidden.getText()+deleteFixup(u));
 	    			  }
				if (u.parent == null)
    				this.root = t;
                        //elimino il nodo
    			u = null;
    		}
    	}
    }
    
    //funzione che permette di ribilanciare i colori dei nodi dell'albero dopo la cancellazione di un nodo
     protected String deleteFixup(Nodo t){
    	String messaggio = "";
    	while (t != this.root && t.color == BLACK) {
    		Nodo p = t.parent;
    		if (t == p.left) {
    			Nodo f = p.right;
    			Nodo ns = f.left;
    			Nodo nd = f.right;
    			if (f.color == RED) {
    				p.color = RED;
    				messaggio += "\n" + p.key + " diventa rosso";
    				f.color = BLACK;
    				messaggio += "\n" + f.key + " diventa nero";
    				leftRotate(p);
    			}
    			else
    				if (ns != null && nd != null && ns.color == nd.color && nd.color == BLACK) {
    					f.color = RED;
    					messaggio += "\n" + f.key + " diventa rosso";
    					t = p;
    				}
    				else
    					if (ns != null && nd != null && ns.color == RED && nd.color == BLACK) {
    						ns.color = BLACK;
    						messaggio += "\n" + ns.key + " diventa nero";
    						f.color = RED;
    						messaggio += "\n" + f.key + " diventa rosso";
    						rightRotate(f);
    					}
    					else
    						if(nd != null && nd.color == RED) {
    							f.color = p.color;
    							messaggio += "\n" + f.key + " diventa dello stesso colore di " + p.key;
    							p.color = BLACK;
    							messaggio += "\n" + p.key + " diventa nero";
    							nd.color = BLACK;
    							messaggio += "\n" + nd.key + " diventa nero";
    							leftRotate(p);
    							t = this.root;
    						}
    						else {
    							t.parent.right.color = RED;
    							return messaggio;
    						}
    		}
    		else{
    			Nodo f = p.left;
    			Nodo ns = f.right;
    			Nodo nd = f.left;
    			if (f.color == RED) {
    				p.color = RED;
    				messaggio += "\n" + p.key + " diventa rosso";
    				f.color = BLACK;
    				messaggio += "\n" + f.key + " diventa nero";
    				leftRotate(p);
    			}
    			else
    				if (ns != null && nd != null && ns.color == nd.color && nd.color == BLACK) {
    					f.color = RED;
    					messaggio += "\n" + f.key + " diventa rosso";
    					t = p;
    				}
    				else
    					if (ns != null && nd != null && ns.color == RED && nd.color == BLACK) {
    						ns.color = BLACK;
    						messaggio += "\n" + ns.key + " diventa nero";
    						f.color = RED;
    						messaggio += "\n" + f.key + " diventa rosso";
    						rightRotate(f);
    					}
    					else
    						if(nd != null && nd.color == RED) {
    							f.color = p.color;
    							messaggio += "\n" + f.key + " diventa dello stesso colore di " + p.key;
    							p.color = BLACK;
    							messaggio += "\n" + p.key + " diventa nero";
    							nd.color = BLACK;
    							messaggio += "\n" + nd.key + " diventa nero";
    							leftRotate(p);
    							t = this.root;
    						}
    						else {
    							t.parent.left.color = RED;
    							return messaggio;
    						}
    		}
    			
    	}
    	if(t != null) {
    		t.color = BLACK;
    		messaggio += "\n" + t.key + " diventa nero";
    	}
    	return messaggio;
    }
		
	
    //funzione che applica la rotazione sinistra su un nodo (@param node);
    protected Nodo leftRotate(Nodo node)
    {
        //assegno a y il figlio dx di node
	Nodo y = node.right;
        //assegno al figlio dx di node il figlio sx di y
	node.right = y.left;
	if (y.left != null)
	    y.left.parent = node;

	//y  diventa la radice del sottoalbero per il quale x era la radice
	y.parent = node.parent;
	
	/*Se node è la radice dell'intero albero,
           y diventerà la radice, 
          altrimenti y diventerà il figlio del genitore del sottoalbero.
        */
	if (root == node)
	    root = y;
	else {
	    if (node == node.parent.left)
		node.parent.left = y;
	    else
		node.parent.right = y;
        }
	// Ricollego i nodi y e node.
	y.left = node;
	node.parent = y;
        return y;
    }
    
    
   //funzione che applica la rotazione sinistra su un nodo (@param node);
    protected Nodo rightRotate(Nodo node)
   {
        //assegno a y il figlio sx di node
	Nodo y = node.left;
        // assegno al figlio sx di node il figlio dx di y
	node.left = y.right;
	if (node.left != null)
	    y.right.parent = node;

	y.parent = node.parent;

	y.right = node;
	node.parent = y;

	if (root == node)
	    root = y;
        else{
	    if (y.parent.left == node)
		y.parent.left = y;
	    else
		y.parent.right = y;
        }
       return y;
   }
    

   public void treeInsert(int x,Text txtComments,Text txtCommentsHidden) {
        txtComments.setText("");
        //istanzio un nuovo oggetto Nodo con chiave x
        Nodo n = new Nodo(x);
        //se la radice dell'albero è null
       if(this.root == null) {
           //imposto il nodo n come la radice dell'albero
    	   this.setRoot(n);
    	   txtComments.setText("La radice e' stata settata a " + x);
       }
       else {
    	   Nodo p = null;
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
    		  final Nodo k=p,k1=n;
    		  Thread one=new Thread() {
    			  public void run(){
    				  txtComments.setText(txtComments.getText()+link(k, k1, x)); 
    			  }
    		  };
    		  one.start();
    		  Thread two=new Thread(new Runnable() {
				@Override
				public void run() {
					 try {
						    
							Thread.sleep(100);
							 //ribilancio l'albero tramite la chiamata alla funzione insertFixup()
		    				 
							txtCommentsHidden.setText(txtCommentsHidden.getText()+insertFixup(k1));
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
    		  });
    		  two.start();
    	   }
    	   while (n.parent != null)
    		   n = n.parent;
       }
   }
   
   //funzione che permette di ribilanciare i colori dei nodi dell'albero dopo l'inserimento di un nuovo nodo
   public String insertFixup(Nodo t) {
	   String messaggio = "";
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
    			   messaggio += "\n" + t.key + "diventa nero";
    			   t = null;
    		   }
    		   else
    			   if (p.color == BLACK)
    				   t = null;
    			   else
    				   if (z != null && z.color == RED) {
    					   p.color = BLACK;
    					   messaggio += "\n" + p.key + " diventa nero";
    					   z.color = BLACK;
    					   messaggio += "\n" + z.key + " diventa nero";
    					   n.color = RED;
    					   messaggio += "\n" + n.key + " diventa rosso";
    					   t = n;
    				   }
    				   else
    					   if (t == p.right && p == n.left) {
    						   leftRotate(p);
    						   t = p;
    						   messaggio += "\nil figlio destro di " + n.key + " diventa il padre di " + n.key;
    					   }
    					   else
    						   if (t == p.left && p == n.right) {
    							   rightRotate(p);
    							   t = p;
    							   messaggio += "\nil figlio sinistro di " + n.key + " diventa il padre di " + n.key;
    						   }
    						   else {
    							   if (t == p.left && p == n.left) {
    								   p = rightRotate(n);
    								   messaggio += "\nil figlio sinistro di " + n.key + " diventa il padre di " + n.key;
    							   }
    							   else
    								   if (t == p.right && p == n.right) {
    									   p = leftRotate(n);
    									   messaggio += "\nil figlio destro di " + n.key + " diventa il padre di " + n.key;
    								   }
    							   p.color = BLACK;
    							   messaggio += "\n" + p.key + " diventa nero";
    							   n.color = RED;
    							   messaggio += "\n" + n.key + " diventa rosso";
    							   t = null;
    						   }
    	   }
       }
       return messaggio;
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
    
    /*public static void main(String[] args) throws Exception {
    	RedBlackTree a = new RedBlackTree();
    	a.treeInsert(1);
    	a.treeInsert(2);
    	a.treeInsert(3);
    	a.treeInsert(4);
    	a.treeDelete(4);
    	a.treeDelete(3);
    }*/
    
}
