package controleur.ControleurModuleSalle;

import controleur.ModificationNomPlaceDialog;
import controleur.ModificationNomPlaceDialogInfo;
import modele.BDD.*;
import modele.Examen;
import vue.VueSalle;
import vue_Examen.DialogVerificationPlacement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ControleurCaseSalle extends JButton implements ActionListener, Observer {
    private Color couleurCase;
    private Color couleurCaseBase;
    private int i,j; //i = hauteur / y=largeur
    public static int WIDTH = 30;
    public static int HEIGHT = 30;
    private Salle salle;
    private VueSalle vueSalle;
    private boolean changementCouleur;

    //Utilisée lors de la verification du placement
    private DialogVerificationPlacement boiteDialogue;
    private Examen examen;

    public static boolean MOUSE_DOWN = false;

    /**
     * Permet de construire la visualisation de verification du placement d'un examen
     * @param c
     * @param i
     * @param j
     * @param salle
     * @param dialog
     * @param examen
     */
    public ControleurCaseSalle(Color c, int i, int j,Salle salle, DialogVerificationPlacement dialog, Examen examen){
        super();
        this.examen = examen;
        this.changementCouleur=true;
        this.salle = salle;
        this.i = i;
        this.j = j;
        this.couleurCase = c;
        this.couleurCaseBase = c;


        //Couleur situation de handicap
        Etudiant etudiant = examen.placement.get(salle).get(salle.getPlaces()[i][j]);
        ArrayList<Particularite> particulariteEtudiant = new ArrayList<Particularite>();
        try{
            particulariteEtudiant = etudiant.getParticularites();
            if(particulariteEtudiant.size() == 0){
                this.couleurCase = new Color(0x11D6FC);
                this.couleurCaseBase = new Color(0x11D6FC);
            }else{
                for(Particularite p : particulariteEtudiant){
                    if(p.getNom().contains("Situation de handicap (Prise en compte)")){
                        this.couleurCase = new Color(0x7800AE);
                        this.couleurCaseBase = new Color(0x7800AE);
                    }else{
                        this.couleurCase = new Color(0x11D6FC);
                        this.couleurCaseBase = new Color(0x11D6FC);
                    }
                }
            }


        }catch(NullPointerException e){

        }



        this.boiteDialogue = dialog;
        setContentAreaFilled(false);
        this.setBackground(this.couleurCase);
        final Examen examenStatic = this.examen;
        final Salle salleStatic = this.salle;
        final int iStatic = this.i;
        final int jStatic =this.j;
        final ControleurCaseSalle controleur = this;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(boiteDialogue.verifierSiDejaSelectionne(controleur)){
                    setCouleurCase(getCouleurCaseBase());
                }else{
                    setCouleurCase(new Color(0x19F10D));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Etudiant etudiant = examenStatic.placement.get(salleStatic).get(salleStatic.getPlaces()[iStatic][jStatic]);
                try{
                    boiteDialogue.modifierInformationEtudiant(etudiant.getPrenom(),etudiant.getNom(),salleStatic.getPlaces()[iStatic][jStatic],etudiant.getGroupe());
                }catch(NullPointerException exception){
                    boiteDialogue.modifierInformationEtudiant("Non occupée","Non occupée",salleStatic.getPlaces()[iStatic][jStatic],"Non occupée");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Permet de construire la visualisation d'une salle dans le module Salle
     * @param c
     * @param i
     * @param j
     * @param salle
     */
    public ControleurCaseSalle(Color c, int i, int j, Salle salle){
        super();
        this.salle = salle;
        this.i = i;
        this.j = j;
        this.changementCouleur=true;
        this.couleurCase = c;
        this.couleurCaseBase = c;

        setContentAreaFilled(false);

       this.setBackground(this.couleurCase);

        final Salle salleStatic = this.salle;
        final int iStatic = this.i;
        final int jStatic = this.j;
       this.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent e) {


           }

           @Override
           public void mousePressed(MouseEvent e) {

               if(SwingUtilities.isRightMouseButton(e)){

                   Place place = salleStatic.getPlaces()[iStatic][jStatic];
                   ModificationNomPlaceDialog dialog = new ModificationNomPlaceDialog(null,"Changement de nom",true,place);
                   ModificationNomPlaceDialogInfo infos = dialog.afficherDialog();
                   try {
                       salleStatic.modifierNomPlace(iStatic, jStatic, infos.getNouveauNom(), infos.getNomColonne(), infos.getNomRangee());
                   }catch(NullPointerException ex){
                   }
               }else{
                   ControleurCaseSalle.MOUSE_DOWN =true;

                   Color couleur = new Color(0);
                   String typePlace = ControleurRadioBoutons.placeSelectionnee;
                   switch(typePlace){
                       case "place":
                           couleur = TypePlace.couleurPlace;
                           break;
                       case "placeInutillisable":
                           couleur = TypePlace.couleurPlaceInutilisable;
                           break;
                       case "allee":
                           couleur= TypePlace.couleurAllee;
                           break;
                   }
                   try {
                       TypePlace tp = TypePlace.findByNom(typePlace);
                       Place[][] places = ControleurSauvegardeSalle.salle.getPlaces();
                       salleStatic.changerLeTypePlace(iStatic,jStatic,tp.getIdTypePlace());
                       //VueSalle.partieAUpdate = VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
                       VueSalle.partieAUpdate = VueSalle.UPDATE_NOTHING;
                   } catch (SQLException e1) {
                       e1.printStackTrace();
                   }
                   couleurCase = couleur;
                   repaint();

               }



           }

           @Override
           public void mouseReleased(MouseEvent e) {
            ControleurCaseSalle.MOUSE_DOWN = false;
               System.out.println("MOUSE RELEASED");
           }

           @Override
           public void mouseEntered(MouseEvent e) {
                int mask = MouseEvent.BUTTON1_DOWN_MASK;
               if(mask == e.getModifiersEx()){
                    Color couleur = new Color(0);
                    String typePlace = ControleurRadioBoutons.placeSelectionnee;
                    switch(typePlace){
                        case "place":
                            couleur = TypePlace.couleurPlace;
                            break;
                        case "placeInutillisable":
                            couleur = TypePlace.couleurPlaceInutilisable;
                            break;
                        case "allee":
                            couleur= TypePlace.couleurAllee;
                            break;
                    }
                    try {
                        TypePlace tp = TypePlace.findByNom(typePlace);
                        Place[][] places = ControleurSauvegardeSalle.salle.getPlaces();
                        salleStatic.changerLeTypePlace(iStatic,jStatic,tp.getIdTypePlace());
                        //VueSalle.partieAUpdate = VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
                        VueSalle.partieAUpdate = VueSalle.UPDATE_NOTHING;

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    couleurCase = couleur;



                    repaint();
                }

               try{
                   vueSalle.mettreAJourInfoPlace(salleStatic.getPlaces()[iStatic][jStatic].getNomRangee(),salleStatic.getPlaces()[iStatic][jStatic].getNomColonne());
               }catch(NullPointerException exception){
                   vueSalle.mettreAJourInfoPlace("ERREUR","ERREUR");

               }

           }

           @Override
           public void mouseExited(MouseEvent e) {
                ControleurCaseSalle.MOUSE_DOWN = false;
           }
       });

    }


    public Color getCouleurCaseBase() {
        return couleurCaseBase;
    }

    public void setCouleurCaseBase(Color couleurCaseBase) {
        this.couleurCaseBase = couleurCaseBase;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Color couleur = new Color(0);
        String typePlace = ControleurRadioBoutons.placeSelectionnee;
        switch(typePlace){
            case "place":
                couleur = TypePlace.couleurPlace;
                break;
            case "placeInutillisable":
                couleur = TypePlace.couleurPlaceInutilisable;
                break;
            case "allee":
                couleur= TypePlace.couleurAllee;
                break;
        }
        try {
            TypePlace tp = TypePlace.findByNom(typePlace);
            Place[][] places = ControleurSauvegardeSalle.salle.getPlaces();
            this.salle.changerLeTypePlace(this.i,this.j,tp.getIdTypePlace());
            VueSalle.partieAUpdate = VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        this.couleurCase = couleur;
        //repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(this.couleurCase);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(new Color(0x000000));
        g2.drawRect(0,0,getWidth()+2,getHeight()+2);

        g2.dispose();

        super.paintComponent(g);
    }

    public Color getCouleurCase() {
        return couleurCase;
    }

    /**
     * Permet de changer la couleur d'une case et de remettre la couleur de base si la méthode est appelée une seconde fois
     * @param color
     */
    public void switchColor(Color color){
        if(this.changementCouleur){
            this.setCouleurCase(color);
            this.changementCouleur=false;
        }else{
            this.setCouleurCase(this.couleurCaseBase);
            this.changementCouleur=true;
        }
        repaint();
    }

    public void setCouleurCase(Color couleurCase) {
        this.couleurCase = couleurCase;
        repaint();
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("UPDATE");
        this.salle = Salle.findById(this.salle.getIdSalle());
    }

    /**
     * Permet l'ajout d'une VueSalle au controleur
     * @param vueSalle
     */
    public void ajouterVueSalle(VueSalle vueSalle){
        this.vueSalle = vueSalle;
    }


    public Etudiant getEtudiant(){
        return examen.placement.get(salle).get(salle.getPlaces()[i][j]);

    }

    public Place getPlace(){
        Place place = null;

        place = salle.getPlaces()[this.i][this.j];

        return place;
    }
}
