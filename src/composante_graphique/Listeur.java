package composante_graphique;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import controleur_Examen.ControleurExamen;
import modele.Examen;
import modele.BDD.Categorie;
import modele.BDD.Groupe;

public class Listeur extends JPanel{
	private ControleurExamen controleur_Exam;
	private JScrollPane scrollpane;
	private JPanel jp_all;
	private PanelListeur pl_courant;
	private ArrayList<Categorie> listecategorie;
	private ArrayList<PanelListeur> liste_panelListeur;
	private GridBagConstraints gbc = new GridBagConstraints();

	public Listeur(ArrayList<Categorie> listep, ControleurExamen ctrlexamp) {
		controleur_Exam = ctrlexamp;
		jp_all = new JPanel();
		jp_all.setBackground(Color.darkGray);
		scrollpane = new JScrollPane(jp_all, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getVerticalScrollBar().setUnitIncrement(15);
		//scrollpane.setBackground(Color.BLUE);
		liste_panelListeur = new ArrayList<PanelListeur>();
		//this.setBackground(Color.blue);
		if(listep == null) {
			listecategorie = new ArrayList<Categorie>();
		}else {
			listecategorie = listep;
		}
		this.setLayout(new GridBagLayout());
		jp_all.setLayout(new GridBagLayout());

		creerZoneListeur();

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(15, 50, 15, 50);
		gbc.weightx = 1;
		gbc.weighty = 0.5;
		scrollpane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

		this.add(scrollpane, gbc);
	}

	private void genererPanelCategorie(){
		liste_panelListeur.clear();
		pl_courant = new PanelListeur();
		for (Categorie categorie : listecategorie) {
			pl_courant = new PanelListeur(categorie, this, controleur_Exam);
			liste_panelListeur.add(pl_courant);
			
		}
	}

	public void setListecategorie(ArrayList<Categorie> listecategorie) {
		this.listecategorie = listecategorie;
	}

	public void creerZoneListeur() {
		genererPanelCategorie();
		int i = 0;

		if((listecategorie == null)||(listecategorie.size()==0)) {
			PanelListeur pl_courant = new PanelListeur(new Categorie("Pas de categorie"), this, controleur_Exam);
			pl_courant.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));


			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 0;
			gbc.gridx = 0;
			gbc.gridy = 0;
			//gbc.ipady = gbc.anchor = GridBagConstraints.NORTH;
			gbc.insets = new Insets( 0, 0, 0, 0);
			System.out.println("pas de categorie");
			jp_all.add(pl_courant, gbc);
			gbc.weighty = 2;
			gbc.gridy = 1;
			jp_all.add(new JPanel(), gbc);
		}else {
			GridBagConstraints gbcd = new GridBagConstraints();
			for (PanelListeur pl : liste_panelListeur) {

				gbcd.gridx = 0;
				gbcd.gridy = i;
				gbcd.fill = GridBagConstraints.BOTH;
				gbcd.insets = new Insets(0, 0, 0, 0);
				gbcd.weightx = 1;
				gbcd.weighty = 0;
				jp_all.add(pl, gbcd);
				i++;
			}
			gbcd.gridx = 0;
			gbcd.gridy = i;
			gbcd.fill = GridBagConstraints.BOTH;
			gbcd.insets = new Insets(0, 0, 0, 0);
			gbcd.weightx = 0;
			gbcd.weighty = i;
			jp_all.add(new JPanel(), gbcd);
		}
		repaint();
	}
	/**
	 * Definie et adapte la taille General
	 */
	public void definirTaille(int w, int h) {
		scrollpane.setPreferredSize(new Dimension(w, h));
	}


	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.setColor(new Color((int)(Math.random()*200), (int)(Math.random()*100), (int)(Math.random()*50)));
		//g.fillRect(0, 10, 10, 10);
		//scrollpane.setPreferredSize(new Dimension(this.getWidth()-20, this.getHeight()-20));
		//this.setVisible(false);
		//this.setVisible(true);
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
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));
		gl1.add(new Groupe("groupe1 A"));
		gl1.add(new Groupe("groupe1 B"));

		gl2.add(new Groupe("groupe2 A"));
		gl2.add(new Groupe("groupe2 B"));
		//Categorie c1 = new Categorie("Ann�e 1", gl1);
		//Categorie c2 = new Categorie("Ann�e 2", gl2);	
		//listcateg.add(c1);
		//listcateg.add(c2);
		Listeur listeur = new Listeur(listcateg, new ControleurExamen(new Examen()));
		fenetre.add(listeur);
		fenetre.pack();
		fenetre.setVisible(true);

	}

}
