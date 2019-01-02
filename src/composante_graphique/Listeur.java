package composante_graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Categorie;
import modele.Groupe;

public class Listeur extends JPanel{
	
	private ArrayList<Categorie> listecategorie;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public Listeur(ArrayList<Categorie> listep) {
		listecategorie = listep;
		this.setLayout(new GridBagLayout());
		creerZoneListeur();
	}
	
	private void creerZoneListeur() {
		PanelListeur pl;
		int i = 0;
		
		for (Categorie categorie : listecategorie) {
			pl = new PanelListeur(categorie, this);
			
			if(listecategorie.size()-1==i) {
				pl.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
			}else {
				pl.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.black));
			}
			
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 0;
			gbc.weighty = 0;
			
			this.add(pl, gbc);
			i++;
			pl.repaint();
		}
		
	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color((int)(Math.random()*200), (int)(Math.random()*100), (int)(Math.random()*50)));
		g.fillRect(0, 10, 10, 10);
		System.out.println("vfg");
	}
	
	
	
	
	
	
	public void refresh() {
		repaint();
	}
	
	
	
	
	/**
	 * methode main de test interne a Listeur
	 * @param arg
	 */
	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(new Dimension(700,600));
		
		ArrayList<Categorie> listcateg = new ArrayList<>();
		ArrayList<Groupe> gl1 = new ArrayList<Groupe>();
		ArrayList<Groupe> gl2 = new ArrayList<Groupe>();
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		gl2.add(new Groupe("groupe2 A"));
		gl2.add(new Groupe("groupe2 B"));
		Categorie c1 = new Categorie("Année 1", gl1);
		Categorie c2 = new Categorie("Année 2", gl2);	
		listcateg.add(c1);
		listcateg.add(c2);
		Listeur listeur = new Listeur(listcateg);
		fenetre.add(listeur);

		fenetre.setVisible(true);
		
	}
}
