package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	//charger le fichier Connexion.fmxl
	public void start(Stage primaryStage) throws Exception {
		
		System.out.println("-------------------------------PROJET MÉDIATHEQUE PAR ZACHARY TRISTAN LUHESHI-------------------------------");
		
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Connexion.fxml"));
		
		
	    Parent root = loader.load();
	    Scene scene = new Scene(root);
	    primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/iconConnexion.png").toExternalForm()));
	    primaryStage.setTitle("Connexion");
	    primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}