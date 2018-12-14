package controleur;

import javax.swing.*;
import java.awt.*;

public class ControleurBoutonsPartieSalle extends JPanel {
    private JButton boutonAjouterSalle;
    private JButton boutonSupprimerSalle;

    public ControleurBoutonsPartieSalle(){
        this.boutonAjouterSalle = new JButton("Ajouter");
        this.boutonSupprimerSalle = new JButton("Supprimer");
      /*  this.boutonAjouterSalle.setPreferredSize(new Dimension(90,35));
        this.boutonAjouterSalle.setPreferredSize(new Dimension(90,35));
        this.boutonSupprimerSalle.setPreferredSize(new Dimension(90,35));*/
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        //Contraintes du bouton ajouter
        gbc.gridx = gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 15, 10);
        this.add(boutonAjouterSalle,gbc);

        //Contraintes du bouton supprimer
        gbc.gridx =1;
        gbc.insets = new Insets(5, 0, 15, 120);
        this.add(boutonSupprimerSalle,gbc);
    }


}
