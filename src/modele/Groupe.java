package modele;

import java.util.ArrayList;
import java.util.Collections;

// TODO: Auto-generated Javadoc
/**
 * The Class Groupe.
 */
public class Groupe {
	
	/** The groupe. */
	private String nom;
	
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
	 * @param pnom the pnom
	 */
	public Groupe(String pnom) {
		this.nom=pnom;
		this.listeEtudiants=new ArrayList<Etudiant>();
	}
	
	/**
	 * Ajouter etudiant.
	 *
	 * @param etudiant the etudiant
	 */
	public void ajouterEtudiant(Etudiant etudiant) {
		boolean a=true;
		if(!etudiant.getGroupe().equals(this.nom)) {
			a=false;
		}
		for(Etudiant e : this.listeEtudiants) {
			if(e.getId()==etudiant.getId()) {
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
			if(this.listeEtudiants.get(i).getId()==etudiant.getId()) {
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
		return nom;
	}

	/**
	 * Sets the groupe.
	 *
	 * @param nom the new groupe
	 */
	public void setGroupe(String nom) {
		this.nom = nom;
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
