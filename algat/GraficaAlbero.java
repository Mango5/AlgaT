/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algat;
import algat.tree.RedBlackTree;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author chiaramengoli
 */
public class GraficaAlbero {
    double rootX;
    double rootY;
    double radius; //raggio per tutti i cerchi
    
   public GraficaAlbero(){
        this.rootX=200;
        this.rootY=50;
        this.radius=20;
    }
            
    public void  DisegnaRadice(int key, Group group){
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
    }
    
    public void DisegnaFiglioSx(int key,Color color, double parentX, double parentY, Group group){
        //disegna il ramo
        Line line=new Line();
        line.setStartX(parentX);
        line.setStartY(parentY + radius);
        line.setEndX(parentX - (double)50);
        line.setEndY(parentY + radius + (double)50);
        //disegna il cerchio per il figlio sx
        Circle childsx = new Circle();
        childsx.setCenterX(line.getEndX());
        childsx.setCenterY(line.getEndY()+radius);
        childsx.setRadius(radius);
        childsx.setFill(color);
        //inserisce una label contenente la chiave del nodo
       String chiave = Integer.toString(key);
        Label label = new Label("4");
        label.setLayoutX(line.getEndX() - 9);
        label.setLayoutY(line.getEndY()+radius - 9);
        label.setTextFill(Color.WHITE);
        group.getChildren().addAll(line, childsx,label);
        
    }
    
    
    public void DisegnaFiglioDx(int key, Color color, double parentX, double parentY,Group group){
        Line line=new Line();
        line.setStartX(parentX);
        line.setStartY(parentY + radius);
        line.setEndX(parentX + (double)50);
        line.setEndY(parentY + radius + (double)50);
        
        Circle childdx = new Circle();
        childdx.setCenterX(line.getEndX());
        childdx.setCenterY(line.getEndY()+radius);
        childdx.setRadius(radius);
        childdx.setFill(color);
        
       //inserisce una label contenente la chiave del nodo
       String chiave = Integer.toString(key);
        Label label = new Label(chiave);
         label.setLayoutX(line.getEndX() - 9);
        label.setLayoutY(line.getEndY()+radius - 9);
        label.setTextFill(Color.WHITE);
       group.getChildren().addAll(line, childdx,label);
                   
    }
    
    public void DisegnaAlbero(RedBlackTree redblack){
        Group group = new Group();
        if(redblack.root != null){
            this.DisegnaRadice(redblack.root.key ,  group);
            if(redblack.root.left != null)
                    this.DisegnaFiglioSx(redblack.root.left.key, redblack.root.left.color , rootX, rootY, group);
        }
    }
}
