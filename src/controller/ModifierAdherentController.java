package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Adherent;
import model.GestionnaireErreur;
import model.Registre;
import utils.GestionnaireDonnee;

public class ModifierAdherentController implements Initializable{
	
	//Instanciation des donnees
	Registre registre;
	GestionnaireDonnee gd = new GestionnaireDonnee();
	PopupController popup = new PopupController();
	
	@FXML TextField addresseField;
	@FXML TextField telephoneField;
	
	@FXML Button btnConfirmer;
	@FXML Button btnAnnuler;
	
	ObservableList<Adherent> listeAdherents;
	Adherent adherent;
	
	public ModifierAdherentController() {
		registre = Registre.getInstance();
		listeAdherents = registre.getListeAdherents();
		
		adherent = GestionAdherentController.selectedAdherent;
	}
	
	//Vérification de la modification d'adhérent. Voir GestionDonnee
	private boolean verifyAdherent() {
		
		
		if (!gd.verificationFormat(addresseField.getText(), "Null")) {
			GestionnaireErreur.setErreur("Vous devez inscrire une addresse");
			return false;
		}
		
		else if (!gd.verificationFormat(telephoneField.getText(), "Null") || !gd.verificationFormat(telephoneField.getText(), "Telephone")) {
			GestionnaireErreur.setErreur("Vous devez inscrire un numéro de téléphone qui suit le format (###) ###-####");
			return false;
		}
		
		
		return true;
	}
	
	@FXML private void handleBtnAnnuler(ActionEvent event) {
		((Stage) btnAnnuler.getScene().getWindow()).close();
	}
	
	@FXML
	private void handleBtnConfirmer(ActionEvent event) {
		
		if (verifyAdherent()) {
			adherent.setAddresse(new SimpleStringProperty(addresseField.getText()));
			adherent.setTelephone(new SimpleStringProperty(telephoneField.getText()));

			((Stage) btnConfirmer.getScene().getWindow()).close();
		}
		
		else {
			popup.showPopup();
		}
		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		addresseField.setText(adherent.getAddresse());
		telephoneField.setText(adherent.getTelephone());
		
	}
	
}
