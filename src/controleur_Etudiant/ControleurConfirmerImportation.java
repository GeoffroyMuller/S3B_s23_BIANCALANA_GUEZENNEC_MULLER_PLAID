package controleur_Etudiant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import barre_chargement.VueChargement;
import controleur_listeur.ListenerDeRefresh;
import modele.*;
import modele.BDD.Groupe;
import modele.GestionFichiersExcel.ImportEtudiant;
import vue_Etudiant.VueEtudiant;
import vue_Etudiant.VueImportation;
import vue_Examen.VueGroupeParticipant;

public class ControleurConfirmerImportation extends JButton implements ActionListener{

	VueImportation vi ;
	VueEtudiant vetu;
	VueChargement vc;
	
	public ControleurConfirmerImportation(VueEtudiant ve,VueImportation v) {
		this.setText("Confimrer importation");
		this.vetu = ve; 
		this.vi=v;
		
		
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		this.vetu.setListeActuelle(new ArrayList<Groupe>());

		ImportEtudiant ie = new ImportEtudiant(vi.getpath(), "Feuil1", vi.getCategSelectioner());
		ie.addObserver(VueGroupeParticipant.getListeur());
		ie.importerEtudiant();

		ListenerDeRefresh.avertirChangement();
		
		
		vi.dispose();
		
	}
		
}
