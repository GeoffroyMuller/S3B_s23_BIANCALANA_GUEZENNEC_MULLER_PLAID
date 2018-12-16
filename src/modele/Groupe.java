package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class Groupe.
 */
public class Groupe implements Serializable, Comparable<Groupe> {

	/** The groupe. */
	private String nom;

	/** The liste etudiants. */
	private ArrayList<Etudiant> listeEtudiants;

	/**
	 * Instantiates a new groupe.
	 *
	 * @param nom the nom
	 */
	public Groupe(String nom) {
		this.nom=nom;
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

	/**
	 * Etudiant dedans.
	 *
	 * @param etudiant the etudiant
	 * @return true, if successful
	 */
	public boolean etudiantDedans(Etudiant etudiant) {
		boolean res=false;
		for(Etudiant e : this.listeEtudiants) {
			if(etudiant.getId()==e.getId()) {
				res=true;
			}
		}
		return res;
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Groupe groupe = (Groupe) o;
		if (this == o) return true;
		return nom.equals(groupe.nom);
	}

	@Override
	public int hashCode() {
		return nom.hashCode();
	}

	@Override
	public int compareTo(Groupe o) {
		return (this.nom.compareTo(o.getNom()));
	}
}
