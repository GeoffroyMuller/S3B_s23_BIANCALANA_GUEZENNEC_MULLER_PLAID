package controleur;

import javax.swing.*;
import java.awt.*;

public class ControleurBoutonsPartieSalle extends JPanel {
    private JButton boutonAjouterSalle;
    private JButton boutonSupprimerSalle;

    public ControleurBoutonsPartieSalle(){
        this.boutonAjouterSalle = new JButton("Ajouter");
        this.boutonSupprimerSalle = new JButton("Supprimer");
        //Mise en place des tailles minimales
        this.boutonAjouterSalle.setPreferredSize(new Dimension(90,30));
        this.boutonSupprimerSalle.setPreferredSize(new Dimension(90,30));
        this.boutonAjouterSalle.setMinimumSize(new Dimension(this.boutonAjouterSalle.getWidth(),this.boutonAjouterSalle.getHeight()));
        this.boutonSupprimerSalle.setMinimumSize(new Dimension(this.boutonSupprimerSalle.getWidth(),this.boutonSupprimerSalle.getHeight()));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //Contraintes du bouton ajouter
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 15, 0);
        this.add(boutonAjouterSalle,gbc);

        //Contraintes du bouton supprimer
        gbc.gridx =1;
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;


        gbc.insets = new Insets(0, 0, 15, 0);
        this.add(boutonSupprimerSalle,gbc);
    }

    public JButton getBoutonAjouterSalle() {
        return boutonAjouterSalle;
    }

    public void setBoutonAjouterSalle(JButton boutonAjouterSalle) {
        this.boutonAjouterSalle = boutonAjouterSalle;
    }

    public JButton getBoutonSupprimerSalle() {
        return boutonSupprimerSalle;
    }

    public void setBoutonSupprimerSalle(JButton boutonSupprimerSalle) {
        this.boutonSupprimerSalle = boutonSupprimerSalle;
    }



}
