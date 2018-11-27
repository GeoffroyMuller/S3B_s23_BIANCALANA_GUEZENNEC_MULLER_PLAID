package modele;

import java.util.ArrayList;
import java.util.Collections;

// TODO: Auto-generated Javadoc
/**
 * The Class Groupe.
 */
public class Groupe {
	
	/** The groupe. */
	private String groupe;
	
	/** The liste etudiants. */
	private ArrayList<Etudiant> listeEtudiants;
	
	/**
	 * Instantiates a new groupe.
	 */
	public Groupe() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new groupe.
	 *
	 * @param groupe the groupe
	 */
	public Groupe(String groupe) {
		this.groupe=groupe;
		this.listeEtudiants=new ArrayList<Etudiant>();
	}
	
	/**
	 * Ajouter etudiant.
	 *
	 * @param etudiant the etudiant
	 */
	public void ajouterEtudiant(Etudiant etudiant) {
		boolean a=true;
		for(Etudiant e : this.listeEtudiants) {
			if(e==etudiant) {
				a=false;
			}
		}
		if(a) {
			this.listeEtudiants.add(etudiant);
		}
	}
	
	/**
	 * Supprimmer etudiant.
	 *
	 * @param etudiant the etudiant
	 */
	public void supprimmerEtudiant(Etudiant etudiant) {
		for(int i = 0 ; i < this.listeEtudiants.size(); i++) {
			if(this.listeEtudiants.get(i)==etudiant) {
				this.listeEtudiants.remove(i);
			}
		}
	}
	
	/**
	 * Trie par nom.
	 */
	public void trieParNom(){
		Collections.sort(this.listeEtudiants, Etudiant.ComparatorNom);
	}
	
	/**
	 * Trie par prenom.
	 */
	public void trieParPrenom(){
		Collections.sort(this.listeEtudiants, Etudiant.ComparatorPrenom);
	}

	/**
	 * Gets the groupe.
	 *
	 * @return the groupe
	 */
	public String getGroupe() {
		return groupe;
	}

	/**
	 * Sets the groupe.
	 *
	 * @param groupe the groupe to set
	 */
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	/**
	 * Gets the liste etudiants.
	 *
	 * @return the listeEtudiants
	 */
	public ArrayList<Etudiant> getListeEtudiants() {
		return listeEtudiants;
	}

	/**
	 * Sets the liste etudiants.
	 *
	 * @param listeEtudiants the listeEtudiants to set
	 */
	public void setListeEtudiants(ArrayList<Etudiant> listeEtudiants) {
		this.listeEtudiants = listeEtudiants;
	}
	
}
