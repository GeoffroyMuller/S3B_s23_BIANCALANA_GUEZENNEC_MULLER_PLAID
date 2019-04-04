package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import controleur_Examen.ControleurExamen;
import modele.Examen;
import modele.BDD.Categorie;

public class VueCreation extends JPanel{
	private ControleurExamen controleur_Exam;
	
	private static Color color = new Color(40, 73, 92);
	private GridBagConstraints gbc = new GridBagConstraints();
	private ArrayList<Categorie> listecateg = new ArrayList<>();
	
	private VueInfoExamen vue_infoExam;
	private VueGroupeParticipant vue_grpParticip;
	private VueSallePriorite vue_sallePrio;
	private VueContrainte vue_contrainte;
	private JPanel jp_bouttonexam = new JPanel(new BorderLayout());
	private Examen examen;
	
	public VueCreation(ControleurExamen ctrlexamp, ArrayList<Categorie> listecategp, Examen examen) {
		this.examen = examen;
		controleur_Exam = ctrlexamp;
		listecateg = listecategp;
		vue_contrainte = new VueContrainte(this.examen,this.controleur_Exam);
		vue_infoExam = new VueInfoExamen(controleur_Exam);
		vue_sallePrio = new VueSallePriorite(controleur_Exam,examen);
		vue_grpParticip = new VueGroupeParticipant(controleur_Exam, listecateg);
		this.setBackground(color);
		creerZoneCreation();
	}
	
	private void creerZoneCreation() {
		Color colorp = Color.black;
		vue_infoExam.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, colorp));
		vue_grpParticip.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, colorp));
		vue_sallePrio.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, colorp));
		vue_contrainte.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, colorp));
		this.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		this.add(vue_infoExam, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0;
		gbc.weighty = 0.5;
		this.add(vue_grpParticip, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0;
		gbc.weighty = 0.5;
		this.add(vue_sallePrio, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		this.add(vue_contrainte, gbc);
		jp_bouttonexam.setBackground(color);
		controleur_Exam.getJb_creerExam().setPreferredSize(new Dimension(200, 40));
		jp_bouttonexam.add(controleur_Exam.getJb_creerExam(), BorderLayout.WEST);
		jp_bouttonexam.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0;
		gbc.weighty = 0.03;

		this.add(jp_bouttonexam, gbc);
	}
	
	public VueGroupeParticipant getVue_grpParticip() {
		return vue_grpParticip;
	}

	
	public VueSallePriorite getVue_sallePrio() {
		return vue_sallePrio;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
