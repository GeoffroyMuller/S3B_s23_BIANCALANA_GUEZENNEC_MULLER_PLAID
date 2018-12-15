package controleur.ControleurModuleSalle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControleurCaseSalle extends JButton implements ActionListener {
    private Color couleurCase;
    public static int WIDTH = 40;
    public static int HEIGHT = 40;

    public ControleurCaseSalle(Color c){
        this.couleurCase = c;
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(this.couleurCase);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
