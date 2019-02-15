package vue;

import controleur.ControleurBoutonsPartieSalle;
import controleur.ControleurModuleSalle.ControleurCaseSalle;
import controleur.ControleurModuleSalle.ControleurRadioBoutons;
import controleur.ControleurModuleSalle.ControleurSauvegardeSalle;
import modele.BDD.Etudiant;
import modele.BDD.Place;
import modele.BDD.Salle;
import modele.BDD.TypePlace;
import vue.ComposantVueSalle.Indicateur;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe permettant la création de la vue du module salle, c'est dans cette vue que son crée les controleurs associés (Boutons "Ajouter" et "Supprimer" et les boutons radios
 */
public class VueSalle extends JPanel implements Observer {
	private JScrollPane containerDeLaListeJScroll, visualisationSalle;
	private JPanel contenantPartieGauche;
	private JPanel contenantMilieu;
	private Salle salle;


	private DefaultListModel<Salle> dlm;

	private JLabel labelDeLaListe;

	public static Salle salleSelectionne;

	/**
	 * Correspond a la partie qui doit être mise à jour, (1 = partie gauche, 2= milieu, 3=partie de droite)
	 */
	public static int partieAUpdate;

	private JList<Salle> listeDesSalles;

	public static int UPDATE_PARTIE_LISTE_SALLE = 1;
	public static int UPDATE_PARTIE_AFFICHAGE_SALLE = 2;
	public static int UPDATE_AJOUT_SALLE = 3;
	public static int UPDATE_ALL = -1;




	/**
	 * Constructeur de la vue salle, construit également les controleurs necessaires (ControleurCaseSalle et ControleurRadioBoutons)
	 */
	public VueSalle(Salle salleModele){
		this.salle = salleModele;
		this.setLayout(new BorderLayout());

		//Jpanel contenant le JLabel
		JPanel conteneurHaut = new JPanel();
		JLabel labelGestionDeSalle = new JLabel("Gestion de salle");
		labelGestionDeSalle.setFont(new Font("Serial",Font.PLAIN,20));
		labelGestionDeSalle.setBorder(new EmptyBorder(10,10,0,0));

		//Partie de gauche Liste des salles

		//JLABEL
		labelDeLaListe = new JLabel("Listes des Salles :",SwingConstants.CENTER);
		labelDeLaListe.setFont(new Font("Serial",Font.PLAIN,14));
		labelDeLaListe.setBorder(BorderFactory.createLineBorder(new Color(0),1));
		labelDeLaListe.setOpaque(true);
		labelDeLaListe.setBackground(new Color(0x656565));
		labelDeLaListe.setForeground(new Color(0xFAFFF1));

		//Composant de la visualisation des listes des groupes
		dlm = new DefaultListModel<Salle>();
		try {
			for(Salle s : Salle.listSalle()){
				dlm.addElement(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.listeDesSalles = new JList<Salle>(dlm);
		VueSalle.salleSelectionne = dlm.firstElement();
		listeDesSalles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				VueSalle.partieAUpdate = VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
				VueSalle.salleSelectionne = listeDesSalles.getSelectedValue();
				String nomSalle = listeDesSalles.getSelectedValue().getNom();
				System.out.println("Nom de la salle selectionne : "+nomSalle);
				Salle salleSelectionne = Salle.findByNom(nomSalle);
				salle.changerSalle(salleSelectionne);
			}
		});

		this.containerDeLaListeJScroll = new JScrollPane(listeDesSalles);

		//Ajout dans un conteneur
		contenantPartieGauche = new JPanel();
		//contenantPartieGauche.setBorder(new EmptyBorder(0,20,0,0));
		contenantPartieGauche.setLayout(new BorderLayout());
		contenantPartieGauche.setPreferredSize(new Dimension(300,this.getHeight()));
		contenantPartieGauche.add(labelDeLaListe,BorderLayout.NORTH);
		contenantPartieGauche.add(containerDeLaListeJScroll,BorderLayout.CENTER);

		//Creation du controleur
		ControleurBoutonsPartieSalle boutons = new ControleurBoutonsPartieSalle(this.salle);

		//Partie visualisation de la liste (Partie du milieux)
		this.visualisationSalle = new JScrollPane(this.construireSalle(this.salle));
		this.visualisationSalle.setPreferredSize(new Dimension(500,500));
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
		indications.setPreferredSize(new Dimension(300,200));
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

		JButton sauvegarde = new ControleurSauvegardeSalle(salle);

		JPanel containerBouton = new JPanel();
		containerBouton.setLayout(new BorderLayout());
		containerBouton.setPreferredSize(new Dimension(300,460));
		containerBouton.add(sauvegarde,BorderLayout.NORTH);

		editionPan.add(edition, BorderLayout.NORTH);
		editionPan.add(partieEdition, BorderLayout.CENTER);
		editionPan.add(containerBouton,BorderLayout.SOUTH);
		editionPan.setPreferredSize(new Dimension(300,300));
		editionPan.setBorder(new EmptyBorder(20,10,0,10));

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
		this.contenantPartieGauche.setPreferredSize(new Dimension( (this.getParent().getWidth())/5, this.getParent().getHeight()));
		this.visualisationSalle.setPreferredSize(new Dimension(this.getParent().getWidth()/3,this.getParent().getWidth()/3));
		super.paintComponent(g);

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
	 	Salle salle = new Salle("Sans nom",x,y);
        JPanel contenant = new JPanel();
		 contenant.setLayout(new GridBagLayout());
		 GridBagConstraints gbc = new GridBagConstraints();
		 gbc.gridx= gbc.gridy = 0;
		 gbc.gridheight = gbc.gridwidth = 1;
		gbc.insets = new Insets(2,2,0,2);
		 for(int i = 0; i < y;i++){
            for(int j = 0; j < x;j++){
            	JPanel jpBouton = new JPanel();
            	jpBouton.setLayout(new BorderLayout());
            	jpBouton.add(new ControleurCaseSalle(new Color(0xB11000),i,j,this.salle));
            	jpBouton.setPreferredSize(new Dimension(ControleurCaseSalle.WIDTH,ControleurCaseSalle.HEIGHT));
                contenant.add(jpBouton,gbc);
                gbc.gridx++;
            }
            gbc.gridy++;
            gbc.gridx=0;
        }
        return contenant;
    }

