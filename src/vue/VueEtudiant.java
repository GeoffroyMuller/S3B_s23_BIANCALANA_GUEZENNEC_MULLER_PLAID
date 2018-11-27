package vue;
import controleur.*;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class VueEtudiant extends JPanel implements Observer{
	
	
	public VueEtudiant() {
		this.add(new ControleurImportationListe());
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}

}
