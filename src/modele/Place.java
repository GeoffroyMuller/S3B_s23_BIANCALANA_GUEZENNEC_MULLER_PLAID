package modele;

public class Place {
	
	private String libelle;
	private boolean disponnible;
	
	public Place(String libelle) {
		this.libelle=libelle;
	}

	
	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the disponnible
	 */
	public boolean isDisponnible() {
		return disponnible;
	}

	/**
	 * @param disponnible the disponnible to set
	 */
	public void setDisponnible(boolean disponnible) {
		this.disponnible = disponnible;
	}
	
}
