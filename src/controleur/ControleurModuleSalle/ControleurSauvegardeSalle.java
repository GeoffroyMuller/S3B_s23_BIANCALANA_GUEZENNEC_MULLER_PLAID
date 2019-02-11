package controleur.ControleurModuleSalle;

import modele.BDD.Salle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurSauvegardeSalle extends JButton implements ActionListener {
    public static Salle salle;

    public ControleurSauvegardeSalle(Salle salle){
        this.salle = salle;
        this.setText("Sauvegarder");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        salle.save();
    }
}
