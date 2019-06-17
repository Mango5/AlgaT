package algat_mod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PescaDomande implements EventHandler<ActionEvent> {
	protected Button[] button=new Button[2];
	protected Label[] label=new Label[2];
	protected FileReader file;
	protected BufferedReader reader;
	protected TextField testo;
	protected Stage stage;
	protected Boolean Spiegazione;
	public PescaDomande(String FileDomande) throws IOException {								//crea una pagina con 2 label, una per la domanda e una per la spiegazione
		label[0]=new Label();												//una casella di testo per la risposta e un bottone per verificarla
		label[1]=new Label();											//inoltre carica il file dove sono contenute le informzaioni
		testo=new TextField();
		file=new FileReader(FileDomande); 
		reader=new BufferedReader(file);
		stage=new Stage();
		Spiegazione=false;
		stage.setTitle("Domande");
		label[0].setText(reader.readLine());
		label[1].setLayoutY(200);
		button[0]=new Button();
		button[0].setLayoutY(100);
		button[0].setText("controlla");
		button[0].setOnAction(this);
		button[1]=new Button();
		button[1].setLayoutY(150);
		button[1].setText("torna al menu");
		button[1].setOnAction(this);
		Pane layout=new Pane();
		layout.getChildren().add(button[0]);
		layout.getChildren().add(button[1]);
		layout.getChildren().add(label[0]);
		layout.getChildren().add(label[1]);
		testo.setLayoutY(50);
		layout.getChildren().add(testo);
		Scene scene=new Scene(layout,300,250);
		this.stage.setScene(scene);
	};
	public Scene vedi() {									//visualizza la pagina creata
		return this.stage.getScene();
	}
	@Override
	public void handle(ActionEvent event) {                //il file di testo letto in precedenza è scritto in modo che la prima linea sia la domanda, la seconda la risposta e la terza la spiegazione 
		if(event.getSource()==button[0]) {					   //se la risposta è corretta viene visualizzata la spiegazione e ripremendo sul botone viene visualizzata la domanda successiva
			try {
				if(this.Spiegazione==false) {			   
					reader.mark(10000);
					if(this.testo.getText().equals(reader.readLine())) {
						this.label[1].setText(reader.readLine());
						this.Spiegazione=true;
						this.button[0].setText("continua");
					}else {
						this.label[1].setText("risposta errata");
						reader.reset();
					}
				}else {
					this.label[0].setText(reader.readLine());
					this.label[1].setText("");
					this.button[0].setText("controlla");
					this.Spiegazione=false;
				}
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(event.getSource()==button[1]) {
			Stage stage=(Stage) button[0].getScene().getWindow();
			stage.close();
			BorderPane root;
			try {
				root = FXMLLoader.load(getClass().getResource("fxml/PaginaIniziale.fxml"));
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
	}
}

