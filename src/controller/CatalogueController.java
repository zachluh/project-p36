package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import model.Adherent;
import model.DVD;
import model.Document;
import model.GestionnaireErreur;
import model.Livre;
import model.Periodique;
import model.Pret;
import model.Registre;
import utils.GestionnaireDonnee;

public class CatalogueController implements Initializable{
	
		//Instanciation classes utilitaires
		
		Registre registre;
		GestionnaireDonnee gd = new GestionnaireDonnee();
		PopupController popup = new PopupController();
		
		//Instanciation objets FXML
		
		@FXML TabPane tabPaneCatalogue;
		
		@FXML Tab tabTout;
		@FXML Tab tabLivres;
		@FXML Tab tabPeriodiques;
		@FXML Tab tabDVDs;
	
		@FXML TableView<Document> tableTout;
		@FXML TableView<Livre> tableLivres;
		@FXML TableView<Periodique> tablePeriodiques;
		@FXML TableView<DVD> tableDVD;
		
		@FXML TableColumn<Document, String> tTNoDocument;
		@FXML TableColumn<Document, String> tTTitre;
		@FXML TableColumn<Document, String> tTAuteur;
		@FXML TableColumn<Document, String> tTDate;
		@FXML TableColumn<Document, String> tTÉtat;
		@FXML TableColumn<Document, String> tTNbPrêts;
		@FXML TableColumn<Document, String> tTEmprunteur;
		
		@FXML TableColumn<Livre, String> tLNoDocument;
		@FXML TableColumn<Livre, String> tLTitre;
		@FXML TableColumn<Livre, String> tLAuteur;
		@FXML TableColumn<Livre, String> tLDate;
		@FXML TableColumn<Livre, String> tLÉtat;
		@FXML TableColumn<Livre, String> tLNbPrêts;
		@FXML TableColumn<Livre, String> tLEmprunteur;
		
		@FXML TableColumn<Periodique, String> tPNoDocument;
		@FXML TableColumn<Periodique, String> tPTitre;
		@FXML TableColumn<Periodique, String> tPDate;
		@FXML TableColumn<Periodique, String> tPNbVolumes;
		@FXML TableColumn<Periodique, String> tPNbPeriodique;
		@FXML TableColumn<Periodique, String> tPÉtat;
		@FXML TableColumn<Periodique, String> tPNbPrêts;
		@FXML TableColumn<Periodique, String> tPEmprunteur;
		
		@FXML TableColumn<DVD, String> tDNoDocument;
		@FXML TableColumn<DVD, String> tDTitre;
		@FXML TableColumn<DVD, String> tDAuteur;
		@FXML TableColumn<DVD, String> tDDate;
		@FXML TableColumn<DVD, String> tDNbDisques;
		@FXML TableColumn<DVD, String> tDÉtat;
		@FXML TableColumn<DVD, String> tDNbPrêts;
		@FXML TableColumn<DVD, String> tDEmprunteur;
		
		@FXML RadioButton btnMotsCles;
		@FXML RadioButton btnAuteurRealisateur;
		@FXML TextField rechercheMotsCles;
		@FXML Button effacerMotsCles;
		@FXML ToggleGroup rechercheToggleGroup = new ToggleGroup(); 
		
		@FXML Button btnAjouterDocument;
		@FXML Button btnSupprimerDocument;
		@FXML Button btnInscrirePret;
		@FXML Button btnInscrireRetour;
		
		@FXML TitledPane titledPaneGestionAdherents;
		@FXML TitledPane titledPaneGestionPrets;
		@FXML TitledPane titledPaneCatalogue;
		
		@FXML Accordion accordionPrepose;
		
		@FXML Button btnQuitter;
		
		@FXML TextField textField1;
		@FXML TextField textField2;
		
		@FXML RadioButton nomPrenomRadioButton;
		@FXML RadioButton telephoneRadioButton;
	
	    @FXML Button btnConsulterCatalogue;
	    @FXML Button btnConnexionPrepose;
	    @FXML Button btnConsulterDossier;
	    
