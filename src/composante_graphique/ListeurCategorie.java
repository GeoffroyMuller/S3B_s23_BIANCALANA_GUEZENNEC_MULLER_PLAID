package composante_graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;


import javax.swing.BorderFactory;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import controleur_Examen.ControleurExamen;
import modele.BDD.Categorie;

/**
 * Composante graphique permettant de lister les cat�gories
 * et d'acc�der � leurs groupes via des menus deroulant.
 * les PanelListeurCategorie("menu deroulant") compose le listeur de categorie.
 * @author Geoff
 *
 */
public class ListeurCategorie extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ControleurExamen controleur_Exam;
	private JScrollPane scrollpane;
	private JPanel jp_all;
	private PanelListeurCategorie pl_courant;
	private ArrayList<Categorie> listecategorie;
	private ArrayList<PanelListeurCategorie> liste_panelListeur;
	public static HashMap<String, Boolean> map_Save = new HashMap<String, Boolean>();
	private GridBagConstraints gbc = new GridBagConstraints();

	/**
	 * Constructeur
	 * @param listep : liste des categories � lister
	 * @param ctrlexamp : controleur d'examen permet d'acceder � divers listener
	 */
	public ListeurCategorie(ArrayList<Categorie> listep, ControleurExamen ctrlexamp) {
		
		controleur_Exam = ctrlexamp;
		jp_all = new JPanel();
		jp_all.setBackground(Color.darkGray);
		scrollpane = new JScrollPane(jp_all, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getVerticalScrollBar().setUnitIncrement(15);
		liste_panelListeur = new ArrayList<PanelListeurCategorie>();
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

	/**
	 * Permet de generer les PanelListeurCategorie qui compose le listeur de categorie 
	 */
	private void genererPanelCategorie(){
		liste_panelListeur.clear();
		pl_courant = new PanelListeurCategorie();
		for (Categorie categorie : listecategorie) {
			pl_courant = new PanelListeurCategorie(categorie, this, controleur_Exam);
			liste_panelListeur.add(pl_courant);
		}
	}

	/**
	 * Permet de creer/assembler le listeur de categorie
	 */
	public void creerZoneListeur() {
		genererPanelCategorie();
		int i = 0;

		if((listecategorie == null)||(listecategorie.size()==0)) {
			PanelListeurCategorie pl_courant = new PanelListeurCategorie(new Categorie("Pas de categorie"), this, controleur_Exam);
			pl_courant.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));


			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 0;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets( 0, 0, 0, 0);
			System.out.println("pas de categorie");
			jp_all.add(pl_courant, gbc);
			gbc.weighty = 2;
			gbc.gridy = 1;
			jp_all.add(new JPanel(), gbc);
		}else {
			GridBagConstraints gbcd = new GridBagConstraints();
			for (PanelListeurCategorie pl : liste_panelListeur) {


				gbc.gridx = 0;
				gbc.gridy = i;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.insets = new Insets(0, 0, 0, 0);
				gbc.weightx = 1;
				gbc.weighty = 0;
				jp_all.add(pl, gbc);

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
		for(PanelListeurCategorie plp : liste_panelListeur) {
			plp.definirTaille(w-200, 30);
		}
	}
	
	/**
	 * Sauvegarde la position des PanelListeurCategorie
	 * @param listeurModif
	 */
	public static void Sauvegarde(ListeurCategorie listeurModif) {
		map_Save.clear();
		for (PanelListeurCategorie pn_save : listeurModif.getListe_panelListeur()) {
			map_Save.put(pn_save.getNomCategorie(), pn_save.isActiver());
		}
	}
	
	/**
	 * Charge la sauvegarde des PanelListeurCategorie
	 * @param listeurModif
	 */
	public static void chargerSauvegarde(ListeurCategorie listeurModif) {
		for (int i=0; i<listeurModif.getListe_panelListeur().size();i++) {
			if(map_Save.containsKey(listeurModif.getListe_panelListeur().get(i).getNomCategorie())) {
				for( String str_save : map_Save.keySet() ) {
					if(str_save.equals(listeurModif.getListe_panelListeur().get(i).getNomCategorie())) {
						listeurModif.getListe_panelListeur().get(i).isActiverAction(map_Save.get(str_save));
					}
				}
			}
		}
	}
	
	/**
	 * Color le listeur de categorie
	 * @param color
	 */
	public void colorer(Color color) {
		this.setBackground(color);
	}

	/**
	 * Getter liste_panelListeur
	 * @return ArrayList<PanelListeurCategorie> liste_panelListeur
	 */
	public ArrayList<PanelListeurCategorie> getListe_panelListeur() {
		return liste_panelListeur;
	}
	
	/**
	 * Setter listecategorie
	 * @param listecategorie
	 */
	public void setListecategorie(ArrayList<Categorie> listecategorie) {
		this.listecategorie = listecategorie;
	}

	/**
	 * paintComponent
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	

}
