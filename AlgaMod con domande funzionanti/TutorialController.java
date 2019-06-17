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
    public  RedBlackTree rbt;
    @FXML Button btnFind;
    @FXML Button btnInsert;
    @FXML Button btnDelete;
    @FXML Button btnForward;
    @FXML Button btnBack;
    @FXML Hyperlink hlDomande;
    @FXML Hyperlink hlPaginaIniziale;
    @FXML private URL location;
    @FXML Text txtTitle;
    @FXML TextField txtValore;
    @FXML Pane pnTree;
     @FXML Text txtComments;
    @FXML  private ResourceBundle resources;
      
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.location=url;
        this.resources = rb;
        
        
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
				try {
					PescaDomande paginadomande=new PescaDomande("/home/alessio/Scrivania/java/domande");
					 Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
		             stageTheEventSourceNodeBelongs.setScene(paginadomande.vedi());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
    
    //imposta il titolo in base al tutorial 1 o 2
    public void setData(String data) {
        if(data == "1"){
            txtTitle.setText("Tutorial 1 - Ricerca");
            btnFind.setDisable(false);
            btnInsert.setDisable(true);
            btnDelete.setDisable(true);
            this.generaAlbero();
        }else{
            txtTitle.setText("Tutorial 2 - Inserimento e Cancellazione");
            btnFind.setDisable(true);
            btnInsert.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
    
    public void btnFind_Clicked(){
        
        String valore= txtValore.getText();
        //converto una stringa in intero
       String messaggio = rbt.treeFind(Integer.parseInt(valore));
       txtComments.setText(messaggio);
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
        String valore= txtValore.getText();
        String messaggio = rbt.treeInsert(Integer.parseInt(valore));
        txtComments.setText(messaggio);
    }
    
    public void btnDelete_Clicked(){
        //chiama la funzione di cancellazione
    }
    
    public void generaAlbero(){
        rbt = new RedBlackTree();
        rbt.setRoot(12);
        rbt.treeInsert(6);
        rbt.treeInsert(15);
        rbt.treeInsert(4);
        rbt.treeInsert(8);
        rbt.treeInsert(22);
        rbt.treeInsert(1);
        rbt.treeInsert(13);
 
   
       GraficaAlbero tree= new GraficaAlbero();
       Group group= new Group();
       group = tree.DisegnaAlbero(rbt, group);       
       
        pnTree.getChildren().add(group);
    }
       
}
