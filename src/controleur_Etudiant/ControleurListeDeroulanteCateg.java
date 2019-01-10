package controleur_Etudiant;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataListener;

import modele.BDD.Categorie;

public class ControleurListeDeroulanteCateg extends JComboBox<Categorie>{
	 ArrayList<Categorie> lcateg;
	public ControleurListeDeroulanteCateg() {

		try {
			
			lcateg= Categorie.getlistCategorie();
		for (Categorie categorie  : Categorie.getlistCategorie()) {
			this.addItem(categorie);
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
			
			lcateg= Categorie.getlistCategorie();
		for (Categorie categorie  : Categorie.getlistCategorie()) {
			this.addItem(categorie);
			
			System.out.println("rrrr"+categorie);
			}
		
		System.out.println(this.getModel());
					
		
		}
		catch(SQLException e) {
			System.out.println("erreur lors de lacces a la table categorie");
		}
		 
		 
		
	}
}
