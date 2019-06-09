package renzi;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class  main extends Application{
	
	public static void main(String[] args)throws Exception{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		PescaDomande pesca=new PescaDomande();
		pesca.vedi();
		
	}

}