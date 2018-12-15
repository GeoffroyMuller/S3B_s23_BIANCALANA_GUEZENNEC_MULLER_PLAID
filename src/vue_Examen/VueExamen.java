package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import composante_graphique.ListeurCategorie;
import controleur_Examen.ControleurExamen;
import modele.Examen;
import vue.BarreOutils;

public class VueExamen extends JPanel{

	private Examen examen;
	private ControleurExamen controleur_Exam = new ControleurExamen();

	private int width_this;
	private int height_this;
	/**
	 * Les JPanel "jp1" contiennent des JPanel "jp2" qui contiennent des "jp3" ...
	 */
	private JPanel jp_all = new JPanel(new BorderLayout());	//JPanel qui contient tous les autre JPanel

	private JPanel jpp_creation_marge = new JPanel(new BorderLayout());				//JPanel principal contient les JPanel qui concerne la creation d'un Examen et contour
	private JPanel jpp_affichListEtu_marge = new JPanel(new BorderLayout());		//JPanel principal contient le JPanel de liste d'etudiant et contour

	private JPanel jp2_affichListEtu = new JPanel();	//JPanel 2 contient la Liste les Etudiants participant a l'Examen
	private JPanel jp2_creation = new JPanel(new BorderLayout());			//JPanel 2 contient les JPanel qui concerne la creation d'un Examen

	private JPanel jp3_infoExamen = new JPanel();		//JPanel 3 contient les JPanel de Nom, Date et de Matiere d'un Examen
	private JPanel jp3_centrecreation = new JPanel(new BorderLayout()); //JPanel 3 contient les JPanel de grpParticip, sallePriori, contrainte

	private JPanel jp4_grpParticip = new JPanel();		//JPanel 3 contient la partie de gestion des groupes pour un Examen
	private JPanel jp4_sallePriori = new JPanel();		//JPanel 3 contient la partie de gestion des salles utiliser pour un Examen
	private JPanel jp4_contrainte = new JPanel();  		//JPanel 3 contient la partie de gestion des contraintes pour un Examen
	private JPanel jp4_nomExamen = new JPanel();			//JPanel 4 contient le Nom de l'Examen
	private JPanel jp4_matiereExamen = new JPanel();		//JPanel 4 contient la Matiere de l'Examen
	private JPanel jp4_dateExamen = new JPanel();			//JPanel 4 contient la Date de l'Examen

	private JPanel contour_creation_South = new JPanel();
	private JPanel contour_creation_North = new JPanel();
	private JPanel contour_creation_East = new JPanel();
	private JPanel contour_creation_West = new JPanel();

	private JPanel contour_affichContour_South = new JPanel();
	private JPanel contour_affichContour_North = new JPanel();
	private JPanel contour_affichContour_East = new JPanel();
	private JPanel contour_affichContour_West = new JPanel();

	private JPanel jp_boutton = new JPanel();		//JPanel contient le boutton de creation d'examen

	private ListeurCategorie listeur;

	private JLabel jl_nom = new JLabel("Nom");							//JLabel Nom
	private JLabel jl_date = new JLabel("Date");						//JLabel Date
	private JLabel jl_matiere = new JLabel("Matiere");					//JLabel Matiere
	private JLabel jl_grpParticip = new JLabel("Groupe Participant"); 	//JLabel Groupe Participant
	private JLabel jl_sallePriori = new JLabel("Salle par priorité");	//JLabel Salle par priorité
	private JLabel jl_contrainte = new JLabel("Contrainte");				//JLabel Contrainte


	private JButton jb_creerExam = controleur_Exam.getJb_creerExam();


	/**
	 * Constructeur principale
	 */
	public VueExamen() {
		//this.setPreferredSize(new Dimension(1500, 800));
		examen = new Examen();
		listeur = new ListeurCategorie(examen.getListecateg(), controleur_Exam);
		this.setBackground(new Color(138, 138, 138));
		jp_all.setBackground(Color.BLACK);
		creerZoneCreation();
		creerZoneAffichageEtu();
		couleurJpp_marge(new Color(38, 38, 38));
		this.add(jp_all);

	}
	/**
	 * Constructeur qui prend en parametre les dimention des onglets
	 */
	public VueExamen(int w,int h) {
		width_this = w;
		height_this = h;
		System.out.println("eeeeeeee "+w+" eeeeeee "+h);
		examen = new Examen();
		listeur = new ListeurCategorie(examen.getListecateg(), controleur_Exam);
		this.setBackground(new Color(138, 138, 138));
		jp_all.setBackground(Color.BLACK);
		creerZoneCreation();
		creerZoneAffichageEtu();
		couleurJpp_marge(new Color(38, 38, 38));
		this.add(jp_all);
		
	}
	
	
	
	
	

