package modele;

import modele.BDD.Etudiant;
import modele.BDD.Place;
import modele.BDD.Salle;

public class InformationsPlacementEtudiant {
    private Salle salle;
    private Place place;

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    private Etudiant etudiant;

    public InformationsPlacementEtudiant(Salle salle, Place place, Etudiant etudiant){
        this.salle = salle;
        this.place = place;
        this.etudiant = etudiant;
    }
}
