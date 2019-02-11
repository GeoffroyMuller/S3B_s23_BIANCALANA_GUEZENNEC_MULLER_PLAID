package controleur.ControleurModuleSalle;

import modele.BDD.Place;
import modele.BDD.Salle;
import modele.BDD.TypePlace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class ControleurCaseSalle extends JButton implements ActionListener {
    private Color couleurCase;
    private int i,j; //i = hauteur / y=largeur
    public static int WIDTH = 40;
    public static int HEIGHT = 40;
    private Salle salle;

    public ControleurCaseSalle(Color c, int i, int j, Salle salle){
        super();
        this.salle = salle;
        this.i = i;
        this.j = j;
        this.couleurCase = c;
        setContentAreaFilled(false);

       /* this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH,HEIGHT));*/
       //this.setSize(new Dimension(WIDTH,HEIGHT));
        // this.setForeground(this.couleurCase);
       this.setBackground(this.couleurCase);
       this.addActionListener(this);
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
