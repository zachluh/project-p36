package model;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public class Pret implements Serializable{
	
	private static final long serialVersionUID = 12319028310293823L;
	
	Registre registre;
	
	private StringProperty numPret;
	private StringProperty datePret;
	private StringProperty dateRetourPrevu;
	private StringProperty dateRetour;
	private Amende amende = new Amende(null);
	private Document documentPrete;
	private ObservableList<Document> listeDocuments;
	
	public Pret(String numPret, String datePret, String dateRetourPrevu,
			String dateRetour, Document documentPrete) {
		this.numPret = new SimpleStringProperty(numPret);
		this.datePret = new SimpleStringProperty(datePret);
		this.dateRetourPrevu = new SimpleStringProperty(dateRetourPrevu);
		this.dateRetour = new SimpleStringProperty(dateRetour);
		this.documentPrete = documentPrete;
		amende.setMontant(new SimpleStringProperty("0"));
		
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {

		//out.defaultWriteObject();
		System.out.println("Serialisation de: Pret" + numPret.get());

		out.writeUTF(numPret.get());
		out.writeUTF(datePret.get());
		out.writeUTF(dateRetourPrevu.get());
		out.writeUTF(dateRetour.get());
		out.writeUTF(documentPrete.getNumDocProperty());
		out.writeObject(amende);

	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

		//in.defaultReadObject();

		numPret = new SimpleStringProperty(in.readUTF());
		datePret = new SimpleStringProperty(in.readUTF());

		System.out.println("Lecture de l'objet: Pret" + numPret.get());

		dateRetourPrevu = new SimpleStringProperty(in.readUTF());
		dateRetour = new SimpleStringProperty(in.readUTF());
		String numDocument = in.readUTF();
		
		registre = Registre.getInstance();
		listeDocuments = registre.getListeTout();
		
		//System.out.println("Size liste Document: " + listeDocuments.size());
		//System.out.println("Num document prete: " + numDocument);
		//System.out.println("Size liste Livre: " + registre.getListeLivres().size());
		
		for (Document document : listeDocuments) {
			System.out.println("Document scanned: " + document.getNumDocProperty());
			if (document.getNumDocProperty().equals(numDocument)) {
				documentPrete = document;
				break;
			}
		}
		
		System.out.println("Document Prete: " + documentPrete);
		
		
		
		amende = (Amende) in.readObject();
	}

	public String getNumPret() {
		return numPret.get();
	}

	public void setNumPret(StringProperty numPret) {
		this.numPret = numPret;
	}

	public String getDatePret() {
		return datePret.get();
	}

	public void setDatePret(StringProperty datePret) {
		this.datePret = datePret;
	}

	public String getDateRetourPrevu() {
		return dateRetourPrevu.get();
	}

	public void setDateRetourPrevu(StringProperty dateRetourPrevu) {
		this.dateRetourPrevu = dateRetourPrevu;
	}

	public String getDateRetour() {
		return dateRetour.get();
	}

	public void setDateRetour(StringProperty dateRetour) {
		this.dateRetour = dateRetour;
	}

	public Document getDocumentPrete() {
		return documentPrete;
	}

	public void setDocumentPrete(Document documentPrete) {
		this.documentPrete = documentPrete;
	}
	
	public String getAmende() {
		return amende.getMontant().get();
	}
	
	public Amende getAmendeProperty() {
		return amende;
	}
	
	public void setAmende(Amende amende) {
		this.amende = amende;
	}
	
	
	
	
	

}
