package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.GestionnaireErreur;
import model.Prepose;
import model.Registre;
import utils.GestionnaireDonnee;

public class AjoutPreposeController implements Initializable{
	
	//Instanciation des donnees
	Registre registre;
	GestionnaireDonnee gd = new GestionnaireDonnee();
	PopupController popup = new PopupController();
	
	@FXML TextField nomField;
	@FXML TextField prenomField;
	@FXML TextField addresseField;
	@FXML TextField telephoneField;
	@FXML TextField motDePasseField;
	
	@FXML Button btnConfirmer;
	@FXML Button btnAnnuler;
	
	ObservableList<Prepose> listePreposes;
	int numPreposes;
	
	public AjoutPreposeController() {
		registre = Registre.getInstance();
		listePreposes = registre.getListePreposes();
		numPreposes = registre.getNumPreposes();
	}
	
	//Vérification de l'ajout de préposé. Voir GestionDonnée
	private boolean verifyPrepose() {
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
		
		else if (!gd.verificationFormat(motDePasseField.getText(), "Null")) {
			GestionnaireErreur.setErreur("Vous devez inscrire un mot de passe");
			return false;
		}
		
		
		return true;
	}
	
	@FXML private void handleBtnAnnuler(ActionEvent event) {
		((Stage) btnAnnuler.getScene().getWindow()).close();
	}
	
	@FXML
	private void handleBtnConfirmer(ActionEvent event) {
		
		if (verifyPrepose()) {
			String numPrepose = (listePreposes.size() == 0) ? "20001" : gd.genereIdentifiant(listePreposes.get(listePreposes.size()-1).getNumPrepose(), 1); 
			
			listePreposes.add(new Prepose("P" + numPrepose, nomField.getText(), prenomField.getText(), 
					telephoneField.getText(), addresseField.getText(), motDePasseField.getText()));
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
