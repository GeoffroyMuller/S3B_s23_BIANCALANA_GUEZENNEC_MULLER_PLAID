package modele;

import java.io.BufferedReader;
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

public class GestionnaireFichier implements Serializable{

	Object o;

	public GestionnaireFichier() {

	}

	public static void sauvegarde(Categorie categorie) {

		try {
			FileOutputStream fileOut = new FileOutputStream("test");
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


	public static Categorie lireSauvegarde() {


		//lecture
		Categorie categorie;
		try {
			System.out.println("Lecture en cours...\n");
			FileInputStream fileIn = new FileInputStream("test");
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
		Categorie c2=GestionnaireFichier.lireSauvegarde();
		System.out.println("Lire les données:");
		System.out.println(c2.getListegroupe().get(0).getListeEtudiants().get(0).getNom());
	}
}
