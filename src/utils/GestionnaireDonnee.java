package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Adherent;
import model.DVD;
import model.Document;
import model.Livre;
import model.Periodique;
import model.Prepose;
import model.Registre;

public class GestionnaireDonnee {
	
	Registre registre;
	
	public GestionnaireDonnee() {
		this.registre = Registre.getInstance();
	}
	
	public boolean verificationFormat(String input, String cas) {
		
			switch (cas) {
			case "Null":
				if (input == "") return false; 
				else return true;
			
			case "Nombre":
				try {
		            Integer.parseInt(input);
		            return true; 
		        } catch (NumberFormatException e) {
		            return false;
		        }
			case "Telephone":
				String regexTelephone = "^\\(\\d{3}\\) \\d{3}-\\d{4}$";
		        
		        Pattern patternTelephone = Pattern.compile(regexTelephone);
		        
		        Matcher matcherTelephone = patternTelephone.matcher(input);
		        
		        
		        
		        return matcherTelephone.matches();
			case "Date":
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				     try {
				          format.parse(input);
				          return true;
				     }
				     catch(ParseException e){
				          return false;
				     }
				
		}
		
		

		
		return false;
	}
	
	public String genereIdentifiant(String precedent, int separateur) {
		int nbPrecedent = Integer.parseInt(precedent.substring(separateur));
		
		return Integer.toString(nbPrecedent+1);
	}
	
