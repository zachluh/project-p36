package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.GestionnaireErreur;

public class PopupController implements Initializable{

	@FXML Text text;
	@FXML Button btnOK;
	
	@FXML private void handleBtnOK(ActionEvent event) {
		((Stage) btnOK.getScene().getWindow()).close();
	}
	
	
	
	public void showPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Popup.fxml"));
            
            Parent root = loader.load();

            Scene scenePopup = new Scene(root);
            

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scenePopup);
            stage.setTitle("Erreur");
            stage.getIcons().add(new Image(getClass().getResource("/images/iconErreur.png").toExternalForm()));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		//Utilise l'erreur décrite dans GestionnaireErreur
		text.setText(GestionnaireErreur.getErreur());
		
	}
	
}
