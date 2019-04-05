package vue_Examen;

import modele.Examen;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import controleur_Examen.ControleurExamen;

public class VueContrainte extends JPanel{
	private static Color color = new Color(236, 241, 245);
	private ControleurExamen ctrlExamen;
	private JLabel jl_containte;
	private JPanel jp_espacement;
	private JPanel jp_grpEtudiant;
	private Checkbox checkGrp;
	private GridBagConstraints gbc;
	private JComboBox jcb_espacement;
	private String[] tab_espacement = {"0", "1", "2", "3", "4", "5"};
	public static Examen examen;
	
	/**
	 * Constructeur
	 * @param examen
	 * @param ctrlExamenp
	 */
	public VueContrainte(Examen examen,ControleurExamen ctrlExamenp) {
		VueContrainte.examen = examen;
		ctrlExamen = ctrlExamenp;
		this.setBackground(new Color(236, 241, 245));
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		jl_containte = new JLabel("   Contrainte");
		jp_espacement = new JPanel();
		jp_grpEtudiant = new JPanel();
		
		jcb_espacement = new JComboBox(tab_espacement);
		jcb_espacement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int pas = Integer.parseInt(tab_espacement[jcb_espacement.getSelectedIndex()]);
				System.out.println("Pas selectionné : "+pas+" "+VueContrainte.examen.getNom());
				VueContrainte.examen.setPas(pas);
			}
		});
		
		jp_espacement.setPreferredSize(new Dimension(100, 60));
		creerZoneContrainte();
	}
	
	/**
	 * Permet de creer le JPanel de gestion de contrainte 
	 */
	public void creerZoneContrainte() {
		Border bordurecolor = new LineBorder(Color.BLACK);
		jp_espacement.setBorder(BorderFactory.createTitledBorder(bordurecolor, "Espacement"));
		jp_grpEtudiant.setBorder(BorderFactory.createTitledBorder(bordurecolor, "Groupe Etudiant"));
		checkGrp = ctrlExamen.getCheckboxc();
		
		checkGrp.setBackground(color);
		jp_grpEtudiant.add(checkGrp);
		
		jp_espacement.add(jcb_espacement);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0.1;
		this.add(jl_containte, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.140;
		gbc.weighty = 0.1;
		jp_espacement.setBackground(color);
		this.add(jp_espacement, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.140;
		gbc.weighty = 0.1;
		jp_grpEtudiant.setBackground(color);
		this.add(jp_grpEtudiant, gbc);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0.1;
		JPanel jpma = new JPanel();
		jpma.setBackground(color);
		this.add(jpma, gbc);
		
		JPanel jpmargeb1 = new JPanel();
		JPanel jpmargeb2 = new JPanel();
		JPanel jpmargeb3 = new JPanel();
		JPanel jpmargeb4 = new JPanel();
	
		jpmargeb1.setBackground(color);
		jpmargeb2.setBackground(color);
		jpmargeb3.setBackground(color);
		jpmargeb4.setBackground(color);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0.1;
		this.add(jpmargeb1, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.140;
		gbc.weighty = 0.05;
		
		this.add(jpmargeb2, gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.140;
		gbc.weighty = 0.05;
		
		this.add(jpmargeb3, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0.05;
		
		this.add(jpma, gbc);
	
	}
	
	/**
	 * paintComponent
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
