package algat_mod.tree;

import javafx.scene.paint.Color;

public class Nodo {
    public int key;
    public Nodo left;
    public Nodo right;
    public Nodo parent;
    //un nodo puo' avere colore rosso o nero
    public Color color;
    public boolean isDeleted;
    
    public Nodo(int data) {
    	this.key = data;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = Color.RED;
        this.isDeleted = false;
    }
 }