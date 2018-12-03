package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import modele.*;
import vue.VueEtudiant;
import vue.VueImportation;

public class ControleurConfirmerImportation extends JButton implements ActionListener{

	VueImportation vi ;
	VueEtudiant vetu;
	
	public ControleurConfirmerImportation(VueEtudiant ve,VueImportation v) {
		this.setText("Confimrer importation");
		this.vetu = ve; 
		this.vi=v;
		
		
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//gerer l'importation du excel !!!!!
		this.vetu.setListeActuelle(new ArrayList<Groupe>());
		
		vi.dispose();
		
	}
		
}
