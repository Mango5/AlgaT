package algat_mod;

import algat_mod.tree.RedBlackTree;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class myThread extends Thread {
	public RedBlackTree rbt;
	public Pane pnTree;
	public myThread(Pane a,RedBlackTree b) {
		pnTree=a;
		rbt=b;
	};
	public void run() {
		GraficaAlbero tree= new GraficaAlbero();
        Group group= new Group();
        /*
        *vado a disegnare l'albero rbt, la funzione DisegnaAlbero mi ritorna un Group che contiene tutti gli elementi per        
        * la visualizzazione grafica dell'albero
        */
        group = tree.DisegnaAlbero(rbt, group);
        //pulisco il Pane per poi riempirlo con il nuovo Group creato
        pnTree.getChildren().clear();       
        pnTree.getChildren().add(group);	
	}
}
