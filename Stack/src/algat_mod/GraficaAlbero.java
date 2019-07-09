package Stack.src.algat_mod;

import Stack.src.algat_mod.tree.*;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class GraficaAlbero {
    //coordinate X,Y del centro del nodo radice dell'albero
    double rootX;
    double rootY;
    //raggio per tutti i cerchi
    double radius; 
    
   public GraficaAlbero(){
        rootX=400;
        rootY=50;
        radius=20;
    }
            
    public Group  DisegnaRadice(Nodo root, Group group){
        Circle node = new Circle();
        node.setCenterX(rootX);
        node.setCenterY(rootY);
        node.setRadius(radius);
        node.setFill(root.color);
        String chiave = Integer.toString(root.key);
        Label label = new Label(chiave);
        label.setLayoutX(rootX - 9);
        label.setLayoutY(rootY - 9);
        label.setTextFill(Color.WHITE);
        //aggiungo gli elementi creati al Group
        group.getChildren().addAll(node,label);
         return group;
    }
    
    public Group DisegnaFiglioSx(Nodo node, double parentX, double parentY, Group group, int n){

        //disegna il ramo
        Line line=new Line();
        line.setStartX(parentX);
        line.setStartY(parentY + radius);
        line.setEndX(parentX - (double)200/n);
        line.setEndY(parentY + radius + (double)50);
        //disegno il cerchio per il figlio sx
        Circle childsx = new Circle();
        childsx.setCenterX(line.getEndX());
        childsx.setCenterY(line.getEndY()+radius);
        childsx.setRadius(radius);
       
        //inserisce una label contenente la chiave del nodo
        if (node != null) {
        	String chiave = Integer.toString(node.key);
        	Label label = new Label(chiave);
        	label.setLayoutX(line.getEndX() - 9);
        	label.setLayoutY(line.getEndY()+radius - 9);
        	label.setTextFill(Color.WHITE);
                childsx.setFill(node.color);
                //aggiungo gli elementi creati al Group
        	group.getChildren().addAll(line, childsx,label);
        }
        else {
        	Label label = new Label("NIL");
        	label.setLayoutX(line.getEndX() - 9);
        	label.setLayoutY(line.getEndY()+radius - 9);
        	label.setTextFill(Color.WHITE);
                childsx.setFill(Color.GREY);
                //aggiungo gli elementi creati al Group
        	group.getChildren().addAll(line, childsx,label);
        }
        return group;
        
    }
    
    
    public Group DisegnaFiglioDx(Nodo node, double parentX, double parentY,Group group, int n){
        //creo una linea che collega il nodo corrente al suo nodo padre
        Line line=new Line();
        line.setStartX(parentX);
        line.setStartY(parentY + radius);
        line.setEndX(parentX + (double)200/n);
        line.setEndY(parentY + radius + (double)50);
        //disegno il cerchio per il figlio dx
        Circle childdx = new Circle();
        childdx.setCenterX(line.getEndX());
        childdx.setCenterY(line.getEndY()+radius);
        childdx.setRadius(radius);
       
       //inserisce una label contenente la chiave del nodo
        if (node != null) {
            String chiave = Integer.toString(node.key);
            Label label = new Label(chiave);
            label.setLayoutX(line.getEndX() - 9);
            label.setLayoutY(line.getEndY()+radius - 9);
            label.setTextFill(Color.WHITE);
            childdx.setFill(node.color);
            //aggiungo gli elementi creati al Group
            group.getChildren().addAll(line, childdx,label);
        }
        else {
            Label label = new Label("NIL");
            label.setLayoutX(line.getEndX() - 9);
            label.setLayoutY(line.getEndY()+radius - 9);
            label.setTextFill(Color.WHITE);
            childdx.setFill(Color.GREY);
            //aggiungo gli elementi creati al Group
            group.getChildren().addAll(line, childdx,label);
        }
        return group;
       
    }
    
    public Group DisegnaFigli(Nodo node, double parentX, double parentY, Group group, int n) {
        if(node == null)
            return group;
        else{ 
          group = this.DisegnaFiglioSx(node.left, parentX, parentY, group, n);
          group = this.DisegnaFiglioDx(node.right, parentX, parentY, group, n);          
        }
        if(node.left != null)
            group = this.DisegnaFigli(node.left, parentX-(double)200/n, parentY+ radius + (double)50 + radius, group, n*2);
        if(node.right != null)
            group = this.DisegnaFigli(node.right, parentX+(double)200/n, parentY+ radius + (double)50 + radius, group, n*2);
      
    	return group;
    }
    
    public Group DisegnaAlbero(RedBlackTree tree, Group group){
        //se la radice dell'albero è diversa da null
        if (tree.root != null) {
            group = this.DisegnaRadice(tree.root, group);
            group = this.DisegnaFigli(tree.root, rootX, rootY, group,1);
        }
        return group;
    }
}
