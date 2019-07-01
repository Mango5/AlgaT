package algat_modChiara;

import algat_modChiara.tree.*;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class GraficaAlbero {
    double rootX;
    double rootY;
    double radius; //raggio per tutti i cerchi
    
   public GraficaAlbero(){
        rootX=400;
        rootY=50;
        radius=20;
    }
            
    public Group  DisegnaRadice(int key, Group group){
        Circle root = new Circle();
        root.setCenterX(rootX);
        root.setCenterY(rootY);
        root.setRadius(radius);
        root.setFill(javafx.scene.paint.Color.BLACK);
         String chiave = Integer.toString(key);
        Label label = new Label(chiave);
         label.setLayoutX(rootX - 9);
        label.setLayoutY(rootY - 9);
        label.setTextFill(Color.WHITE);
         group.getChildren().addAll(root,label);
         return group;
    }
    
    public Group DisegnaFiglioSx(Nodo node, double parentX, double parentY, Group group){
        //disegna il ramo
        Line line=new Line();
        line.setStartX(parentX);
        line.setStartY(parentY + radius);
        line.setEndX(parentX - (double)75);
        line.setEndY(parentY + radius + (double)50);
        //disegna il cerchio per il figlio sx
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
        	group.getChildren().addAll(line, childsx,label);
        }
        else {
        	Label label = new Label("NIL");
        	label.setLayoutX(line.getEndX() - 9);
        	label.setLayoutY(line.getEndY()+radius - 9);
        	label.setTextFill(Color.WHITE);
                 childsx.setFill(Color.GREY);
        	group.getChildren().addAll(line, childsx,label);
        }
        return group;
    }
    
    
    public Group DisegnaFiglioDx(Nodo node, double parentX, double parentY,Group group){
        Line line=new Line();
        line.setStartX(parentX);
        line.setStartY(parentY + radius);
        line.setEndX(parentX + (double)75);
        line.setEndY(parentY + radius + (double)50);
        
        Circle childdx = new Circle();
        childdx.setCenterX(line.getEndX());
        childdx.setCenterY(line.getEndY()+radius);
        childdx.setRadius(radius);
       
        
       //inserisce una label contenente la chiave del nodo
        if (node != null) {
            //converte un intero in una stringa
        	String chiave = Integer.toString(node.key);
        	Label label = new Label(chiave);
        	label.setLayoutX(line.getEndX() - 9);
        	label.setLayoutY(line.getEndY()+radius - 9);
        	label.setTextFill(Color.WHITE);
                 childdx.setFill(node.color);
        	group.getChildren().addAll(line, childdx,label);
        }
        else {
        	Label label = new Label("NIL");
        	label.setLayoutX(line.getEndX() - 9);
        	label.setLayoutY(line.getEndY()+radius - 9);
        	label.setTextFill(Color.WHITE);
                 childdx.setFill(Color.GREY);
        	group.getChildren().addAll(line, childdx,label);
        }
        return group;
    }
    
    public Group DisegnaFigli(Nodo node, double parentX, double parentY, Group group) {
        if(node == null)
                return group;
        else{
            group = this.DisegnaFiglioSx(node.left, parentX, parentY, group);
            group = this.DisegnaFiglioDx(node.right, parentX, parentY, group);          
        }
        if(node.left != null)
                group = this.DisegnaFigli(node.left, parentX-(double)75, parentY+ radius + (double)50 + radius, group);
        if(node.right != null)
                 group = this.DisegnaFigli(node.right, parentX+(double)75, parentY+ radius + (double)50 + radius, group);
      
    	return group;
    }
    
    public Group DisegnaAlbero(RedBlackTree tree, Group group){
        group = this.DisegnaRadice(tree.root.key, group);
        group = this.DisegnaFigli(tree.root, rootX, rootY, group);
        return group;
    }
}
