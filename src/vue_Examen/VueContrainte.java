package vue_Examen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueContrainte extends JPanel{

	private JLabel jl_containte;
	private JPanel jp_emplacement;
	private JPanel jp_grpEtudiant;
	private GridBagConstraints gbc;
	
	
	public VueContrainte() {
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		jl_containte = new JLabel("Contrainte");
		jp_emplacement = new JPanel();
		jp_grpEtudiant = new JPanel();
		jp_emplacement.setPreferredSize(new Dimension(100, 100));
		creerZoneContrainte();
	}
	
	public void creerZoneContrainte() {
		jp_emplacement.setBorder(BorderFactory.createTitledBorder("Emplacement"));
		jp_grpEtudiant.setBorder(BorderFactory.createTitledBorder("Groupe Etudiant"));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 0.2;
		this.add(jl_containte, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 0.2;
		this.add(jp_emplacement, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 0.2;
		this.add(jp_grpEtudiant, gbc);
		
		
	
	}
	
	

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("THIS Contrainte w "+this.getWidth()+" h "+this.getHeight());
	}
	
	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setMinimumSize(new Dimension(900,200));

		VueContrainte vuec = new VueContrainte();
		fenetre.add(vuec);
		fenetre.setVisible(true);

	}
}
