package controleur.ControleurModuleSalle;

import controleur.ModificationNomPlaceDialog;
import controleur.ModificationNomPlaceDialogInfo;
import modele.BDD.Place;
import modele.BDD.Salle;
import modele.BDD.TypePlace;
import vue.VueSalle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ControleurCaseSalle extends JButton implements ActionListener {
    private Color couleurCase;
    private int i,j; //i = hauteur / y=largeur
    public static int WIDTH = 30;
    public static int HEIGHT = 30;
    private Salle salle;

    public static boolean MOUSE_DOWN = false;

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
               if(SwingUtilities.isRightMouseButton(e)){
                   Place place = salle.getPlaces()[i][j];
                   ModificationNomPlaceDialog dialog = new ModificationNomPlaceDialog(null,"Changement de nom",true,place.getNom());
                   ModificationNomPlaceDialogInfo infos = dialog.afficherDialog();
                   place.setNom(infos.getNouveauNom());
                   place.save();
               }

           }

           @Override
           public void mousePressed(MouseEvent e) {
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
                   VueSalle.partieAUpdate = VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
               } catch (SQLException e1) {
                   e1.printStackTrace();
               }
               couleurCase = couleur;

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
                        VueSalle.partieAUpdate = VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    couleurCase = couleur;
                }
           }

           @Override
           public void mouseExited(MouseEvent e) {
                ControleurCaseSalle.MOUSE_DOWN = false;
           }
       });

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
}