	/**
	 * Créer La zone de creation d' Examen
	 */
	private void creerZoneCreation() {
		couleurDansJp2_creation(new Color(38, 38, 38));
		creerBordureCreation(Color.white);
		ajouterJLabel(Color.WHITE);

		//ajout de couleur de font au JPanel

		jp4_nomExamen.setBackground(Color.green);
		jp4_matiereExamen.setBackground(Color.green);
		jp4_dateExamen.setBackground(Color.green);

		jp2_creation.setBackground(Color.black);

		//ajout de label aux "jp4"
		jp4_nomExamen.add(jl_nom);
		jp4_matiereExamen.add(jl_matiere);
		jp4_dateExamen.add(jl_date);

		//ajout de JTextfield du controleur "controleur_Exam" sur "jp4"
		jp4_nomExamen.add(controleur_Exam.getJtf_nom());
		jp4_matiereExamen.add(controleur_Exam.getJtf_matiere());
		jp4_dateExamen.add(controleur_Exam.getJtf_Date());

		//ajout sur "jp4"
		jp4_grpParticip.add(listeur);

		//ajout de "jp4" aux "jp3"
		jp3_infoExamen.add(jp4_nomExamen);
		jp3_infoExamen.add(jp4_matiereExamen);
		jp3_infoExamen.add(jp4_dateExamen);
		jp3_centrecreation.add(jp4_grpParticip, BorderLayout.NORTH);
		jp3_centrecreation.add(jp4_sallePriori, BorderLayout.CENTER);
		jp3_centrecreation.add(jp4_contrainte, BorderLayout.SOUTH);

		//ajout de "jp3" aux "jp2"
		jp2_creation.add(jp3_infoExamen, BorderLayout.NORTH);
		jp2_creation.add(jp3_centrecreation, BorderLayout.CENTER);

		//ajout boutton
		jb_creerExam.setPreferredSize(new Dimension(200, 30));
		jp_boutton.add(jb_creerExam);
		jp_boutton.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		jp2_creation.add(jp_boutton, BorderLayout.SOUTH);

		//ajout de "jp2" aux "jpp"
		jpp_creation_marge.add(jp2_creation,BorderLayout.CENTER);

		//contoure de jp2
		jpp_creation_marge.add(contour_creation_South, BorderLayout.SOUTH);
		jpp_creation_marge.add(contour_creation_North, BorderLayout.NORTH);
		jpp_creation_marge.add(contour_creation_East, BorderLayout.EAST);
		jpp_creation_marge.add(contour_creation_West, BorderLayout.WEST);


		//ajout de "jpp" aux "jp_all"
		this.jp_all.add(jpp_creation_marge, BorderLayout.CENTER);
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

		
		// ajout de "jps" aux "this"
		this.jp_all.add(jpp_affichListEtu_marge, BorderLayout.EAST);

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

		jp_boutton.setBackground(colorp);
	}
	/**
	 * Met la couleur passer en parametre sur les JPanel correspondant contenu dans le JPanel Jp2_creation
	 * @param color_jp3_information_p
	 * @param color_jp4_grpPartici_p
	 * @param color_jp4_sallePriori_p
	 * @param color_jp4_contrainte_p
	 */
	private void couleurDansJp2_creation(Color color_jp3_information_p, Color color_jp4_grpPartici_p, Color color_jp4_sallePriori_p, Color color_jp4_contrainte_p) {
		jp3_infoExamen.setBackground(color_jp3_information_p);
		jp4_grpParticip.setBackground(color_jp4_grpPartici_p);
		jp4_sallePriori.setBackground(color_jp4_sallePriori_p);
		jp4_contrainte.setBackground(color_jp4_contrainte_p);
	}
	/**
	 * Met la couleur passer en parametre sur les JPanel contenu dans le JPanel Jp2_creation
	 */
	private void couleurDansJp2_creation(Color colorp) {
		jp3_infoExamen.setBackground(colorp);
		jp4_grpParticip.setBackground(colorp);
		jp4_sallePriori.setBackground(colorp);
		jp4_contrainte.setBackground(colorp);
	}
	/**
	 * ajoute les JLabel avec la couleur passer en parametre
	 * @param colorp
	 */
	private void ajouterJLabel(Color colorp) {
		jp4_grpParticip.add(jl_grpParticip);
		jp4_sallePriori.add(jl_sallePriori);
		jp4_contrainte.add(jl_contrainte);
		jl_grpParticip.setForeground(colorp);
		jl_sallePriori.setForeground(colorp);
		jl_contrainte.setForeground(colorp);
	}
	/**
	 * Creer les Bordures avec la couleur passer en parametre
	 */
	private void creerBordureCreation(Color colorp) {

		jp3_infoExamen.setBorder(BorderFactory.createMatteBorder(3, 3, 0, 3, colorp));
		jp4_grpParticip.setBorder(BorderFactory.createMatteBorder(3, 3, 0, 3, colorp));
		jp4_sallePriori.setBorder(BorderFactory.createMatteBorder(3, 3, 0, 3, colorp));
		jp4_contrainte.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, colorp));

	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Definie la taille de jp_all et des contour de jp2_affichListEtu
	 */
	private void definirTaille() {
		int ww = this.getWidth()-100;
		int hh = this.getHeight()-10;
		jp_all.setPreferredSize(new Dimension(ww, hh));

		contour_affichContour_North.setPreferredSize(new Dimension(jp_all.getWidth()/3, jp_all.getHeight()/15));
		contour_affichContour_South.setPreferredSize(new Dimension(jp_all.getWidth()/3, jp_all.getHeight()/7));
		contour_affichContour_East.setPreferredSize(new Dimension(jp_all.getWidth()/30, jp_all.getHeight()/3));
		contour_affichContour_West.setPreferredSize(new Dimension(jp_all.getWidth()/30, jp_all.getHeight()/3));
		//jp2_affichListEtu.setPreferredSize(new Dimension(jp_all.getWidth()/3, jp_all.getHeight()/15));
	}
	



	
	public void setTailleGeneral(int w, int h) {
		this.setPreferredSize(new Dimension(w, h));
		jp_all.setPreferredSize(new Dimension(w-100, h-10));
		/*contour_affichContour_North.setPreferredSize(new Dimension(jp_all.getWidth()/3, jp_all.getHeight()/15));
		contour_affichContour_South.setPreferredSize(new Dimension(jp_all.getWidth()/3, jp_all.getHeight()/7));
		contour_affichContour_East.setPreferredSize(new Dimension(jp_all.getWidth()/30, jp_all.getHeight()/3));
		contour_affichContour_West.setPreferredSize(new Dimension(jp_all.getWidth()/30, jp_all.getHeight()/3));
		*/
		contour_affichContour_North.setPreferredSize(new Dimension(100, 100));
		contour_affichContour_South.setPreferredSize(new Dimension(100, 100));
		contour_affichContour_East.setPreferredSize(new Dimension(100, 100));
		contour_affichContour_West.setPreferredSize(new Dimension(100, 100));
		
	}
	
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("THIS w: "+this.getWidth()+" h: "+this.getHeight());
		System.out.println("ALL w: "+this.jp_all.getWidth()+" h: "+this.jp_all.getHeight());
		System.out.println("AFFLISTEETU w: "+this.jp2_affichListEtu.getWidth()+" h: "+this.jp2_affichListEtu.getHeight());
	//	definirTaille();
	}
	
	
	
	
	
	
	

	/**
	 * methode main de test interne a VueExamen
	 * @param arg
	 */
/*	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(new Dimension(1049,900));

		VueExamen vueExam = new VueExamen();
		//vueExam.setSize(new Dimension(100, 100));
		fenetre.add(vueExam);
		fenetre.pack();
		fenetre.setVisible(true);

	}*/
}

