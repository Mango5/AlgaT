package algat_mod;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
         file =new FileReader(data);
         reader=new BufferedReader(file);
          spiegazione = false;
        try {
            txtDomanda.setText(reader.readLine());
        } catch (IOException ex) {
            Logger.getLogger(DomandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void esci() {
    	 Stage stage=(Stage) btnConferma.getScene().getWindow();
 		stage.close();
 		BorderPane root;
 		try {
 			root = FXMLLoader.load(getClass().getResource("PaginaIniziale.fxml"));
 			int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
 		    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
 			int sceneWidth = 0;
 	        int sceneHeight = 0;
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

 	        // Scene       
 	       Scene scene = new Scene(root,sceneWidth,sceneHeight);
 	      // Scene scene  = new  Scene(root);
 	        stage.setResizable(true);
 	        stage.setScene(scene);
 	        stage.show();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	}
     public void btnConferma_Clicked(){
         try {
        	 
                    if(this.spiegazione==false) {			   
                            reader.mark(10000);
                            if(this.txtRisposta.getText().equals(reader.readLine())) {
                                    this.txtRisultato.setText(reader.readLine());
                                    this.spiegazione=true;
                                    this.btnConferma.setText("Continua");
                            }else {
                                    this.txtRisultato.setText("risposta errata");
                                    reader.reset();
                            }
                    }else {
                            this.txtRisposta.setText("");
                            this.txtDomanda.setText(reader.readLine());
                            this.txtRisultato.setText("");
                            this.btnConferma.setText("Conferma");
                            this.spiegazione=false;
                    }
                    reader.mark(10000);
                    if(reader.readLine()==null) {
                    	esci();
                    }else {
                    	reader.reset();
                    }
            }catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
     }
}
