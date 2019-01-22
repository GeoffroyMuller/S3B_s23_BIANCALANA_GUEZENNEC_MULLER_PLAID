package controleur_Etudiant;

import javax.swing.JComboBox;

import listeuretudiant.AfficheurTree;
import listeuretudiant.DifListeurEtu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.BDD.Categorie;
import modele.BDD.Etudiant;

public class DeroulOptionTriCateg extends JComboBox<String>{
	DifListeurEtu dls;
	
	public DeroulOptionTriCateg(AfficheurTree pdls) {
		//super();
		//dls=pdls;
		this.addItem("A-Z");
		this.addItem("Z-A");
		this.addItem("Date");
		this.addItem("Date inverse");
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DeroulOptionTriCateg dotc =(DeroulOptionTriCateg)e.getSource();
				dls.changementTriArbre(dotc.getTri());
				
			}
		});
		
	}
	
	
	public int getTri() {
		int tri = 0;
		
		String choix = (String)this.getSelectedItem();
		
		switch (choix) {
		case "A-Z":
			tri = Categorie.ALPHA_ASC;
			break;
		case "Z-A":
			tri = Categorie.ALPHA_DSC;
			break;
			
		case "Date":
			tri = Categorie.DATE_ASC;
			break;

			
		case "Date inverse":
			tri = Categorie.DATE_DSC;
			break;
			
		default:
			tri = Categorie.ALPHA_ASC;
			break;
		}
		
		return tri;
	}

}
