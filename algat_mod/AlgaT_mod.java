package algat_mod;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AlgaT_mod extends Application {
    //dimensioni dello schermo
    public static final int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    public static final int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
    public static  int sceneWidth = 0;
    public static  int sceneHeight = 0;
    @Override
    public void start(Stage stage) throws Exception {    
        // creazione di un design responsive, le dimensioni dell'applicazione sono calcolate in proporzione alla dimensione dello schermo
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
        //caricamento della pagina iniziale, la pagina iniziale e' un file .fxml
        BorderPane root = FXMLLoader.load(getClass().getResource("fxml/PaginaIniziale.fxml"));
        // creazione di una nuova istanza dell'oggeto scene
        Scene scene = new Scene(root,sceneWidth,sceneHeight);
        stage.setResizable(true);
        //visualizzazione della scene
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}