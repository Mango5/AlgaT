package renzi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PescaDomande implements EventHandler<ActionEvent> {
	protected Button button;
	protected Label[] label=new Label[2];
	protected FileReader file;
	protected BufferedReader reader;
	protected TextField testo;
	protected Stage stage;
	protected Boolean Spiegazione;
	public PescaDomande() throws IOException {								//crea una pagina con 2 label, una per la domanda e una per la spiegazione
		label[0]=new Label();												//una casella di testo per la risposta e un bottone per verificarla
		this.label[1]=new Label();											//inoltre carica il file dove sono contenute le informzaioni
		this.testo=new TextField();
		this.file=new FileReader("/home/alessio/Scrivania/java/domande"); 
		this.reader=new BufferedReader(file);
		this.stage=new Stage();
		this.Spiegazione=false;
		stage.setTitle("Domande");
		label[0].setText(reader.readLine());
		label[1].setLayoutY(200);
		button=new Button();
		button.setText("controlla");
		button.setLayoutY(100);
		button.setText("Click me");
		button.setOnAction(this);
		Pane layout=new Pane();
		layout.getChildren().add(button);
		layout.getChildren().add(label[0]);
		layout.getChildren().add(label[1]);
		testo.setLayoutY(50);
		layout.getChildren().add(testo);
		Scene scene=new Scene(layout,300,250);
		this.stage.setScene(scene);
	};
	public void vedi() {									//visualizza la pagina creata
		this.stage.show();
	}
	@Override
	public void handle(ActionEvent event) {                //il file di testo letto in precedenza è scritto in modo che la prima linea sia la domanda, la seconda la risposta e la terza la spiegazione 
		if(event.getSource()==button) {					   //se la risposta è corretta viene visualizzata la spiegazione e ripremendo sul botone viene visualizzata la domanda successiva
			try {
				if(this.Spiegazione==false) {			   
					reader.mark(10000);
					if(this.testo.getText().equals(reader.readLine())) {
						this.label[1].setText(reader.readLine());
						this.Spiegazione=true;
						this.button.setText("continua");
					}else {
						this.label[1].setText("risposta errata");
						reader.reset();
					}
				}else {
					this.label[0].setText(reader.readLine());
					this.label[1].setText("");
					this.button.setText("controlla");
					this.Spiegazione=false;
				}
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

