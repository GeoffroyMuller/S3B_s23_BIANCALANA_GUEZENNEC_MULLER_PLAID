package controleur.ControleurModuleSalle;

import vue.ComposantVueSalle.Indicateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurRadioBoutons extends JPanel implements ActionListener {

    public ControleurRadioBoutons(){
        this.setLayout(new GridBagLayout());

        JRadioButton allee = new JRadioButton();
        allee.setName("allee");

        JRadioButton place = new JRadioButton();
        place.setName("place");

        JRadioButton placeInutilisable = new JRadioButton();
        placeInutilisable.setName("placeInutillisable");

        ButtonGroup bg = new ButtonGroup();
        bg.add(allee);bg.add(place);bg.add(placeInutilisable);

        //Contraintes allee
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(65,0,20,0);

        this.add(allee,gbc);

        //Contrainte place
        gbc.gridy = 1;
        gbc.insets = new Insets(0,0,18,0);
        this.add(place,gbc);

        //Contraintes placeInutilisable
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weighty = 1;
        this.add(placeInutilisable,gbc);


        //Contraintes Indicateur
       /* gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(new Indicateur(), gbc);*/


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
