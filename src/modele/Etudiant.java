package modele;

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
	
	/** The groupe. */
	private String groupe;
	
	/** The email. */
	private String email;
	
	/** The prendre en compte dans le placement. */
	private boolean prendreEnCompteDansLePlacement;
	
	/** The tiersTemps. */
	private boolean tiersTemps;
	
	/** The handicap. */
	private boolean handicap;
	
	/**
	 * Instantiates a new etudiant.
	 */
	public Etudiant() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new etudiant.
	 *
	 * @param nom the nom
	 * @param prenom the prenom
	 * @param groupe the groupe
	 */
	public Etudiant(String nom, String prenom, String groupe) {
		this.nom=nom;
		this.prenom=prenom;
		this.groupe=groupe;
		this.email="";
		this.prendreEnCompteDansLePlacement=true;
		this.tiersTemps=false;
		this.handicap=false;
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
	 * Checks if is prendre en compte dans le placement.
	 *
	 * @return the prendreEnCompteDansLePlacement
	 */
	public boolean isPrendreEnCompteDansLePlacement() {
		return prendreEnCompteDansLePlacement;
	}

	/**
	 * Sets the prendre en compte dans le placement.
	 *
	 * @param prendreEnCompteDansLePlacement the prendreEnCompteDansLePlacement to set
	 */
	public void setPrendreEnCompteDansLePlacement(boolean prendreEnCompteDansLePlacement) {
		this.prendreEnCompteDansLePlacement = prendreEnCompteDansLePlacement;
	}

	/**
	 * Checks if is tiers temps.
	 *
	 * @return the tiersTemps
	 */
	public boolean isTiersTemps() {
		return tiersTemps;
	}

	/**
	 * Sets the tiers temps.
	 *
	 * @param tiersTemps the tiersTemps to set
	 */
	public void setTiersTemps(boolean tiersTemps) {
		this.tiersTemps = tiersTemps;
	}

	/**
	 * Checks if is handicap.
	 *
	 * @return the handicap
	 */
	public boolean isHandicap() {
		return handicap;
	}

	/**
	 * Sets the handicap.
	 *
	 * @param handicap the handicap to set
	 */
	public void setHandicap(boolean handicap) {
		this.handicap = handicap;
	}

}
