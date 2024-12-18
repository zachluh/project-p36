package model;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DVD extends Document{
	private static final long serialVersionUID = 32342342342L;
	
	private StringProperty nbDisquesProperty;

	public DVD(String numDoc, String titre, String auteur, String dateDeParution, String nbDisques, String etat, String emprunteur) {
		super(numDoc, titre, auteur, dateDeParution, etat, emprunteur);
		
		this.nbDisquesProperty = new SimpleStringProperty(nbDisques);
		// TODO Auto-generated constructor stub
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		System.out.println("Serialisation de: Document");

		//out.defaultWriteObject();
		out.writeUTF(numDocProperty.get());
	    out.writeUTF(titreProperty.get());
	    out.writeUTF(auteurProperty.get());
	    out.writeUTF(dateDeParutionProperty.get());
	    out.writeUTF(etatProperty.get());
	    out.writeUTF(emprunteurProperty.get());
		out.writeUTF(nbDisquesProperty.get());
		
		

	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		
		numDocProperty = new SimpleStringProperty(in.readUTF());
		titreProperty = new SimpleStringProperty(in.readUTF());

		System.out.println("Lecture de l'objet: Document" + numDocProperty.get());

		auteurProperty = new SimpleStringProperty(in.readUTF());
		dateDeParutionProperty = new SimpleStringProperty(in.readUTF());
		etatProperty = new SimpleStringProperty(in.readUTF());
		emprunteurProperty = new SimpleStringProperty(in.readUTF());
		nbDisquesProperty = new SimpleStringProperty(in.readUTF());

		//in.defaultReadObject();

	}
	
	public String getNbDisquesProperty() {
		return nbDisquesProperty.get();
	}
	
	

}
