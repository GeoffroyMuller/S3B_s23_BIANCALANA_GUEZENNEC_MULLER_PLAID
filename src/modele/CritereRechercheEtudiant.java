package modele;

public class CritereRechercheEtudiant {
    private String nom;
    private String prenom;
    private String groupe;


    public CritereRechercheEtudiant(String nom, String prenom, String groupe){
        this.nom = nom.toLowerCase();
        this.prenom = prenom.toLowerCase();
        this.groupe = groupe.toLowerCase();
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }


}
