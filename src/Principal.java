import modele.BDD.Salle;
import modele.Examen;
import vue.BarreOutils;
import vue.VueOngletModules;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Principal {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        //Modeles
        Examen examen = new Examen();
        Salle salle = new Salle("Sans nom",Salle.DEFAULT_SIZE_ROOM_HEIGHT,Salle.DEFAULT_SIZE_ROOM_WIDTH);



        //Barre d'outil
        BarreOutils barreOutils = new BarreOutils();
        JFrame fenetre = new JFrame("EtuPlacement");

        //Panneau onglets modules
       VueOngletModules onglets = new VueOngletModules(examen,salle);


        fenetre.getContentPane().setLayout(new BorderLayout());
        fenetre.getContentPane().add(onglets,BorderLayout.CENTER);
        fenetre.setJMenuBar(barreOutils);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setMinimumSize(new Dimension(1500,830));
        fenetre.setSize(new Dimension(1200,830));
        fenetre.setVisible(true);
    }
}
