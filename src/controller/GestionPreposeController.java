package controller;

import java.net.URL;

import java.util.ResourceBundle;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Prepose;
import model.Registre;
import utils.GestionnaireDonnee;

public class GestionPreposeController implements Initializable {
	
	//Instanciation des donnees
	Registre registre;
	GestionnaireDonnee gd = new GestionnaireDonnee();
	
	@FXML TableView<Prepose> tablePreposes;
	@FXML TableColumn<Prepose, String> colNumPrepose;
	@FXML TableColumn<Prepose, String> colNom;
	@FXML TableColumn<Prepose, String> colPrenom;
	@FXML TableColumn<Prepose, String> colAddresse;
	@FXML TableColumn<Prepose, String> colTelephone;
	
	@FXML Button btnAjouterPrepose;
	@FXML Button btnSupprimerPrepose;
	
	@FXML Button btnQuitter;
	
	@FXML Accordion accordionAdmin;
	@FXML TitledPane titledPanePreposes;
	
	
	ObservableList<Prepose> listePreposes;
	
	public GestionPreposeController() {
		registre = Registre.getInstance();
		listePreposes = registre.getListePreposes();
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
	//Ouvre AjoutPrepose.fxml
	private void handleBtnAjouterPrepose() {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjoutPrepose.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.setScene(scene);
            stage.setTitle("Ajouter un préposé");
            stage.getIcons().add(new Image(getClass().getResource("/images/iconAjouterPersonne.png").toExternalForm()));
            stage.showAndWait();
            
            //tablePreposes.refresh();
            for (Prepose prepose : listePreposes) { System.out.println(prepose.getNom()); }  
            System.out.println(listePreposes.size());
            
            gd.serializerListeObservable("Prepose");
            

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	//Supprimer un prepose
	private void handleBtnSupprimerPrepose() {
    	Prepose removedPrepose = tablePreposes.getSelectionModel().getSelectedItem();
    	listePreposes.remove(listePreposes.indexOf(removedPrepose));
    	gd.serializerListeObservable("Prepose");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		accordionAdmin.setExpandedPane(titledPanePreposes);
		
		listePreposes.setAll(gd.deserializeListePrepose());
		
		tablePreposes.setItems(listePreposes);
		
		colNumPrepose.setCellValueFactory(new PropertyValueFactory<>("numPrepose"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colAddresse.setCellValueFactory(new PropertyValueFactory<>("addresse"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
		
		
	}

}
