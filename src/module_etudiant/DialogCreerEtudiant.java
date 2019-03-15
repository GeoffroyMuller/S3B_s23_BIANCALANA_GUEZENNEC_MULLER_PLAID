package module_etudiant;

import javafx.scene.control.ComboBox;
import modele.BDD.*;
import modele.CritereRechercheEtudiant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DialogCreerEtudiant extends JDialog {
    private boolean sendData;
    private JLabel labNom,labPrenom,labCategorie,labGroupe,labPrendreEnComptePlacement;
    private JTextField textNom,textPrenom;
    private JComboBox<Categorie> comboCategorie;
    private JComboBox<Groupe> comboGroupe;
    private JButton valider,annuler;
    private boolean modification;
    private ArrayList<Groupe> donneeComboGroupe;
    private JCheckBox checkBoxHandicap,checkBoxTierTemps;
    private JRadioButton oui,non;
    private CritereRechercheEtudiant cre;

    public DialogCreerEtudiant(JFrame parent, String title, boolean modal,boolean modification){
        super(parent,title,modal);
        this.modification = modification;
        this.setSize(new Dimension(550,350));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();
    }

    public DialogCreerEtudiant(JFrame parent, String title, boolean modal, boolean modification, CritereRechercheEtudiant cre){
        super(parent,title,modal);
        this.modification = modification;
        this.cre = cre;
        this.setSize(new Dimension(850,350));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();
    }

    private void initComponent(){
        JPanel containerInfo = new JPanel();
        containerInfo.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets = new Insets(5,0,10,5);

        this.labNom = new JLabel("Nom de l'étudiant : ");
        containerInfo.add(this.labNom,gbc);

        gbc.gridx=1;
        this.textNom = new JTextField();
        this.textNom.setPreferredSize(new Dimension(VueModuleEtudiant.TEXTFIELD_WIDTH,VueModuleEtudiant.TEXTFIELD_HEIGHT));
        if(modification){
            this.textNom.setText(cre.getNom());
        }
        containerInfo.add(this.textNom,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        this.labPrenom = new JLabel("Prénom : ");
        containerInfo.add(this.labPrenom,gbc);

        gbc.gridx=1;
        this.textPrenom = new JTextField();
        this.textPrenom.setPreferredSize(new Dimension(VueModuleEtudiant.TEXTFIELD_WIDTH,VueModuleEtudiant.TEXTFIELD_HEIGHT));
        if(modification){
            this.textPrenom.setText(cre.getPrenom());
        }
        containerInfo.add(this.textPrenom,gbc);

        if(!modification){
            gbc.gridy=2;
            gbc.gridx=0;
            this.labCategorie = new JLabel("Catégorie : ");
            containerInfo.add(this.labCategorie,gbc);

            gbc.gridx=1;
            ArrayList<Categorie> categories = Categorie.getlistCategorie();
            this.comboCategorie = new JComboBox<Categorie>((Categorie[])categories.toArray(new Categorie[categories.size()]));
            this.comboCategorie.setSelectedIndex(0);
            this.donneeComboGroupe = this.comboCategorie.getItemAt(0).getListGroupe();
            this.comboCategorie.setPreferredSize(new Dimension(VueModuleEtudiant.COMBOBOX_WIDTH,VueModuleEtudiant.COMBOBOX_HEIGHT));

            this.comboCategorie.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    donneeComboGroupe = ((Categorie)comboCategorie.getSelectedItem()).getListGroupe();
                    comboGroupe.removeAllItems();
                    comboGroupe = new JComboBox<Groupe>((Groupe[])donneeComboGroupe.toArray());
                    comboGroupe.setSelectedIndex(0);
                }
            });

            containerInfo.add(this.comboCategorie,gbc);
        }


        gbc.gridy=3;
        gbc.gridx=0;
        this.labGroupe = new JLabel("Groupe : ");
        if(modification){
            this.labGroupe.setText("Groupe(s) : ");
        }
        containerInfo.add(this.labGroupe,gbc);



        //gridx = 1 gridy=3
        if(modification){
            ArrayList<Groupe> groupesEtu = (Etudiant.findById(cre.getId()).recupererGroupes());
            ArrayList<JComboBox<Groupe>> groupesCombo = new ArrayList<JComboBox<Groupe>>();
            ArrayList<Groupe> toutLesGroupes = Groupe.listGroupe();
            gbc.gridx=1;
            gbc.gridy=3;
            System.out.println("Nombre de groupe de l'etu : "+groupesEtu.size());
            for(Groupe groupe : groupesEtu){
                JComboBox<Groupe> combo = new JComboBox<Groupe>((Groupe[])toutLesGroupes.toArray(new Groupe[toutLesGroupes.size()]));
                combo.setSelectedItem(groupe);
                combo.setPreferredSize(new Dimension(VueModuleEtudiant.COMBOBOX_WIDTH,VueModuleEtudiant.COMBOBOX_HEIGHT));
                /*
                TO DO ACTION LISTENER
                 */
                containerInfo.add(combo,gbc);
                if(gbc.gridx==2){
                    gbc.gridy++;
                    gbc.gridx=1;
                }else{
                    gbc.gridx++;
                }
            }
        }else{
            gbc.gridx=1;
            ArrayList<Groupe> groupes = this.donneeComboGroupe;
            this.comboGroupe = new JComboBox<Groupe>((Groupe[])groupes.toArray(new Groupe[groupes.size()]));
            comboGroupe.setSelectedIndex(0);
            this.comboGroupe.setPreferredSize(new Dimension(VueModuleEtudiant.COMBOBOX_WIDTH,VueModuleEtudiant.COMBOBOX_HEIGHT));
            gbc.gridx=1;
            gbc.gridy=3;
            containerInfo.add(comboGroupe,gbc);
        }


        gbc.gridy=4;
        gbc.gridx=0;
        this.checkBoxHandicap = new JCheckBox("En situation de handicap");
        if(modification){
            if(cre.isHandicap()){
                this.checkBoxHandicap.setSelected(true);
            }
        }
        containerInfo.add(this.checkBoxHandicap,gbc);

        gbc.gridx=1;
        this.checkBoxTierTemps = new JCheckBox("Tiers-Temps");
        if(modification){
            if(cre.isTierTemps()){
                this.checkBoxTierTemps.setSelected(true);
            }
        }
        containerInfo.add(this.checkBoxTierTemps,gbc);

        gbc.gridy=5;
        gbc.gridx=0;
        this.labPrendreEnComptePlacement= new JLabel("Prendre en compte dans le placement : ");
        containerInfo.add(this.labPrendreEnComptePlacement,gbc);

        gbc.gridx = 1;
        this.oui = new JRadioButton("Oui");

        containerInfo.add(this.oui,gbc);

        gbc.gridx=2;
        this.non = new JRadioButton("Non");
        if(modification){
            if(cre.isPriseEncompte()){
                this.oui.setSelected(true);
            }else{
                this.non.setSelected(true);
            }
        }
        containerInfo.add(this.non,gbc);

        ButtonGroup bg = new ButtonGroup();
        bg.add(this.oui);
        bg.add(this.non);




        JPanel boutons = new JPanel();
        boutons.setLayout(new GridBagLayout());

        gbc =new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy=0;
        gbc.insets = new Insets(10,5,10,0);

        this.valider = new JButton("Valider");
        boutons.add(this.valider,gbc);

        this.annuler = new JButton("Annuler");
        gbc.gridx=1;
        boutons.add(this.annuler,gbc);


        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Création de l'étudiant
                Etudiant etudiant = new Etudiant(textNom.getText(),textPrenom.getText());
                etudiant.save();
                    //Ajout des contraintes
                ArrayList<Particularite> particularites = new ArrayList<Particularite>();
                if(checkBoxHandicap.isSelected()){
                    if(oui.isSelected()){
                        particularites.add(Particularite.findByNom("Situation de handicap (Prise en compte)"));
                    }else if(non.isSelected()){
                        particularites.add(Particularite.findByNom("Situation de handicap (Non pris en compte)"));
                    }
                }

                if(checkBoxTierTemps.isSelected()){
                    if(oui.isSelected()){
                        particularites.add(Particularite.findByNom("Tiers-Temps (Prendre en compte)"));
                    }else if(non.isSelected()){
                        particularites.add(Particularite.findByNom("Tiers-Temps (Non pris en compte)"));
                    }
                }
                etudiant.ajouterParticularite(particularites);

                //Ajout à un groupe
                Groupe groupe = (Groupe)comboGroupe.getSelectedItem();
                groupe.ajouterEtudiant(etudiant);
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,"Etudiant crée avec succés ! ","Réussite !",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        this.annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });


        this.getContentPane().add(containerInfo, BorderLayout.CENTER);
        this.getContentPane().add(boutons,BorderLayout.SOUTH);



    }

    public void afficherDialog(){
        this.sendData =false;
        this.setVisible(true);
    }
}
