package controleur_composante_graphique;

import java.util.ArrayList;

import modele.*;

public class constructeurDataEtudiant {
	ArrayList<Etudiant> le ;
	
	public constructeurDataEtudiant(ArrayList<Etudiant> ple) {
		le=ple;
	}
	
	public String[][] genererDataLigneEtu(){
		String[][] data = new String[100000][3];
		for (int i = 0; i < le.size(); i++) {
			data[i][0]=le.get(i).getNom();
			data[i][1]=le.get(i).getPrenom();
			data[i][2]=le.get(i).getEmail();
		}
		
		return data;
		
	}
	
	public String[] GenereColonneEtu() {
		String[] colo = new String[] {"Nom","Prenom","Email"};
		return colo;
	}

}
