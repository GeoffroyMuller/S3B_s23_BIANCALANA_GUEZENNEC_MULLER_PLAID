package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import composante_graphique.Listeur;
import controleur_Examen.ControleurExamen;
import modele.Examen;
import vue.BarreOutils;

public class VueExamen extends JPanel{
	
	private Examen examen;
	private ControleurExamen controleur_Exam = new ControleurExamen();
	
	/**
	 * Les JPanel "jp1" contiennent des JPanel "jp2" qui contiennent des "jp3" ...
	 */
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
	
	private Listeur listeur;

	private JLabel jl_nom = new JLabel("Nom");							//JLabel Nom
	private JLabel jl_date = new JLabel("Date");						//JLabel Date
	private JLabel jl_matiere = new JLabel("Matiere");					//JLabel Matiere
	private JLabel jl_grpParticip = new JLabel("Groupe Participant"); 	//JLabel Groupe Participant
	private JLabel jl_sallePriori = new JLabel("Salle par priorité");	//JLabel Salle par priorité
	private JLabel jl_contrainte = new JLabel("Contrainte");				//JLabel Contrainte
	

	/**
	 * Constructeur principale
	 */
	public VueExamen() {
		examen = new Examen();
		listeur = new Listeur(examen.getListecateg(), controleur_Exam);
		this.setBackground(Color.darkGray);
		creerZoneCreation();
		creerZoneAffichageEtu();
	}
	
	/**
	 * Créer La zone de creation d' Examen
	 */
	private void creerZoneCreation() {
		
		//ajout de couleur de font au JPanel
		jp3_infoExamen.setBackground(Color.magenta);
		jp4_grpParticip.setBackground(Color.red);
		jp4_sallePriori.setBackground(Color.magenta);
		jp4_contrainte.setBackground(Color.red);
		
		jp4_nomExamen.setBackground(Color.green);
		jp4_matiereExamen.setBackground(Color.green);
		jp4_dateExamen.setBackground(Color.green);
		
		jp2_creation.setBackground(Color.black);

		//ajout de label aux "jp4"
		jp4_grpParticip.add(jl_grpParticip);
		jp4_sallePriori.add(jl_sallePriori);
		jp4_contrainte.add(jl_contrainte);

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
		jp2_creation.add(controleur_Exam.getJb_creerExam(), BorderLayout.SOUTH);
		
		//ajout de "jp2" aux "jpp"
	    jpp_creation_marge.add(jp2_creation,BorderLayout.CENTER);
	    
		//contoure de jp2
	    JPanel contour_creation_South = new JPanel();
	    JPanel contour_creation_North = new JPanel();
	    JPanel contour_creation_East = new JPanel();
	    JPanel contour_creation_West = new JPanel();
	    
	    jpp_creation_marge.add(contour_creation_South, BorderLayout.SOUTH);
	    jpp_creation_marge.add(contour_creation_North, BorderLayout.NORTH);
	    jpp_creation_marge.add(contour_creation_East, BorderLayout.EAST);
	    jpp_creation_marge.add(contour_creation_West, BorderLayout.WEST);
		
		//ajout de "jp1" aux "this"
		this.add(jpp_creation_marge);
	}
	
	/**
	 * Créer La zone d'Affichage d'Etudiant
	 */
	private void creerZoneAffichageEtu() {
		
		jpp_affichListEtu_marge.setPreferredSize(new Dimension(100, 150));
		//ajout de couleur de font au JPanel
		jpp_affichListEtu_marge.setBackground(Color.darkGray);
		jp2_affichListEtu.setBackground(Color.blue);

		//ajout de "jp2" aux "jp1"
		jpp_affichListEtu_marge.add(jp2_affichListEtu, BorderLayout.CENTER);
		
		//contoure de jp2
		JPanel contour_affichContour_South = new JPanel();
		JPanel contour_affichContour_North = new JPanel();
		JPanel contour_affichContour_East = new JPanel();
		JPanel contour_affichContour_West = new JPanel();
		
		jpp_affichListEtu_marge.add(contour_affichContour_South,BorderLayout.SOUTH);
		jpp_affichListEtu_marge.add(contour_affichContour_North,BorderLayout.NORTH);
		jpp_affichListEtu_marge.add(contour_affichContour_East,BorderLayout.EAST);
		jpp_affichListEtu_marge.add(contour_affichContour_West,BorderLayout.WEST);
	
		// ajout de "jps" aux "this"
		this.add(jpp_affichListEtu_marge);
	}
	
	
	
	
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	}
	
	/**
	 * methode main de test interne a VueExamen
	 * @param arg
	 */
	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(new Dimension(800,600));
		
		VueExamen vueExam = new VueExamen();
		vueExam.setSize(new Dimension(100, 100));
		fenetre.add(vueExam);
		
		fenetre.setVisible(true);
		
		
	}
}

