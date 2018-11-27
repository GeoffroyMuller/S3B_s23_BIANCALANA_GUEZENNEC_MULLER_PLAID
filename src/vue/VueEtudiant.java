package vue;
import controleur.*;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class VueEtudiant extends JPanel implements Observer{
	ControleurImportationListe bouttonImport ;
	JTextField cheminFichier;
	
	
	public VueEtudiant() {
		bouttonImport = new ControleurImportationListe(this);
		cheminFichier = new JTextField(" ");
		this.add(bouttonImport);
		this.add(cheminFichier);
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
