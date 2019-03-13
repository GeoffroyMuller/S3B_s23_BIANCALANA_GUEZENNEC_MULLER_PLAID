package modele;

import controleur.ControleurModuleSalle.ControleurCaseSalle;

import javax.swing.*;
import java.util.ArrayList;

public class ProprietesCaseSalle {
    private JPanel salleConstruite;
    private ArrayList<ControleurCaseSalle> listeControleurs;

    public ProprietesCaseSalle(JPanel salle, ArrayList<ControleurCaseSalle> liste){
        this.salleConstruite = salle;
        this.listeControleurs = liste;
    }

    public JPanel getSalleConstruite() {
        return salleConstruite;
    }

    public void setSalleConstruite(JPanel salleConstruite) {
        this.salleConstruite = salleConstruite;
    }

    public ArrayList<ControleurCaseSalle> getListeControleurs() {
        return listeControleurs;
    }

    public void setListeControleurs(ArrayList<ControleurCaseSalle> listeControleurs) {
        this.listeControleurs = listeControleurs;
    }
}