	public void chargerDocuments(String type) throws IOException {
		
		
		String pathFichierTexte = "/donnees/";
		
		
		try (Scanner lecteur = new Scanner(Document.class.getResourceAsStream(pathFichierTexte + type + ".txt"))) {
			
			lecteur.useDelimiter(",|\\n");
			
			if (registre.getListeLivres().size() == 0 || registre.getListePeriodiques().size() == 0 || registre.getListeDVDs().size() == 0) {
				if (type == "Livres") {
					ObservableList<Livre> listeLivres = FXCollections.observableArrayList();
					while (lecteur.hasNextLine()) {
						String numDoc = lecteur.next().trim();
						String titre = lecteur.next().trim();
						String date = lecteur.next().trim();
						String auteur = lecteur.next().trim();
						listeLivres.add(new Livre(numDoc, titre, date, auteur, "Disponible", ""));
					}
					
					registre.setListeLivres(listeLivres);
					serializerListeObservable("Livre");
					
				}
				
				else if (type == "Periodiques") {
					ObservableList<Periodique> listePeriodiques = FXCollections.observableArrayList();
					while (lecteur.hasNextLine()) {
						String numDoc = lecteur.next().trim();
						String titre = lecteur.next().trim();
						String date = lecteur.next().trim();
						String noVolume = lecteur.next().trim();
						String noPeriodique = lecteur.next().trim();
						listePeriodiques.add(new Periodique(numDoc, titre, noVolume, noPeriodique, date, "Disponible", ""));
					}
					
					registre.setListePeriodiques(listePeriodiques);
					serializerListeObservable("Periodique");
				}
				
				else if (type == "DVD") {
					ObservableList<DVD> listeDVDs = FXCollections.observableArrayList();
					while (lecteur.hasNextLine()) {
						String numDoc = lecteur.next().trim();
						String titre = lecteur.next().trim();
						String date = lecteur.next().trim();
						String noDisques = lecteur.next().trim();
						String realisateur = lecteur.next().trim();
						listeDVDs.add(new DVD(numDoc, titre, date, realisateur, noDisques, "Disponible", ""));
					}
					
					registre.setListeDVDs(listeDVDs);
					serializerListeObservable("DVD");
				}
			}
			
			
			
			
			lecteur.close();
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void serializerListeObservable(String nomListe) {
		
        File repertoire = new File("donnees_ser");
        if (!repertoire.exists()) {
        	repertoire.mkdirs();
        }
		
		switch (nomListe) {
			case "Adherent":
				File fichierSerAdherent = new File(repertoire, "adherents.ser");

				try (FileOutputStream fichier = new FileOutputStream(fichierSerAdherent);
						ObjectOutputStream out = new ObjectOutputStream(fichier)) {

					out.writeObject(new ArrayList<Adherent>(registre.getListeAdherents()));

				} catch (IOException e) {
					e.printStackTrace();
				}
			case "Prepose":
				File fichierSerPrepose = new File(repertoire, "preposes.ser");

				try (FileOutputStream fichier = new FileOutputStream(fichierSerPrepose);
						ObjectOutputStream out = new ObjectOutputStream(fichier)) {

					out.writeObject(new ArrayList<Prepose>(registre.getListePreposes()));

				} catch (IOException e) {
					e.printStackTrace();
				}
			case "Livre":
				File fichierSerLivres = new File(repertoire, "livres.ser");

				try (FileOutputStream fichier = new FileOutputStream(fichierSerLivres);
						ObjectOutputStream out = new ObjectOutputStream(fichier)) {

					out.writeObject(new ArrayList<Livre>(registre.getListeLivres()));

				} catch (IOException e) {
					e.printStackTrace();
				}
			
			case "Periodique":
				File fichierSerPeriodique = new File(repertoire, "periodiques.ser");

				try (FileOutputStream fichier = new FileOutputStream(fichierSerPeriodique);
						ObjectOutputStream out = new ObjectOutputStream(fichier)) {

					out.writeObject(new ArrayList<Periodique>(registre.getListePeriodiques()));

				} catch (IOException e) {
					e.printStackTrace();
				}
			
			case "DVD":
				File fichierSerDVD = new File(repertoire, "DVDs.ser");

				try (FileOutputStream fichier = new FileOutputStream(fichierSerDVD);
						ObjectOutputStream out = new ObjectOutputStream(fichier)) {

					out.writeObject(new ArrayList<DVD>(registre.getListeDVDs()));

				} catch (IOException e) {
					e.printStackTrace();
				}
				
		}
	}
	
	public ObservableList<Adherent> deserializeListeAdherents() {
		
		File fichierSer = new File("donnees_ser", "adherents.ser");
		
		if (fichierSer.exists()) {
			try (FileInputStream fichier = new FileInputStream(fichierSer);
					ObjectInputStream in = new ObjectInputStream(fichier)) {

				List<Adherent> listeAdherent = (List<Adherent>) in.readObject();

				return FXCollections.observableArrayList(listeAdherent);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		return FXCollections.observableArrayList();

	}
	
	public ObservableList<Prepose> deserializeListePrepose() {
		File fichierSer = new File("donnees_ser", "preposes.ser");
		
		if (fichierSer.exists()) {
			try (FileInputStream fichier = new FileInputStream(fichierSer);
					ObjectInputStream in = new ObjectInputStream(fichier)) {

				List<Prepose> listePrepose = (List<Prepose>) in.readObject();

				return FXCollections.observableArrayList(listePrepose);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		return FXCollections.observableArrayList();
	}
	
	public ObservableList<Livre> deserializeListeLivres() {
		File fichierSer = new File("donnees_ser", "livres.ser");
		
		if (fichierSer.exists()) {
			try (FileInputStream fichier = new FileInputStream(fichierSer);
					ObjectInputStream in = new ObjectInputStream(fichier)) {

				List<Livre> listeLivres = (List<Livre>) in.readObject();

				return FXCollections.observableArrayList(listeLivres);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		return FXCollections.observableArrayList();
	}
	
	public ObservableList<Periodique> deserializeListePeriodiques() {
		File fichierSer = new File("donnees_ser", "periodiques.ser");
		
		if (fichierSer.exists()) {
			try (FileInputStream fichier = new FileInputStream(fichierSer);
					ObjectInputStream in = new ObjectInputStream(fichier)) {

				List<Periodique> listePeriodiques = (List<Periodique>) in.readObject();

				return FXCollections.observableArrayList(listePeriodiques);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		return FXCollections.observableArrayList();
	}
	
	public ObservableList<DVD> deserializeListeDVDs() {
		File fichierSer = new File("donnees_ser", "DVDs.ser");
		
		if (fichierSer.exists()) {
			try (FileInputStream fichier = new FileInputStream(fichierSer);
					ObjectInputStream in = new ObjectInputStream(fichier)) {

				List<DVD> listeDVDs = (List<DVD>) in.readObject();

				return FXCollections.observableArrayList(listeDVDs);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		return FXCollections.observableArrayList();
	}
	
	
	

}
