package modele.BDD.test;

import modele.BDD.Categorie;
import modele.BDD.Etudiant;
import modele.BDD.EtudiantGroupe;
import modele.BDD.Groupe;
import modele.BDD.GroupeCategorie;
import modele.BDD.Particularite;
import modele.BDD.ParticulariteEtudiant;
import modele.BDD.Place;
import modele.BDD.Salle;
import modele.BDD.TypePlace;
import modele.GestionFichiersExcel.ImportEtudiant;

public class Reset {

	public Reset() {
		// TODO Auto-generated constructor stub
	}
	public static void reset() {
		System.out.println("Suppresion en cours");
		Particularite.deleteTable();
		Categorie.deleteTable();
		Etudiant.deleteTable();
		EtudiantGroupe.deleteTable();
		Groupe.deleteTable();
		GroupeCategorie.deleteTable();
		ParticulariteEtudiant.deleteTable();
		Place.deleteTable();
		Salle.deleteTable();
		TypePlace.deleteTable();
		System.out.println("Suppresion ok");
		System.out.println("Création en cours");
		
		Particularite.createTable();
		Categorie.createTable();
		Etudiant.createTable();
		Groupe.createTable();
		EtudiantGroupe.createTable();
		GroupeCategorie.createTable();
		ParticulariteEtudiant.createTable();
		Place.createTable();
		Salle.createTable();
		TypePlace.createTable();
		System.out.println("Création ok");
		System.out.println("Jeu de données en cours");
		
		ImportEtudiant i = new ImportEtudiant("fichierPourTest\\JeuDonnee.xlsx","Feuil1");
		System.out.println("Jeu de données ok");
	}
	public static void main(String[] args) {
		Reset.reset();
		System.out.println("Terminer");
	}

}
