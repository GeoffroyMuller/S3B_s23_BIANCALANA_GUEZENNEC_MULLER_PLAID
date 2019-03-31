package vue;

import controleur.ControleurBoutonsPartieSalle;
import controleur.ControleurModuleSalle.ControleurCaseSalle;
import controleur.ControleurModuleSalle.ControleurModifierNomSalle;
import controleur.ControleurModuleSalle.ControleurRadioBoutons;
import controleur.ControleurModuleSalle.ControleurSauvegardeSalle;
import controleur.OutilsSalleDialog;
import modele.BDD.Etudiant;
import modele.BDD.Place;
import modele.BDD.Salle;
import modele.BDD.TypePlace;
import modele.Examen;
import modele.ProprietesCaseSalle;
import module_etudiant.VueModuleEtudiant;
import vue.ComposantVueSalle.Indicateur;
import vue_Examen.DialogVerificationPlacement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe permettant la création de la vue du module salle, c'est dans cette vue que son crée les controleurs associés (Boutons "Ajouter" et "Supprimer" et les boutons radios
 */
public class VueSalle extends JPanel implements Observer {
	public static JLabel SORTIE = new JLabel("Bureau");
	private JScrollPane containerDeLaListeJScroll;
	private JScrollPane visualisationSalle;
	private JPanel contenantPartieGauche;
	private JPanel contenantMilieu;
	private Salle salle;
	private JPanel salleConstruite;
	private JLabel nomRangee,nomColonne;
	private DefaultListModel<Salle> dlm;
	private JLabel labelDeLaListe;
	public static Salle salleSelectionne;
	/**
	 * Correspond a la partie qui doit être mise à jour, (1 = partie gauche, 2= milieu, 3=partie de droite)
	 */
	public static int partieAUpdate;

	private JList<Salle> listeDesSalles;
	private Border bordureJtextFieldBase;
	private ProprietesCaseSalle proprieteDeLaSalle;
	private ControleurCaseSalle dernierePlaceRechercher;

	public static int UPDATE_CREATION_SALLE = 1;
	public static int UPDATE_PARTIE_AFFICHAGE_SALLE = 2;
	public static int UPDATE_AJOUT_SALLE = 3;
	public static int UPDATE_ALL = -1;
	public static int UPDATE_NOTHING = 0;




