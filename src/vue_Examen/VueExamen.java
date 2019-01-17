package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import composante_graphique.PanelDev_Afficheur;
import controleur_Examen.ControleurExamen;


import modele.BDD.Categorie;
import modele.BDD.Etudiant;
import modele.BDD.Groupe;


import modele.Examen;

public class VueExamen extends JPanel implements Observer {

	private Examen examen;
	private ControleurExamen controleur_Exam;

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


	//dev
	public static PanelDev_Afficheur paneldev = new PanelDev_Afficheur();
	JScrollPane jscrol_dev;
	/**
	 * Constructeur principale
	 * @throws SQLException 
	 */
	public VueExamen() throws SQLException{
		examen = new Examen();
		controleur_Exam = new ControleurExamen(examen);
		//this.setPreferredSize(new Dimension(1500, 800));
		jpp_creation_marge.setBackground(Color.red);
		
		//testlisteur();
		//try {
			jp2_creation = new VueCreation(controleur_Exam, Categorie.getlistCategorie());	
		/*} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERREUR>>VueExamen::L'importation des catégories via la base de données a échoué.");
			jp2_creation = new VueCreation(controleur_Exam, new ArrayList<Categorie>());
		}*/
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
		
		Categorie c1 = new Categorie("Année 1");
		Categorie c2 = new Categorie("Année 2");
		listcateg.add(c1);
		listcateg.add(c2);
		c1.save();
		c2.save();
		Groupe g1 = new Groupe("groupe a");
		Groupe g2 = new Groupe("groupe b");
		Groupe g3 = new Groupe("groupe c");
		Groupe g4 = new Groupe("groupe d");
		Groupe g5 = new Groupe("groupe e");
		g1.save();
		g2.save();
		g3.save();
		g4.save();
		g5.save();
		
		gl1.add(g1);
		gl1.add(g2);
		gl1.add(g3);
		
		gl2.add(g4);
		gl2.add(g5);
		c1.ajouterGroupe(gl1);
		c2.ajouterGroupe(gl2);
		
		Etudiant e1 = new Etudiant("nna", "poilon");
		Etudiant e2 = new Etudiant("jee", "galo");
		Etudiant e3 = new Etudiant("gounalou", "lucas");
		
		e1.save();
		e2.save();
		e3.save();
		ArrayList<Etudiant> listetu = new ArrayList<>();
		listetu.add(e1);
		listetu.add(e2);
		listetu.add(e3);
		g1.ajouterEtudiants(listetu);
		
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
		
		devPane();
	}
	
	
	
	private void devPane() {
		//Developpeur a suppr
		JPanel jp_dev = new JPanel();
		jp_dev.add(paneldev);
		jp_dev.setBackground(Color.white);
		jscrol_dev = new JScrollPane(jp_dev, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//jscrol_dev.add(paneldev);
		jscrol_dev.setPreferredSize(new Dimension(300,120));
	
		
		jp2_affichListEtu.add(jscrol_dev);
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
		//dev
		jscrol_dev.setPreferredSize(new Dimension(jp2_affichListEtu.getWidth()-5, jp2_affichListEtu.getHeight()-20));//dev
		paneldev.suppliste();
		paneldev.ajouterInfo(">nombre etudiant participant:: "+examen.getEtudiants().size());
		int compte = 1;
		for(Entry<Etudiant, String> etul : examen.getEtudiants().entrySet()) {
			paneldev.ajouterInfo(""+compte+" > "+etul.getKey().getNom());
			compte++;
		}
		paneldev.repaint();
		//findev
	}
	
	public static void main(String arg[]) throws SQLException {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		VueExamen vuec = new VueExamen();
		fenetre.add(vuec);
		fenetre.setMinimumSize(new Dimension(1155,700));
		fenetre.setPreferredSize(new Dimension(1155,700));
		fenetre.setVisible(true);
		vuec.definirTaille(fenetre.getWidth(),fenetre.getHeight());
		
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}

