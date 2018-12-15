package controleur_composante_graphique;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import modele.Categorie;

public class ConstructeurListeModelCategorie {
	private ArrayList<Categorie> lc;
	
	public ConstructeurListeModelCategorie(ArrayList<Categorie> pac) {
		lc = pac;
	}
	
	public DefaultListModel<Categorie> genererModelCatégorie(){
		DefaultListModel<Categorie> dlmc = new DefaultListModel<>();
		for (int i = 0; i < lc.size(); i++) {
			dlmc.addElement(lc.get(i));
		}
		
		return dlmc;
	}
	


}
