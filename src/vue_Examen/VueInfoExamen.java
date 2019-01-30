package vue_Examen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur_Examen.ControleurExamen;
import modele.Examen;

public class VueInfoExamen extends JPanel{
	private Examen examen;
	private ControleurExamen controleur_Exam;
	
	private JLabel jl_nom = new JLabel("Nom");							//JLabel Nom
	private JLabel jl_date = new JLabel("Date");						//JLabel Date
	private JLabel jl_matiere = new JLabel("Matiere");					//JLabel Matiere
	
	private JPanel jp_nomExamen = new JPanel();			//JPanel 4 contient le Nom de l'Examen
	private JPanel jp_matiereExamen = new JPanel();		//JPanel 4 contient la Matiere de l'Examen
	private JPanel jp_dateExamen = new JPanel();			//JPanel 4 contient la Date de l'Examen
	
	public VueInfoExamen(ControleurExamen ctrlexamp) {
		colorer(new Color(162, 190, 251));
		controleur_Exam = ctrlexamp;                                     
		creerZoneInfo();
	}
	
	private void creerZoneInfo() {
		this.setLayout(new GridBagLayout());
		jp_nomExamen.add(jl_nom);
		jp_matiereExamen.add(jl_matiere);
		jp_dateExamen.add(jl_date);
		
		jp_nomExamen.add(controleur_Exam.getJtf_nom());
		jp_matiereExamen.add(controleur_Exam.getJtf_matiere());
		jp_dateExamen.add(controleur_Exam.getJtf_Date());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.weightx = 1;
		gbc.weighty = 0;
		this.add(jp_nomExamen, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weightx = 1;
		gbc.weighty = 0;
		this.add(jp_matiereExamen, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weightx = 1;
		gbc.weighty = 0;
		this.add(jp_dateExamen, gbc);
		
		
	}
	
	public void colorer(Color color) {
		this.setBackground(color);
		jp_dateExamen.setBackground(color);
		jp_matiereExamen.setBackground(color);
		jp_nomExamen.setBackground(color);
	}
	
	/**
	 * Definie et adapte la taille General
	 */
	public void definirTaille(int w, int h) {
		controleur_Exam.getJtf_nom().setPreferredSize(new Dimension(w, h));
		controleur_Exam.getJtf_matiere().setPreferredSize(new Dimension(w, h));
		controleur_Exam.getJtf_Date().setPreferredSize(new Dimension(w, h));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	}
	
	
	
	/*public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setMinimumSize(new Dimension(500,100));

		VueInfoExamen vuec = new VueInfoExamen(new ControleurExamen(new Examen()));
		fenetre.add(vuec);
		fenetre.setVisible(true);

	}*/
}
