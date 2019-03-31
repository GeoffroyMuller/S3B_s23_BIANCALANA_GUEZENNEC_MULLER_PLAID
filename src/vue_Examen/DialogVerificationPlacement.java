package vue_Examen;

import controleur.ControleurModuleSalle.ControleurCaseSalle;
import modele.BDD.Etudiant;
import modele.BDD.Place;
import modele.BDD.Salle;
import modele.CritereRechercheEtudiant;
import modele.Examen;
import modele.GestionFichiersExcel.ExportEtudiant;
import modele.InformationsPlacementEtudiant;
import modele.ProprietesCaseSalle;
import vue.VueSalle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class DialogVerificationPlacement extends JDialog {
    private boolean sendData;
    private Examen modele;
    private JLabel labelTitre;
    private ArrayList<JLabel> labelNomSalles;
    private JScrollPane scrollTableau;
    private ArrayList<JTable> placement;
    private JButton valider,annuler;

    private JTabbedPane onglets,salles;
    private JTextField rechercherEtudiantNom,rechercherEtudiantPrenom;
    private JLabel labelGroupe,labelRechercheEtudiantNom,labelRechercheEtudiantPrenom,labelRechercheEtudiantGroupe,labelNom,labelPrenom,labelPlace,labelNomSalle;
    private JButton rechercher,effacerRecherche;
    private JComboBox<String> groupes;
    private HashMap<Salle, ProprietesCaseSalle> proprietesSalle;
    private HashMap<String, Integer> indexVersSalle;
    private ArrayList<JScrollPane> contenantSalle;


    private ArrayList<ControleurCaseSalle> etudiantsSelectionne;
    private JButton reinitialiseSelection;
    private JButton swapPlace;



    public DialogVerificationPlacement(JFrame parent, String title, boolean modal,Examen examen){
        super(parent,title,modal);
        this.proprietesSalle = new HashMap<Salle,ProprietesCaseSalle>();
        this.indexVersSalle = new HashMap<String, Integer>();
        this.modele = examen;
        this.etudiantsSelectionne = new ArrayList<ControleurCaseSalle>();
        this.setSize(new Dimension(1300,800));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();

    }

    private void initComponent(){

        //PARTIE PREVISUALISATION SOUS FORME DE FICHE

        JPanel contenant = new JPanel();
        contenant.setLayout(new BorderLayout());

        this.labelTitre = new JLabel("Prévisualisation du placement");

        contenant.add(this.labelTitre,BorderLayout.NORTH);
        this.labelNomSalles = new ArrayList<JLabel>();
        //Création de JLabel pour chaque salle
        for(Salle salleExamen : this.modele.getSalles()) {
            String nomSalle = salleExamen.getNom();
            this.labelNomSalles.add(new JLabel("Salle : "+nomSalle));
        }


        //Recuperation de la prévisualisation
        this.placement = this.modele.genererPrevisualisationFiche();

        //Disposition des éléments
        JPanel contenantJTable = new JPanel();
        contenantJTable.setLayout(new GridLayout(this.modele.getSalles().size(),1));

        //int index = this.labelNomSalles.size()-1;
        int index = 0;
        System.out.println("INDEX : "+index);
        for(JTable tableau : this.placement) {
            JPanel tableauAvecJLabel = new JPanel();
            tableauAvecJLabel.setLayout(new BorderLayout());
            tableauAvecJLabel.add(this.labelNomSalles.get(index),BorderLayout.NORTH);
            tableauAvecJLabel.add(tableau,BorderLayout.CENTER);
            contenantJTable.add(tableauAvecJLabel);
            index++;
        }

        this.scrollTableau = new JScrollPane(contenantJTable);
        this.scrollTableau.setPreferredSize(new Dimension(500,800));

        contenant.add(this.scrollTableau,BorderLayout.CENTER);

        //Ajout des boutons valider et annuler
        this.valider = new JButton("Valider");
        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportEtudiant exportEtu = new ExportEtudiant();
                exportEtu.exporterPlacement(modele.getPlacement(),modele);
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
        boutons.add(this.valider);
        boutons.add(this.annuler);


        JPanel vueFiche = new JPanel();
        vueFiche.setLayout(new BorderLayout());
        vueFiche.add(contenant,BorderLayout.CENTER);




        //PREVISUALISATION INTERACTIVE

        //TopBar recherche d'étudiant
        this.rechercherEtudiantNom = new JTextField();
        this.effacerRecherche = new JButton("Effacer Recherche");
        this.rechercherEtudiantNom.setPreferredSize(new Dimension(100,20));
        this.rechercherEtudiantPrenom = new JTextField();
        this.rechercherEtudiantPrenom.setPreferredSize(new Dimension(100,20));
        this.labelRechercheEtudiantNom =new JLabel("Nom étudiant : ");
        this.labelRechercheEtudiantPrenom =new JLabel("Prénom étudiant : ");
        this.labelRechercheEtudiantGroupe =new JLabel("Groupe étudiant : ");
        String[] groupesParticipant = this.modele.groupeParticipant();
        this.groupes = new JComboBox(groupesParticipant);
        this.rechercher = new JButton("Rechercher");
        this.groupes.setPreferredSize(new Dimension(80,20));

        JPanel topbar = new JPanel();
        topbar.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        this.labelNom = new JLabel("Nom : ");
        gbc.insets=new Insets(0,5,0,0);

        topbar.add(this.labelNom,gbc);
        gbc.gridx = 1;
        topbar.add(this.rechercherEtudiantNom,gbc);
        gbc.gridx=2;

        this.labelPrenom = new JLabel("Prénom : ");
        topbar.add(this.labelPrenom,gbc);
        gbc.gridx=3;
        topbar.add(this.rechercherEtudiantPrenom,gbc);
        gbc.gridx=4;
        topbar.add(this.groupes,gbc);
        gbc.gridx=5;
        topbar.add(this.rechercher,gbc);
        gbc.gridx=6;
        topbar.add(this.effacerRecherche,gbc);

        this.effacerRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retablirVisualisation();
                rechercherEtudiantPrenom.setText("");
                rechercherEtudiantNom.setText("");

            }
        });


        //Interface de visualisation de la salle avec le placement
        this.contenantSalle = new ArrayList<JScrollPane>();
        ArrayList<JPanel> sallesConstruites = new ArrayList<JPanel>();
        //Construction des salles
        for(int i = 0; i < this.modele.getSalles().size();i++){
            Salle salle = this.modele.getSalles().get(i);
            this.proprietesSalle.put(salle,VueSalle.construireSalleDialogBox(salle,this,this.modele));
            sallesConstruites.add(this.proprietesSalle.get(salle).getSalleConstruite());
            JScrollPane scrollPane = new JScrollPane(sallesConstruites.get(sallesConstruites.indexOf(this.proprietesSalle.get(salle).getSalleConstruite())));
            this.contenantSalle.add(scrollPane);
            this.indexVersSalle.put(salle.getNom(),i);
        }
        //Construction panneau latéral droit (affichage des informations de la case selectionne)
        this.labelNom = new JLabel("Nom : ");
        this.labelPrenom =new JLabel("Prénom : ");
        this.labelPlace = new JLabel("Place : ");
        this.labelGroupe = new JLabel("Groupe : ");

        this.reinitialiseSelection = new JButton("Reinitialiser la sélection");
        this.swapPlace = new JButton("Echanger de place");

        JPanel panneauDroit = new JPanel();
        panneauDroit.setLayout(new GridBagLayout());
        panneauDroit.setPreferredSize(new Dimension(150,600));
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets = new Insets(0,0,10,0);
        panneauDroit.add(this.labelNom,gbc);
        gbc.gridy=1;
        panneauDroit.add(this.labelPrenom,gbc);
        gbc.gridy=2;
        panneauDroit.add(this.labelPlace,gbc);
        gbc.gridy=3;
        panneauDroit.add(this.labelGroupe,gbc);

        gbc.gridy=4;
        panneauDroit.add(this.reinitialiseSelection,gbc);
        gbc.gridx=1;
        panneauDroit.add(this.swapPlace,gbc);

        this.reinitialiseSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ControleurCaseSalle etu : etudiantsSelectionne){
                    etu.setCouleurCase(etu.getCouleurCaseBase());
                }
                etudiantsSelectionne = new ArrayList<ControleurCaseSalle>();
            }
        });

        this.swapPlace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(etudiantsSelectionne.size() != 2){
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null,"Deux étudiants doivent être sélectionnés pour pouvoir échanger leurs place.","Erreur",JOptionPane.ERROR_MESSAGE);
                }else{
                    Etudiant etudiant1 = etudiantsSelectionne.get(0).getEtudiant();
                    Etudiant etudiant2 = etudiantsSelectionne.get(1).getEtudiant();
                    modele.echangerPlaceEtudiants(etudiant1,etudiant2);
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null,"Les étudiants : "+etudiant1.getNom()+" "+etudiant1.getPrenom()+" et "+etudiant2.getNom()+" "+etudiant2.getPrenom()+" ont bien échangé leur place !","Succés",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Ajouts des vues salles dans les onglets
        this.salles = new JTabbedPane();
        for(int i = 0; i < this.modele.getSalles().size();i++){
            this.salles.addTab(this.modele.getSalles().get(i).getNom(), this.contenantSalle.get(i));
        }


        this.rechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Rechercher etudiant
                CritereRechercheEtudiant cre = new CritereRechercheEtudiant(rechercherEtudiantNom.getText(),rechercherEtudiantPrenom.getText(),groupes.getItemAt(groupes.getSelectedIndex()));
                InformationsPlacementEtudiant informationsPlacement = modele.trouverPlaceEtudiant(cre);
                //Assombrir toutes les cases sauf celle de l'étudiant
                Salle salleActuelle = informationsPlacement.getSalle();
                ArrayList<ControleurCaseSalle> casesSalle = proprietesSalle.get(salleActuelle).getListeControleurs();

                if(rechercherEtudiantNom.getText().isEmpty() || rechercherEtudiantPrenom.getText().isEmpty()){
                    for(ControleurCaseSalle controleur : casesSalle){
                        controleur.switchColor(controleur.getCouleurCaseBase());
                    }
                }else{
                    for(ControleurCaseSalle controleur : casesSalle){
                        if((controleur.getI() == informationsPlacement.getPlace().getI()) &&
                                controleur.getJ() == informationsPlacement.getPlace().getJ()){
                            controleur.setCouleurCase(new Color(0x019600));
                        }else{
                            controleur.switchColor(controleur.getCouleurCaseBase().darker());
                        }
                    }
                    //Mettre la selection sur la case
                    modifierInformationEtudiant(cre.getPrenom(),cre.getNom(),informationsPlacement.getPlace(),cre.getGroupe());
                    salles.setSelectedIndex(indexVersSalle.get(informationsPlacement.getSalle().getNom()));
                }

            }

        });

        this.rechercherEtudiantPrenom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField jtf = (JTextField)e.getSource();
                if(jtf.getText().isEmpty()) {
                    retablirVisualisation();
                }
            }
        });
        this.rechercherEtudiantNom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField jtf = (JTextField)e.getSource();
                if(jtf.getText().isEmpty()) {
                    retablirVisualisation();
                }
            }
        });
        JPanel vueInteractive = new JPanel();
        vueInteractive.setLayout(new BorderLayout());
        vueInteractive.add(topbar, BorderLayout.NORTH);
        vueInteractive.add(panneauDroit, BorderLayout.EAST);
        vueInteractive.add(this.salles, BorderLayout.CENTER);

        this.onglets = new JTabbedPane();
        this.onglets.addTab("Vue Fiche",vueFiche);
        this.onglets.addTab("Vue Interactive",vueInteractive);


        this.getContentPane().add(this.onglets, BorderLayout.CENTER);
        this.getContentPane().add(boutons,BorderLayout.SOUTH);



    }

    public void afficherDialog(){
        this.sendData =false;
        this.setVisible(true);

    }

    private void retablirVisualisation(){
            CritereRechercheEtudiant cre = new CritereRechercheEtudiant(rechercherEtudiantNom.getText(),rechercherEtudiantPrenom.getText(),groupes.getItemAt(groupes.getSelectedIndex()));
            InformationsPlacementEtudiant informationsPlacement = modele.trouverPlaceEtudiant(cre);
            Salle salleActuelle = informationsPlacement.getSalle();
            ArrayList<ControleurCaseSalle> casesSalle = proprietesSalle.get(salleActuelle).getListeControleurs();
            for(ControleurCaseSalle controleur : casesSalle){
                controleur.switchColor(controleur.getCouleurCaseBase());
            }
    }

    /**
     * Permet la modification des informations de l'étudiant affiché
     * @param prenom
     * @param nom
     * @param place
     * @param groupe
     */
    public void modifierInformationEtudiant(String prenom,String nom, Place place,String groupe){
        this.labelPrenom.setText(" Prénom : "+prenom);
        this.labelNom.setText(" Nom : "+nom);
        this.labelPlace.setText(" Place : "+place.getNom());
        this.labelGroupe.setText(" Groupe : "+groupe);
    }

    public boolean verifierSiDejaSelectionne(ControleurCaseSalle etu){
        boolean res = false;
        for(ControleurCaseSalle etudiant : this.etudiantsSelectionne){
            if(etu == etudiant){
                res=true;
                System.out.println("Déja selectionné");
            }
        }
        return res;
    }

    public void ajouterSelection(ControleurCaseSalle etudiant){
        if(this.etudiantsSelectionne.size() == 2){
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null,"Deux étudiants ont déja été selectionné. Veuillez en déselectionner un manuellement (En cliquant dessus) \n ou en pressant le bouton \"Reinitiliser la selection\" !","Erreur",JOptionPane.ERROR_MESSAGE);
        }else{
            this.etudiantsSelectionne.add(etudiant);
        }
    }
}


