package vue_Examen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import composante_graphique.ListeurCategorie;
import controleur_Examen.ControleurExamen;
import modele.Examen;

public class VueGroupeParticipant extends JPanel{
	private Examen examen;
	private ControleurExamen controleur_Exam = new ControleurExamen();
	private ListeurCategorie listeur;
	private GridBagConstraints gbc;
	private JLabel jl_grpParticip = new JLabel("Groupe Participant"); 
	
	public VueGroupeParticipant() {
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		examen = new Examen();
		listeur = new ListeurCategorie(examen.getListecateg(), controleur_Exam);
		creerZoneGroupeParticipant();
	}
	
	private void creerZoneGroupeParticipant() {
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		this.add(jl_grpParticip, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(listeur, gbc);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setMinimumSize(new Dimension(500,100));

		VueGroupeParticipant vuec = new VueGroupeParticipant();
		fenetre.add(vuec);
		fenetre.setVisible(true);

	}
}
