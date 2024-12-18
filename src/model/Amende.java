package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Amende implements Serializable{
	
	private static final long serialVersionUID = 234293847923874L;
	
	private StringProperty montant;

	public Amende(String montant) {
		this.montant = new SimpleStringProperty(montant);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {

		//out.defaultWriteObject();
		out.writeUTF(montant.get());

	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

		//in.defaultReadObject();
		
		montant = new SimpleStringProperty(in.readUTF());
	}

	public StringProperty getMontant() {
		return montant;
	}

	public void setMontant(StringProperty montant) {
		this.montant = montant;
	}
	
	
	
	
}
