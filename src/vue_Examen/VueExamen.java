package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import composante_graphique.PanelDev_Afficheur;
import controleur_Examen.ControleurExamen;
import modele.BDD.Categorie;
import modele.BDD.Etudiant;


import modele.Examen;

public class VueExamen extends JPanel implements Observer{
	public final static int VUE_ETU = 200;
	public final static int VUE_CATEG = 100;
	public final static int VUE_CATEG_SAVE = 101;
	public final static int INIT = 1;
	public static Examen examen;
	public static VueExamen vueExamen;
	private ControleurExamen controleur_Exam;
	private static Color color = new Color(40, 73, 92);//40, 73, 92
	
	private JPanel jp_all = new JPanel(new GridBagLayout());	//JPanel qui contient tous les autre JPanel

	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel jpp_creation_marge = new JPanel(new BorderLayout());				//JPanel principal contient les JPanel qui concerne la creation d'un Examen et contour
	private JPanel jpp_affichListEtu_marge = new JPanel(new BorderLayout());		//JPanel principal contient le JPanel de liste d'etudiant et contour

	private JPanel jp2_affichListEtu = new JPanel();	//JPanel 2 contient la Liste les Etudiants participant a l'Examen
	private VueCreation jp2_creation;			//JPanel 2 contient les JPanel qui concerne la creation d'un Examen


	private JPanel contour_creation_South = new JPanel();
	private JPanel contour_creation_North = new JPanel();
	private JPanel contour_creation_East = new JPanel();
	private JPanel contour_creation_West = new JPanel();

	private JPanel contour_affichContour_South = new JPanel();
	private JPanel contour_affichContour_North = new JPanel();
	private JPanel contour_affichContour_East = new JPanel();
	private JPanel contour_affichContour_West = new JPanel();

	private VueEtudiantParticipant vue_etudiantparticipant;

	//dev
	public static PanelDev_Afficheur paneldev = new PanelDev_Afficheur();
	JScrollPane jscrol_dev;
	//fin dev
	
	/**
	 * Constructeur principale
	 * @throws SQLException 
	 */
	public VueExamen(Examen exam) throws SQLException{
		vueExamen = this;
		examen = exam;
		vue_etudiantparticipant = new VueEtudiantParticipant(examen);
		controleur_Exam = new ControleurExamen(examen);

		jpp_creation_marge.setBackground(Color.red);

		jp2_creation = new VueCreation(controleur_Exam, Categorie.getlistCategorie(),examen);

		creerZoneCreation();
		creerZoneAffichageEtu();
		colorer(color);
		placerElementPrincipaux();
		this.add(jp_all);
		definirTaille(1000, 300);
	}
	static public Examen getModeleExamen() {
		return examen;
	}

	/**
	 * Créer La zone de creation d' Examen
	 */
	private void creerZoneCreation() {

		jpp_creation_marge.add(jp2_creation,BorderLayout.CENTER);

		jpp_creation_marge.add(contour_creation_South, BorderLayout.SOUTH);
		jpp_creation_marge.add(contour_creation_North, BorderLayout.NORTH);
		jpp_creation_marge.add(contour_creation_East, BorderLayout.EAST);
		jpp_creation_marge.add(contour_creation_West, BorderLayout.WEST);

		this.jp_all.add(jpp_creation_marge);
	}

