package controleur_listeur;

import listeuretudiant.DifListeurEtu;

public  class ListenerDeRefresh {
	static DifListeurEtu dle;
	
	
	public ListenerDeRefresh(DifListeurEtu pdle) {
		dle = pdle;
	}
	
	
	static public void avertirChangement() {
		dle.refresh();
	}

}
