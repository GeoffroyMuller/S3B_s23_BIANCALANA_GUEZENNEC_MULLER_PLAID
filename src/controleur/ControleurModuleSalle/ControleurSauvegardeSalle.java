package controleur.ControleurModuleSalle;

import controleur.ControleurBoutonsPartieSalle;
import modele.BDD.Categorie;
import modele.BDD.Salle;
import module_etudiant.DialogTraitement;
import vue.VueSalle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurSauvegardeSalle extends JButton implements ActionListener {
    public static Salle salle;

    /**
     * Permet la création du bouton de sauvegarde d'une salle
     * @param salle
     */
    public ControleurSauvegardeSalle(Salle salle){
        this.salle = salle;
        this.setText("Sauvegarder");
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        VueSalle.partieAUpdate = VueSalle.UPDATE_NOTHING;
        final DialogTraitement traitement = new DialogTraitement(null, "Traitement en cours...", true);

        Thread trLoader = new Thread(new Runnable() {
            @Override
            public void run() {
                traitement.afficherDialog();
            }
        });
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                salle.save();
                traitement.close();
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,"La salle : "+salle.getNom()+" à bien été sauvegarder !","Sauvegarde effectuée avec succés",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        tr.start();
        trLoader.start();



    }
}
