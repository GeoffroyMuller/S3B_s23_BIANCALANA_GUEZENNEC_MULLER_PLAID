package controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControleurCaseSalle extends JButton implements ActionListener {
    private Color couleurCase;

    public ControleurCaseSalle(Color c){
        this.couleurCase = c;
        this.setPreferredSize(new Dimension(40,40));
        this.setBackground(this.couleurCase);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