	    @FXML Label label1;
	    @FXML Label label2;
	    
	    
	    ToggleGroup tgBouton = new ToggleGroup();
		
	
		
		ObservableList<Document> listeTout;
		ObservableList<Livre> listeLivres;
		ObservableList<Periodique> listePeriodiques;
		ObservableList<DVD> listeDVDs;
		ObservableList<Adherent> listeAdherents;
		
		ObservableList<Document> sortedListeTout;
		ObservableList<Livre> sortedListeLivres;
		ObservableList<Periodique> sortedListePeriodiques;
		ObservableList<DVD> sortedListeDVDs;
		
		//Instanciation varaibles statiques
		
		public static Document selectedDocument;
		
		public CatalogueController() {
			registre = Registre.getInstance();
			
			listeTout = registre.getListeTout();
			listePeriodiques = registre.getListePeriodiques();
			listeLivres = registre.getListeLivres();
			listeDVDs = registre.getListeDVDs();
			listeAdherents = registre.getListeAdherents();
			
			
			sortedListeTout = registre.getSortedListeTout();
			sortedListePeriodiques = registre.getSortedListePeriodiques();
			sortedListeLivres = registre.getSortedListeLivres();
			sortedListeDVDs = registre.getSortedListeDVDs();
			
			
		}
		
		
		//Retourne à la fenêtre de connexion
		@FXML
		private void handleBtnQuitter(ActionEvent event) { 
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Connexion.fxml"));
	            Parent root = loader.load();

	            Scene mainScene = new Scene(root);

	            Stage stage = (Stage) btnQuitter.getScene().getWindow();
	            stage.setScene(mainScene);
	            stage.setTitle("Connexion");
	            stage.show();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		
		//Méthode utilitaire pour trouver le document sélectionné
		private Document findSelectedItem() {
			if (tabPaneCatalogue.getSelectionModel().getSelectedItem() == tabTout) {
				
				
				Document item = tableTout.getSelectionModel().getSelectedItem();
				
				if (item instanceof Livre) {
					for (Livre livre : listeLivres) {
						if (((Livre) item).equals(livre)) {
							return livre;
						}
					}
				}
				
				else if (item instanceof Periodique) {
					for (Periodique periodique : listePeriodiques) {
						if (((Periodique) item).equals(periodique)) {
							return periodique;
						}
					}
				}
				
				else if (item instanceof DVD) {
					for (DVD dvd : listeDVDs) {
						if (((DVD) item).equals(dvd)) {
							return dvd;
						}
					}
				}
				
				
				return tableTout.getSelectionModel().getSelectedItem();
			}
			
			else if (tabPaneCatalogue.getSelectionModel().getSelectedItem() == tabLivres) {
				
				return tableLivres.getSelectionModel().getSelectedItem();
			}
			
			else if (tabPaneCatalogue.getSelectionModel().getSelectedItem() == tabPeriodiques) {
				return tablePeriodiques.getSelectionModel().getSelectedItem();
			}
			
			else if (tabPaneCatalogue.getSelectionModel().getSelectedItem() == tabDVDs) {
				return tableDVD.getSelectionModel().getSelectedItem();
			}
			
			else return null;
		}
		
		//Inscrire un retour
		@FXML
		private void handleBtnInscrireRetour() {
			Document document = findSelectedItem();
			
			//Vérifier que le document existe
			
			if (document != null) {
				
        		int index = 0;
        		
        		if (selectedDocument instanceof Livre) { index = sortedListeLivres.indexOf( (Livre) document); }
        		else if (selectedDocument instanceof Periodique) { index = sortedListePeriodiques.indexOf( (Periodique) document); }
        		else if (selectedDocument instanceof DVD) { index = sortedListeDVDs.indexOf( (DVD) document); }
				
				//Trouver l'adhérent qui emprunte le document, ainsi que l'objet Pret associé au document
				
				Adherent emprunteur = null;
				Pret pret = null;
				String[] coordonnees = document.getEmprunteurProperty().split("\\s+");
				
				for (Adherent adherent : registre.getListeAdherents()) {
					if (adherent.getNom().equals(coordonnees[0])) {
						if (adherent.getPrenom().equals(coordonnees[1]) ) {
							emprunteur = adherent;
						}
					}
				}
				
				
				
				for (Pret p : emprunteur.getListePretsActifs()) {
					System.out.println("Pret: " + p.getNumPret());
					if (p.getDocumentPrete().equals(document)) {
						pret = p;
					}
				}
				
				//"Retourner" le document
				
				pret.setDateRetour(new SimpleStringProperty((LocalDate.now()).toString()));
				emprunteur.getListePretsActifs().remove(emprunteur.getListePretsActifs().indexOf(pret));
				
				document.setEtatProperty(new SimpleStringProperty("Disponible"));
				document.setEmprunteurProperty(new SimpleStringProperty(""));
				
        		if (selectedDocument instanceof Livre) { sortedListeLivres.set(index, (Livre) document); }
        		else if (selectedDocument instanceof Periodique) { sortedListePeriodiques.set(index, (Periodique) document); }
        		else if (selectedDocument instanceof DVD) { sortedListeDVDs.set(index, (DVD) document); }
				
	            tableLivres.refresh();
	            tablePeriodiques.refresh();
	            tableDVD.refresh();
	            tableTout.refresh();
	            
	            gd.serializerListeObservable("Livre");
	            gd.serializerListeObservable("Periodique");
	            gd.serializerListeObservable("DVD");
	            gd.serializerListeObservable("Adherent");
	            
				ObservableList<Document> listToutTemp = FXCollections.observableArrayList();
				listToutTemp.addAll(listeLivres);
				listToutTemp.addAll(listePeriodiques);
				listToutTemp.addAll(listeDVDs);
				listeTout.setAll(listToutTemp);
			}
			
			else {
				GestionnaireErreur.setErreur("Vous devez sélectionner un document à retourner");
				popup.showPopup();
			}
			
			
			
		}
		
		@FXML
		private void handleBtnInscrirePret() {
	        try {
	        	
	        	selectedDocument = findSelectedItem();
	        	
	        	//Vérifier que le document existe
	        	
	        	if (selectedDocument != null) {
	        		
	        		//Vérifier que le document est disponible
	        		
		        	if (selectedDocument.getEtatProperty().equals("Disponible")) {
		        		
		        		int index = 0;
		        		
		        		if (selectedDocument instanceof Livre) { index = sortedListeLivres.indexOf( (Livre) selectedDocument); }
		        		else if (selectedDocument instanceof Periodique) { index = sortedListePeriodiques.indexOf( (Periodique) selectedDocument); }
		        		else if (selectedDocument instanceof DVD) { index = sortedListeDVDs.indexOf( (DVD) selectedDocument); }
		        		
		        		//Si oui, charger le fichier EffectuerPret.fxml
		        		
			            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EffectuerPret.fxml"));
			            Parent root = loader.load();

			            Scene sceneInscrirePret = new Scene(root);

			            Stage stage = new Stage();
			            stage.initModality(Modality.APPLICATION_MODAL);
			            stage.setScene(sceneInscrirePret);
			            stage.getIcons().add(new Image(getClass().getResource("/images/iconEmprunter.png").toExternalForm()));
			            stage.setTitle("Inscrire un prêt");
			            stage.showAndWait();
			            
			            
		        		if (selectedDocument instanceof Livre) { sortedListeLivres.set(index, (Livre) selectedDocument); }
		        		else if (selectedDocument instanceof Periodique) { sortedListePeriodiques.set(index, (Periodique) selectedDocument); }
		        		else if (selectedDocument instanceof DVD) { sortedListeDVDs.set(index, (DVD) selectedDocument); }
			            
			            
			            tableLivres.refresh();
			            tablePeriodiques.refresh();
			            tableDVD.refresh();
			            tableTout.refresh();
			            
			            gd.serializerListeObservable("Livre");
			            gd.serializerListeObservable("Periodique");
			            gd.serializerListeObservable("DVD");
			            
						ObservableList<Document> listToutTemp = FXCollections.observableArrayList();
						listToutTemp.addAll(listeLivres);
						listToutTemp.addAll(listePeriodiques);
						listToutTemp.addAll(listeDVDs);
						listeTout.setAll(listToutTemp);
						
						filtrerDocuments();
		        	}
	        	}
	        	
	        	else {
					GestionnaireErreur.setErreur("Vous devez sélectionner un document à prêter");
					popup.showPopup();
	        	}
	        	

	        	


	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		@FXML
		private void handleBtnAjouterDocument(ActionEvent event) {
	        try {
	        	
	        	//charger le fichier AjoutDocument.fxml
	        	
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjoutDocument.fxml"));
	            Parent root = loader.load();

	            Scene sceneAjouterDocument = new Scene(root);

	            Stage stage = new Stage();
	            stage.initModality(Modality.APPLICATION_MODAL);
	            stage.setScene(sceneAjouterDocument);
	            stage.getIcons().add(new Image(getClass().getResource("/images/iconAjouterDocument.png").toExternalForm()));
	            stage.setTitle("Ajouter un document");
	            stage.showAndWait();
	            
	            filtrerDocuments();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		
		@FXML
		private void handleBtnSupprimerDocument(ActionEvent event) {
			
			/*
			 Pour chaque table: 
			 
			 1- Vérifie que le document choisi existe
			 2- Si oui, vérifie qu'il n'est pas emprunté
			 3- Si oui, supprimer le document de son ObservableList
			 
			 Si le document est supprimé sur tabTout:
			 
			 3.1- Trouver dans quelle ObservableList le document ce trouve
			 3.2- Supprimer le document de son ObservableList
			 
			 4- Supprimer tous les prêts qui incluent le document supprimé
			 */
			
			if (tabPaneCatalogue.getSelectionModel().getSelectedItem() == tabTout) {
				Document item = tableTout.getSelectionModel().getSelectedItem();
				
				
				
				if (item != null) {
					
					if (item.getEtatProperty().equals("Disponible")) {
						Livre removedLivre = null;
						Periodique removedPeriodique = null;
						DVD removedDVD = null;
						
						if (item instanceof Livre) {
							for (Livre livre : listeLivres) {
								if (((Livre) item).equals(livre)) {
									removedLivre = livre;
								}
							}
						}
						

						else if (item instanceof Periodique) {
							for (Periodique periodique : listePeriodiques) {
								if (((Periodique) item).equals(periodique)) {
									removedPeriodique = periodique;
								}
							}
						}
						
						
						else if (item instanceof DVD) {
							for (DVD dvd : listeDVDs) {
								if (((DVD) item).equals(dvd)) {
									removedDVD = dvd;
								}
							}
						}
						

						
						if (removedLivre != null) { 
							listeLivres.remove(listeLivres.indexOf(removedLivre));
							sortedListeLivres.remove(sortedListeLivres.indexOf(removedLivre));
							gd.serializerListeObservable("Livre");
						}
						else if (removedPeriodique != null) {
							listePeriodiques.remove(listePeriodiques.indexOf(removedPeriodique));
							sortedListePeriodiques.remove(sortedListePeriodiques.indexOf(item));
							gd.serializerListeObservable("Periodique");
						}
						else if (removedDVD != null) {
							listeDVDs.remove(listeDVDs.indexOf(removedDVD));
							sortedListeDVDs.remove(sortedListeDVDs.indexOf(item));
							gd.serializerListeObservable("DVD");
						}
					}
					
					else {
						GestionnaireErreur.setErreur("Vous ne pouvez pas supprimer un document alors que qu'il est emprunté");
						popup.showPopup();
					}
					
					
				}
				else {
					GestionnaireErreur.setErreur("Vous devez sélectionner un document à supprimer");
					popup.showPopup();
				}
				
				
				
			}
			
			else if (tabPaneCatalogue.getSelectionModel().getSelectedItem() == tabLivres) {
				
				Livre item = tableLivres.getSelectionModel().getSelectedItem();	
				
				if (item != null) {
					
					if (item.getEtatProperty().equals("Disponible")) {
						listeLivres.remove(listeLivres.indexOf(item));
						sortedListeLivres.remove(sortedListeLivres.indexOf(item));	
						gd.serializerListeObservable("Livre");
					}
					
					else {
						GestionnaireErreur.setErreur("Vous ne pouvez pas supprimer un document alors que qu'il est emprunté");
						popup.showPopup();
					}
					

				}
				
				else {
					GestionnaireErreur.setErreur("Vous devez sélectionner un document à supprimer");
					popup.showPopup();
				}
			}
			
			else if (tabPaneCatalogue.getSelectionModel().getSelectedItem() == tabPeriodiques) {
				
				Periodique item = tablePeriodiques.getSelectionModel().getSelectedItem();
				
				if (item != null) {
					
					if (item.getEtatProperty().equals("Disponible")) {
						listePeriodiques.remove(listePeriodiques.indexOf(item));
						sortedListePeriodiques.remove(sortedListePeriodiques.indexOf(item));
						gd.serializerListeObservable("Periodique");
					}
					
					else {
						GestionnaireErreur.setErreur("Vous ne pouvez pas supprimer un document alors que qu'il est emprunté");
						popup.showPopup();
					}
					
					
				}
				
				else {
					GestionnaireErreur.setErreur("Vous devez sélectionner un document à supprimer");
					popup.showPopup();
				}
				
				
				
				

			}
			
			else if (tabPaneCatalogue.getSelectionModel().getSelectedItem() == tabDVDs) {
				
				DVD item = tableDVD.getSelectionModel().getSelectedItem();
				
				if (item != null) {
					
					if (item.getEtatProperty().equals("Disponible")) {
						listeDVDs.remove(listeDVDs.indexOf(item));
						sortedListeDVDs.remove(sortedListeDVDs.indexOf(item));
						gd.serializerListeObservable("DVD");
					}
					
					else {
						GestionnaireErreur.setErreur("Vous ne pouvez pas supprimer un document alors que qu'il est emprunté");
						popup.showPopup();
					}

				}
					


				
				

				
				else {
					GestionnaireErreur.setErreur("Vous devez sélectionner un document à supprimer");
					popup.showPopup();
				}
				

			}
			
			Document item = findSelectedItem();
			
			if (item != null && item.getEtatProperty().equals("Disponible")) {
				for (Adherent adherent : listeAdherents) {
					Pret removedPret = null;
					
					for (Pret pret : adherent.getListePrets()) {
						if (pret.getDocumentPrete().equals(item)) {
							removedPret = pret;
						}
					}
					
					adherent.getListePrets().remove(adherent.getListePrets().indexOf(removedPret));
					gd.serializerListeObservable("Adherent");
				}
			}
			

			
			ObservableList<Document> listToutTemp = FXCollections.observableArrayList();
			listToutTemp.addAll(listeLivres);
			listToutTemp.addAll(listePeriodiques);
			listToutTemp.addAll(listeDVDs);
			listeTout.setAll(listToutTemp);
			
			filtrerDocuments();
			
		}
		
		private void filtrerDocuments( ) {
			
			/*
			 1- Vider les listes filtrées.
			 
			 2- Si btnMotsCles est sélectionné, filtrer les documents par leur titre
			 3- Si btnAuteurRealisateur est sélectionné, filtrer les documents par leur auteur/réalisateur
			 
			 4-Afficher les listes filtrées sur les TableView
			 5-Si la boîte de recherche est vide, les listes originaux deviennent le contenu des listes filtrées
			 */
			
			
			sortedListeTout.clear();
			sortedListeLivres.clear();
			sortedListePeriodiques.clear();
			sortedListeDVDs.clear();
			
			
			
			if (btnMotsCles.isSelected() && !rechercheMotsCles.getText().isEmpty()) {
				
				String textMotsClesString = rechercheMotsCles.getText().toLowerCase();
				
				for (Livre livre : listeLivres) {
					if (livre.getTitreProperty().toLowerCase().contains(textMotsClesString)) { sortedListeLivres.add(livre); }
				}
				
				for (Periodique periodique : listePeriodiques) {
					if (periodique.getTitreProperty().toLowerCase().contains(textMotsClesString)) { sortedListePeriodiques.add(periodique); }
				}
				
				for (DVD dvd : listeDVDs) {
					if (dvd.getTitreProperty().toLowerCase().contains(textMotsClesString)) { sortedListeDVDs.add(dvd); }
				}
				
				tableLivres.setItems(sortedListeLivres);
				tablePeriodiques.setItems(sortedListePeriodiques);
				tableDVD.setItems(sortedListeDVDs);
				
				sortedListeTout.addAll(sortedListeLivres);
				sortedListeTout.addAll(sortedListePeriodiques);
				sortedListeTout.addAll(sortedListeDVDs);
				tableTout.setItems(sortedListeTout);
				
				//listeTout.setAll(sortedListeTout);
				tableTout.refresh();
				tableLivres.refresh();
				tablePeriodiques.refresh();
				tableDVD.refresh();
				
				
			}
			
			else if (btnAuteurRealisateur.isSelected() && !rechercheMotsCles.getText().isEmpty()) {
				String textAuteurRealisateurString = rechercheMotsCles.getText().toLowerCase();
				
				for (Livre livre : listeLivres) {
					if (livre.getAuteurProperty().toLowerCase().contains(textAuteurRealisateurString)) { sortedListeLivres.add(livre); }
				}
				
				for (Periodique periodique : listePeriodiques) {
					if (periodique.getAuteurProperty().toLowerCase().contains(textAuteurRealisateurString)) { sortedListePeriodiques.add(periodique); }
				}
				
				for (DVD dvd : listeDVDs) {
					if (dvd.getAuteurProperty().toLowerCase().contains(textAuteurRealisateurString)) { sortedListeDVDs.add(dvd); }
				}
				
				tableLivres.setItems(sortedListeLivres);
				tablePeriodiques.setItems(sortedListePeriodiques);
				tableDVD.setItems(sortedListeDVDs);
				
				sortedListeTout.addAll(sortedListeLivres);
				sortedListeTout.addAll(sortedListePeriodiques);
				sortedListeTout.addAll(sortedListeDVDs);
				tableTout.setItems(sortedListeTout);
				
				//listeTout.setAll(sortedListeTout);
				tableTout.refresh();
				tableLivres.refresh();
				tablePeriodiques.refresh();
				tableDVD.refresh();
			}
			
		
			
			else {

				sortedListeLivres.setAll(listeLivres);
				sortedListePeriodiques.setAll(listePeriodiques);
				sortedListeDVDs.setAll(listeDVDs);				
				sortedListeTout.setAll(listeTout);
				
			}
		}
		
		@FXML
		private void handleRechercheMotsCles(KeyEvent event) {
			//Filtrer lorsque une touche est tapée dans la barre de recherche
			
			filtrerDocuments();
						
		}
		
		@FXML
		private void handleChangementFiltre(ActionEvent event) {
			//Filtrer lorsque un des RadioButtons sont cliqués
			filtrerDocuments();
		}
		
		@FXML
		private void handleEffacerMotsCles(ActionEvent event) {
			rechercheMotsCles.setText("");
			
			sortedListeLivres.setAll(listeLivres);
			sortedListePeriodiques.setAll(listePeriodiques);
			sortedListeDVDs.setAll(listeDVDs);				
			sortedListeTout.setAll(listeTout);
			
		}
		
		@FXML
		private void handleTitledPaneGestionAdherents() {
			//Ouvrir le gestionnaire d'adhérent
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GestionAdherent.fxml"));
	            Parent root = loader.load();

	            Scene mainScene = new Scene(root);

	            Stage stage = (Stage) titledPaneGestionAdherents.getScene().getWindow();
	            stage.setScene(mainScene);
	            stage.setTitle("Médiathèque");
	            stage.show();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		@FXML
	    private void handleBtnConsulterDossier(ActionEvent event) {
			//Voir ConnexionController
	    	if (textField2.isVisible()) {
	    		for (Adherent adherent : listeAdherents) {
	    			if (textField1.getText().equals(adherent.getNom())) {
	    				if (textField2.getText().equals(adherent.getPrenom())) {
	    			        try {
	    			        	ConnexionController.selectedAdherent = adherent;
	    			        	
	    			            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VueDossierAdherent.fxml"));
	    			            Parent root = loader.load();	
	    			            
	    			            
	    			            Scene mainScene = new Scene(root);
	    			            

	    			            Stage stage = (Stage) btnQuitter.getScene().getWindow();
	    			            stage.setScene(mainScene);
	    			            stage.setTitle("Dossier de " + adherent.getNom() + ", " + adherent.getPrenom());
	    			            stage.show();

	    			        } catch (Exception e) {
	    			            e.printStackTrace();
	    			        }
	    				}
	    			}
	    		}
	    	}
	    	
	    	else {
	    		for (Adherent adherent : listeAdherents) {
	    			if (textField1.getText().equals(adherent.getTelephone())) {
	    		        try {
	    		        	
	    		        	ConnexionController.selectedAdherent = adherent;
	    		        	
	    		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VueDossierAdherent.fxml"));
    			            Parent root = loader.load();	
    			            
    			            
    			            Scene mainScene = new Scene(root);
    			            

    			            Stage stage = (Stage) btnQuitter.getScene().getWindow();
    			            stage.setScene(mainScene);
    			            stage.getIcons().add(new Image(getClass().getResource("/images/iconMediatheque.png").toExternalForm()));
    			            stage.setTitle("Dossier de " + adherent.getNom() + ", " + adherent.getPrenom());
    			            stage.show();

	    		        } catch (Exception e) {
	    		            e.printStackTrace();
	    		        }
	    			}
	    		}
	    	}
	    }
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			//Voir quel Tab a été cliqué à partir de GestionAdherent
			if (accordionPrepose != null) {
				if (GestionAdherentController.selectedTab == null || GestionAdherentController.selectedTab.equals("Catalogue")) {
					accordionPrepose.setExpandedPane(titledPaneCatalogue);
				}
				
				else accordionPrepose.setExpandedPane(titledPaneGestionPrets);
			}
			

			//Accorder titledPaneGestionAdherents à son évènement associé
			if (titledPaneGestionAdherents != null) {
				titledPaneGestionAdherents.setOnMouseClicked(e -> handleTitledPaneGestionAdherents());
			}
			
			//Voir ConnexionController
			if (nomPrenomRadioButton != null) {
				nomPrenomRadioButton.setToggleGroup(tgBouton);
				telephoneRadioButton.setToggleGroup(tgBouton);
				
				nomPrenomRadioButton.setSelected(true);
				
				nomPrenomRadioButton.setOnMouseClicked(e -> {
					textField2.setVisible(true);
					label2.setVisible(true);
					label1.setText("Nom");
				});
				
				telephoneRadioButton.setOnMouseClicked(e -> {
					textField2.setVisible(false);
					label2.setVisible(false);
					label1.setText("Téléphone");
				});
			}
			
			btnAuteurRealisateur.setSelected(true);
			
			btnMotsCles.setToggleGroup(rechercheToggleGroup);
			btnAuteurRealisateur.setToggleGroup(rechercheToggleGroup);
			
			//Chargement des donnees du catalogue
			
			try {
				
				gd.chargerDocuments("Livres");
				gd.chargerDocuments("Periodiques");
				gd.chargerDocuments("DVD");
				
				sortedListeLivres.setAll(listeLivres);
				sortedListePeriodiques.setAll(listePeriodiques);
				sortedListeDVDs.setAll(listeDVDs);
				
								
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			listeTout.clear();
			listeTout.addAll(listeLivres);
			listeTout.addAll(listePeriodiques);
			listeTout.addAll(listeDVDs);
			
			sortedListeTout.setAll(listeTout);
			
			System.out.println("Emprunteur " + listeTout.get(0).getEmprunteurProperty());
			
			tableTout.setItems(sortedListeTout);
			tableLivres.setItems(sortedListeLivres);
			tablePeriodiques.setItems(sortedListePeriodiques);
			tableDVD.setItems(sortedListeDVDs);
			
            tTNoDocument.setCellValueFactory(new PropertyValueFactory<>("numDocProperty"));
            tTTitre.setCellValueFactory(new PropertyValueFactory<>("titreProperty"));
            tTAuteur.setCellValueFactory(new PropertyValueFactory<>("auteurProperty"));
            tTDate.setCellValueFactory(new PropertyValueFactory<>("dateDeParutionProperty"));
            tTÉtat.setCellValueFactory(new PropertyValueFactory<>("etatProperty"));
            tTNbPrêts.setCellValueFactory(new PropertyValueFactory<>("nbPrêts"));
            tTEmprunteur.setCellValueFactory(new PropertyValueFactory<>("emprunteurProperty"));
            
            tLNoDocument.setCellValueFactory(new PropertyValueFactory<>("numDocProperty"));
            tLTitre.setCellValueFactory(new PropertyValueFactory<>("titreProperty"));
            tLAuteur.setCellValueFactory(new PropertyValueFactory<>("auteurProperty"));
            tLDate.setCellValueFactory(new PropertyValueFactory<>("dateDeParutionProperty"));
            tLÉtat.setCellValueFactory(new PropertyValueFactory<>("etatProperty"));
            tLNbPrêts.setCellValueFactory(new PropertyValueFactory<>("nbPrêts"));
            tLEmprunteur.setCellValueFactory(new PropertyValueFactory<>("emprunteurProperty"));
            
            tPNoDocument.setCellValueFactory(new PropertyValueFactory<>("numDocProperty"));
            tPTitre.setCellValueFactory(new PropertyValueFactory<>("titreProperty"));
            tPDate.setCellValueFactory(new PropertyValueFactory<>("dateDeParutionProperty"));
            tPNbVolumes.setCellValueFactory(new PropertyValueFactory<>("nbVolumesProperty"));
            tPNbPeriodique.setCellValueFactory(new PropertyValueFactory<>("nbPeriodiqueProperty"));
            tPÉtat.setCellValueFactory(new PropertyValueFactory<>("etatProperty"));
            tPNbPrêts.setCellValueFactory(new PropertyValueFactory<>("nbPrêts"));
            tPEmprunteur.setCellValueFactory(new PropertyValueFactory<>("emprunteurProperty"));
            
            tDNoDocument.setCellValueFactory(new PropertyValueFactory<>("numDocProperty"));
            tDTitre.setCellValueFactory(new PropertyValueFactory<>("titreProperty"));
            tDAuteur.setCellValueFactory(new PropertyValueFactory<>("auteurProperty"));
            tDDate.setCellValueFactory(new PropertyValueFactory<>("dateDeParutionProperty"));
            tDNbDisques.setCellValueFactory(new PropertyValueFactory<>("nbDisquesProperty"));
            tDÉtat.setCellValueFactory(new PropertyValueFactory<>("etatProperty"));
            tDNbPrêts.setCellValueFactory(new PropertyValueFactory<>("nbPrêts"));
            tDEmprunteur.setCellValueFactory(new PropertyValueFactory<>("emprunteurProperty"));
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
}
