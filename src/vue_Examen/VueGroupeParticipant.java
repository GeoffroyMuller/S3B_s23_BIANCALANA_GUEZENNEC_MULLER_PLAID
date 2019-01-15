package vue_Examen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import composante_graphique.Listeur;

//import composante_graphique.Listeur;

import controleur_Examen.ControleurExamen;
import modele.Examen;
import modele.BDD.Categorie;

public class VueGroupeParticipant extends JPanel{
	private ControleurExamen controleur_Exam;

	public static Listeur listeur;
	private GridBagConstraints gbc;
	private JLabel jl_grpParticip = new JLabel("   Groupe Participant"); 

	public VueGroupeParticipant(ControleurExamen ctrlexamp, ArrayList<Categorie> listecateg) {
		controleur_Exam = ctrlexamp;


		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		listeur = new Listeur(listecateg, ctrlexamp);
		creerZoneGroupeParticipant();
	}

	public static Listeur getListeur(){
		return listeur;
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
		listeur.definirTaille(this.getWidth(), this.getHeight());
	}

	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setMinimumSize(new Dimension(500,100));

		VueGroupeParticipant vuec = new VueGroupeParticipant(new ControleurExamen(new Examen()), new ArrayList<Categorie>());
		fenetre.add(vuec);
		fenetre.setVisible(true);

	}
}
