package modele;

import java.util.ArrayList;
import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class Etudiant.
 */
public class Etudiant {

	/** The id etudiant. */
	private static int idEtudiant=0;
	/** The nom. */
	private String nom;

	/** The id. */
	private int id;

	/** The prenom. */
	private String prenom;

	/** The email. */
	private String email;

	/** The list particularite. */
	private ArrayList<Particularite> listParticularite;

	/**
	 * Instantiates a new etudiant.
	 *
	 * @param nom the nom
	 * @param prenom the prenom
	 */
	public Etudiant(String nom, String prenom) {
		this.nom=nom;
		this.prenom=prenom;
		this.email="";
		this.listParticularite= new ArrayList<Particularite>();
		this.listParticularite.add(new Particularite());
		this.idEtudiant++;
		this.id=this.idEtudiant;
	}

	/** The Comparator nom. */
	public static Comparator<Etudiant> ComparatorNom = new Comparator<Etudiant>() {

		@Override
		public int compare(Etudiant e1, Etudiant e2) {
			return e1.getNom().compareTo(e2.getNom());
		}
	};

	/** The Comparator prenom. */
	public static Comparator<Etudiant> ComparatorPrenom = new Comparator<Etudiant>() {

		@Override
		public int compare(Etudiant e1, Etudiant e2) {
			return e1.getPrenom().compareTo(e2.getPrenom());
		}
	};

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
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
	 * Sets the nom.
	 *
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Gets the prenom.
	 *
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Sets the prenom.
	 *
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Gets the list particularite.
	 *
	 * @return the listParticularite
	 */
	public ArrayList<Particularite> getListParticularite() {
		return listParticularite;
	}


	/**
	 * Sets the list particularite.
	 *
	 * @param listParticularite the listParticularite to set
	 */
	public void setListParticularite(ArrayList<Particularite> listParticularite) {
		this.listParticularite = listParticularite;
	}
}
