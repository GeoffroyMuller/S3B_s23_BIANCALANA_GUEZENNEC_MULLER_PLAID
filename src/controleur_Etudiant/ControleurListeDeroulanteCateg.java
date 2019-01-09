package controleur_Etudiant;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;

import modele.BDD.Categorie;

public class ControleurListeDeroulanteCateg extends JComboBox<String>{
	ArrayList<Categorie> lcateg;
	public ControleurListeDeroulanteCateg() {
		try {
			
			this.lcateg= Categorie.getlistCategorie();
		for (Categorie categorie  : Categorie.getlistCategorie()) {
			this.addItem(categorie.getNom());
		}
		}
		catch(SQLException e) {
			System.out.println("erreur lors de lacces a la table categorie");
		}
		
	}
	
	
	public Categorie getSelectedCategorie() {
		Categorie c = null;
		
		c= lcateg.get(this.getSelectedIndex());
		return c;
	}
	
	public void refresh() {
		
		try {
			
			this.lcateg= Categorie.getlistCategorie();
		for (Categorie categorie  : Categorie.getlistCategorie()) {
			this.addItem(categorie.getNom());
		}
		}
		catch(SQLException e) {
			System.out.println("erreur lors de lacces a la table categorie");
		}
		
	}
}
