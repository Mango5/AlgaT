package algat;

import java.awt.Color;

public class Nodo {
    public int key;
	public Nodo left;
    public Nodo right;
    public Nodo parent;
    //un nodo puo' avere colore rosso o nero
    public Color color;
    
    public Nodo(int data) {
    	key = data;
        left = null;
        right = null;
        parent = null;
        color = Color.RED;
    }
 }