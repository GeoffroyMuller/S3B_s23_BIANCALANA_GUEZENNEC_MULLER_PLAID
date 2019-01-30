package vue_Examen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import composante_graphique.ListeurCategorie;
import composante_graphique.ListeurSalle;
import composante_graphique.PanelListeurCategorie;
import controleur_Examen.ControleurExamen;
import modele.Examen;
import modele.BDD.Categorie;

public class VueSallePriorite extends JPanel{
	private ControleurExamen ctrlexam;
	private JLabel label = new JLabel("Salle Priorité");
	private ListeurSalle listeur;
	private ControleurExamen controleur_Exam;
	private JPanel jp_all;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public VueSallePriorite(ControleurExamen ctrlexamp) {
		ctrlexam = ctrlexamp;
		listeur = new ListeurSalle(ctrlexamp);
		
		controleur_Exam = ctrlexamp;
		jp_all = new JPanel();
		this.setLayout(new GridBagLayout());
		jp_all.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 0;
		gbc.weighty = 0;
		this.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(listeur, gbc);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(162, 190, 251));
		listeur.definirTaille(this.getWidth(), this.getHeight());
		setVisible(false);
		setVisible(true);
	}


}
