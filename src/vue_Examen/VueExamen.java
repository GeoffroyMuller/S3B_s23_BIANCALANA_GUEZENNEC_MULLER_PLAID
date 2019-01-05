package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import composante_graphique.PanelDev_Afficheur;
import controleur_Examen.ControleurExamen;


import modele.BDD.Categorie;
import modele.BDD.Groupe;


import modele.Examen;
import modele.BDD.Categorie;
import modele.BDD.Groupe;
import vue.BarreOutils;

public class VueExamen extends JPanel{

	private Examen examen;
	private ControleurExamen controleur_Exam = new ControleurExamen();

	/**
	 * Les JPanel "jp1" contiennent des JPanel "jp2" qui contiennent des "jp3" ...
	 */
	private JPanel jp_all = new JPanel(new GridBagLayout());	//JPanel qui contient tous les autre JPanel
	
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel jpp_creation_marge = new JPanel(new BorderLayout());				//JPanel principal contient les JPanel qui concerne la creation d'un Examen et contour
	private JPanel jpp_affichListEtu_marge = new JPanel(new BorderLayout());		//JPanel principal contient le JPanel de liste d'etudiant et contour

	private JPanel jp2_affichListEtu = new JPanel();	//JPanel 2 contient la Liste les Etudiants participant a l'Examen
	private JPanel jp2_creation;			//JPanel 2 contient les JPanel qui concerne la creation d'un Examen
	
	
	private JPanel contour_creation_South = new JPanel();
	private JPanel contour_creation_North = new JPanel();
	private JPanel contour_creation_East = new JPanel();
	private JPanel contour_creation_West = new JPanel();

	private JPanel contour_affichContour_South = new JPanel();
	private JPanel contour_affichContour_North = new JPanel();
	private JPanel contour_affichContour_East = new JPanel();
	private JPanel contour_affichContour_West = new JPanel();



	private PanelDev_Afficheur paneldev = new PanelDev_Afficheur();
	/**
	 * Constructeur principale
	 */
	public VueExamen() {
		//this.setPreferredSize(new Dimension(1500, 800));
		jpp_creation_marge.setBackground(Color.red);
		examen = new Examen();
		testlisteur();
		jp2_creation = new VueCreation(controleur_Exam, examen);	
		this.setBackground(new Color(138, 138, 138));
		jp_all.setBackground(new Color(138, 138, 138));
		creerZoneCreation();
		creerZoneAffichageEtu();
		couleurJpp_marge(new Color(138, 138, 138));
		placerElementPrincipaux();
		this.add(jp_all);
		definirTaille(1000, 300);
	}
	
	private void testlisteur() {
		ArrayList<Categorie> listcateg = new ArrayList<>();
		ArrayList<Groupe> gl1 = new ArrayList<Groupe>();
		ArrayList<Groupe> gl2 = new ArrayList<Groupe>();
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		
		gl2.add(new Groupe("groupe2 A"));
		gl2.add(new Groupe("groupe2 B"));
		Categorie c1 = new Categorie("Année 1", gl1);
		Categorie c2 = new Categorie("Année 2", gl2);	
		listcateg.add(c1);
		listcateg.add(c2);
		Categorie.setListeCateg(listcateg);
	}
	

	/**
	 * Créer La zone de creation d' Examen
	 */
	private void creerZoneCreation() {

		//ajout de "jp2" aux "jpp"
		jpp_creation_marge.add(jp2_creation,BorderLayout.CENTER);

		//contoure de jp2
		jpp_creation_marge.add(contour_creation_South, BorderLayout.SOUTH);
		jpp_creation_marge.add(contour_creation_North, BorderLayout.NORTH);
		jpp_creation_marge.add(contour_creation_East, BorderLayout.EAST);
		jpp_creation_marge.add(contour_creation_West, BorderLayout.WEST);
		
		

		//ajout de "jpp" aux "jp_all"
		this.jp_all.add(jpp_creation_marge);
	}
	
	
	
	

	/**
	 * Créer La zone d'Affichage d'Etudiant
	 */
	private void creerZoneAffichageEtu() {

		//ajout de couleur de font au JPanel
		jpp_affichListEtu_marge.setBackground(Color.darkGray);
		jp2_affichListEtu.setBackground(Color.white);
		
		//jp2_affichListEtu.setPreferredSize(new Dimension(200, 200));
		//ajout de "jp2" aux "jp1"
		jpp_affichListEtu_marge.add(jp2_affichListEtu, BorderLayout.CENTER);

		
		//contoure de jp2
		jpp_affichListEtu_marge.add(contour_affichContour_South,BorderLayout.SOUTH);
		jpp_affichListEtu_marge.add(contour_affichContour_North,BorderLayout.NORTH);
		jpp_affichListEtu_marge.add(contour_affichContour_East,BorderLayout.EAST);
		jpp_affichListEtu_marge.add(contour_affichContour_West,BorderLayout.WEST);
		
		jp2_affichListEtu.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		
		// ajout de "jps" aux "this"
		this.jp_all.add(jpp_affichListEtu_marge);
		
		jp2_affichListEtu.add(paneldev);
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Met la couleur passer en parametre sur les JPanel jpp_[...]_marge et du jpanel jp_boutton
	 */
	private void couleurJpp_marge(Color colorp) {
		contour_creation_South.setBackground(colorp);
		contour_creation_North.setBackground(colorp);
		contour_creation_East.setBackground(colorp);
		contour_creation_West.setBackground(colorp);

		contour_affichContour_South.setBackground(colorp); 
		contour_affichContour_North.setBackground(colorp);
		contour_affichContour_East.setBackground(colorp); 
		contour_affichContour_West.setBackground(colorp);

	}


	
	
	
	/**
	 * Definie et adapte la taille General
	 */
	public void definirTaille(int w, int h) {
		this.setPreferredSize(new Dimension(w, h));
		jp_all.setPreferredSize(new Dimension(w-100, h-40));

		int width = w/4;
		jp2_affichListEtu.setPreferredSize(new Dimension(width, h));
		contour_affichContour_North.setPreferredSize(new Dimension(100, 40));
		contour_affichContour_South.setPreferredSize(new Dimension(100, 80));
		contour_affichContour_East.setPreferredSize(new Dimension(30, 40));
		contour_affichContour_West.setPreferredSize(new Dimension(30, 40));
		
		contour_creation_West.setPreferredSize(new Dimension(40, 0));
		
	}
	
	public void placerElementPrincipaux() {
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.4;
		gbc.weighty = 2;
		jp_all.add(jpp_creation_marge, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.3;
		gbc.weighty = 1;
		jp2_affichListEtu.setPreferredSize(new Dimension(100, 200));
		jp_all.add(jpp_affichListEtu_marge, gbc);
	}
	
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paneldev.ajouterInfo("THIS w: "+getWidth()+" h: "+this.getHeight());
		paneldev.ajouterInfo("jp2_affichlistEtu w: "+jp2_affichListEtu.getWidth()+" h: "+jp2_affichListEtu.getHeight());
		paneldev.ajouterInfo("> mettre a This w: 1138 pour minimal");
	}
	
	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		VueExamen vuec = new VueExamen();
		fenetre.add(vuec);
		fenetre.setMinimumSize(new Dimension(1155,900));
		fenetre.setPreferredSize(new Dimension(1155,900));
		fenetre.setVisible(true);
		vuec.definirTaille(fenetre.getWidth(),fenetre.getHeight());
		
	}
	
}

