package controleur;

/**
 * Classe permettant de stocker les données rentrer par l'utilisateur lors du modification du nom de la place
 */
public class ModificationNomPlaceDialogInfo {


    private String nouveauNom,nomColonne,nomRangee;

    /**
     * Instancie la classe
     * @param nomColonne
     * @param nomRangee
     */
    public ModificationNomPlaceDialogInfo(String nomColonne,String nomRangee){
        this.nouveauNom = nomRangee+""+nomColonne;
        this.nomColonne = nomColonne;
        this.nomRangee = nomRangee;
    }

    public String getNomColonne() {
        return nomColonne;
    }

    public void setNomColonne(String nomColonne) {
        this.nomColonne = nomColonne;
    }

    public String getNomRangee() {
        return nomRangee;
    }

    public void setNomRangee(String nomRangee) {
        this.nomRangee = nomRangee;
    }

    public String getNouveauNom() {
        return nouveauNom;
    }

    public void setNouveauNom(String nouveauNom) {
        this.nouveauNom = nouveauNom;
    }
}
