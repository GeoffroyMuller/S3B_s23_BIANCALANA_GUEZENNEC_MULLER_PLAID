package vue;

import controleur.ControleurBoutonsPartieSalle;
import controleur.ControleurModuleSalle.ControleurCaseSalle;
import controleur.ControleurModuleSalle.ControleurRadioBoutons;
import modele.BDD.Etudiant;
import vue.ComposantVueSalle.Indicateur;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Classe permettant la création de la vue du module salle, c'est dans cette vue que son crée les controleurs associés (Boutons "Ajouter" et "Supprimer" et les boutons radios
 */
public class VueSalle extends JPanel {
	private JScrollPane containerDeLaListeJScroll, visualisationSalle;
	private JPanel contenantPartieGauche;
	private JPanel contenantMilieu;

	/**
	 * Définition de la largeur par défaut d'une salle dans la visualisation
	 */
	public static int DEFAULT_SIZE_ROOM_WIDTH = 10;
	/**
	 * Définition de la hauteur par défaut d'une salle dans la visualisation
	 */
	public static int DEFAULT_SIZE_ROOM_HEIGHT = 10;

	/**
	 * Constructeur de la vue salle, construit également les controleurs necessaires (ControleurCaseSalle et ControleurRadioBoutons)
	 */
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
		this.visualisationSalle = new JScrollPane(this.construireSalle(DEFAULT_SIZE_ROOM_WIDTH,DEFAULT_SIZE_ROOM_HEIGHT));
		this.visualisationSalle.setPreferredSize(new Dimension(400,400));
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

		//Partie selection (Partie de droite)
		//Composant Indications
		JPanel indications = new Indicateur();
		indications.setPreferredSize(new Dimension(300,600));
		//Composant Edition
		JPanel editionPan = new JPanel();
		editionPan.setLayout(new BorderLayout());
		JLabel edition = new JLabel("Edition",SwingConstants.CENTER);
		edition = this.applicationStylePolice(edition);

		//Boutons radio
		ControleurRadioBoutons editionBoutons = new ControleurRadioBoutons();
		JPanel partieEdition = new JPanel();

		partieEdition.setLayout(new BorderLayout());
		partieEdition.add(editionBoutons,BorderLayout.WEST);
		partieEdition.add(new Indicateur(), BorderLayout.CENTER);

		editionPan.add(edition, BorderLayout.NORTH);
		editionPan.add(partieEdition, BorderLayout.CENTER);
		editionPan.setPreferredSize(new Dimension(300,600));
		editionPan.setBorder(new EmptyBorder(20,10,0,0));






		contenantPartieGauche.add(boutons,BorderLayout.SOUTH);
		contenantPartieGauche.setBorder(new EmptyBorder(20,20,0,0));
		conteneurHaut.setLayout(new GridLayout(0,2));
		conteneurHaut.add(labelGestionDeSalle);
		this.add(conteneurHaut,BorderLayout.NORTH);
		this.add(this.contenantMilieu, BorderLayout.CENTER);
		this.add(contenantPartieGauche, BorderLayout.WEST);
		this.add(editionPan,BorderLayout.EAST);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.contenantPartieGauche.setPreferredSize(new Dimension( (this.getParent().getWidth())/3, this.getParent().getHeight()));
		this.visualisationSalle.setPreferredSize(new Dimension(this.getParent().getWidth()/3,this.getParent().getWidth()/3));
	}

	/**
	 * Construit la visualisation d'une salle
	 * @param x
	 *      entier précisant la largeur de la salle
	 * @param y
	 *      entier précisant la hauteur de la salle
	 * @return
	 *      JPanel contenant la représentation de la salle
	 */
	 private JPanel construireSalle(int x, int y){
        JPanel contenant = new JPanel();
        contenant.setPreferredSize(new Dimension((x*ControleurCaseSalle.WIDTH),(y*ControleurCaseSalle.HEIGHT)));
        contenant.setLayout(new GridLayout(x,y));
        for(int i = 0; i < x;i++){
            for(int j = 0; j < y;j++){
                contenant.add(new ControleurCaseSalle(new Color(0)));
            }
        }
        return contenant;
    }

    /**
	 * Applique les styles de polices aux labels
	 * @param label
	 * @return
	 */
	 private JLabel applicationStylePolice(JLabel label){
        label.setFont(new Font("Serial",Font.PLAIN,14));
        label.setBorder(BorderFactory.createLineBorder(new Color(0),1));
        label.setOpaque(true);
        label.setBackground(new Color(0x656565));
        label.setForeground(new Color(0xFAFFF1));
        return label;
    }

}
