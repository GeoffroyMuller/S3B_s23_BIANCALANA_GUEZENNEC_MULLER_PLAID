package controleur_listeur;

import java.util.ArrayList;

import modele.*;
import modele.BDD.Etudiant;
import modele.BDD.Groupe;

public class ConstructeurDataEtudiant {
	private ArrayList<Groupe> lg ;
	
	public ConstructeurDataEtudiant(ArrayList<Groupe> plg) {
		lg=plg;
	}
	
	public String[][] genererDataLigneEtu(){
		int tailleMin = 100;
		int nbetu=0;
		String[][] data ;
		for (Groupe groupe : lg) {
			for (Etudiant Etu : groupe.getListeEtudiants()) {
				nbetu++;
			}
		}
		if(nbetu>tailleMin) {
			data = new String[nbetu][5];
		}
		else{
			 data = new String[tailleMin][5];
		}
		
		int row;
		row=0;
		for (int i = 0; i < lg.size(); i++) {
			ArrayList<Etudiant> le = lg.get(i).getListeEtudiants();
			//System.out.println("fffff"+i);
			for (int j = 0; j < le.size(); j++) {
				data[row][0]=""+le.get(j).getIdEtu();
				data[row][1]=le.get(j).getNom();
				data[row][2]=le.get(j).getPrenom();
				data[row][3]=lg.get(i).getNom();
				//System.out.println(i);
				data[row][4]=le.get(j).getEmail();
				row++;
			}

		}
		
		return data;
		
	}
	
	public String[] GenereColonneEtu() {
		String[] colo = new String[] {"ID","Nom","Prenom","Groupe","Email"};
		return colo;
	}

}
