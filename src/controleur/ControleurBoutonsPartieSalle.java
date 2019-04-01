package controleur;

import modele.BDD.Salle;
import module_etudiant.DialogTraitement;
import vue.CreationSalleDialog;
import vue.CreationSalleDialogInfos;
import vue.VueSalle;
import vue_Examen.VueExamen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controleur du module Salle permettant l'ajout et la supression de salle
 */
public class ControleurBoutonsPartieSalle extends JPanel {
    private JButton boutonAjouterSalle;
    private JButton boutonSupprimerSalle;
    public static Salle modele;

    /**
     * Cr�er le controleur avec les boutons n�cessaires
     * @param modele
     */
    public ControleurBoutonsPartieSalle(Salle modele){
        this.boutonAjouterSalle = new JButton("Ajouter");
        this.boutonSupprimerSalle = new JButton("Supprimer");
        this.modele = modele;

        //Ajout des actions listener
        this.boutonAjouterSalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Cr�ation d'une fenetre contextuelle
                CreationSalleDialog dialogBox = new CreationSalleDialog(null,"Cr�ation d'une salle",true,false,null);
                CreationSalleDialogInfos infos = dialogBox.afficherDialog();
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,infos.toString(),"R�capitulatif de la salle",JOptionPane.INFORMATION_MESSAGE);
                VueSalle.partieAUpdate = VueSalle.UPDATE_CREATION_SALLE;
                ControleurBoutonsPartieSalle.modele.changerInformation(infos.getNom(),infos.getHauteur(),infos.getLargeur());
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
                        ControleurBoutonsPartieSalle.modele.save();
                        traitement.close();
                    }
                });
                tr.start();
                trLoader.start();
                VueExamen.rechargerlisteurSalle();
               // traitement.run();
                //ControleurBoutonsPartieSalle.modele.save();
                //traitement.close();

            }
        });

        this.boutonSupprimerSalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] choix = {"Oui","Non"};
                JOptionPane confirmerSuppression = new JOptionPane();
                int rang = confirmerSuppression.showOptionDialog(null,"�tes vous du de vouloir supprimer la salle : "+ VueSalle.salleSelectionne +" ? Cette action est irr�versible",
                        "Confirmer la suppression",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,choix,choix[1]);

                if(choix[rang].equals("Oui")){
                    Salle salle = Salle.findByNom(VueSalle.salleSelectionne.getNom());
                    salle.delete();
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null,"La salle � bien �t� supprimer !","Supression effectu�e avec succ�s",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null,"La suppression de la salle � �t� annul�e !","Supression annul�e",JOptionPane.INFORMATION_MESSAGE);


                }
                VueExamen.rechargerlisteurSalle();

            }
        });

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
