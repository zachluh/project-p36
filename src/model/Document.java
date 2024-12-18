package model;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Document implements Serializable{
	private static final long serialVersionUID = 45684956749586L;
	
	protected StringProperty numDocProperty;
	protected StringProperty titreProperty;
	protected StringProperty auteurProperty;
	protected StringProperty dateDeParutionProperty;
	protected StringProperty etatProperty;
	protected StringProperty emprunteurProperty;
	protected Integer nbPrêts = 0;
	
	public Document(String numDoc, String titre, String dateDeParution, String auteur, String etat, String emprunteur) {
		this.numDocProperty = new SimpleStringProperty(numDoc);
		this.titreProperty = new SimpleStringProperty(titre);
		this.auteurProperty = new SimpleStringProperty(auteur);
		this.dateDeParutionProperty = new SimpleStringProperty(dateDeParution);
		this.etatProperty = new SimpleStringProperty(etat);
		this.emprunteurProperty = new SimpleStringProperty(emprunteur);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {

		//out.defaultWriteObject();
		System.out.println("Serialisation de: Document" + numDocProperty.get());

	    out.writeUTF(numDocProperty.get());
	    out.writeUTF(titreProperty.get());
	    out.writeUTF(auteurProperty.get());
	    out.writeUTF(dateDeParutionProperty.get());
	    out.writeUTF(etatProperty.get());
	    out.writeUTF(emprunteurProperty.get());
	    out.writeInt(nbPrêts);

	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

		//in.defaultReadObject();

		numDocProperty = new SimpleStringProperty(in.readUTF());
		titreProperty = new SimpleStringProperty(in.readUTF());

		System.out.println("Lecture de l'objet: Document" + numDocProperty.get());

		auteurProperty = new SimpleStringProperty(in.readUTF());
		dateDeParutionProperty = new SimpleStringProperty(in.readUTF());
		etatProperty = new SimpleStringProperty(in.readUTF());
		emprunteurProperty = new SimpleStringProperty(in.readUTF());
		nbPrêts = in.readInt();
	}

	public String getNumDocProperty() {
		return numDocProperty.get();
	}
	

	public void setNumDocProperty(StringProperty numDocProperty) {
		this.numDocProperty = numDocProperty;
	}

	public String getTitreProperty() {
		return titreProperty.get();
	}

	public void setTitreProperty(StringProperty titreProperty) {
		this.titreProperty = titreProperty;
	}

	public String getAuteurProperty() {
		return auteurProperty.get();
	}

	public void setAuteurProperty(StringProperty auteurProperty) {
		this.auteurProperty = auteurProperty;
	}

	public String getDateDeParutionProperty() {
		return dateDeParutionProperty.get();
	}

	public void setDateDeParutionProperty(StringProperty dateDeParutionProperty) {
		this.dateDeParutionProperty = dateDeParutionProperty;
	}

	public String getEtatProperty() {
		return etatProperty.get();
	}

	public void setEtatProperty(StringProperty etatProperty) {
		this.etatProperty = etatProperty;
	}

	public String getEmprunteurProperty() {
		return emprunteurProperty.get();
	}

	public void setEmprunteurProperty(StringProperty emprunteurProperty) {
		this.emprunteurProperty = emprunteurProperty;
	}
	
	public Integer getNbPrêts() {
		return nbPrêts;
	}
	
	public void addPrêt() {
		nbPrêts++;
	}
	
	


}
