package vue;

import vue_Examen.VueExamen;

import javax.swing.*;
import java.awt.*;

public class VueOngletModules extends JPanel {
    JTabbedPane onglets;

    public VueOngletModules(){
        this.setBackground(new Color(0xFFFFFF));
        this.onglets = new JTabbedPane();
        this.onglets.setFont(new Font("Serial",Font.BOLD,20));
        this.onglets.setUI(new TabbedPanDesign());
        //A supprimer
        VueEtudiant moduleEtudiant = new VueEtudiant();
        JPanel test = new JPanel();
        //FIN DELETE
        this.setLayout(null);
        this.onglets.add("Examen",new VueExamen());
        this.onglets.add("Etudiants",moduleEtudiant);
        this.onglets.add("Salles",test);
        this.onglets.setBounds(0,0,800,1000);
        this.onglets.setToolTipTextAt(0,"Module permettant la gestion de liste");
        this.add(this.onglets);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.onglets.setBounds(0,0,this.getParent().getWidth(),this.getParent().getHeight());
    }
}