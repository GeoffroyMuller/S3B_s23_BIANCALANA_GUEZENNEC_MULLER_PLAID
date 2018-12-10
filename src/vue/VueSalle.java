package vue;

import modele.Etudiant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class VueSalle extends JPanel {

    public VueSalle(){
        this.setLayout(new BorderLayout());
        //Jpanel contenant le JLabel
        JPanel conteneurHaut = new JPanel();
        JLabel labelGestionDeSalle = new JLabel("Gestion de salle");
        labelGestionDeSalle.setFont(new Font("Serial",Font.PLAIN,20));
        labelGestionDeSalle.setBorder(new EmptyBorder(10,10,0,0));

        //Partie de gauche Liste des salles

        //JLABEL
        JLabel labelDeLaListe = new JLabel("Listes des Salles :",SwingConstants.CENTER);
        labelDeLaListe.setFont(new Font("Serial",Font.PLAIN,14));
        labelDeLaListe.setBorder(BorderFactory.createLineBorder(new Color(0),1));
        labelDeLaListe.setOpaque(true);
        labelDeLaListe.setBackground(new Color(0x656565));
        labelDeLaListe.setForeground(new Color(0xFAFFF1));

        //Composant de la visualisation des listes des groupes
        JList<Etudiant> listeDesSalles = new JList<Etudiant>();
        JScrollPane containerDeLaListeJScroll = new JScrollPane(listeDesSalles);
        containerDeLaListeJScroll.setBorder(new EmptyBorder(0,0,20,0));

        //Ajout dans un conteneur
        JPanel contenantPartieGauche = new JPanel();
        //contenantPartieGauche.setBorder(new EmptyBorder(0,20,0,0));
        contenantPartieGauche.setLayout(new BorderLayout());
        contenantPartieGauche.add(labelDeLaListe,BorderLayout.NORTH);
        contenantPartieGauche.add(containerDeLaListeJScroll,BorderLayout.CENTER);


        contenantPartieGauche.setBorder(new EmptyBorder(20,20,0,0));

        //conteneurHaut.setBackground(new Color(0));
        conteneurHaut.setLayout(new GridLayout(0,2));
        conteneurHaut.add(labelGestionDeSalle);
        this.add(conteneurHaut,BorderLayout.NORTH);
        this.add(contenantPartieGauche, BorderLayout.WEST);
    }

}
