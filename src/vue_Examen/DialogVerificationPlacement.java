package vue_Examen;

import controleur.ControleurModuleSalle.ControleurCaseSalle;
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
    private JLabel labelRechercheEtudiantNom,labelRechercheEtudiantPrenom,labelRechercheEtudiantGroupe,labelNom,labelPrenom,labelPlace,labelNomSalle;
    private JButton rechercher;
    private JComboBox<String> groupes;
    private HashMap<Salle, ProprietesCaseSalle> proprietesSalle;
    private HashMap<String, Integer> indexVersSalle;



    public DialogVerificationPlacement(JFrame parent, String title, boolean modal,Examen examen){
        super(parent,title,modal);
        this.proprietesSalle = new HashMap<Salle,ProprietesCaseSalle>();
        this.indexVersSalle = new HashMap<String, Integer>();
        this.modele = examen;
        this.setSize(new Dimension(550,270));
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

        //Création de JLabel pour chaque salle
        for(Salle salle : this.modele.getSalles()) {
            this.labelNomSalles.add(new JLabel("Salle : "+salle.getNom()));
        }


        //Recuperation de la prévisualisation
        this.placement = this.modele.genererPrevisualisationFiche();

        //Disposition des éléments
        JPanel contenantJTable = new JPanel();
        contenantJTable.setLayout(new GridLayout(this.modele.getSalles().size(),1));

        int index = 0;
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
        this.rechercherEtudiantPrenom = new JTextField();
        this.labelRechercheEtudiantNom =new JLabel("Nom étudiant : ");
        this.labelRechercheEtudiantPrenom =new JLabel("Prénom étudiant : ");
        this.labelRechercheEtudiantGroupe =new JLabel("Groupe étudiant : ");
        String[] groupesParticipant = this.modele.groupeParticipant();
        this.groupes = new JComboBox(groupesParticipant);
        this.rechercher = new JButton("Rechercher");

        JPanel topbar = new JPanel();
        topbar.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        topbar.add(this.labelNom,gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        topbar.add(this.rechercherEtudiantNom,gbc);
        gbc.gridx=2;
        gbc.gridwidth = 1;
        topbar.add(this.labelPrenom,gbc);
        gbc.gridx=3;
        gbc.gridwidth=2;
        topbar.add(this.rechercherEtudiantPrenom);
        gbc.gridx=4;
        topbar.add(this.groupes,gbc);
        gbc.gridx=5;
        topbar.add(this.rechercher);


        //Interface de visualisation de la salle avec le placement
        ArrayList<JPanel> sallesConstruites = new ArrayList<JPanel>();
        //Construction des salles
        for(int i = 0; i < this.modele.getSalles().size();i++){
            Salle salle = this.modele.getSalles().get(i);
            this.proprietesSalle.put(salle,VueSalle.construireSalleDialogBox(salle,this,this.modele));
            sallesConstruites.add(this.proprietesSalle.get(salle).getSalleConstruite());
            this.indexVersSalle.put(salle.getNom(),i);
        }
        //Construction panneau latéral droit (affichage des informations de la case selectionne)
        this.labelNom = new JLabel("Nom : ");
        this.labelPrenom =new JLabel("Prénom : ");
        this.labelPlace = new JLabel("Place : ");

        JPanel panneauDroit = new JPanel();
        panneauDroit.setLayout(new GridLayout(3,0));
        panneauDroit.add(this.labelNom);
        panneauDroit.add(this.labelPrenom);
        panneauDroit.add(this.labelPlace);

        //Ajouts des vues salles dans les onglets
        this.salles = new JTabbedPane();
        for(int i = 0; i < this.modele.getSalles().size();i++){
            this.salles.addTab(this.modele.getSalles().get(i).getNom(), sallesConstruites.get(i));
        }


        this.rechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Rechercher etudiant
                CritereRechercheEtudiant cre = new CritereRechercheEtudiant(rechercherEtudiantNom.getText(),rechercherEtudiantPrenom.getText(),groupes.getItemAt(groupes.getSelectedIndex()));
                InformationsPlacementEtudiant informationsPlacement = modele.trouverPlaceEtudiant(cre);
                //Assombrir toutes les cases sauf celle de l'étudiant
                ArrayList<ControleurCaseSalle> casesSalle = proprietesSalle.get(informationsPlacement.getSalle()).getListeControleurs();

                if(((JTextField)e.getSource()).getText().isEmpty()){
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
                    modifierInformationEtudiant(cre.getPrenom(),cre.getNom(),informationsPlacement.getPlace());
                    salles.setSelectedIndex(indexVersSalle.get(informationsPlacement.getSalle().getNom()));
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

    public void modifierInformationEtudiant(String prenom,String nom, Place place){
        this.labelPrenom.setText(" Prénom : "+prenom);
        this.labelNom.setText(" Nom : "+nom);
        this.labelPlace.setText(" Place : "+place.getNom());
    }
}


