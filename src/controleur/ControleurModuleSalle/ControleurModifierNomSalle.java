package controleur.ControleurModuleSalle;

import modele.BDD.Salle;
import vue.CreationSalleDialog;
import vue.CreationSalleDialogInfos;
import vue.VueSalle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurModifierNomSalle extends JButton implements ActionListener {
    private Salle salle;

    /**
     * Créer le bouton permettant l'ouverture d'une boîte de dialogue permettant la modification des informations d'une salle
     * @param salle
     */
    public ControleurModifierNomSalle(Salle salle){
        this.salle = salle;
        this.setText("Modifier les informations de la salle");
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CreationSalleDialog dialogBox = new CreationSalleDialog(null,"Modifier les informations d'une salle",true,true,salle);
        CreationSalleDialogInfos infos = dialogBox.afficherDialog();
        JOptionPane jop = new JOptionPane();
        jop.showMessageDialog(null,infos.toString(),"Récapitulatif de la salle",JOptionPane.INFORMATION_MESSAGE);
        salle.changerInformation(infos.getNom(),infos.getHauteur(),infos.getLargeur());
    }
}
