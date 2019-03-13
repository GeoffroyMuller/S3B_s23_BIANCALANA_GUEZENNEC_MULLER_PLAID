package controleur.ControleurModuleSalle;

import modele.BDD.Salle;
import vue.VueSalle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurSauvegardeSalle extends JButton implements ActionListener {
    public static Salle salle;

    public ControleurSauvegardeSalle(Salle salle){
        this.salle = salle;
        this.setText("Sauvegarder");
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        VueSalle.partieAUpdate = VueSalle.UPDATE_AJOUT_SALLE;
        salle.save();
        JOptionPane jop = new JOptionPane();
        jop.showMessageDialog(null,"La salle : "+salle.getNom()+" à bien été sauvegarder !","Sauvegarde effectuée avec succés",JOptionPane.INFORMATION_MESSAGE);

    }
}
