package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Periodique extends Document{
	
	private static final long serialVersionUID = 345345345L;
	
	private StringProperty nbVolumesProperty;
	private StringProperty nbPeriodiqueProperty;

	public Periodique(String numDoc, String titre, String nbVolumes, String nbPeriodique, String dateDeParution, String etat,
			String emprunteur) {
		
		super(numDoc, titre, dateDeParution, "", etat, emprunteur);
		
		this.nbVolumesProperty = new SimpleStringProperty(nbVolumes);
		this.nbPeriodiqueProperty = new SimpleStringProperty(nbPeriodique);
		// TODO Auto-generated constructor stub
	}
	
	
	private void writeObject(ObjectOutputStream out) throws IOException {

		//out.defaultWriteObject();
		
		out.writeUTF(numDocProperty.get());
	    out.writeUTF(titreProperty.get());
	    out.writeUTF(auteurProperty.get());
	    out.writeUTF(dateDeParutionProperty.get());
	    out.writeUTF(etatProperty.get());
	    out.writeUTF(emprunteurProperty.get());
		out.writeUTF(nbVolumesProperty.get());
		out.writeUTF(nbPeriodiqueProperty.get());
		
		

	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		
		numDocProperty = new SimpleStringProperty(in.readUTF());
		titreProperty = new SimpleStringProperty(in.readUTF());

		System.out.println("Lecture de l'objet: Document" + numDocProperty.get());

		auteurProperty = new SimpleStringProperty(in.readUTF());
		dateDeParutionProperty = new SimpleStringProperty(in.readUTF());
		etatProperty = new SimpleStringProperty(in.readUTF());
		emprunteurProperty = new SimpleStringProperty(in.readUTF());
		nbVolumesProperty = new SimpleStringProperty(in.readUTF());
		nbPeriodiqueProperty = new SimpleStringProperty(in.readUTF());

		//in.defaultReadObject();

	}
	
	public String getNbVolumesProperty() {
		return nbVolumesProperty.get();
	}
	
	public String getNbPeriodiqueProperty() {
		return nbPeriodiqueProperty.get();
	}

}
