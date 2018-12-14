package modele;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Salle.
 */
public class Salle {
	
	/** The nom. */
	String nom;
	
	/** The list place. */
	ArrayList <Place> listPlace;
	
	/**
	 * Instantiates a new salle.
	 *
	 * @param nom the nom
	 */
	public Salle(String nom) {
		this.nom=nom;
		listPlace=new ArrayList<Place>();
	}
	
	/**
	 * Ajouter place.
	 *
	 * @param place the place
	 */
	public void ajouterPlace(Place place) {
		
	}
	
	/**
	 * Supprimer place.
	 *
	 * @param place the place
	 */
	public void supprimerPlace(Place place) {
		
	}

}
