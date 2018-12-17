package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

// TODO: Auto-generated Javadoc
/**
 * The Class GestionnaireFichier.
 */
public class GestionnaireFichier implements Serializable{

	/**
	 * Instantiates a new gestionnaire fichier.
	 */
	public GestionnaireFichier() {

	}

	/**
	 * Sauvegarde.
	 *
	 * @param categorie the categorie
	 */
	public static void sauvegarde(Categorie categorie) {
		String nomFichier=categorie.getNom();
		File i = new File("./Save/Categorie/","index");
		ArrayList<String> index;
		if(i.exists()) {
			index = GestionnaireFichier.recuperationIndexCategorie();
		}
		else {
			index = new ArrayList<String>();
		}
		index.add(nomFichier);
		GestionnaireFichier.ecritureIndexCategorie(index);
		try {
			File fichier = new File("./Save/Categorie/",nomFichier);
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(categorie);
			out.close();
			fileOut.close();
			System.out.println("Sauvegarde terminée avec succès...\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sauvegarde.
	 *
	 * @param p the p
	 */
	public static void sauvegarde(Groupe p) {

	}


	/**
	 * Lire sauvegarde categorie.
	 *
	 * @param nomFichier the nom fichier
	 * @return the categorie
	 */
	public static Categorie lireSauvegardeCategorie(String nomFichier) {
		//lecture
		Categorie categorie;
		try {
			System.out.println("Lecture en cours...\n");
			File fichier = new File("./Save/Categorie/",nomFichier);
			FileInputStream fileIn = new FileInputStream(fichier);
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			categorie = (Categorie) ois.readObject();
			ois.close();
			fileIn.close();
			return categorie;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Recuperation index categorie.
	 *
	 * @return the array list
	 */
	public static ArrayList<String> recuperationIndexCategorie() {
		try {
			//lecture de l'index
			System.out.println("Lecture en cours d'index...\n");
			File fichier = new File("./Save/Categorie/","index");
			FileInputStream fileIn = new FileInputStream(fichier);
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			ArrayList<String> Index = (ArrayList<String>) ois.readObject();
			ois.close();
			fileIn.close();
			return Index;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ecriture index categorie.
	 *
	 * @param nvIndex the nv index
	 */
	public static void ecritureIndexCategorie(ArrayList<String> nvIndex) {
		try {
			//ecriture de l index
			File fichier = new File("./Save/Categorie/","index");
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(nvIndex);
			out.close();
			fileOut.close();
			System.out.println("index terminée avec succès...\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	//test
	public static void main (String [] args) {
		Etudiant e1=new Etudiant("Plaid","Justin");
		Etudiant e2=new Etudiant("Muller","Geoffroy");
		Etudiant e3=new Etudiant("Guezennec","Lucas");
		Etudiant e4=new Etudiant("Biancalana","Théo");
		Groupe g1=new Groupe("A");
		Groupe g2=new Groupe("B");
		g1.ajouterEtudiant(e1);
		g1.ajouterEtudiant(e2);
		g2.ajouterEtudiant(e3);
		g2.ajouterEtudiant(e4);
		Categorie c1=new Categorie("cat");
		c1.ajouterGroupe(g1);
		c1.ajouterGroupe(g2);
		GestionnaireFichier.sauvegarde(c1);
		Categorie c2=GestionnaireFichier.lireSauvegardeCategorie("cat");
		System.out.println("Lire les données:");
		System.out.println(c2.getListegroupe().get(0).getListeEtudiants().get(0).getNom());
	}
}
