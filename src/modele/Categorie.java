package modele;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Categorie.
 */
public class Categorie {

	/** The nom. */
	private String nom;

	/** The listegroupe. */
	private ArrayList<Groupe> listeGroupe;

	/**
	 * Instantiates a new categorie.
	 *
	 * @param nom the nom
	 * @param list the list
	 */
	public Categorie(String nom, ArrayList<Groupe> list) {
		this.nom=nom;
		this.listeGroupe = list;
	}

	/**
	 * Instantiates a new categorie.
	 *
	 * @param nom the nom
	 */
	public Categorie(String nom) {
		this.nom=nom;
		this.listeGroupe = new ArrayList<Groupe>();
	}

	/**
	 * Sauvegarder categorie.
	 */
	public void sauvegarderCategorie() {
		//ecrire dans un fichier
	}

	/**
	 * Ajouter groupe.
	 *
	 * @param groupe the groupe
	 */
	public void ajouterGroupe(Groupe groupe) {
		boolean a=true;
		for(Groupe e : this.listeGroupe) {
			if(e.getGroupe()==groupe.getGroupe()) {
				a=false;
			}
		}
		if(a) {
			this.listeGroupe.add(groupe);
		}
	}


	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Gets the listegroupe.
	 *
	 * @return the listegroupe
	 */
	public ArrayList<Groupe> getListegroupe() {
		return listeGroupe;
	}


}
