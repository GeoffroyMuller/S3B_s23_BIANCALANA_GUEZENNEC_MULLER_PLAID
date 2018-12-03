package modele;

import java.util.ArrayList;

public class Categorie {
	//utiliser par la class listeur pour le moment
	private String nom;
	private ArrayList<Groupe> listegroupe;
	
	public Categorie(String nomp, ArrayList<Groupe> listp) {
		nom = nomp;
		listegroupe = listp;
	}
	
	public String getNom() {
		return nom;
	}
	public ArrayList<Groupe> getListegroupe() {
		return listegroupe;
	}
	
	
}
