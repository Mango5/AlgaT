/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chiaramengoli
 */
public class TutorialController implements Initializable {
  private   int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    private int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
    @FXML Button btnFind;
    @FXML Button btnInsert;
    @FXML Button btnDelete;
    @FXML Button btnForward;
    @FXML Button btnBack;
    @FXML Hyperlink hlDomande;
    @FXML Hyperlink hlPaginaIniziale;
    @FXML private URL location;
     
    @FXML  private ResourceBundle resources;
      private int sceneWidth = 0;
       private int sceneHeight = 0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.location=url;
        this.resources = rb;
        // TODO
        
         if (screenWidth <= 800 && screenHeight <= 600) {
            sceneWidth = 600;
            sceneHeight = 350;
        } else if (screenWidth <= 1280 && screenHeight <= 768) {
            sceneWidth = 800;
            sceneHeight = 450;
        } else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 1000;
            sceneHeight = 650;
        }
        
       hlDomande.setOnAction(new EventHandler<ActionEvent>() {
	@Override public void handle(ActionEvent e){
                Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                stageTheEventSourceNodeBelongs.setScene(new Scene(new Pane()));
        }
});
       
         hlPaginaIniziale.setOnAction(new EventHandler<ActionEvent>() {
	@Override public void handle(ActionEvent e){
                try {
                Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                BorderPane root = FXMLLoader.load(getClass().getResource("PaginaIniziale.fxml")); 
                  Scene scene = new Scene(root,sceneWidth,sceneHeight);
                stageTheEventSourceNodeBelongs.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(PaginaInizialeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
});
        
    }  
    
    public void btnFind_Clicked(){
        
    }
    
    
    public void btnBack_Clicked(){
        
    }
    
    public void btnForward_Clicked(){
        
    }
    
    
    public void btnInsert_Clicked(){
        
    }
    
    public void btnDelete_Clicked(){
        
    }
       
}
