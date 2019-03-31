package controleur;

import modele.BDD.Salle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boîte de dialogue permettant d'accéder aux différents outils du module Salle
 */
public class OutilsSalleDialog extends JDialog {
    private JLabel labeldebut,labelExplication;
    private JButton alphabetique,numerique,numeriqueReset;
    private JRadioButton haut,bas;
    private boolean commencerHaut,commencerBas;

    /**
     * Instancie la boîte de dialogue
     * @param parent
     * @param title
     * @param modal
     * @param salle
     */
    public OutilsSalleDialog(JFrame parent, String title, boolean modal, Salle salle){
        super(parent,title,modal);

        this.setSize(new Dimension(500,250));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent(salle);
    }

    private void initComponent(Salle salle){
        final Salle salleVar = salle;
        JPanel boite = new JPanel();
        boite.setLayout(new GridLayout(1,2));


        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());

        this.labelExplication = new JLabel("*Avec cette option chaque rangée débutera à 1"+"\n"+"**Avec cette option le compteur ne sera pas réinitialisé");

        //Partie seleciton haut/bas
        JPanel containerCheckBox = new JPanel();
        containerCheckBox.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.labeldebut = new JLabel("[Rangée] Commencer à partir du : ");
        this.haut = new JRadioButton("Haut");
        this.bas = new JRadioButton("Bas");
        ButtonGroup bg = new ButtonGroup();
        bg.add(this.haut);
        bg.add(this.bas);

        this.haut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(commencerHaut){
                    commencerHaut=false;
                }else{
                    commencerHaut=true;
                }
            }
        });

        this.bas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(commencerBas){
                    commencerBas=false;
                }else{
                    commencerBas=true;
                }
            }
        });



        gbc.gridy=0;
        gbc.gridx=0;
        containerCheckBox.add(this.labeldebut,gbc);
        gbc.gridy=1;
        containerCheckBox.add(this.haut,gbc);
        gbc.gridy=2;
        containerCheckBox.add(this.bas,gbc);




        //Partie boutons
        JPanel containerButton = new JPanel();
        containerButton.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        this.alphabetique = new JButton("Nommage alphabétique rangées");
        this.alphabetique.setPreferredSize(new Dimension(200,20));
        this.alphabetique.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salleVar.renommerLigneAlpha(commencerBas);
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,"Les rangées ont été renommé alphabétiquement","Outils Salle",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,10,0);
        gbc.anchor = GridBagConstraints.LINE_START;
        containerButton.add(this.alphabetique,gbc);

        this.numerique = new JButton("Nommage continu numérique*");
        this.numerique.setPreferredSize(new Dimension(200,20));
        this.numerique.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salleVar.renommerColonneNumerique(false);
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,"Les colonnes ont été renommé","Outils Salle",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridy=1;
        gbc.gridx=0;
        containerButton.add(this.numerique,gbc);

        this.numeriqueReset = new JButton("Nommage discontinu numérique**");
        this.numeriqueReset.setPreferredSize(new Dimension(200,20));
        this.numeriqueReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salleVar.renommerColonneNumerique(true);
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,"Les colonnes ont été renommé","Outils Salle",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.gridy =2;
        gbc.gridx =0;
        containerButton.add(this.numeriqueReset,gbc);

        boite.add(containerButton);
        boite.add(containerCheckBox);
        container.add(boite,BorderLayout.CENTER);
        container.add(labelExplication, BorderLayout.SOUTH);
        this.getContentPane().add(container);
    }


    /**
     * Affiche la boîte de dialogue
     */
    public void afficherDialog(){
        this.setVisible(true);
    }
}


