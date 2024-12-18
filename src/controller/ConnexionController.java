package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Adherent;
import model.Document;
import model.Prepose;
import model.Pret;
import model.Registre;
import utils.GestionnaireDonnee;

public class ConnexionController implements Initializable{
	
		//Instanciation classes utilitaires
	
		Registre registre;
		GestionnaireDonnee gd = new GestionnaireDonnee();
	
		//Instanciation objets FXML
		
		@FXML private TextField numEmployeField;
		@FXML private TextField motDePasseField; 
		@FXML private TextField textField1;
		@FXML private TextField textField2;
		
		@FXML private RadioButton nomPrenomRadioButton;
		@FXML private RadioButton telephoneRadioButton;
	
	    @FXML private Button btnConsulterCatalogue;
	    @FXML private Button btnConnexionPrepose;
	    @FXML private Button btnConsulterDossier;
	    
	    @FXML private Label label1;
	    @FXML private Label label2;
	    
	    @FXML Accordion accordionConnexion;
	    
	    @FXML TitledPane paneAdherent;
	    @FXML TitledPane panePrepose;
	    
	    ObservableList<Prepose> listePreposes;
	    ObservableList<Adherent> listeAdherents;
	    
	    ToggleGroup tg = new ToggleGroup();
	    
	    //Instanciation variables statique
	    
	    public static Adherent selectedAdherent;
	    
	    
	    
	    
	    public ConnexionController() {
	    	registre = Registre.getInstance();
	    	listePreposes = registre.getListePreposes();
	    	listeAdherents = registre.getListeAdherents();
	    }
	    
	    private void updateSoldes() {
	    	LocalDate maintenant = LocalDate.now();
	    	for (Adherent adherent : listeAdherents) {
		    	int solde = 0;
	    		for (Pret pret : adherent.getListePrets()) {
	    			
	    			//vérifie pour chaque pret si la date actuelle est après la date de retour prévue
	    			//si oui, calculer l'amende
	    			
	    			if (maintenant.isAfter(LocalDate.parse(pret.getDateRetourPrevu()))) {
	    				
	    				long jours = ChronoUnit.DAYS.between(maintenant, LocalDate.parse(pret.getDateRetourPrevu()));
	    				
	    				String newMontant = String.valueOf(0.5*jours);
	    				pret.getAmendeProperty().setMontant(new SimpleStringProperty(newMontant));
	    				solde += Integer.parseInt(newMontant);
	    			}
	    		}
	    		
	    		adherent.setSolde(new SimpleStringProperty(String.valueOf(solde)));
	    	}
	    	
	    	gd.serializerListeObservable("Adherent");
	    }
	    
	    @FXML
	    private void handleBtnConsulterDossier(ActionEvent event) {
	    	
	    	//si textField2 est visible, la connexion se fait par Nom et Prénom. Sinon, elle se fait par Téléphone
	    	
	    	if (textField2.isVisible()) {
	    		for (Adherent adherent : listeAdherents) {
	    			
	    			//vérifier si les paramètres correspondes au coordonnées d'un adhérent
	    			
	    			if (textField1.getText().equals(adherent.getNom())) {
	    				if (textField2.getText().equals(adherent.getPrenom())) {
	    			        try {
	    			        	selectedAdherent = adherent;
	    			        	
	    			            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VueDossierAdherent.fxml"));
	    			            Parent root = loader.load();	
	    			            
	    			            
	    			            Scene mainScene = new Scene(root);
	    			            

	    			            Stage stage = (Stage) btnConsulterCatalogue.getScene().getWindow();
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
	    	
	    	else {
	    		for (Adherent adherent : listeAdherents) {
	    			if (textField1.getText().equals(adherent.getTelephone())) {
	    		        try {
	    		        	
	    		        	selectedAdherent = adherent;
	    		        	
    			            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VueDossierAdherent.fxml"));
    			            Parent root = loader.load();	
    			            
    			            
    			            Scene mainScene = new Scene(root);
    			            

    			            Stage stage = (Stage) btnConsulterCatalogue.getScene().getWindow();
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
	    
	    @FXML
	    private void handleBtnConsulterCatalogue(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VueCatalogueAdherent.fxml"));
	            Parent root = loader.load();

	            Scene mainScene = new Scene(root);

	            Stage stage = (Stage) btnConsulterCatalogue.getScene().getWindow();
	            stage.setScene(mainScene);
	            stage.getIcons().add(new Image(getClass().getResource("/images/iconMediatheque.png").toExternalForm()));
	            stage.setTitle("Médiathèque");
	            stage.show();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @FXML
	    private void handleBtnConnexionPrepose(ActionEvent event) {
	    	
	    	//Connexion admin
	    	
	    	if (numEmployeField.getText().equals("admin") && motDePasseField.getText().equals("admin")) {
		        try {
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GestionPrepose.fxml"));
		            Parent root = loader.load();

		            Scene mainScene = new Scene(root);

		            Stage stage = (Stage) btnConsulterCatalogue.getScene().getWindow();
		            stage.setScene(mainScene);
		            stage.getIcons().add(new Image(getClass().getResource("/images/iconMediatheque.png").toExternalForm()));
		            stage.setTitle("Médiathèque");
		            stage.show();

		        } catch (Exception e) {
		            e.printStackTrace();
		        }	
	    	}
	    	
	    	//sinon, vérifier si les paramètres correspondent à un compte préposé
	    	
	    	else {
	    		
	    		for (Prepose prepose : listePreposes) {
	    			if (numEmployeField.getText().equals(prepose.getNumPrepose())) {
	    				if (motDePasseField.getText().equals(prepose.getMotDePasse())) {
	    			        try {
	    			            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VueCataloguePrepose.fxml"));
	    			            Parent root = loader.load();

	    			            Scene mainScene = new Scene(root);

	    			            Stage stage = (Stage) btnConsulterCatalogue.getScene().getWindow();
	    			            stage.setScene(mainScene);
	    			            stage.getIcons().add(new Image(getClass().getResource("/images/iconMediatheque.png").toExternalForm()));
	    			            stage.setTitle("Médiathèque");
	    			            stage.show();

	    			        } catch (Exception e) {
	    			            e.printStackTrace();
	    			        }
	    				}
	    			}
	    		}
	
	    	}
	    	

	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			accordionConnexion.setExpandedPane(paneAdherent);
			// TODO Auto-generated method stub
			listePreposes.setAll(gd.deserializeListePrepose());
			
			registre.setListeLivres(gd.deserializeListeLivres());
			registre.setListePeriodiques(gd.deserializeListePeriodiques());
			registre.setListeDVDs(gd.deserializeListeDVDs());
			
			ObservableList<Document> listToutTemp = FXCollections.observableArrayList();
			listToutTemp.addAll(registre.getListeLivres());
			listToutTemp.addAll(registre.getListePeriodiques());
			listToutTemp.addAll(registre.getListeDVDs());
			registre.setListeTout(listToutTemp);
			
			listeAdherents.setAll(gd.deserializeListeAdherents());
			updateSoldes();
			listeAdherents.setAll(gd.deserializeListeAdherents());
			
			
			nomPrenomRadioButton.setToggleGroup(tg);
			telephoneRadioButton.setToggleGroup(tg);
			
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
	}
