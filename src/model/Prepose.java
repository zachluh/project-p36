package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Prepose implements Serializable{
	private static final long serialVersionUID = 2L;
	
	private StringProperty numPrepose;
	private StringProperty nom;
	private StringProperty prenom;
	private StringProperty telephone;
	private StringProperty addresse;
	private String motDePasse;
	
	public Prepose(String numPrepose, String nom, String prenom, String telephone,
			String addresse, String motDePasse) {
		this.numPrepose = new SimpleStringProperty(numPrepose);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.telephone = new SimpleStringProperty(telephone);
		this.addresse = new SimpleStringProperty(addresse);
		this.motDePasse = motDePasse;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {

		//out.defaultWriteObject();
		System.out.println("Serialisation de: " + nom.get());

		out.writeUTF(numPrepose.get());
		out.writeUTF(nom.get());
		out.writeUTF(prenom.get());
		out.writeUTF(telephone.get());
		out.writeUTF(addresse.get());
		out.writeUTF(motDePasse);

	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

		//in.defaultReadObject();

		numPrepose = new SimpleStringProperty(in.readUTF());
		nom = new SimpleStringProperty(in.readUTF());

		System.out.println("Lecture de l'objet: " + nom.get());

		prenom = new SimpleStringProperty(in.readUTF());
		telephone = new SimpleStringProperty(in.readUTF());
		addresse = new SimpleStringProperty(in.readUTF());
		motDePasse = in.readUTF();
	}

	public String getNumPrepose() {
		return numPrepose.get();
	}

	public void setNumPrepose(StringProperty numPrepose) {
		this.numPrepose = numPrepose;
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

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	
	
	
}
