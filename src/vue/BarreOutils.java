package vue;

import javax.swing.*;

public class BarreOutils extends JMenuBar {

    public BarreOutils(){
        //Barre d'outils contenant les menu fichier,options,aide
        JMenu menuFichier = new JMenu("Fichier");
        JMenu menuOption = new JMenu("Options");
        JMenu menuAide = new JMenu("Aide");
        this.add(menuFichier);
        this.add(menuOption);
        this.add(menuAide);
    }
}