    private JPanel construireSalle(Salle salle){
		JPanel contenant = new JPanel();
		contenant.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx= gbc.gridy = 0;
		gbc.gridheight = gbc.gridwidth = 1;
		gbc.insets = new Insets(2,2,0,2);
		for(int i = 0; i < salle.getNbCaseLargeur();i++){
			for(int j = 0; j < salle.getNbCaseHauteur();j++){
				JPanel jpBouton = new JPanel();
				jpBouton.setLayout(new BorderLayout());
				TypePlace typePlace = TypePlace.findById(salle.getPlaces()[i][j].getIdTypePlace());
				Color couleurPlace = null;
				switch(typePlace.getNom()){
					case "place":
					case "chaise":
						couleurPlace = TypePlace.couleurPlace;
						break;
					case "placeInutillisable":
						couleurPlace = TypePlace.couleurPlaceInutilisable;
						break;
					case "allee":
						couleurPlace = TypePlace.couleurAllee;
						break;
				}
				jpBouton.add(new ControleurCaseSalle(couleurPlace,i,j,this.salle));
				jpBouton.setPreferredSize(new Dimension(ControleurCaseSalle.WIDTH,ControleurCaseSalle.HEIGHT));
				contenant.add(jpBouton,gbc);
				gbc.gridx++;
			}
			gbc.gridy++;
			gbc.gridx=0;
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

	@Override
	public void update(Observable o, Object arg) {
	 	this.salle = (Salle)o;

	 	if(VueSalle.partieAUpdate == VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE || VueSalle.partieAUpdate == VueSalle.UPDATE_ALL) {
			this.remove(this.contenantMilieu);
			this.visualisationSalle = new JScrollPane(this.construireSalle(this.salle));
			this.visualisationSalle.setPreferredSize(new Dimension(500, 500));
			//Mise en place du controlleur
			this.contenantMilieu = new JPanel();
			this.contenantMilieu.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = gbc.gridy = 0;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.PAGE_START;
			gbc.weightx = 1;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(20, 10, 20, 0);

			JLabel titrePartieMilieu = new JLabel("Visualisation de la salle :", SwingConstants.CENTER);
			titrePartieMilieu = applicationStylePolice(titrePartieMilieu);

			this.contenantMilieu.add(titrePartieMilieu, gbc);

			gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = gbc.gridheight = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.PAGE_START;
			gbc.weightx = 1;
			gbc.weighty = 1;
			this.contenantMilieu.add(visualisationSalle, gbc);
			this.add(this.contenantMilieu, BorderLayout.CENTER);
			this.revalidate();
			this.repaint();
		}


		if(partieAUpdate == VueSalle.UPDATE_AJOUT_SALLE){
			Salle salle = new Salle(this.salle);
			this.dlm.addElement(salle);
			listeDesSalles.setSelectedIndex(this.dlm.indexOf(salle));
			contenantPartieGauche.revalidate();
			contenantPartieGauche.repaint();
			partieAUpdate=VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
		}

	}
}
