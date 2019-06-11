package algat_mod;

import algat_mod.tree.*;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TutorialController implements Initializable {
    
    //dimensioni dello schermo del pc su cui viene eseguita l'applicazione
    private  final int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    private final int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
   //dimensioni della scene, cioè dell'applicazione
    private int sceneWidth = 0;
    private int sceneHeight = 0;
    
    private  int step = 0;
    
    @FXML Button btnFind;
    @FXML Button btnInsert;
    @FXML Button btnDelete;
    @FXML Button btnForward;
    @FXML Button btnBack;
    @FXML Hyperlink hlDomande;
    @FXML Hyperlink hlPaginaIniziale;
    @FXML private URL location;
    @FXML Text txtTitle;
    @FXML Pane pnTree;
    @FXML  private ResourceBundle resources;
      
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.location=url;
        this.resources = rb;
        
        RedBlackTree rbt = new RedBlackTree();
        rbt.setRoot(12);
        rbt.treeInsert(6);
        rbt.treeInsert(10);
   
        GraficaAlbero tree= new GraficaAlbero();
       Group group= new Group();
       group = tree.DisegnaAlbero(rbt, group);       
       
         pnTree.getChildren().add(group);
         
        // settare il titolo e la visibilità dei pulsanti in base al tutorial selezionato
      /*  if(true){
            txtTitle.setText("Tutorial - Lezione 1");
            btnInsert.setDisable(true);
            btnDelete.setDisable(true);
        }else{
            txtTitle.setText("Tutorial - Lezione 2");
            btnFind.setDisable(true);           
        }*/
        
        //bisogna istanziare l'albero di partenza
        
        
        //gestione dimensione schermo responsive
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
         
        //gestione azione al click sull'hyperlink relativo alle domande
       hlDomande.setOnAction(new EventHandler<ActionEvent>() {
	@Override public void handle(ActionEvent e){
                Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                stageTheEventSourceNodeBelongs.setScene(new Scene(new Pane()));
        }
    });
    
       //gestione azione al click sull'hyperlink relativo alla pagina iniziale
         hlPaginaIniziale.setOnAction(new EventHandler<ActionEvent>() {
	@Override public void handle(ActionEvent e){
                try {
                    //cattura lo stage corrente
                Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                //carica il file fxml contenente la pagina da aprire
                BorderPane root = FXMLLoader.load(getClass().getResource("fxml/PaginaIniziale.fxml")); 
                //istanzia una nuova scene passandogli come parametro il borderpane e le dimensioni
                  Scene scene = new Scene(root,sceneWidth,sceneHeight);
                  //setta la scene dello stage con la nuova scene della pagina iniziale
                stageTheEventSourceNodeBelongs.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(PaginaInizialeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
});
        
    }  
    
    public void btnFind_Clicked(){
        //chiama la funzione di ricerca
    }
    
    //i pulsanti back e forward mandano avanti step by step la funzione corrente 
    public void btnBack_Clicked(){
        step -=1;
    }
    
    public void btnForward_Clicked(){
        step +=1;
    }
    
    
    public void btnInsert_Clicked(){
        //chiama la funzione di inserimento
    }
    
    public void btnDelete_Clicked(){
        //chiama la funzione di cancellazione
    }
       
}
