package composante_graphique;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import modele.*;

public class Cat�gorieExtension extends JList<Categorie>{
	private ArrayList<Categorie> lc;
	


	
	public Cat�gorieExtension(DefaultListModel<Categorie> dlmc ) {
		super(dlmc);
	}
	
	


}
