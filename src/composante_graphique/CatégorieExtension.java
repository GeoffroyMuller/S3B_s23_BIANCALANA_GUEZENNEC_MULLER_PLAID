package composante_graphique;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import modele.*;

public class CatégorieExtension extends JList<Categorie>{
	private ArrayList<Categorie> lc;
	


	
	public CatégorieExtension(DefaultListModel<Categorie> dlmc ) {
		super(dlmc);
	}
	
	


}
