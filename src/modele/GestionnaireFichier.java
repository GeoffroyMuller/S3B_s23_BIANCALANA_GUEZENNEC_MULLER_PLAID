package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class GestionnaireFichier.
 */
public class GestionnaireFichier{


	/**
	 * Sauvegarde.
	 *
	 * @param categorie the categorie
	 */
	public static void sauvegarde(Categorie categorie) {
		//Le nom du fichier qui sera sauvegarder et le nom de la categorie
		String nomFichier=categorie.getNom();
		//L'emplacement ou se situe les sauvegardes
		String emplacement = "./Save/Categorie/";
		File fi = new File(emplacement,"index");
		ArrayList<String> index;
		boolean trouve=false;
		//verification si il y a deja un index
		if(fi.exists()) {
			//on le recuperer
			index = GestionnaireFichier.recuperationIndex(emplacement);
			//verification si la categorie et pas deja sauvegarder
			for(int i=0;i<index.size();i++)
			{
				if(index.get(i).equals(nomFichier))
				{
					trouve = true;
				}
			}
		}
		else {
			index = new ArrayList<String>();
		}
		if(!trouve) {
			index.add(nomFichier);
		}
		GestionnaireFichier.ecritureIndex(emplacement,index);
		try {
			File fichier = new File("./Save/Categorie/",nomFichier);
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(categorie);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * @param emplacement the emplacement
	 * @return the array list
	 */
	public static ArrayList<String> recuperationIndex(String emplacement) {
		try {
			//lecture de l'index
			File fichier = new File(emplacement,"index");
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
	 * @param emplacement the emplacement
	 * @param nvIndex the nv index
	 */
	public static void ecritureIndex(String emplacement,ArrayList<String> nvIndex) {
		try {
			//ecriture de l index
			File fichier = new File(emplacement,"index");
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(nvIndex);
			out.close();
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Categorie> recuperationTouteCategorie(){
		ArrayList<String> index = GestionnaireFichier.recuperationIndex("./Save/Categorie/");
		ArrayList<Categorie> listCat = new ArrayList<Categorie>();
		for(int i=0;i<index.size();i++) {
			listCat.add(GestionnaireFichier.lireSauvegardeCategorie(index.get(i)));
		}
		return listCat;
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
		for(int i=0;i<100000;i++) {
			Etudiant e5=new Etudiant("Biancalana","Théo"+i);
			g2.ajouterEtudiant(e5);
		}
		System.out.println("la");
		Categorie c1=new Categorie("cat");
		c1.ajouterGroupe(g1);
		c1.ajouterGroupe(g2);
		GestionnaireFichier.sauvegarde(c1);
		Categorie c2=GestionnaireFichier.lireSauvegardeCategorie("cat");
		System.out.println("Lire les données:");
		System.out.println(c2.getListegroupe().get(0).getListeEtudiants().get(0).getNom());
		ArrayList<String> index = GestionnaireFichier.recuperationIndex("./Save/Categorie/");
		for(int i=0;i<index.size();i++) {
			System.out.println(index.get(i));
		}
		System.out.println("toute les cat");
		ArrayList<Categorie> listCat = GestionnaireFichier.recuperationTouteCategorie();
		for(int i=0;i<listCat.size();i++) {
			System.out.println(listCat.get(i).getNom());
		}
	}
}
