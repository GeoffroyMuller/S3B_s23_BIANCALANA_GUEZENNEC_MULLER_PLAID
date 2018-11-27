package vue;
import modele.*;
import controleur.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class VueEtudiant extends JPanel implements Observer{
	ControleurImportationListe bouttonImport ;
	JTextField cheminFichier;
	ArrayList<Groupe> listeActuelle;
	
	
	public VueEtudiant() {
		listeActuelle = new ArrayList<Groupe>();
		bouttonImport = new ControleurImportationListe(this);
		cheminFichier = new JTextField(" ");
		this.add(bouttonImport);
		this.add(cheminFichier);
	}

	public ArrayList<Groupe> getListeActuelle() {
		return listeActuelle;
	}

	public void setListeActuelle(ArrayList<Groupe> listeActuelle) {
		this.listeActuelle = listeActuelle;
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	public void setTextChemin(String s) {
		cheminFichier.setText(s);
		this.revalidate();
		this.repaint();
		
	}


}
