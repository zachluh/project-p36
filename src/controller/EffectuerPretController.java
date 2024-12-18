package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Adherent;
import model.DVD;
import model.Document;
import model.GestionnaireErreur;
import model.Livre;
import model.Periodique;
import model.Pret;
import model.Registre;
import utils.GestionnaireDonnee;

public class EffectuerPretController implements Initializable {
	
	Registre registre;
	GestionnaireDonnee gd = new GestionnaireDonnee();
	PopupController popup = new PopupController();
	
	@FXML TableView<Adherent> tableAdherents;
	@FXML TableColumn<Adherent, String> colNumAdherent;
	@FXML TableColumn<Adherent, String> colNom;
	@FXML TableColumn<Adherent, String> colPrenom;
	@FXML TableColumn<Adherent, String> colAddresse;
	@FXML TableColumn<Adherent, String> colTelephone;
	@FXML TableColumn<Adherent, Integer> colPrets;
	@FXML TableColumn<Adherent, Integer> colSolde;
	
	@FXML Button btnConfirmer;
	@FXML Button btnAnnuler;
	
	ObservableList<Adherent> listeAdherents;
	Document document;
	
	
	public EffectuerPretController() {
		registre = Registre.getInstance();
		listeAdherents = registre.getListeAdherents();
		document = CatalogueController.selectedDocument;
	} 
	
	@FXML private void handleBtnAnnuler(ActionEvent event) {
		((Stage) btnAnnuler.getScene().getWindow()).close();
	}

	@FXML
	private void handleBtnConfirmer(ActionEvent event) {
		Adherent adherent = tableAdherents.getSelectionModel().getSelectedItem();
		
		if (adherent != null) {
			
			if (adherent.getTrueSolde().equals("0")) {
				int nbLivresAdherent = 0;
				int nbPeriodiquesAdherent = 0;
				int nbDVDsAdherent = 0;
				
				if (document instanceof Livre) { nbLivresAdherent++; }
				else if (document instanceof Periodique) { nbPeriodiquesAdherent++; }
				else if (document instanceof DVD) { nbDVDsAdherent++; }
				
				for (Pret pret : adherent.getListePretsActifs()) {
					if (pret.getDocumentPrete() instanceof Livre) { nbLivresAdherent++; }
					else if (pret.getDocumentPrete() instanceof Periodique) { nbPeriodiquesAdherent++; }
					else if (pret.getDocumentPrete() instanceof DVD) { nbDVDsAdherent++; }
				}
				
				if (nbLivresAdherent>3 || nbPeriodiquesAdherent>1 || nbDVDsAdherent>2) {
					GestionnaireErreur.setErreur("Un adherent peut seulement avoir 3 livres, 2 DVDs et 1 periodique maximum");
					popup.showPopup();
				}
				
				else {
					
					document.addPrêt();
					document.setEmprunteurProperty(new SimpleStringProperty(adherent.getNom() + " " + adherent.getPrenom()));
					document.setEtatProperty(new SimpleStringProperty("Non Disponible"));
					
					int dureePret = 0;
					
					if (document instanceof Livre) { dureePret = 14; }
					else if (document instanceof Periodique) { dureePret = 3; }
					else if (document instanceof DVD) { dureePret = 7; }
					
					
					LocalDate dateCourante = LocalDate.now();
					LocalDate dateRetour = dateCourante.plusDays(dureePret);
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			        String dateCouranteFormatte = dateCourante.format(formatter);
			        String dateRetourFormatte = dateRetour.format(formatter);
					Pret pret = new Pret(document.getNumDocProperty(), dateCouranteFormatte, dateRetourFormatte, "", document);
			        
					adherent.getListePrets().add(pret);
					adherent.getListePretsActifs().add(pret);
					gd.serializerListeObservable("Adherent");
					
					//System.out.println("Document: " + document.getNumDocProperty());
					//System.out.println("Document prêts: " + document.getNbPrêts());
					//System.out.println("Document emprunteur: " + document.getEmprunteurProperty());
					
					((Stage) btnConfirmer.getScene().getWindow()).close();
					
				}
			}
			
			else {
				GestionnaireErreur.setErreur("Un adhérent avec une amende non-payée ne peut pas emprunter un article");
				popup.showPopup();
			}
			
		}
		
		else {
			GestionnaireErreur.setErreur("Vous devez sélectionner un adhérent");
			popup.showPopup();
		}
		
		
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		tableAdherents.setItems(listeAdherents);
		
		
		colNumAdherent.setCellValueFactory(new PropertyValueFactory<>("numAdherent"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colAddresse.setCellValueFactory(new PropertyValueFactory<>("addresse"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colPrets.setCellValueFactory(new PropertyValueFactory<>("prets"));
        colSolde.setCellValueFactory(new PropertyValueFactory<>("solde"));
		
	}
}
