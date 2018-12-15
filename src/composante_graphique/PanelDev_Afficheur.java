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
		this.setPreferredSize(new Dimension(300, 50));
		this.setBackground(Color.BLACK);
	}
	
	public void ajouterInfo(String sp) {
		listaffiche.add(sp);
	}

	public void suppliste() {
		listaffiche.clear();
	}
	public void paintComponent(Graphics g) {
		int compte = 10;
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		for(String string : listaffiche) {
			g.drawString(string, 2, compte);
			compte+=10;
		}
		suppliste();
	}
}
