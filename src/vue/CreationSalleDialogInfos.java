package vue;

public class CreationSalleDialogInfos {
    private String nom;
    private int hauteur;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    private int largeur;

    public CreationSalleDialogInfos(String nom,int hauteur,int largeur){
        this.nom=nom;
        this.hauteur = hauteur;
        this.largeur = largeur;
    }

    public String toString(){
        return "Nom de la salle : "+this.nom+"\n"+"Hauteur de la salle : "+this.hauteur+"\nLargeur de la salle : "+this.largeur+
                "\n La hauteur et la largeur doivent inclure les allées !";
    }
}
