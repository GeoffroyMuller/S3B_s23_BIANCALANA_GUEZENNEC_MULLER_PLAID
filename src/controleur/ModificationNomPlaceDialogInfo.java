package controleur;

public class ModificationNomPlaceDialogInfo {
    public String getNouveauNom() {
        return nouveauNom;
    }

    public void setNouveauNom(String nouveauNom) {
        this.nouveauNom = nouveauNom;
    }

    private String nouveauNom;

    public ModificationNomPlaceDialogInfo(String nouveauNom){
        this.nouveauNom = nouveauNom;
    }
}
