package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreException extends JDialog {

    public FenetreException(String erreur, String texteBouton){

       // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea erreurTexte = new JTextArea(erreur);
        JButton bouton = new JButton(texteBouton);
        JPanel containerBouton = new JPanel();
        JPanel containerTexte = new JPanel();
        containerTexte.add(erreurTexte);
        //this.setSize(new Dimension(containerTexte.getWidth()+20,containerTexte.getHeight()+20));

        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        containerBouton.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy= 0;
        gbc.gridwidth = gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0,0,10,0);

        containerBouton.add(bouton);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(containerTexte, BorderLayout.CENTER);
        this.getContentPane().add(containerBouton, BorderLayout.SOUTH);
        this.setResizable(false);
        this.setVisible(true);


    }

    public static void main(String[] args) {
        FenetreException fe = new FenetreException("Ceci rufbuirbugbuigbtugtbgtpgbtpgutbgtbtgbutipguighurfhuerighutg \n gtgtgtgtg\n","Ok");
    }
}
