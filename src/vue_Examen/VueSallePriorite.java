package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import composante_graphique.ListeurCategorie;
import composante_graphique.ListeurSalle;
import composante_graphique.PanelListeurCategorie;
import composante_graphique.PanelListeurPriorite;
import controleur_Examen.ControleurExamen;
import modele.Examen;
import modele.BDD.Categorie;

public class VueSallePriorite extends JPanel{
	private static Color color = new Color(236, 241, 245);
	public static ControleurExamen ctrlexam;
	private JLabel label = new JLabel("Salle Priorité");
	private ListeurSalle listeur;
	private ControleurExamen controleur_Exam;
	private JPanel jp_all;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Examen examen;
	
	/**
	 * Constructeur
	 * @param ctrlexamp
	 * @param examp
	 */
	public VueSallePriorite(ControleurExamen ctrlexamp,Examen examp) {
		ctrlexam = ctrlexamp;
		examen = examp;
		try {
			listeur = new ListeurSalle(ctrlexamp, examen);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur:: probleme de recuperation des salles dans la base de données");
			e.printStackTrace();
		}
		
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
		
		
		JPanel miseformejb= new JPanel();
		JButton jb_ajouterSalle = new JButton("Ajouter une prioritée");
		jb_ajouterSalle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Ajouter Salle");
				listeur.ajouterPriorite();
				listeur.creerZonePriorite();
			}
		});
		jb_ajouterSalle.setPreferredSize(new Dimension(200, 30));
		miseformejb.add(jb_ajouterSalle);
		miseformejb.setBackground(color);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 15, 0);
		gbc.weightx = 1;
		gbc.weighty = 0.05;
		this.add(miseformejb, gbc);
		

	}
	/**
	 * Recharge le listeur
	 */
	public void rechargeListeur() {
		listeur.recharger();
		
	}
	
	/**
	 * Color
	 * @param color_
	 */
	public static void setColor_(Color color_) {
		VueSallePriorite.color = color_;
	}


	/**
	 * paintComponent
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(color);
		listeur.definirTaille(this.getWidth(), this.getHeight());
	}


}
