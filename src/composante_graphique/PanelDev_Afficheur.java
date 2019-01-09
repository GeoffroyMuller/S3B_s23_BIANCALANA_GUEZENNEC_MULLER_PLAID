package composante_graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * Permet l'affichage d'information
 *
 */
public class PanelDev_Afficheur extends JPanel{
	private ArrayList<String> listaffiche = new ArrayList<String>();
	public PanelDev_Afficheur() {
		this.setPreferredSize(new Dimension(300, 100));
		this.setBackground(Color.BLACK);
	}
	
	public void ajouterInfo(String sp) {
		listaffiche.add(sp);
	}

	public void suppliste() {
		listaffiche.clear();
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		//System.out.println("pdev");
		int compte = 10;
		g.setColor(Color.GREEN);
		g.drawString("PanelDeveloppeur>", 2, compte);
		compte+=12;
		for(String string : listaffiche) {
			g.drawString(string, 2, compte);
			compte+=12;
		}
		this.setPreferredSize(new Dimension(300, compte+20));
		//suppliste();
		setVisible(false);

		setVisible(true);
	}
	
	public int getlongeur() {
		return this.getWidth();
	}
	public int gethauteur() {
		return this.getHeight();
	}
}
