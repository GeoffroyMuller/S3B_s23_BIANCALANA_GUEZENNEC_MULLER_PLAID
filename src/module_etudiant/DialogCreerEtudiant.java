package module_etudiant;

import javafx.scene.control.ComboBox;
import modele.BDD.*;
import modele.CritereRechercheEtudiant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class DialogCreerEtudiant extends JDialog {
    private boolean sendData;
    private JLabel labNom,labPrenom,labCategorie,labGroupe,labPrendreEnComptePlacement;
    private JTextField textNom,textPrenom;
    private JComboBox<Categorie> comboCategorie;
    private JComboBox comboGroupe;
    private JButton valider,annuler;
    private boolean modification;
    private ArrayList<Groupe> donneeComboGroupe;
    private JCheckBox checkBoxHandicap,checkBoxTierTemps;
    private JRadioButton oui,non;
    private CritereRechercheEtudiant cre;

    private JScrollPane containerGroupesCombo;
    private JButton ajouterUnGroupe,enleverUnGroupe;
    private ArrayList<JComboBox> listeComboGroupe;
    private JPanel contenant;
    private GridBagConstraints gbcScroll;
    private HashMap<JButton,JComboBox> liaisonEnleverGroupeCombo;
    private ArrayList<Groupe> groupeARetireDeEtudiant;


    public DialogCreerEtudiant(JFrame parent, String title, boolean modal,boolean modification){
        super(parent,title,modal);
        this.modification = modification;
        this.liaisonEnleverGroupeCombo = new HashMap<JButton, JComboBox>();
        this.groupeARetireDeEtudiant = new ArrayList<Groupe>();

        this.setSize(new Dimension(550,550));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();
    }

    public DialogCreerEtudiant(JFrame parent, String title, boolean modal, boolean modification, CritereRechercheEtudiant cre){
        super(parent,title,modal);
        this.modification = modification;
        this.cre = cre;
        this.liaisonEnleverGroupeCombo = new HashMap<JButton, JComboBox>();
        this.groupeARetireDeEtudiant = new ArrayList<Groupe>();

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

        gbc.gridy=3;
        gbc.gridx=0;
        this.labGroupe = new JLabel("Groupe : ");
        if(modification){
            this.labGroupe.setText("Groupe(s) : ");
        }
        containerInfo.add(this.labGroupe,gbc);

        contenant = new JPanel();
        contenant.setLayout(new GridBagLayout());
        gbcScroll = new GridBagConstraints();
        gbcScroll.gridx = 0;
        gbcScroll.gridy = 0;
        gbcScroll.insets = new Insets(0,10,10,10);
        this.containerGroupesCombo = new JScrollPane(contenant);
        this.listeComboGroupe = new ArrayList<JComboBox>();
        this.containerGroupesCombo.setPreferredSize(new Dimension(230,100));

        if(modification){
                ArrayList<Groupe> groupesDeEtudiant = (Etudiant.findById(cre.getId()).recupererGroupes());
                boolean premierGroupe = true;
                for(Groupe groupe : groupesDeEtudiant){
                    JComboBox comboBox = new JComboBox();
                    setupComboBox(comboBox);
                    comboBox.setPreferredSize(new Dimension(VueModuleEtudiant.COMBOBOX_WIDTH+15,VueModuleEtudiant.COMBOBOX_HEIGHT));
                    comboBox.setSelectedItem(groupe);
                    listeComboGroupe.add(comboBox);
                    contenant.add(comboBox,gbcScroll);
                    if(!premierGroupe){
                        JButton bouton = new JButton("-");
                        bouton = ajouterActionListenerSuprressionDuGroupe(bouton);
                        gbcScroll.gridx=1;
                        contenant.add(bouton,gbcScroll);
                        liaisonEnleverGroupeCombo.put(bouton,comboBox);
                        gbcScroll.gridx=0;
                        gbcScroll.gridy++;
                    }

                }
        }else{
            gbc.gridx=1;
            ArrayList<Groupe> groupes = this.donneeComboGroupe;
            this.comboGroupe = new JComboBox(new ComboBoxModel());
            setupComboBox(this.comboGroupe);
            this.comboGroupe.setPreferredSize(new Dimension(VueModuleEtudiant.COMBOBOX_WIDTH+15,VueModuleEtudiant.COMBOBOX_HEIGHT));
            contenant.add(comboGroupe,gbcScroll);

            this.listeComboGroupe.add(this.comboGroupe);
        }
        //Ajout du JScrollPan
        gbc.gridx=1;
        gbc.gridy=3;
        containerInfo.add(this.containerGroupesCombo,gbc);

        //Ajout d'un bouton permettant l'ajout d'un groupe via une combobox
        gbc.gridx=4;
        this.ajouterUnGroupe = new JButton("+");
        containerInfo.add(this.ajouterUnGroupe,gbc);
        this.ajouterUnGroupe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gbcScroll.gridy++;
                JComboBox nouvelleComboBox = new JComboBox();
                setupComboBox(nouvelleComboBox);
                nouvelleComboBox.setPreferredSize(new Dimension(VueModuleEtudiant.COMBOBOX_WIDTH+15,VueModuleEtudiant.COMBOBOX_HEIGHT));
                listeComboGroupe.add(nouvelleComboBox);
                contenant.add(nouvelleComboBox,gbcScroll);

                JButton bouton = new JButton("-");
                bouton = ajouterActionListenerSuprressionDuGroupe(bouton);
                gbcScroll.gridx=1;
                contenant.add(bouton,gbcScroll);
                liaisonEnleverGroupeCombo.put(bouton,nouvelleComboBox);
                gbcScroll.gridx=0;


                containerGroupesCombo.setViewportView(contenant);

            }
        });


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
                Etudiant etudiant=null;
                if(modification){
                    etudiant = Etudiant.findById(cre.getId());

                    //Si modification alors on enleve les potentiels groupes
                    if(modification){
                        for(Groupe groupe : groupeARetireDeEtudiant){
                            groupe.retirerEtudiantDuGroupe(cre.getId());
                        }
                    }
                }else{
                    //Création de l'étudiant
                    etudiant = new Etudiant(textNom.getText(),textPrenom.getText());
                    etudiant.save();
                }

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

                //Ajout des groupes
                for(JComboBox combo : listeComboGroupe){
                    Groupe groupe = (Groupe)combo.getSelectedItem();
                    groupe.ajouterEtudiant(etudiant);
                }
                etudiant.save();

                JOptionPane jop = new JOptionPane();
                if(modification){
                    jop.showMessageDialog(null,"Etudiant.e modifié.e avec succés ! ","Réussite !",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    jop.showMessageDialog(null,"Etudiant.e crée avec succés ! ","Réussite !",JOptionPane.INFORMATION_MESSAGE);
                }

                setVisible(false);
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

    private void setupComboBox(JComboBox comboBox){
        comboBox.addItem("Selectionner un groupe");
        for(Categorie categorie : Categorie.getlistCategorie()){
            comboBox.addItem("----"+categorie.getNom());
            for(Groupe groupe : categorie.getListGroupe()){
                comboBox.addItem(groupe);
            }
        }
        comboBox.setSelectedIndex(0);
    }

    private JButton ajouterActionListenerSuprressionDuGroupe(JButton bouton){
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JComboBox combo = liaisonEnleverGroupeCombo.get(bouton);
               if(combo.getSelectedItem() instanceof Groupe){
                   groupeARetireDeEtudiant.add((Groupe)combo.getSelectedItem());
               }
               listeComboGroupe.remove(combo);
               refreshScrollGroupe();
            }
        });
        return bouton;
    }

    private void refreshScrollGroupe(){
        contenant.removeAll();
        gbcScroll.gridx = 0;
        gbcScroll.gridy = 0;
        for(JComboBox combo : listeComboGroupe){
            contenant.add(combo,gbcScroll);
            JButton button = new JButton("-");
            button = ajouterActionListenerSuprressionDuGroupe(button);
            gbcScroll.gridx=1;
            contenant.add(button,gbcScroll);
            gbcScroll.gridy++;
            gbcScroll.gridx=0;
            liaisonEnleverGroupeCombo.clear();
            liaisonEnleverGroupeCombo.put(button,combo);
        }
        containerGroupesCombo.setViewportView(contenant);
    }
}
