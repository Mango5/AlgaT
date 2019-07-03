/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algat_mod;

import java.io.BufferedReader;
import java.io.FileReader;
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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 *
 * @author chiaramengoli
 */
public class DomandeController implements Initializable{
    @FXML Text txtDomanda;  
    @FXML Text txtRisultato;
    @FXML TextField txtRisposta; 
    @FXML Button btnConferma;
    protected FileReader file;
    protected BufferedReader reader;
    protected Boolean spiegazione;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
    }
    
     public void setData(String data) throws IOException{
         //istanzio un FileReader a cui passo come parametro il percorso del file da leggere
         file =new FileReader(data);
         //istanzio un BufferedReader che legge e 'bufferizza' il contenuto del file per permettere un'efficiente lettura
         reader=new BufferedReader(file);
          spiegazione = false;
        try {
            /* readLine() --> Legge una riga di testo. Una riga è considerata terminata da una qualsiasi delle linee di alimentazione ('\n'), 
            un ritorno in carrozza ('\r'), o un ritorno in carrozza seguito immediatamente da un'alimentazione di linea.
            */
            txtDomanda.setText(reader.readLine());
        } catch (IOException ex) {
            Logger.getLogger(DomandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public void btnConferma_Clicked(){
         try {
                    if(this.spiegazione==false) {
                            /*
                                contrassegna la posizione corrente nel flusso di lettura del testo
                            */
                            reader.mark(10000);
                            //confronto il testo inserito dall'utente nel TextField txtRisposta con la risposta scritta sul file, se coincidono
                            if(this.txtRisposta.getText().equals(reader.readLine())) {
                                    this.txtRisultato.setText(reader.readLine());
                                    this.spiegazione=true;
                                    this.btnConferma.setText("Continua");
                            }else {
                                    this.txtRisultato.setText("risposta errata");
                                    //riposiziona il punto del flusso di testo al punto precedente (torna indietro con la posizione)
                                    reader.reset();
                            }
                    }else {
                            this.txtRisposta.setText("");
                            this.txtDomanda.setText(reader.readLine());
                            this.txtRisultato.setText("");
                            this.btnConferma.setText("Conferma");
                            this.spiegazione=false;
                    }
            }catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
     }
}
