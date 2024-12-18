package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Adherent implements Serializable{
	private static final long serialVersionUID = 12089312038129038L;

	private StringProperty numAdherent;
	private StringProperty nom;
	private StringProperty prenom;
	private StringProperty telephone;
	private StringProperty addresse;
	private IntegerProperty prets;
	private StringProperty solde;
	private ObservableList<Pret> listePrets = FXCollections.observableArrayList();
	private ObservableList<Pret> listePretsActifs = FXCollections.observableArrayList();
	
	public Adherent(String numAdherent, String nom, String prenom, String telephone, String addresse, String solde) {
		this.numAdherent = new SimpleStringProperty(numAdherent);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.telephone = new SimpleStringProperty(telephone);
		this.addresse = new SimpleStringProperty(addresse);
		this.prets = new SimpleIntegerProperty(listePrets.size());
		this.solde = new SimpleStringProperty(solde);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {

		//out.defaultWriteObject();
		System.out.println("Serialisation de: " + nom.get());

		out.writeUTF(numAdherent.get());
		out.writeUTF(nom.get());
		out.writeUTF(prenom.get());
		out.writeUTF(telephone.get());
		out.writeUTF(addresse.get());
		out.writeInt(prets.get());
		out.writeUTF(solde.get());
		
		out.writeObject((new ArrayList<Pret>(listePrets)));
		out.writeObject((new ArrayList<Pret>(listePretsActifs)));

	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

		//in.defaultReadObject();

		numAdherent = new SimpleStringProperty(in.readUTF());
		nom = new SimpleStringProperty(in.readUTF());

		System.out.println("Lecture de l'objet: " + nom.get());

		prenom = new SimpleStringProperty(in.readUTF());
		telephone = new SimpleStringProperty(in.readUTF());
		addresse = new SimpleStringProperty(in.readUTF());
		prets = new SimpleIntegerProperty(in.readInt());
		solde = new SimpleStringProperty(in.readUTF());
		listePrets = FXCollections.observableArrayList((List<Pret>) in.readObject());
		listePretsActifs = FXCollections.observableArrayList((List<Pret>) in.readObject());
	}

	public String getNumAdherent() {
		return numAdherent.get();
	}

	public void setNumAdherent(StringProperty numAdherent) {
		this.numAdherent = numAdherent;
	}

	public String getNom() {
		return nom.get();
	}

	public void setNom(StringProperty nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom.get();
	}

	public void setPrenom(StringProperty prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone.get();
	}

	public void setTelephone(StringProperty telephone) {
		this.telephone = telephone;
	}

	public String getAddresse() {
		return addresse.get();
	}

	public void setAddresse(StringProperty addresse) {
		this.addresse = addresse;
	}

	public Integer getPrets() {
		return prets.get();
	}

	public void setPrets(IntegerProperty prets) {
		this.prets = prets;
	}

	public String getSolde() {
		Currency cad = Currency.getInstance("CAD");
		NumberFormat cadFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
		cadFormat.setCurrency(cad);
		
		BigDecimal soldeDecimal = new BigDecimal(solde.get());
		
		return (cadFormat.format(soldeDecimal)).toString();
	}

	public void setSolde(StringProperty solde) {
		this.solde = solde;
	}
	
	public String getTrueSolde() {
		return solde.get();
	}

	public ObservableList<Pret> getListePrets() {
		return listePrets;
	}

	public void setListePrets(ObservableList<Pret> listePrets) {
		this.listePrets = listePrets;
	}
	
	public ObservableList<Pret> getListePretsActifs() {
		return listePretsActifs;
	}

	public void setListePretsActifs(ObservableList<Pret> listePrets) {
		this.listePretsActifs = listePrets;
	}
	
	
	
	
	
}
