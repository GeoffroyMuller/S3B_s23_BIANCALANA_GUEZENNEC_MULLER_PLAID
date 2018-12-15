import vue.BarreOutils;
import vue.VueOngletModules;


import javax.swing.*;
import java.awt.*;

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

        //Barre d'outil
        BarreOutils barreOutils = new BarreOutils();
        JFrame fenetre = new JFrame("EtuPlacement");
        //Panneau onglets modules
        VueOngletModules onglets = new VueOngletModules();

        fenetre.getContentPane().setLayout(new BorderLayout());
        fenetre.getContentPane().add(onglets,BorderLayout.CENTER);
        fenetre.setJMenuBar(barreOutils);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setMinimumSize(new Dimension(1070,550));
        fenetre.setSize(new Dimension(1070,900));
        fenetre.setVisible(true);
    }
}
