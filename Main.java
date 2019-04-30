package application;
	
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {//primarystage e' la finestra window
		try {
			
			primaryStage.setTitle("PROGETTO ALGORITMI");
			
			primaryStage.setWidth(800); 
	        primaryStage.setHeight(500);
	        
			Text testo1 = new Text(50, 50, "PROGETTO ALGAT");//testo in posizione (50,50)
			testo1.setFont(Font.font("verdana", FontWeight.BOLD, 60));
			testo1.setFill(Color.YELLOW);
			testo1.setStroke(Color.RED);
			testo1.setStrokeWidth(4);
			Text testo2 = new Text(20,100, "In informatica, un albero binario di ricerca bilanciato è un albero"
					+ "\nbinario di ricerca la cui altezza, grazie a particolari condizioni che la sua struttura"
					+ "\ndeve soddisfare, rimane limitata. Queste condizioni implicano delle operazioni di"
					+ "\ninserimento ed eliminazione più complesse rispetto a quelle di semplici alberi binari,"
					+ "\nma garantiscono che esse vengano eseguite in O(log n).");
			testo2.setFont(Font.font("arial", FontPosture.ITALIC, 20));
			
			Button bTutorial1 = new Button("TUTORIAL1");
			bTutorial1.setLayoutX(50);
			bTutorial1.setLayoutY(250);
			Button bTutorial2 = new Button("TUTORIAL2");
			bTutorial2.setLayoutX(50);
			bTutorial2.setLayoutY(300);
			
			Group gruppo1 = new Group();//"lista" degli elementi in una scena
			gruppo1.getChildren().addAll(Arrays.asList(testo1,testo2,bTutorial1,bTutorial2));
			Scene scena1 = new Scene(gruppo1);
			
	    	Text testo3 = new Text(50, 50, "PROGETTO ALGAT");
	    	Button bIndietro = new Button("TORNA INDIETRO");
			bIndietro.setLayoutX(50);
			bIndietro.setLayoutY(250);
			
			Group gruppo2 = new Group();
			gruppo2.getChildren().addAll(Arrays.asList(testo3,bIndietro));
			Scene scena2 = new Scene(gruppo2);
			
			Text testo4 = new Text(50, 50, "PROGETTO ALGAT");
	    	Button bIndietro2 = new Button("TORNA INDIETRO");
			bIndietro2.setLayoutX(50);
			bIndietro2.setLayoutY(250);
			
			Group gruppo3 = new Group();
			gruppo3.getChildren().addAll(Arrays.asList(testo4,bIndietro2));
			Scene scena3 = new Scene(gruppo3);
			
			bTutorial1.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	primaryStage.setScene(scena2);
			    }
			});
			
			bTutorial2.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	primaryStage.setScene(scena3);
			    }
			});
			
			bIndietro.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	primaryStage.setScene(scena1);
			    }
			});
			
			bIndietro2.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	primaryStage.setScene(scena1);
			    }
			});
			
			primaryStage.setScene(scena1);
			primaryStage.show();
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
