package vue;

import controleur.ControleurBoutonsPartieSalle;
import controleur.ControleurCaseSalle;
import modele.Etudiant;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class VueSalle extends JPanel {
    private JScrollPane containerDeLaListeJScroll;
    private JPanel contenantPartieGauche;
    private JPanel contenantMilieu;

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
        this.containerDeLaListeJScroll = new JScrollPane(listeDesSalles);

        //Ajout dans un conteneur
        contenantPartieGauche = new JPanel();
        //contenantPartieGauche.setBorder(new EmptyBorder(0,20,0,0));
        contenantPartieGauche.setLayout(new BorderLayout());
        contenantPartieGauche.setPreferredSize(new Dimension(300,this.getHeight()));
        contenantPartieGauche.add(labelDeLaListe,BorderLayout.NORTH);
        contenantPartieGauche.add(containerDeLaListeJScroll,BorderLayout.CENTER);

        //Creation du controleur
        ControleurBoutonsPartieSalle boutons = new ControleurBoutonsPartieSalle();

        //Partie visualisation de la liste (Partie du milieux)
        JScrollPane visualisationSalle = new JScrollPane(this.construireSalle(10,10));
            //Mise en place du controlleur
        this.contenantMilieu = new JPanel();
        this.contenantMilieu.setLayout(new GridBagLayout());

        JLabel titrePartieMilieu = new JLabel("Visualisation de la salle :",SwingConstants.CENTER);
        //FACTORISER LE CODE
        titrePartieMilieu.setFont(new Font("Serial",Font.PLAIN,14));
        titrePartieMilieu.setBorder(BorderFactory.createLineBorder(new Color(0),1));
        titrePartieMilieu.setOpaque(true);
        titrePartieMilieu.setBackground(new Color(0x656565));
        titrePartieMilieu.setForeground(new Color(0xFAFFF1));


        // Ajout de contraintes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20,10,20,0);
        this.contenantMilieu.add(titrePartieMilieu,gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.contenantMilieu.add(visualisationSalle, gbc);





        contenantPartieGauche.add(boutons,BorderLayout.SOUTH);
        contenantPartieGauche.setBorder(new EmptyBorder(20,20,0,0));
        conteneurHaut.setLayout(new GridLayout(0,2));
        conteneurHaut.add(labelGestionDeSalle);
        this.add(conteneurHaut,BorderLayout.NORTH);
        this.add(this.contenantMilieu, BorderLayout.CENTER);
        this.add(contenantPartieGauche, BorderLayout.WEST);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.contenantPartieGauche.setPreferredSize(new Dimension( (this.getParent().getWidth())/3, this.getParent().getHeight()));
    }

    public JPanel construireSalle(int x, int y){
        JPanel contenant = new JPanel();
        contenant.setLayout(new GridLayout(x,y));
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 10;j++){
                contenant.add(new ControleurCaseSalle(new Color(0)));
            }
        }
        return contenant;
    }

}
