package controleur;

import modele.BDD.Place;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boîte de dialogue du module Salle pour modifier le nom d'une place
 */
public class ModificationNomPlaceDialog extends JDialog {
    private boolean sendData;
    private ModificationNomPlaceDialogInfo dialogInfo;
    private JLabel labelNomPlace,labelOldPlaceName,oldColonne,oldRangee,labelcolonne,labelrangee;
    private JTextField nomPlace,nomColonne,nomRangee;
    private JButton valider,annuler;

    /**
     * Instancie une boîte de dialogue permettant le changement de nom d'une place
     * @param parent
     * @param title
     * @param modal
     * @param place
     *      Place que l'on souhaite modifier
     */
    public ModificationNomPlaceDialog(JFrame parent, String title, boolean modal, Place place){
        super(parent,title,modal);

        this.setSize(new Dimension(300,130));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent(place);
    }

    private void initComponent(Place place){
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());

        //Création des contraintes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0,0,5,0);


        //JLabel
        this.labelOldPlaceName = new JLabel("Nom actuel :  "+place.getNomRangee()+""+place.getNomColonne());
        container.add(this.labelOldPlaceName,gbc);

        this.labelcolonne = new JLabel("Nouveau nom colonne : ");
        gbc.gridx=0;
        gbc.gridy=1;
        container.add(this.labelcolonne,gbc);

        this.nomColonne = new JTextField();
        this.nomColonne.setPreferredSize(new Dimension(100,20));
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth = 3;
        container.add(this.nomColonne,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        this.labelrangee = new JLabel("Nouveau nom rangee : ");
        container.add(this.labelrangee,gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        this.nomRangee = new JTextField();
        this.nomRangee.setPreferredSize(new Dimension(100,20));
        container.add(this.nomRangee,gbc);




        //JButton
        this.valider = new JButton("Valider");
        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogInfo = new ModificationNomPlaceDialogInfo(nomColonne.getText(),nomRangee.getText());
                setVisible(false);
            }
        });
        this.annuler = new JButton("Annuler");
        this.annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });


        JPanel boutons = new JPanel();
        boutons.setLayout(new GridLayout(1,2));
        boutons.add(this.valider);
        boutons.add(this.annuler);
        this.getContentPane().add(container,BorderLayout.CENTER);
        this.getContentPane().add(boutons,BorderLayout.SOUTH);
    }

    /**
     * Permet d'afficher la boîte de dialogue
     * @return
     */
    public ModificationNomPlaceDialogInfo afficherDialog(){
        this.sendData =false;
        this.setVisible(true);
        return this.dialogInfo;
    }
}
