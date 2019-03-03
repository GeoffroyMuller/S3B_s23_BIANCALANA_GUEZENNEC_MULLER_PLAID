package vue_Examen;

import modele.BDD.Salle;
import modele.CritereRechercheEtudiant;
import modele.Examen;
import modele.GestionFichiersExcel.ExportEtudiant;
import modele.InformationsPlacementEtudiant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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



    public DialogVerificationPlacement(JFrame parent, String title, boolean modal,Examen examen){
        super(parent,title,modal);
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
                exportEtu.exporterPlacement(modele.getPlacement());
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




        this.getContentPane().add(contenant, BorderLayout.CENTER);
        this.getContentPane().add(boutons,BorderLayout.SOUTH);



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


            //Interface de visualisation de la salle avec le placement

        this.rechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Rechercher etudiant
                CritereRechercheEtudiant cre = new CritereRechercheEtudiant(rechercherEtudiantNom.getText(),rechercherEtudiantPrenom.getText(),groupes.getItemAt(groupes.getSelectedIndex()));
                InformationsPlacementEtudiant informationsPlacement = modele.trouverPlaceEtudiant(cre);
                //Assombrir toutes les cases sauf celle de l'étudiant
                //Mettre la selection sur la case
            }
        });




        this.salles = new JTabbedPane();
        /*for(Salle salle: this.modele.getSalles()){
            this.salles.add(salle.getNom(),);
        }*/




    }

    public void afficherDialog(){
        this.sendData =false;
        this.setVisible(true);

    }
}


