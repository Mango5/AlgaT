package algat_mod.algat_mod;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PaginaInizialeController implements Initializable {

     @FXML private Text txtTesto;
     @FXML private Text txtProg;
     @FXML private Button btnUno;
     @FXML private Button btnDue;

    /**
     * Inizializzazione della classe PaginaInizialeController
     * La classe TutorialController gestisce la pagina PaginaIniziale.fxml
     */
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //fissa la larghezza massima del testo e permette di andare automaticamente a capo quando necessario
        txtProg.wrappingWidthProperty().set(AlgaT_mod.sceneWidth - 10);
        txtTesto.wrappingWidthProperty().set(AlgaT_mod.sceneWidth * 60/100);
        
        //funzione che carica la parte di testo riguardante la descrizione del progetto
        loadData();
        
        //gestione azione click sul bottone per il collegamento al primo tutorial
        btnUno.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e){
                int data = 1;
                try {
                    //cattura lo stage da cui è partito l'evento di click
                    Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    //caricamento della pagina Tutorial.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Tutorial.fxml"));
                    BorderPane root = loader.load();
                    //istanzio un controller che mi permette di impostare il titolo del tutorial dinamicamente, tramite la chiamata alla funzione setData()
                    TutorialController controller = loader.<TutorialController>getController();
                    controller.setData(data);
                    //visualizzazione della nuova scene
                    Scene scene = new Scene(root,AlgaT_mod.sceneWidth,AlgaT_mod.sceneHeight);
                    stageTheEventSourceNodeBelongs.setScene(scene);
                } catch (IOException ex) {
                    Logger.getLogger(PaginaInizialeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
         //gestione azione click sul bottone per il collegamento al secondo tutorial
        btnDue.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e){
                    int data = 2;
                try {
                    //cattura lo stage da cui è partito l'evento di click
                    Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    //caricamento della pagina Tutorial.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Tutorial.fxml"));
                    BorderPane root = loader.load();
                    //istanzio un controller che mi permette di impostare il titolo del tutorial dinamicamente, tramite la chiamata alla funzione setData()
                    TutorialController controller = loader.<TutorialController>getController();
                    controller.setData(data);
                    //visualizzazione della nuova scene
                    Scene scene = new Scene(root,AlgaT_mod.sceneWidth,AlgaT_mod.sceneHeight);
                    stageTheEventSourceNodeBelongs.setScene(scene);
                } catch (IOException ex) {
                    Logger.getLogger(PaginaInizialeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
    } 
  
    public void loadData(){
        txtTesto.setText("In informatica, un albero binario di ricerca bilanciato e' un albero"
					+ "binario di ricerca la cui altezza, grazie a particolari condizioni che la sua struttura"
					+ "deve soddisfare, rimane limitata. Queste condizioni implicano delle operazioni di"
					+ "inserimento ed eliminazione piu' complesse rispetto a quelle di semplici alberi binari,"
					+ "ma garantiscono che esse vengano eseguite in O(log n)."
					+ " Alcune strutture di dati che implementano questo tipo di alberi sono B-Albero e Alberi Red Black"
					+ " Per semplicità utilizzeremo gli alberi red black per spiegare meglio questo concetto."
                    + "\n\n"
                    + "CHE COS'E' UN RED-BLACK TREE?"
                    + "\n\nUn Red Black Tree è un albero di ricerca binario autobilanciante utilizzato per"
                    + "memorizzare coppie di dati chiave-valore, di qualsiasi tipo, in modo ordinato."
                    + "\nUn RBT deve soddisfare le seguenti proprietà:"
                    + "\n    1) Un nodo può essere rosso o nero"
                    + "\n    2) Il nodo radice è nero"
                    + "\n    3) Tutti i nodi foglia sono neri e hanno chiave NULL"
                    + "\n    4) Entrambi i figli di ogni nodo rosso sono neri"
                    + "\n    5) Tutti i percorsi diretti dal nodo radice alle foglie contengono lo stesso numero di nodi neri"
                    + "\nOperazioni di inserimento e rimozione in un RBT apportano modifiche, alla struttura"
                    + "dell'albero, che possono violare le condizioni di bilanciamento (proprietà dei RBT)."
                    + "Se le condizioni di bilanciamento sono violate occorre:"
                    + "- modificare i colori nella zona della violazione"
                    + "- ribilanciare l'albero con opportune rotazioni");

         txtProg.setText("AlgaT e' un progetto universitario per il corso di Algoritmi e strutture dati."
                 + "Questa applicazione ha lo scopo di mostrare, all'utente che ne fara' uso,"
                 + " gli alberi bilanciati di ricerca tramite la struttura dati degli alberi Red-Black."
                 + "L'applicazione contiene alcuni tutorial che mostreranno aspetti diversi dell'argomento"
                 + " e alla fine di ognuno di essi e' presente una sezione di domande di autoapprendimento.");      
    }
    
}
