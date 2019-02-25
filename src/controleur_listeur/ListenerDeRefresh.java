package controleur_listeur;

import listeuretudiant.DifListeurEtu;
import modele.Examen;
import vue_Examen.VueExamen;

public  class ListenerDeRefresh {
	static DifListeurEtu dle;
	
	
	public ListenerDeRefresh(DifListeurEtu pdle) {
		dle = pdle;
	}
	
	
	static public void avertirChangement() {
		dle.refresh();
		VueExamen.getModeleExamen().refresh();
	}

}
