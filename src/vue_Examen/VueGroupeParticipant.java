package vue_Examen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import composante_graphique.ListeurCategorie;

//import composante_graphique.Listeur;

import controleur_Examen.ControleurExamen;
import javafx.beans.Observable;
import modele.Examen;
import modele.BDD.Categorie;

public class VueGroupeParticipant extends JPanel implements Observer{
	private ControleurExamen controleur_Exam;

	private static ListeurCategorie listeur;
	private GridBagConstraints gbc;
	private JLabel jl_grpParticip = new JLabel("Groupe Participant"); 
	private ArrayList<Categorie> listecateg;

	public VueGroupeParticipant(ControleurExamen ctrlexamp, ArrayList<Categorie> listecategp) {
		controleur_Exam = ctrlexamp;
		listecateg = listecategp;
		gbc = new GridBagConstraints();
		creerZoneGroupeParticipant(listecategp);
		
	}

	/**
	 * Creer la zone Groupe Participant
	 * @param listecategp : liste de Categories qui seront placer dans le Listeur
	 */
	public void creerZoneGroupeParticipant(ArrayList<Categorie> listecategp) {
		if(listeur!=null) {
			listeur.removeAll();
		}
		this.setLayout(new GridBagLayout());
		listeur = new ListeurCategorie(listecategp, controleur_Exam);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 0;
		gbc.weighty = 0;
		this.add(jl_grpParticip, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weightx = 1;
		gbc.weighty = 2;
		this.add(listeur, gbc);
		//repaint();
		colorer(new Color(206, 221, 253));
	}
	
	public void colorer(Color color) {
		this.setBackground(color);
		this.listeur.colorer(color);
	}
	
	
	/*public static Listeur getListeur() {
		return listeur;
	}*/


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		listeur.definirTaille(this.getWidth(), this.getHeight());
		setVisible(false);
		setVisible(true);
	}


	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setMinimumSize(new Dimension(500,100));

		VueGroupeParticipant vuec = new VueGroupeParticipant(new ControleurExamen(new Examen()), new ArrayList<Categorie>());
		fenetre.add(vuec);
		fenetre.setVisible(true);

	}


	@Override
	public void update(java.util.Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
