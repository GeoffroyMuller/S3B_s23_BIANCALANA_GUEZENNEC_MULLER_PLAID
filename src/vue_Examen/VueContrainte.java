package vue_Examen;

import modele.Examen;

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

public class VueContrainte extends JPanel{
	private static Color color = new Color(236, 241, 245);
	private JLabel jl_containte;
	private JPanel jp_espacement;
	private JPanel jp_grpEtudiant;
	private GridBagConstraints gbc;
	private JComboBox jcb_espacement;
	private String[] tab_espacement = {"0", "1", "2", "3", "4", "5"};
	public static Examen examen;
	public VueContrainte(Examen examen) {
		VueContrainte.examen = examen;
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
	 * permet de creer le JPanel de gestion de contrainte 
	 */
	public void creerZoneContrainte() {
		Border bordurecolor = new LineBorder(Color.BLACK);
		jp_espacement.setBorder(BorderFactory.createTitledBorder(bordurecolor, "Espacement"));
		jp_grpEtudiant.setBorder(BorderFactory.createTitledBorder(bordurecolor, "Groupe Etudiant"));
		
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
		gbc.weightx = 0.154;
		gbc.weighty = 0.1;
		jp_espacement.setBackground(color);
		this.add(jp_espacement, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		jp_grpEtudiant.setBackground(color);
		this.add(jp_grpEtudiant, gbc);
		
		
	
	}
	
	

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	/*public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setMinimumSize(new Dimension(500,100));

		VueContrainte vuec = new VueContrainte(null);
		fenetre.add(vuec);
		fenetre.setVisible(true);

	}*/
}
