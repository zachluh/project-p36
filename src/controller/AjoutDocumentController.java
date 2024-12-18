package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DVD;
import model.Document;
import model.GestionnaireErreur;
import model.Livre;
import model.Periodique;
import model.Registre;
import utils.GestionnaireDonnee;

public class AjoutDocumentController implements Initializable{
	
	//Instanciation des donnees
	
	Registre registre;
	GestionnaireDonnee gd = new GestionnaireDonnee();
	PopupController popup = new PopupController();
	
	@FXML ChoiceBox<String> choixTypeDocument;
	
	@FXML TextField titreDocument;
	@FXML TextField auteurRealisateurDocument;
	@FXML TextField dateDocument;
	@FXML TextField motsClesDocument;
	@FXML TextField noVolumePeriodique = new TextField();
	@FXML TextField noPeriodique = new TextField();
	@FXML TextField noDisquesDVD = new TextField();
	
	@FXML Label labelTitre;
	@FXML Label labelAuteurRealisateur;
	@FXML Label labelDate;
	@FXML Label labelMotsCles;
	@FXML Label labelVolumePeriodique = new Label();
	@FXML Label labelNoPeriodique = new Label();
	@FXML Label labelNoDisquesDVD = new Label();
	
	@FXML Button btnConfirmer;
	@FXML Button btnAnnuler;
	
	@FXML VBox ajoutLabels;
	@FXML VBox ajoutFields;
	
	ObservableList<Document> listeTout;
	ObservableList<Livre> listeLivres;
	ObservableList<Periodique> listePeriodiques;
	ObservableList<DVD> listeDVDs;
	
	ObservableList<Document> sortedListeTout;
	ObservableList<Livre> sortedListeLivres;
	ObservableList<Periodique> sortedListePeriodiques;
	ObservableList<DVD> sortedListeDVDs;
	
	public AjoutDocumentController() {
		registre = Registre.getInstance();
		
		listeTout = registre.getListeTout();
		listePeriodiques = registre.getListePeriodiques();
		listeLivres = registre.getListeLivres();
		listeDVDs = registre.getListeDVDs();
		
		sortedListeTout = registre.getSortedListeTout();
		sortedListeLivres = registre.getSortedListeLivres();
		sortedListePeriodiques = registre.getSortedListePeriodiques();
		sortedListeDVDs = registre.getSortedListeDVDs();
		
		
		
		
	}
	
	@FXML private void handleBtnAnnuler(ActionEvent event) {
		((Stage) btnAnnuler.getScene().getWindow()).close();
	}
	
	//Vérification de l'ajout de document. Voir GestionDonnee
	private boolean verifyAjout() {
		if (!gd.verificationFormat(titreDocument.getText(), "Null")) {
			GestionnaireErreur.setErreur("Vous devez inscrire un titre");
			return false;
		}
		
		else if (ajoutFields.getChildren().contains(auteurRealisateurDocument)) {
			 if (!gd.verificationFormat(auteurRealisateurDocument.getText(), "Null")) {
				GestionnaireErreur.setErreur("Vous devez inscrire un auteur/réalisateur");
				return false;
			}
			
		}
		
		else if (!gd.verificationFormat(dateDocument.getText(), "Null") || !gd.verificationFormat(dateDocument.getText(), "Date")) {
			GestionnaireErreur.setErreur("Vous devez inscrire une date qui suit le format yyyy-mm-dd");
			return false;
		}
		
		else if (!gd.verificationFormat(motsClesDocument.getText(), "Null")) {
			GestionnaireErreur.setErreur("Vous devez inscrire des mots clés");
			return false;
		}
		
		if (ajoutFields.getChildren().contains(noVolumePeriodique)) {
			if (!gd.verificationFormat(noVolumePeriodique.getText(), "Null") || !gd.verificationFormat(noVolumePeriodique.getText(), "Nombre")) {
				GestionnaireErreur.setErreur("Vous devez inscrire un numéro de volumes");
				return false;
			}
		}
		
		if (ajoutFields.getChildren().contains(noPeriodique)) {
			if (!gd.verificationFormat(noPeriodique.getText(), "Null") || !gd.verificationFormat(noPeriodique.getText(), "Nombre")) {
				GestionnaireErreur.setErreur("Vous devez inscrire un numéro de periodique");
				return false;
			}
		}
		
		if (ajoutFields.getChildren().contains(noDisquesDVD)) {
			if (!gd.verificationFormat(noDisquesDVD.getText(), "Null") || !gd.verificationFormat(noDisquesDVD.getText(), "Nombre")) {
				GestionnaireErreur.setErreur("Vous devez inscrire un numéro de disques");
				return false;
			}
		}
		
		return true;
	}
	
