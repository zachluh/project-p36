package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Adherent;
import model.Document;
import model.Pret;

public class DossierAdherentController implements Initializable{
	
	private Adherent adherent = null;
	
	@FXML private TableView<Pret> tablePrets;
	@FXML private TableView<Document> tableDocument;
	
	@FXML private TableColumn<Pret, String> colNumPret;
	@FXML private TableColumn<Pret, String> colDatePret; 
	@FXML private TableColumn<Pret, String> colRetourPrevuPret; 
	@FXML private TableColumn<Pret, String> colRetourPret; 
	@FXML private TableColumn<Pret, String> colAmende; 
	
	@FXML private TableColumn<Document, String> colNumDocument;
	@FXML private TableColumn<Document, String> colTitre; 
	@FXML private TableColumn<Document, String> colAuteur; 
	@FXML private TableColumn<Document, String> colDateDeParution; 
	
	@FXML private Button btnQuitter;
	
	public DossierAdherentController() {
		adherent = ConnexionController.selectedAdherent;
	}
	
	
	public void setAdherent(Adherent adherent) {
		this.adherent = adherent;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		tablePrets.getSelectionModel().selectedIndexProperty().addListener((observableValue, vieuChoix, nouvChoix) -> {
			ObservableList<Document> nouvListe = FXCollections.observableArrayList();
			Pret pret = adherent.getListePrets().get( (int) nouvChoix);
			nouvListe.add(pret.getDocumentPrete());
			tableDocument.setItems(nouvListe);
			
		});
		
		System.out.println(adherent.getNom());
		tablePrets.setItems(adherent.getListePrets());
		
		colNumPret.setCellValueFactory(new PropertyValueFactory<>("numPret"));
        colDatePret.setCellValueFactory(new PropertyValueFactory<>("datePret"));
        colRetourPrevuPret.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevu"));
        colRetourPret.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        colAmende.setCellValueFactory(new PropertyValueFactory<>("amende"));
        
        colNumDocument.setCellValueFactory(new PropertyValueFactory<>("numDocProperty"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titreProperty"));
        colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteurProperty"));
        colDateDeParution.setCellValueFactory(new PropertyValueFactory<>("dateDeParutionProperty"));
		
	}

}
