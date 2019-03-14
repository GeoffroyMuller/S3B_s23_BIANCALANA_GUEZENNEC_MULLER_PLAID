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

		lcateg= Categorie.getlistCategorie();
		for (Categorie categorie  : Categorie.getlistCategorie()) {
			this.addItem(categorie);
		}

	}
	
	
	public Categorie getSelectedCategorie() {
		Categorie c = null;
		
		c= lcateg.get(this.getSelectedIndex());
		return c;
	}
	
	 public void refresh() {

		 lcateg= Categorie.getlistCategorie();
		 for (Categorie categorie  : Categorie.getlistCategorie()) {
			 this.addItem(categorie);

			 System.out.println("rrrr"+categorie);
			 }

		 System.out.println(this.getModel());


	 }
}