	//Change les "children" des conteneurs ajoutLabels et ajoutFields
	private void changeChildren(boolean ajoutOuEnleve, Label ... labels) {
		
		System.out.println(labels.length);
		
		if (ajoutOuEnleve == false) {
			for (Label label : labels) {
				if (ajoutLabels.getChildren().contains(label)) {
					if (label == labelVolumePeriodique)  {
						ajoutLabels.getChildren().remove(labelVolumePeriodique);
						ajoutFields.getChildren().remove(noVolumePeriodique);
					}
					
					else if (label == labelNoPeriodique)  {
						ajoutLabels.getChildren().remove(labelNoPeriodique);
						ajoutFields.getChildren().remove(noPeriodique);
					}
					
					else if (label == labelNoDisquesDVD) {
						ajoutLabels.getChildren().remove(labelNoDisquesDVD);
						ajoutFields.getChildren().remove(noDisquesDVD);
					}
					
					else if (label == labelAuteurRealisateur) {
						ajoutLabels.getChildren().remove(labelAuteurRealisateur);
						ajoutFields.getChildren().remove(auteurRealisateurDocument);
					}
				}

			}
		}
		
		else {
			for (Label label : labels) {
				if (!ajoutLabels.getChildren().contains(label)) {
					if (label == labelVolumePeriodique)  {
						ajoutLabels.getChildren().add(labelVolumePeriodique);
						ajoutFields.getChildren().add(noVolumePeriodique);
					}
					
					else if (label == labelNoPeriodique)  {
						ajoutLabels.getChildren().add(labelNoPeriodique);
						ajoutFields.getChildren().add(noPeriodique);
					}
					
					else if (label == labelNoDisquesDVD) {
						ajoutLabels.getChildren().add(labelNoDisquesDVD);
						ajoutFields.getChildren().add(noDisquesDVD);
					}
					
					else if (label == labelAuteurRealisateur) {
						System.out.println("as3");
						ajoutLabels.getChildren().add(labelAuteurRealisateur);
						ajoutFields.getChildren().add(auteurRealisateurDocument);
					}
				}

			}
		}
		

	}
	
	//Génère le formulaire d'ajout
	private void creerFormulaire(String typeDocument) {
		System.out.println("typeDocument: " + typeDocument);
		switch (typeDocument) {
			case "Livre":
				System.out.println("Livre");
				changeChildren(false, labelVolumePeriodique, labelNoPeriodique, labelNoDisquesDVD);
				changeChildren(true, labelAuteurRealisateur);
				labelAuteurRealisateur.setText("Auteur:");
				labelAuteurRealisateur.setLayoutY(37);
				break;
			case "Periodique":
				System.out.println("Periodique");
				changeChildren(false, labelNoDisquesDVD, labelAuteurRealisateur);
				changeChildren(true, labelVolumePeriodique, labelNoPeriodique);
				break;
			case "DVD":
				System.out.println("DVD");
				changeChildren(false, labelVolumePeriodique, labelNoPeriodique);
				changeChildren(true, labelNoDisquesDVD, labelAuteurRealisateur);
				labelAuteurRealisateur.setText("Realisateur:");
				labelAuteurRealisateur.setLayoutY(37);
				break;
				
		}
		
		
	}
	
	@FXML
	private void handleBtnConfirmer(ActionEvent event) {
		
		if (verifyAjout()) {
			if (choixTypeDocument.getSelectionModel().getSelectedItem() == "Livre") {
				String numDoc = (listeLivres.size() == 0) ? "Liv1" : gd.genereIdentifiant(listeLivres.get(listeLivres.size()-1).getNumDocProperty(), 3);
				Livre newLivre = new Livre("Liv" + numDoc, titreDocument.getText(), dateDocument.getText(), 
						auteurRealisateurDocument.getText(), "Disponible", "");
				
				listeLivres.add(newLivre);
				sortedListeLivres.add(newLivre);
				
				gd.serializerListeObservable("Livre");
			}
			
			else if (choixTypeDocument.getSelectionModel().getSelectedItem() == "Periodique") {
				String numDoc = (listePeriodiques.size() == 0) ? "Per1" : gd.genereIdentifiant(listePeriodiques.get(listePeriodiques.size()-1).getNumDocProperty(), 3);
				Periodique newPeriodique = new Periodique("Per" + numDoc, titreDocument.getText(), noVolumePeriodique.getText(), 
						noPeriodique.getText(), dateDocument.getText() , "Disponible", "");
				
				listePeriodiques.add(newPeriodique);
				sortedListePeriodiques.add(newPeriodique);
				
				gd.serializerListeObservable("Periodique");
			}
			
			else if (choixTypeDocument.getSelectionModel().getSelectedItem() == "DVD") {
				String numDoc = (listeDVDs.size() == 0) ? "DVD1" : gd.genereIdentifiant(listeDVDs.get(listeDVDs.size()-1).getNumDocProperty(), 3);
				DVD newDvd = new DVD("DVD" + numDoc, titreDocument.getText(), dateDocument.getText(), noDisquesDVD.getText() ,  
						auteurRealisateurDocument.getText(), "Disponible", "");
				listeDVDs.add(newDvd);
				sortedListeDVDs.add(newDvd);
				gd.serializerListeObservable("DVD");
				
			}
			
			ObservableList<Document> listToutTemp = FXCollections.observableArrayList();
			listToutTemp.addAll(listeLivres);
			listToutTemp.addAll(listePeriodiques);
			listToutTemp.addAll(listeDVDs);
			listeTout.setAll(listToutTemp);
			
			((Stage) btnConfirmer.getScene().getWindow()).close();
		}
		
		else {
			popup.showPopup();
		}
		

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		labelVolumePeriodique.setText("No Volume:");
		labelNoPeriodique.setText("No Periodique");
		labelNoDisquesDVD.setText("No Disques");
		
		String[] typeDocuments = {"Livre", "Periodique", "DVD"};
		choixTypeDocument.setItems(FXCollections.observableArrayList(typeDocuments));
		
		choixTypeDocument.getSelectionModel().select(0);
		
		//Un listener qui crée le formulaire avec creerFormulaire() à chaque fois que le type de document change
		choixTypeDocument.getSelectionModel().selectedIndexProperty().addListener((observableValue, vieuChoix, nouvChoix) -> {
			System.out.println("document choisi: " + typeDocuments[(int)nouvChoix]);
			if (typeDocuments[(int)nouvChoix] == "Livre") {
				creerFormulaire("Livre");
			}
			
			else if (typeDocuments[(int)nouvChoix] == "Periodique") {
				creerFormulaire("Periodique");
			}
			
			else if (typeDocuments[(int)nouvChoix] == "DVD") {
				creerFormulaire("DVD");
			}
			
		});
		
	}
	

}
