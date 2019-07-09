package algat_mod;

import algat_mod.tree.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.application.Platform;
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

    private int numTutorial; //indica il numero del tutorial
    public  RedBlackTree rbt;
    public Text txtCommentsHidden;
    @FXML Button btnFind;
    @FXML Button btnInsert;
    @FXML Button btnDelete;
    @FXML Button btnForward;
    @FXML Button btnBack;
    @FXML Hyperlink hlDomande;
    @FXML Hyperlink hlPaginaIniziale;
    @FXML Text txtTitle;
    @FXML TextField txtValore;
    @FXML Pane pnTree;
     @FXML Text txtComments;
    public Boolean stepInEsecuzione;
     

///////////////////////////////////////////////////////////////////////////////////////
//Creazione Matrice 10x2(funzione undo)
    public int c;//indica l'ultima azione eseguita
	public int h;//indica l'ultima azione o quelle precedenti,servirà a tornare indietro
	public int matrice[][];//matrice contenente un numero(colonna 0) e il tipo di azione(inserimento o cancellazione; colonna 1)
///////////////////////////////////////////////////////////////////////////////////////      
    
    /**
     * Inizializzazione della classe TutorialController
     * La classe TutorialController gestisce la pagina Tutorial.fxml
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //inizializzazione matrice
            matrice= new int[10][2];
            c=-1;
            h=-1;
    	//////////////////////////
        rbt = new RedBlackTree();
        //fissa la larghezza massima del testo e permette di andare automaticamente a capo quando necessario
         txtComments.wrappingWidthProperty().set(180); 
         txtCommentsHidden=new Text("");
         txtCommentsHidden.setVisible(false);
        //gestione azione click sull'hyperlink relativo alle domande
       hlDomande.setOnAction(new EventHandler<ActionEvent>() {
	@Override public void handle(ActionEvent e){
            try {
                //catturo lo stage da cui è partito l'evento di click
                Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)e.getSource()).getScene().getWindow();    
                //caricamento della pagina Domande.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Domande.fxml"));
                BorderPane root = loader.load();
                //istanzio un controller che mi permette di scegliere il file delle domande da caricare dinamicamente, tramite la chiamata alla funzione setData()
                DomandeController controller = loader.<DomandeController>getController();
                //carico il file delle domande relativo al tutorial attuale
                if(numTutorial == 1)
                     controller.setData("/home/alessio/Scrivania/Domande/DomandeTutorial1");
                else 
                    controller.setData("/home/alessio/Scrivania/Domande/DomandeTutorial2");
                //visualizzazione della nuova scene
                Scene scene = new Scene(root,AlgaT_mod.sceneWidth,AlgaT_mod.sceneHeight);
                stageTheEventSourceNodeBelongs.setScene(scene);
            } catch (IOException e1) {
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
                  Scene scene = new Scene(root,AlgaT_mod.sceneWidth,AlgaT_mod.sceneHeight);
                  //setta la scene dello stage con la nuova scene della pagina iniziale
                stageTheEventSourceNodeBelongs.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(PaginaInizialeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
});
        
    }  
    
    //imposta il titolo in base al tutorial 1 o 2
    public void setData(int data) {
        //imposta il titolo a seconda del tutorial selezionato dall'utente e abilita/disabilita i tasti
        numTutorial = data;
        if(data == 1){
            txtTitle.setText("Tutorial 1 - Ricerca");
            btnFind.setDisable(false);
            btnInsert.setDisable(true);
            btnDelete.setDisable(true);
            //genera un albero che verrà visualizzato subito all'apertura del tutorial
            this.generaAlbero();
        }else{
            txtTitle.setText("Tutorial 2 - Inserimento e Cancellazione");
            btnFind.setDisable(true);
            btnInsert.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
    
    public void btnFind_Clicked(){
       //catturo il valore inserito dall'utente nel TextField
       String valore= txtValore.getText();
        //converto la stringa in intero per poter passare il valore alla funzione treeFind() che mi ritornerà un messaggio
       String messaggio = rbt.treeFind(Integer.parseInt(valore));
       //visualizzo il messaggio restituito nell'apposito spazio
       txtComments.setText(messaggio);
       txtValore.setText("");
    }
    
    //i pulsanti avanti e indietro permettono l'esecuzione step by step dell'azione (ricerca/inseriemento/cancellazione) selezionata 
    public void btnBack_Clicked(){
       if(c > -1) {
        if(matrice[h][1] == 0) { 
        //se l'ultima azione è stata una cancelazione allora reinserisco nell'albero il numero cancellato
        	rbt.treeInsert(matrice[h][0],txtComments,txtCommentsHidden);
        	this.ridisegna();
        	System.out.println(matrice[h][0]);
        }else {									
        //se è un inserimento allora cancello il numero inserito
        	rbt.treeDelete(matrice[h][0],txtComments,txtCommentsHidden);		
        	this.ridisegna();
        	System.out.println(matrice[h][0]);
        }
        h--;
        //con h-- "punto" all'azione precedente nel caso voglia cambiare anche quella
    }
    }
    
    public void btnForward_Clicked(){
    	this.ridisegna();
    	txtComments.setText(txtCommentsHidden.getText());
    	txtCommentsHidden.setText("");
    	btnBack.setDisable(false);
    	btnDelete.setDisable(false);
    	btnInsert.setDisable(false);
    }
    
    
    public void btnInsert_Clicked(){
    	btnBack.setDisable(true);
    	btnDelete.setDisable(true);
    	btnInsert.setDisable(true);
        //catturo il valore inserito dall'utente nel TextField
        String valore= txtValore.getText();
        //converto la stringa in intero per poter passare il valore alla funzione treeInsert() che mi ritornerà un messaggio
        rbt.treeInsert(Integer.parseInt(valore),txtComments,txtCommentsHidden);
        //visualizzo il messaggio restituito nell'apposito spazio
        txtValore.setText("");
        this.ridisegna();
        //ridisegno l'albero
        //qui registro l'azione inserimento nella matrice
        	c = h + 1;
        	h = c;
        	matrice[c][0] = Integer.parseInt(valore);
        	matrice[c][1] = 1;// l'1 indica l'inserimento       
    }
    
    public void btnDelete_Clicked(){
    	btnBack.setDisable(true);
    	btnDelete.setDisable(true);
    	btnInsert.setDisable(true);
        //catturo il valore inserito dall'utente nel TextField
        String valore= txtValore.getText();
        //converto la stringa in intero per poter passare il valore alla funzione treeDelete() che mi ritornerà un messaggio
        rbt.treeDelete(Integer.parseInt(valore),txtComments,txtCommentsHidden);
        //visualizzo il messaggio restituito nell'apposito spazio
        txtValore.setText("");
       
        //ridisegno l'albero
        this.ridisegna();
        //qui registro l'azione cancella nella matrice
        c = h + 1;
        h = c;
        matrice[c][0] = Integer.parseInt(valore);
        matrice[c][1] = 0; //lo 0 indica la cancellazione
        
    }
    
    public void ridisegna() {
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
    
    public void generaAlbero(){
        //istanzio una nuova classe RedBlackTree e vado a creare un albero tramite la funzione treeInsert()
        rbt = new RedBlackTree();
        rbt.treeInsert(12,txtComments,txtCommentsHidden);
        rbt.treeInsert(7,txtComments,txtCommentsHidden);
        
       //vado a disgnare l'albero nel Pane
       GraficaAlbero tree= new GraficaAlbero();
       Group group= new Group();
       group = tree.DisegnaAlbero(rbt, group);  
       pnTree.getChildren().add(group);
    }
       
}
