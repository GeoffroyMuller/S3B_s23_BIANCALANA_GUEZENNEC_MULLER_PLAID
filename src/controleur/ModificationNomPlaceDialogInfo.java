package controleur;

public class ModificationNomPlaceDialogInfo {
    public String getNouveauNom() {
        return nouveauNom;
    }

    public void setNouveauNom(String nouveauNom) {
        this.nouveauNom = nouveauNom;
    }

    private String nouveauNom,nomColonne,nomRangee;

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
}
