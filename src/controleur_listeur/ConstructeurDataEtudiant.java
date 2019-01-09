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
		String[][] data = new String[100000][4];
		int row;
		row=0;
		for (int i = 0; i < lg.size(); i++) {
			ArrayList<Etudiant> le = lg.get(i).getListeEtudiants();
			//System.out.println("fffff"+i);
			for (int j = 0; j < le.size(); j++) {
				data[row][0]=le.get(j).getNom();
				data[row][1]=le.get(j).getPrenom();
				data[row][2]=lg.get(i).getNom();
				//System.out.println(i);
				data[row][3]=le.get(j).getEmail();
				row++;
			}

		}
		
		return data;
		
	}
	
	public String[] GenereColonneEtu() {
		String[] colo = new String[] {"Nom","Prenom","Groupe","Email"};
		return colo;
	}

}
