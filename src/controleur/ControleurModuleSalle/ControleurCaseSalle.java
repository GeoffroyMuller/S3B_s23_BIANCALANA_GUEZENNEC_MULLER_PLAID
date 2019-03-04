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
    private boolean changementCouleur;
    //Utilisée lors de la verification du placement
    private DialogVerificationPlacement boiteDialogue;
    private Examen examen;

    public static boolean MOUSE_DOWN = false;

    public ControleurCaseSalle(Color c, int i, int j, Salle salle, DialogVerificationPlacement dialog, Examen examen){
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
            for(Particularite p : particulariteEtudiant){
                if(p.getNom().contains("Situation de handicap (Prise en compte)")){
                    this.couleurCase = new Color(0x7800AE);
                    this.couleurCaseBase = new Color(0x7800AE);
                }
            }
        }catch(NullPointerException e){

        }



        this.boiteDialogue = dialog;
        setContentAreaFilled(false);
        this.setBackground(this.couleurCase);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Etudiant etudiant = examen.placement.get(salle).get(salle.getPlaces()[i][j]);
                try{
                    boiteDialogue.modifierInformationEtudiant(etudiant.getPrenom(),etudiant.getNom(),salle.getPlaces()[i][j],etudiant.getGroupe());
                }catch(NullPointerException exception){
                    boiteDialogue.modifierInformationEtudiant("Non occupée","Non occupée",salle.getPlaces()[i][j],"Non occupée");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public ControleurCaseSalle(Color c, int i, int j, Salle salle){
        super();
        this.salle = salle;
        this.i = i;
        this.j = j;
        this.couleurCase = c;
        setContentAreaFilled(false);

       this.setBackground(this.couleurCase);
       this.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent e) {


           }

           @Override
           public void mousePressed(MouseEvent e) {

               if(SwingUtilities.isRightMouseButton(e)){
                   System.out.println("CLique droit !");

                   Place place = salle.getPlaces()[i][j];
                   System.out.println("ID PLace : "+place.getIdPlace());
                   ModificationNomPlaceDialog dialog = new ModificationNomPlaceDialog(null,"Changement de nom",true,place);
                   ModificationNomPlaceDialogInfo infos = dialog.afficherDialog();
                   try {
                       salle.modifierNomPlace(i, j, infos.getNouveauNom(), infos.getNomColonne(), infos.getNomRangee());
                   }catch(NullPointerException ex){
                       System.out.println("Aucun changement");
                   }
                   /*place.setNom(infos.getNouveauNom());
                   place.save();
                   salle.save();*/
               }else{
                   System.out.println("MOUSE PRESSED");
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
                       salle.changerLeTypePlace(i,j,tp.getIdTypePlace());
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
                        salle.changerLeTypePlace(i,j,tp.getIdTypePlace());
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
}
