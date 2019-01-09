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

public class Reset {

	public Reset() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
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
	}

}
