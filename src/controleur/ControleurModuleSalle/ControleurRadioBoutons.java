package controleur.ControleurModuleSalle;

import vue.ComposantVueSalle.Indicateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurRadioBoutons extends JPanel implements ActionListener {

    public static String placeSelectionnee="place";

    public ControleurRadioBoutons(){
        this.setLayout(new GridBagLayout());

        ActionListener choixBouton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControleurRadioBoutons.placeSelectionnee = ((JRadioButton)e.getSource()).getName();
            }
        };

        JRadioButton allee = new JRadioButton();
        allee.setName("allee");
        allee.addActionListener(choixBouton);

        JRadioButton place = new JRadioButton();
        place.setName("place");
        place.addActionListener(choixBouton);


        JRadioButton placeInutilisable = new JRadioButton();
        placeInutilisable.setName("placeInutillisable");
        placeInutilisable.addActionListener(choixBouton);

        switch(ControleurRadioBoutons.placeSelectionnee){
            case "allee":
                allee.setSelected(true);
                break;
            case "place":
                place.setSelected(true);
                break;
            case "placeInutilisable":
                placeInutilisable.setSelected(true);
                break;
        }


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
