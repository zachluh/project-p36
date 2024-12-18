package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Registre {
	
	public static Registre instance;
	
	private final ObservableList<Document> listeTout = FXCollections.observableArrayList();
	private final ObservableList<Livre> listeLivres = FXCollections.observableArrayList();
	private final ObservableList<Periodique> listePeriodiques = FXCollections.observableArrayList();
	private final ObservableList<DVD> listeDVDs = FXCollections.observableArrayList();
	private final ObservableList<Adherent> listeAdherents = FXCollections.observableArrayList();
	private final ObservableList<Prepose> listePreposes = FXCollections.observableArrayList();
	
	private final ObservableList<Document> sortedListeTout = FXCollections.observableArrayList();
	private final ObservableList<Livre> sortedListeLivres = FXCollections.observableArrayList();
	private final ObservableList<Periodique> sortedListePeriodiques = FXCollections.observableArrayList();
	ObservableList<DVD> sortedListeDVDs = FXCollections.observableArrayList();
	
	private int numAdherents = 1;
	private int numPreposes = 1;
	
	public static Registre getInstance() {
		if (instance == null) {
			instance = new Registre();
		}
		
		return instance;
	}

	public ObservableList<Document> getListeTout() {
		return listeTout;
	}
	
	public ObservableList<Prepose> getListePreposes() {
		return listePreposes;
	}

	public ObservableList<Livre> getListeLivres() {
		return listeLivres;
	}

	public ObservableList<Periodique> getListePeriodiques() {
		return listePeriodiques;
	}
	
	public ObservableList<Document> getSortedListeTout() {
		return sortedListeTout;
	}
	
	public ObservableList<Livre> getSortedListeLivres() {
		return sortedListeLivres;
	}

	public ObservableList<Periodique> getSortedListePeriodiques() {
		return sortedListePeriodiques;
	}

	public ObservableList<DVD> getListeDVDs() {
		return listeDVDs;
	}
	
	public ObservableList<DVD> getSortedListeDVDs() {
		return sortedListeDVDs;
	}
	
	public ObservableList<Adherent> getListeAdherents() {
		return listeAdherents;
	}
	
	public int getNumAdherents() {
		return numAdherents;
	}
	
	public int getNumPreposes() {
		return numPreposes;
	}
	
	public void setNumAdherents(int num) {
		numAdherents = num;
	}
	
	public void setNumPreposes(int num) {
		numPreposes = num;
	}
	
	public void setListePreposes(ObservableList<Prepose> liste) {
		this.listePreposes.setAll(liste);
	}
	
	public void setListeTout(ObservableList<Document> liste) {
		this.listeTout.setAll(liste);
	}
	
	public void setListeLivres(ObservableList<Livre> liste) {
		this.listeLivres.setAll(liste);
	}
	
	public void setListePeriodiques(ObservableList<Periodique> liste) {
		this.listePeriodiques.setAll(liste);
	}
	
	public void setListeDVDs(ObservableList<DVD> liste) {
		this.listeDVDs.setAll(liste);
	}
	
	public void setListeAdherents(ObservableList<Adherent> liste) {
		this.listeAdherents.setAll(liste);
	}
	
	public void setSortedListeTout(ObservableList<Document> liste) {
		this.sortedListeTout.setAll(liste);
	}
	
	public void setSortedListeLivres(ObservableList<Livre> liste) {
		this.sortedListeLivres.setAll(liste);
	}
	
	public void setSortedListePeriodiques(ObservableList<Periodique> liste) {
		this.sortedListePeriodiques.setAll(liste);
	}
	
	public void setSortedListeDVDs(ObservableList<DVD> liste) {
		this.sortedListeDVDs.setAll(liste);
	}
	
	

	
}


