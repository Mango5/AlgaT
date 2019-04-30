package application;
	
import java.util.Arrays;

import javafx.application.Application;
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
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.setTitle("PROGETTO ALGORITMI");
			
			primaryStage.setWidth(800); 
	        primaryStage.setHeight(500);
	        
			Text t1 = new Text(50, 50, "PROGETTO ALGAT");
			t1.setFont(Font.font("verdana", FontWeight.BOLD, 60));
			t1.setFill(Color.YELLOW);
			t1.setStroke(Color.RED);
			t1.setStrokeWidth(4);
			Text t2 = new Text(20,100, "In informatica, un albero binario di ricerca bilanciato è un albero"
					+ "\nbinario di ricerca la cui altezza, grazie a particolari condizioni che la sua struttura"
					+ "\ndeve soddisfare, rimane limitata. Queste condizioni implicano delle operazioni di"
					+ "\ninserimento ed eliminazione più complesse rispetto a quelle di semplici alberi binari,"
					+ "\nma garantiscono che esse vengano eseguite in O(log n).");
			t2.setFont(Font.font("arial", FontPosture.ITALIC, 20));
			Group g = new Group();
			g.getChildren().addAll(Arrays.asList(t1,t2));
			
			Button tutorial1 = new Button("TUTORIAL1");
			tutorial1.setLayoutX(50);
			tutorial1.setLayoutY(250);
			Button tutorial2 = new Button("TUTORIAL2");
			tutorial2.setLayoutX(50);
			tutorial2.setLayoutY(300);
			g.getChildren().addAll(Arrays.asList(tutorial1,tutorial2));
			
			Scene s1 = new Scene(g);
			primaryStage.setScene(s1);
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