	/**
	 * Constructeur de la vue salle, construit également les controleurs necessaires (ControleurCaseSalle et ControleurRadioBoutons)
	 */
	public VueSalle(final Salle salleModele){

		VueSalle.SORTIE.setVerticalAlignment(SwingConstants.CENTER);
		VueSalle.SORTIE.setHorizontalAlignment(SwingConstants.CENTER);
		VueSalle.SORTIE.setFont(new Font("Serial",Font.PLAIN,20));

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
		try{
			VueSalle.salleSelectionne = dlm.firstElement();
		}catch(NoSuchElementException e){
			VueSalle.salleSelectionne = null;
		}
		listeDesSalles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				VueSalle.partieAUpdate = VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
				VueSalle.salleSelectionne = listeDesSalles.getSelectedValue();
				String nomSalle = listeDesSalles.getSelectedValue().getNom();
				Salle salleSelectionne = Salle.findByNom(nomSalle);
				salle.changerSalle(salleSelectionne);
			}
		});

		this.containerDeLaListeJScroll = new JScrollPane(listeDesSalles);

		//Ajout dans un conteneur
		contenantPartieGauche = new JPanel();
		//contenantPartieGauche.setBorder(new EmptyBorder(0,20,0,0));
		contenantPartieGauche.setLayout(new BorderLayout());
		contenantPartieGauche.setPreferredSize(new Dimension(200,this.getHeight()));
		contenantPartieGauche.add(labelDeLaListe,BorderLayout.NORTH);
		contenantPartieGauche.add(containerDeLaListeJScroll,BorderLayout.CENTER);

		//Creation du controleur
		ControleurBoutonsPartieSalle boutons = new ControleurBoutonsPartieSalle(this.salle);

		//Partie visualisation de la liste (Partie du milieux)
		this.salleConstruite = this.construireSalle(this.salle);
		this.visualisationSalle = new JScrollPane(this.salleConstruite);
		this.visualisationSalle.setPreferredSize(new Dimension(930,600));
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

		//Champs de recherche d'une place
		JLabel nomColonne = new JLabel("Nom colonne : ");
		JLabel nomRangee = new JLabel("Nom rangee : ");

		final JTextField textNomColonne = new JTextField();
		final JTextField textNomRangee = new JTextField();
		textNomColonne.setPreferredSize(new Dimension(VueModuleEtudiant.TEXTFIELD_WIDTH,VueModuleEtudiant.TEXTFIELD_HEIGHT));
		textNomRangee.setPreferredSize(new Dimension(VueModuleEtudiant.TEXTFIELD_WIDTH,VueModuleEtudiant.TEXTFIELD_HEIGHT));
		this.bordureJtextFieldBase = textNomColonne.getBorder();
		JButton rechercher = new JButton("Rechercher");

		rechercher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(null != dernierePlaceRechercher)dernierePlaceRechercher.switchColor(dernierePlaceRechercher.getCouleurCaseBase());
				if(textNomColonne.getText().isEmpty() || textNomRangee.getText().isEmpty()){
					if(textNomColonne.getText().isEmpty()) {
						textNomColonne.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(0xAA0C00)));
					}else{
						textNomColonne.setBorder(bordureJtextFieldBase);
					}
					if(textNomRangee.getText().isEmpty()) {
						textNomRangee.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(0xAA0C00)));
					}else{
						textNomRangee.setBorder(bordureJtextFieldBase);
					}
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null,"Veuillez renseigner tout les champs","Message Informatif",JOptionPane.INFORMATION_MESSAGE);
				}else{
					textNomColonne.setBorder(bordureJtextFieldBase);
					textNomRangee.setBorder(bordureJtextFieldBase);

					for(ControleurCaseSalle caseSalle : proprieteDeLaSalle.getListeControleurs()){
						if(caseSalle.getPlace().getNomColonne().toLowerCase().equals(textNomColonne.getText().toLowerCase()) &&
								caseSalle.getPlace().getNomRangee().toLowerCase().equals(textNomRangee.getText().toLowerCase())){
							caseSalle.switchColor(new Color(0x23FF00));
							dernierePlaceRechercher = caseSalle;
							break;
						}
					}

					if(null == dernierePlaceRechercher){
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null,"La place demandée n'a pas été trouvé ! \n Veuillez vérifier les informations indiquées","La place n'a pas été trouvée",JOptionPane.INFORMATION_MESSAGE);
					}


				}
			}
		});

		JPanel contenantRecherche = new JPanel();
		contenantRecherche.setLayout(new GridBagLayout());
		Border bordurecolor = new LineBorder(Color.BLACK);
		contenantRecherche.setBorder(BorderFactory.createTitledBorder(bordurecolor, "Recherche Place"));

		gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.anchor = GridBagConstraints.BASELINE;
		gbc.insets = new Insets(5,10,10,0);
		contenantRecherche.add(nomRangee,gbc);

		gbc.gridx=1;
		contenantRecherche.add(textNomRangee,gbc);

		gbc.gridx=2;
		contenantRecherche.add(nomColonne,gbc);

		gbc.gridx=3;
		contenantRecherche.add(textNomColonne,gbc);

		gbc.gridx=4;
		contenantRecherche.add(rechercher,gbc);

		gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=1;
		this.contenantMilieu.add(contenantRecherche,gbc);


		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.contenantMilieu.add(visualisationSalle, gbc);


		//Partie selection (Partie de droite)
		//Composant Indications
		JPanel indications = new Indicateur();
		indications.setPreferredSize(new Dimension(400,200));
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
		JButton modifierInfo = new ControleurModifierNomSalle(salle);

		sauvegarde.setPreferredSize(new Dimension(200,20));

		JPanel containerBouton = new JPanel();
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridBagLayout());
		JButton outils = new JButton("Outils");
		outils.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OutilsSalleDialog outilsSalleDialog = new OutilsSalleDialog(null,"Outils Salle",true,salle);
				outilsSalleDialog.afficherDialog();
			}
		});
		GridBagConstraints gbcv2 = new GridBagConstraints();

		gbcv2.gridx = 0;
		gbcv2.gridy = 0;
		gbcv2.anchor = GridBagConstraints.LINE_START;
		panelBoutons.add(sauvegarde,gbcv2);
		gbcv2.gridy=1;
		panelBoutons.add(modifierInfo,gbcv2);

		gbcv2.insets = new Insets(10,0,5,0);
		gbcv2.gridy=2;
		panelBoutons.add(outils,gbcv2);

		//PARTIE NOM PLACE
		this.nomColonne = new JLabel("Nom colonne : ");
		this.nomRangee = new JLabel("Nom rangee : ");
		//END PARTIE NOM PLACE

		gbcv2.gridy=3;
		panelBoutons.add(this.nomRangee,gbcv2);
		gbcv2.gridy=4;
		panelBoutons.add(this.nomColonne,gbcv2);

		containerBouton.setLayout(new BorderLayout());
		containerBouton.setPreferredSize(new Dimension(250,460));
		containerBouton.add(panelBoutons,BorderLayout.NORTH);




		editionPan.add(edition, BorderLayout.NORTH);
		editionPan.add(partieEdition, BorderLayout.CENTER);
		editionPan.add(containerBouton,BorderLayout.SOUTH);
		editionPan.setPreferredSize(new Dimension(250,300));
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
		super.paintComponent(g);
		//this.contenantPartieGauche.setPreferredSize(new Dimension( (this.getParent().getWidth())/5, this.getParent().getHeight()));
		//this.visualisationSalle.setPreferredSize(new Dimension((this.getWidth()-(480)),(this.getHeight()-130)));

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

	public static ProprietesCaseSalle construireSalleDialogBox(Salle salle, DialogVerificationPlacement dialog, Examen examen){
		JPanel contenant = new JPanel();
		contenant.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx= gbc.gridy = 0;
		gbc.gridheight = gbc.gridwidth = 1;
		gbc.insets = new Insets(2,2,0,2);
		ArrayList<ControleurCaseSalle> liste= new ArrayList<ControleurCaseSalle>();
		for(int i = 0; i < salle.getNbCaseHauteur();i++){
			for(int j = 0; j < salle.getNbCaseLargeur();j++){
				JPanel jpBouton = new JPanel();
				jpBouton.setLayout(new BorderLayout());
				Place place = salle.getPlaces()[i][j];
				TypePlace typePlace = TypePlace.findById(place.getIdTypePlace());
				Color couleurPlace = null;
				couleurPlace = TypePlace.trouverCouleurPlace(typePlace.getNom());
				ControleurCaseSalle controleur = new ControleurCaseSalle(couleurPlace,i,j,salle,dialog,examen);
				liste.add(controleur);
				place.addObserver(controleur);
				jpBouton.add(controleur);
				jpBouton.setPreferredSize(new Dimension(ControleurCaseSalle.WIDTH,ControleurCaseSalle.HEIGHT));
				contenant.add(jpBouton,gbc);
				gbc.gridx++;
			}
			gbc.gridy++;
			gbc.gridx=0;
		}
		return new ProprietesCaseSalle(contenant,liste);
	}

	private JPanel construireSalle(Salle salle){
		JPanel superContenant = new JPanel();
		superContenant.setLayout(new BorderLayout());
		//superContenant.add(VueSalle.ENTREE, BorderLayout.NORTH);
		superContenant.add(VueSalle.SORTIE, BorderLayout.SOUTH);
		JPanel contenant = new JPanel();
		contenant.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx= gbc.gridy = 0;
		gbc.gridheight = gbc.gridwidth = 1;
		gbc.insets = new Insets(2,2,0,2);
		ArrayList<ControleurCaseSalle> listControleur = new ArrayList<ControleurCaseSalle>();
		for(int i = 0; i < salle.getNbCaseHauteur();i++){
			for(int j = 0; j < salle.getNbCaseLargeur();j++){
				JPanel jpBouton = new JPanel();
				jpBouton.setLayout(new BorderLayout());
				Place place = salle.getPlaces()[i][j];
				int idTypePlace = place.getIdTypePlace();
				TypePlace typePlace = TypePlace.findById(place.getIdTypePlace());
				Color couleurPlace = TypePlace.trouverCouleurPlace(typePlace.getNom());
				ControleurCaseSalle controleur = new ControleurCaseSalle(couleurPlace,i,j,this.salle);
				listControleur.add(controleur);
				controleur.ajouterVueSalle(this);
				place.addObserver(controleur);
				jpBouton.add(controleur);
				jpBouton.setPreferredSize(new Dimension(ControleurCaseSalle.WIDTH,ControleurCaseSalle.HEIGHT));
				contenant.add(jpBouton,gbc);
				gbc.gridx++;
			}
			gbc.gridy++;
			gbc.gridx=0;
		}
		superContenant.add(contenant,BorderLayout.CENTER);
		//return contenant;
		this.proprieteDeLaSalle = new ProprietesCaseSalle(superContenant,listControleur);
		return superContenant;
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
		int oldValueScrollBarH = this.visualisationSalle.getHorizontalScrollBar().getValue();
		int oldValueScrollBarV = this.visualisationSalle.getVerticalScrollBar().getValue();

		if(VueSalle.partieAUpdate == VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE || VueSalle.partieAUpdate == VueSalle.UPDATE_ALL
				|| partieAUpdate == VueSalle.UPDATE_CREATION_SALLE) {
			System.out.println("OUIII");
			this.salleConstruite = this.construireSalle(this.salle);
			this.visualisationSalle.setViewportView(this.salleConstruite);
			this.visualisationSalle.getHorizontalScrollBar().setValue(oldValueScrollBarH);
			this.visualisationSalle.getVerticalScrollBar().setValue(oldValueScrollBarV);
			/*this.revalidate();
			this.repaint();*/
		}


		if(partieAUpdate == VueSalle.UPDATE_AJOUT_SALLE || partieAUpdate == VueSalle.UPDATE_CREATION_SALLE){
			System.out.println("Oui ca marche");
			Salle salle = new Salle(this.salle);
			this.dlm.addElement(salle);
			listeDesSalles.setSelectedIndex(this.dlm.indexOf(salle));
			contenantPartieGauche.revalidate();
			contenantPartieGauche.repaint();
			partieAUpdate=VueSalle.UPDATE_PARTIE_AFFICHAGE_SALLE;
		}
	}

	public  void mettreAJourInfoPlace(String nomRangee, String nomColonne){
		this.nomRangee.setText("Nom rangée : "+nomRangee);
		this.nomColonne.setText("Nom colonne : "+nomColonne);
	}
}
