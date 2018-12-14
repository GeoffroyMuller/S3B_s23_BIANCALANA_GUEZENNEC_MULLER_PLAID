package modele;

public class Particularite {

	private String nom;
	private boolean prendreEnCompteDansPlacement;
	
	public Particularite() {
		this.nom="default";
		this.prendreEnCompteDansPlacement=true;
	}
	
	public Particularite(String nom, boolean enCompte) {
		this.nom=nom;
		this.prendreEnCompteDansPlacement=enCompte;
	}

}
