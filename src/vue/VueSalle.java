package vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VueSalle extends JPanel {

    public VueSalle(){
        this.setLayout(new BorderLayout());
        //Jpanel contenant le JLabel
        JPanel conteneurHaut = new JPanel();
        JLabel labelGestionDeSalle = new JLabel("Gestion de salle");
        labelGestionDeSalle.setFont(new Font("Serial",Font.PLAIN,20));
        labelGestionDeSalle.setBorder(new EmptyBorder(10,10,0,0));
        conteneurHaut.setLayout(new GridLayout(0,2));
        conteneurHaut.add(labelGestionDeSalle);
        this.add(conteneurHaut,BorderLayout.NORTH);
    }

}