	/**
	 * Créer La zone d'Affichage d'Etudiant
	 */
	private void creerZoneAffichageEtu() {

		//ajout de couleur de font au JPanel
		jpp_affichListEtu_marge.setBackground(color);
		jp2_affichListEtu.setBackground(Color.white);

		vue_etudiantparticipant.setBackground(Color.blue);
		vue_etudiantparticipant.definirTaille(500, 500);

		jpp_affichListEtu_marge.add(vue_etudiantparticipant, BorderLayout.CENTER);

		jpp_affichListEtu_marge.add(contour_affichContour_South,BorderLayout.SOUTH);
		jpp_affichListEtu_marge.add(contour_affichContour_North,BorderLayout.NORTH);
		jpp_affichListEtu_marge.add(contour_affichContour_East,BorderLayout.EAST);
		jpp_affichListEtu_marge.add(contour_affichContour_West,BorderLayout.WEST);

		jp2_affichListEtu.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

		// ajout de "jps" aux "this"
		this.jp_all.add(jpp_affichListEtu_marge);

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
	 * Color
	 * @param color
	 */
	public void colorer(Color color) {
		this.setBackground(color);
		jp_all.setBackground(color);
		couleurJpp_marge(color);
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

	/**
	 * Place les Elements Principaux (les plus Grosse Vues qui compose VueExamen)
	 */
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


	/**
	 * Getter Vue_etudiantparticipant
	 * @return VueEtudiantParticipant vue_etudiantparticipant
	 */
	public VueEtudiantParticipant getVue_etudiantparticipant() {
		return vue_etudiantparticipant;
	}
	
	/**
	 * paintComponent
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		vue_etudiantparticipant.definirTaille(jpp_affichListEtu_marge.getWidth()-90, jpp_affichListEtu_marge.getHeight());
	}

	/**
	 * Remet le listeur de salle a zero
	 */
	public static void rechargerlisteurSalle() {
		vueExamen.jp2_creation.getVue_sallePrio().rechargeListeur();
	}

	/**
	 * Recharge toute la Vue
	 */
	public static void rechargerAll() {
		ArrayList<Etudiant> liste_supp_etu  = new ArrayList<Etudiant>();
		if(examen.getEtudiants().size()>=0) {
			for(Etudiant etu : examen.getEtudiants().keySet()){
				liste_supp_etu.add(etu);
			}

			examen.enleverDesEtudiantsDeExamen(liste_supp_etu);
		}
		vueExamen.vue_etudiantparticipant.ajouterListes(examen.getEtudiants());
		((VueCreation) vueExamen.jp2_creation).getVue_grpParticip().creerZoneGroupeParticipant(Categorie.getlistCategorie());
		rechargerlisteurSalle();
	}

	/**
	 * Update
	 */
	@Override
	public void update(Observable o, Object arg) {
		int cas = INIT;
		if(arg!=null) {
			cas =(int)arg;
		}
		switch(cas) {
		case VUE_ETU:
			vue_etudiantparticipant.ajouterListes(examen.getEtudiants());
			break;
		case VUE_CATEG:
			ArrayList<Etudiant> liste_supp_etu  = new ArrayList<Etudiant>();
			if(examen.getEtudiants().size()>=0) {
				for(Etudiant etu : examen.getEtudiants().keySet()){
					liste_supp_etu.add(etu);
				}

				examen.enleverDesEtudiantsDeExamen(liste_supp_etu);
			}
			vue_etudiantparticipant.ajouterListes(examen.getEtudiants());
			((VueCreation) jp2_creation).getVue_grpParticip().creerZoneGroupeParticipant(Categorie.getlistCategorie());	
			
			break;
		case VUE_CATEG_SAVE:
			ArrayList<Etudiant> liste_supp_etu1  = new ArrayList<Etudiant>();
			if(examen.getEtudiants().size()>=0) {
				for(Etudiant etu : examen.getEtudiants().keySet()){
					liste_supp_etu1.add(etu);
				}

				examen.enleverDesEtudiantsDeExamen(liste_supp_etu1);
			}
			vue_etudiantparticipant.ajouterListes(examen.getEtudiants());
			jp2_creation.getVue_grpParticip().sauvegarder();
			((VueCreation) jp2_creation).getVue_grpParticip().creerZoneGroupeParticipant(Categorie.getlistCategorie());
			jp2_creation.getVue_grpParticip().charger();
			
			break;
		default:

			break;
		}
		repaint();


	}


}

