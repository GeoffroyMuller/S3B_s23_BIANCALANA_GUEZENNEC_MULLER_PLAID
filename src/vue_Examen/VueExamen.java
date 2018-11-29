package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import controleur_Examen.ControleurExamen;
import vue.BarreOutils;

public class VueExamen extends JPanel{
	
	ControleurExamen controleur_Exam = new ControleurExamen();
	/**
	 * Les JPanel "jp1" contiennent les JPanel "jp2" qui contiennent les "jp3"
	 */
	private JPanel jp1_creation = new JPanel(new GridLayout(4, 1));	//JPanel 1 contient les JPanel qui concerne la creation d'un Examen
	private JPanel jp1_affichListEtu = new JPanel();				//JPanel 1 permet de visualiser les Etudiants participant a l'Examen
	
	private JPanel jp2_infoExamen = new JPanel();		//JPanel 2 contient les JPanel de Nom, Date et de Matiere d'un Examen
	private JPanel jp2_grpParticip = new JPanel();		//JPanel 2 contient la partie de gestion des groupes pour un Examen
	private JPanel jp2_sallePriori = new JPanel();		//JPanel 2 contient la partie de gestion des salles utiliser pour un Examen
	private JPanel jp2_contrainte = new JPanel();  		//JPanel 2 contient la partie de gestion des contraintes pour un Examen
	
	private JPanel jp3_nomExamen = new JPanel();			//JPanel 3 contient le Nom de l'Examen
	private JPanel jp3_matiereExamen = new JPanel();		//JPanel 3 contient la Matiere de l'Examen
	private JPanel jp3_dateExamen = new JPanel();			//JPanel 3 contient la Date de l'Examen

	private JLabel jl_nom = new JLabel("Nom");							//JLabel Nom
	private JLabel jl_date = new JLabel("Date");						//JLabel Date
	private JLabel jl_matiere = new JLabel("Matiere");					//JLabel Matiere
	private JLabel jl_grpParticip = new JLabel("Groupe Participant"); 	//JLabel Groupe Participant
	private JLabel jl_sallePriori =new JLabel("Salle par priorité");	//JLabel Salle par priorité
	private JLabel jl_contrainte =new JLabel("Contrainte");				//JLabel Contrainte
	
	/**
	 * Constructeur principale
	 */
	public VueExamen() {
		
		/**
		 * ajout de couleur de font au JPanel
		 */
		this.setBackground(Color.black);
		
		jp1_creation.setBackground(Color.darkGray);
		jp1_affichListEtu.setBackground(Color.darkGray);
		
		jp2_infoExamen.setBackground(Color.magenta);
		jp2_grpParticip.setBackground(Color.red);
		jp2_sallePriori.setBackground(Color.magenta);
		jp2_contrainte.setBackground(Color.red);
		
		jp3_nomExamen.setBackground(Color.green);
		jp3_matiereExamen.setBackground(Color.green);
		jp3_dateExamen.setBackground(Color.green);
		
		/**
		 * ajout de label aux "jp2"
		 */
		jp2_grpParticip.add(jl_grpParticip);
		jp2_sallePriori.add(jl_sallePriori);
		jp2_contrainte.add(jl_contrainte);
		
		/**
		 * ajout de label aux "jp3"
		 */
		jp3_nomExamen.add(jl_nom);
		jp3_matiereExamen.add(jl_matiere);
		jp3_dateExamen.add(jl_date);
		
		/**
		 * ajout de JTextfield du controleur "controleur_Exam" sur "jp3"
		 */
		jp3_nomExamen.add(controleur_Exam.getJtf_nom());
		jp3_matiereExamen.add(controleur_Exam.getJtf_matiere());
		jp3_dateExamen.add(controleur_Exam.getJtf_Date());

		/**
		 * ajout de "jp3" aux "jp2"
		 */
		jp2_infoExamen.add(jp3_nomExamen);
		jp2_infoExamen.add(jp3_matiereExamen);
		jp2_infoExamen.add(jp3_dateExamen);

		/**
		 * ajout de "jp2" aux "jp1"
		 */
		jp1_creation.add(jp2_infoExamen);
		jp1_creation.add(jp2_grpParticip);
		jp1_creation.add(jp2_sallePriori);
		jp1_creation.add(jp2_contrainte);
		
		/**
		 * ajout de "jps" aux "this"
		 */
		this.add(jp1_creation);
		this.add(jp1_affichListEtu);
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
		fenetre.setSize(new Dimension(700,600));
		
		VueExamen vueExam = new VueExamen();
		vueExam.setSize(new Dimension(100, 100));
		fenetre.add(vueExam);
		
		fenetre.setVisible(true);
		
		
	}
}

