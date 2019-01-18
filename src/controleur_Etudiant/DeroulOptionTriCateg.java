package controleur_Etudiant;

import javax.swing.JComboBox;

import modele.BDD.Etudiant;

public class DeroulOptionTriCateg extends JComboBox<String>{
	
	public DeroulOptionTriCateg() {
		//super();
		this.addItem("A-Z");
		this.addItem("Z-A");
		this.addItem("Date");
		this.addItem("Date inverse");
	}

}
