package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Adherent;
import model.GestionnaireErreur;
import model.Pret;
import model.Registre;
import utils.GestionnaireDonnee;

public class GestionAdherentController implements Initializable {
	
	Registre registre;
	GestionnaireDonnee gd = new GestionnaireDonnee();
	PopupController popup = new PopupController();
	
	@FXML Accordion accordionPrepose;
	
	@FXML TitledPane titledPaneGestionAdherents;
	@FXML TitledPane titledPaneGestionCatalogue;
	@FXML TitledPane titledPaneGestionPrets;
	
	@FXML TableView<Adherent> tableAdherents;
	@FXML TableColumn<Adherent, String> colNumAdherent;
	@FXML TableColumn<Adherent, String> colNom;
	@FXML TableColumn<Adherent, String> colPrenom;
	@FXML TableColumn<Adherent, String> colAddresse;
	@FXML TableColumn<Adherent, String> colTelephone;
	@FXML TableColumn<Adherent, Integer> colPrets;
	@FXML TableColumn<Adherent, Integer> colSolde;
	
	@FXML Button btnAjouterAdherent;
	@FXML Button btnModifierAdherent;
	@FXML Button btnSupprimerAdherent;
	@FXML Button btnPayerSolde;
	
	@FXML Button btnQuitter;
	
	ObservableList<Adherent> listeAdherents;
	int numAdherent;
	
	public static Adherent selectedAdherent;
	public static String selectedTab = "Catalogue";
	
	public GestionAdherentController() {
		registre = Registre.getInstance();
		listeAdherents = registre.getListeAdherents();
	}
	
	@FXML
	private void handleBtnQuitter(ActionEvent event) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Connexion.fxml"));
            Parent root = loader.load();

            Scene mainScene = new Scene(root);

            Stage stage = (Stage) btnQuitter.getScene().getWindow();
            stage.setScene(mainScene);
            stage.setTitle("Médiathèque");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    @FXML
    private void handleTitledPaneGestionCatalogue(MouseEvent event) {
    	
    	TitledPane paneChoisi = (TitledPane) event.getSource();
    	
    	//Enregistre quel tab était cliqué
    	if (paneChoisi.equals(titledPaneGestionCatalogue)) {
    		selectedTab = "Catalogue";
    	}
    	
    	else selectedTab = "Prets";
    	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VueCataloguePrepose.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) titledPaneGestionCatalogue.getScene().getWindow();
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResource("/images/iconMediatheque.png").toExternalForm()));
            stage.setTitle("Médiathèque");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    //Ouvre AjoutAdherent.fxml
    private void handleBtnAjouterAdherent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjoutAdherent.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResource("/images/iconAjouterPersonne.png").toExternalForm()));
            stage.setTitle("Ajouter un adhérent");
            stage.showAndWait();
            
            gd.serializerListeObservable("Adherent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    //Ouvre ModifierAdherent.fxml
    private void handleBtnModifierAdherent(ActionEvent event) {
    	
    	selectedAdherent = tableAdherents.getSelectionModel().getSelectedItem();
    	
    	if (selectedAdherent != null) {
            try {
            	
            	
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModifierAdherent.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                
                stage.initModality(Modality.APPLICATION_MODAL);
                
                stage.setScene(scene);
                stage.setTitle("Modifier un adhérent");
                stage.getIcons().add(new Image(getClass().getResource("/images/iconModifierAdherent.png").toExternalForm()));
                stage.showAndWait();
                
        		

        		tableAdherents.refresh();
        		gd.serializerListeObservable("Adherent");
        		

            } catch (Exception e) {
                e.printStackTrace();
            }
    	}
    	
    	else {
    		GestionnaireErreur.setErreur("Vous devez sélectionner un adhérent");
    		popup.showPopup();
    	}

        
        
        
    }
    
    @FXML
    //Supprimer un adhérent
    private void handleBtnSupprimerAdherent(ActionEvent event) {
    	Adherent removedAdherent = tableAdherents.getSelectionModel().getSelectedItem();
    	
    	if (removedAdherent != null) {
        	if (removedAdherent.getListePretsActifs().size() != 0) {
        		GestionnaireErreur.setErreur("Vous ne pouvez pas supprimer un adhérent qui a encore des prêts actifs");
        		popup.showPopup();
        	}
        	
        	else {
            	listeAdherents.remove(listeAdherents.indexOf(removedAdherent));
            	gd.serializerListeObservable("Adherent");
        	}
    	}
    	
    	else {
    		GestionnaireErreur.setErreur("Vous devez sélectionner un adhérent");
    		popup.showPopup();
    	}
    	

    	

    }
    
    @FXML private void handleBtnPayerSolde(ActionEvent event) {
    	
    	Adherent adherent = tableAdherents.getSelectionModel().getSelectedItem();
    	
    	if (adherent != null) {
        	boolean verify = true;
        	
        	for (Pret pret : adherent.getListePretsActifs()) {
        		//System.out.println(pret.getAmendeProperty().getMontant());
        		if (!pret.getAmendeProperty().getMontant().get().equals("0")) {
        			verify = false;
        		}
        	}
        	
        	if (verify) {
        		adherent.setSolde(new SimpleStringProperty("0"));
        	}
        	
        	else {
        		GestionnaireErreur.setErreur("Vous ne pouvez pas payer le solde d'un adhérent qui tient encore des articles en retard");
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
		accordionPrepose.setExpandedPane(titledPaneGestionAdherents);
		
		listeAdherents.setAll(gd.deserializeListeAdherents());
		
		tableAdherents.setItems(listeAdherents);
		
		titledPaneGestionAdherents.setExpanded(true);		
		titledPaneGestionCatalogue.setOnMouseClicked(e -> handleTitledPaneGestionCatalogue(e));
		titledPaneGestionPrets.setOnMouseClicked(e -> handleTitledPaneGestionCatalogue(e));
		
		colNumAdherent.setCellValueFactory(new PropertyValueFactory<>("numAdherent"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colAddresse.setCellValueFactory(new PropertyValueFactory<>("addresse"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colPrets.setCellValueFactory(new PropertyValueFactory<>("prets"));
        colSolde.setCellValueFactory(new PropertyValueFactory<>("solde"));
		
	}
	
}
