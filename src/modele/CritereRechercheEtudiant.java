package modele;

public class CritereRechercheEtudiant {
    private String nom;
    private String prenom;
    private String groupe;
    private boolean handicap,tierTemps,priseEncompte;
    private int id;


    public CritereRechercheEtudiant(String nom, String prenom, String groupe){
        this.nom = nom.toLowerCase();
        this.prenom = prenom.toLowerCase();
        this.groupe = groupe.toLowerCase();
    }

    public CritereRechercheEtudiant(int id,String nom, String prenom, String groupe, String handicap, String tierTemps, String priseEnCompe){
        this.nom = nom;
        this.prenom = prenom;
        this.groupe = groupe;
        this.id = id;
        if(handicap.equals("OUI")){
            this.handicap = true;
        }else{
            this.handicap = false;
        }

        if(tierTemps.equals("OUI")){
            this.tierTemps = true;
        }else{
            this.tierTemps = false;
        }

        if(priseEnCompe.equals("OUI")){
            this.priseEncompte = true;
        }else{
            this.priseEncompte = false;
        }
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

    public boolean isHandicap() {
        return handicap;
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }

    public boolean isTierTemps() {
        return tierTemps;
    }

    public void setTierTemps(boolean tierTemps) {
        this.tierTemps = tierTemps;
    }

    public boolean isPriseEncompte() {
        return priseEncompte;
    }

    public void setPriseEncompte(boolean priseEncompte) {
        this.priseEncompte = priseEncompte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
