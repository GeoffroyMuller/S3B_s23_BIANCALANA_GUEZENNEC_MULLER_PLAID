package vue;

import javax.swing.*;
import java.awt.*;

public class VueOngletModules extends JPanel {
    JTabbedPane onglets;

    public VueOngletModules(){
        this.onglets = new JTabbedPane();
        //A supprimer
        JPanel test = new JPanel();
        JPanel test2 = new JPanel();
        //FIN DELETE
        this.setLayout(null);
        this.onglets.add("Examen",test);
        this.onglets.add("Etudiants",test2);
        this.onglets.add("Salles",test);
        this.onglets.setBounds(0,0,800,1000);
        this.add(this.onglets);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.onglets.setBounds(0,0,this.getParent().getWidth(),this.getParent().getHeight());
    }
}
