package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Adherent;
import model.GestionnaireErreur;
import model.Registre;
import utils.GestionnaireDonnee;

public class AjoutAdherentController implements Initializable {
	
	//Instanciation des donnees
	
	Registre registre;
	GestionnaireDonnee gd = new GestionnaireDonnee();
	PopupController popup = new PopupController();
	
	@FXML Label labelNom;
	@FXML Label labelPrenom;
	@FXML Label labelAddresse;
	@FXML Label labelTelephone;
	
	@FXML TextField nomField;
	@FXML TextField prenomField;
	@FXML TextField addresseField;
	@FXML TextField telephoneField;
	
	@FXML Button btnConfirmer;
	@FXML Button btnAnnuler;
	
	ObservableList<Adherent> listeAdherents;

	
	public AjoutAdherentController() {
		registre = Registre.getInstance();
		listeAdherents = registre.getListeAdherents();
	}
	
	@FXML private void handleBtnAnnuler(ActionEvent event) {
		((Stage) btnAnnuler.getScene().getWindow()).close();
	}
	
	//Verification de l'ajout de l'adherent. Voir GestionDonnee
	private boolean verifyAdherent() {
		if (!gd.verificationFormat(nomField.getText(), "Null")) {
			GestionnaireErreur.setErreur("Vous devez inscrire un nom");
			return false;
		}
		
		else if (!gd.verificationFormat(prenomField.getText(), "Null")) {
			GestionnaireErreur.setErreur("Vous devez inscrire un prénom");
			return false;
		}
		
		
		else if (!gd.verificationFormat(addresseField.getText(), "Null")) {
			GestionnaireErreur.setErreur("Vous devez inscrire une addresse");
			return false;
		}
		
		else if (!gd.verificationFormat(telephoneField.getText(), "Null") || !gd.verificationFormat(telephoneField.getText(), "Telephone")) {
			GestionnaireErreur.setErreur("Vous devez inscrire un numéro de téléphone qui suit le format (###) ###-####");
			return false;
		}
		
		
		return true;
	}
	
	@FXML 
	private void handleBtnConfirmer(ActionEvent event) {
		
		if (verifyAdherent()) {
			String numAdherent = (listeAdherents.size() == 0) ? "20001" : gd.genereIdentifiant(listeAdherents.get(listeAdherents.size()-1).getNumAdherent(), 1);
			listeAdherents.add(new Adherent("A" + numAdherent, nomField.getText(), prenomField.getText(), 
					telephoneField.getText(), addresseField.getText(), "0"));
			((Stage) btnConfirmer.getScene().getWindow()).close();
		}
		
		else {
			popup.showPopup();
		}
		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
	}
	
}
