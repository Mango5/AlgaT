package algat_mod;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PaginaInizialeController implements Initializable {

  private   int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    private int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
     @FXML private URL location;
     @FXML private Text txtTesto;
     @FXML private Text txtProg;
    @FXML  private ResourceBundle resources;
      @FXML private Button btnUno;
      @FXML private Button btnDue;
       private int sceneWidth = 0;
       private int sceneHeight = 0;
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.location=url;
        this.resources = rb;
        
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
        // TODO
        loadData();
        
        btnUno.setOnAction(new EventHandler<ActionEvent>() {
	@Override public void handle(ActionEvent e){
            try {
                Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                BorderPane root = FXMLLoader.load(getClass().getResource("fxml/Tutorial.fxml")); 
                  Scene scene = new Scene(root,sceneWidth,sceneHeight);
                stageTheEventSourceNodeBelongs.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(PaginaInizialeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
        
        btnDue.setOnAction(new EventHandler<ActionEvent>() {
	@Override public void handle(ActionEvent e){
            try {
                Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                BorderPane root = FXMLLoader.load(getClass().getResource("fxml/Tutorial.fxml")); 
                  Scene scene = new Scene(root,sceneWidth,sceneHeight);
                stageTheEventSourceNodeBelongs.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(PaginaInizialeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
       
    } 
  
    public void loadData(){
        txtTesto.setText("In informatica, un albero binario di ricerca bilanciato è un albero"
					+ "binario di ricerca la cui altezza, grazie a particolari condizioni che la sua struttura"
					+ "deve soddisfare, rimane limitata. Queste condizioni implicano delle operazioni di"
					+ "inserimento ed eliminazione più complesse rispetto a quelle di semplici alberi binari,"
					+ "ma garantiscono che esse vengano eseguite in O(log n)."
                                        + "\n\n"
                                        + "Che cos'è un Red-Black Tree?"
                                        + "....[risposta]....");

         txtProg.setText("AlgaT è un progetto universitario per il corso di Algoritmi e strutture dati."
                 + "Questa applicazione ha lo scopo di mostrare, all'utente che ne farà uso,"
                 + " gli alberi bilanciati di ricerca tramite l'algoritmo degli alberi Red-Black."
                 + "L'applicazione contiene alcuni tutorial che mostreranno aspetti diversi dell'argomento"
                 + " e alla fine di ognuno di essi è presente una sezione di domande di autoapprendimento.");      
    }
    
}
