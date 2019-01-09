package vue_Examen;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur_Examen.ControleurExamen;
import modele.Examen;

public class VueSallePriorite extends JPanel{
	private ControleurExamen ctrlexam;
	private JLabel label = new JLabel("Salle Priorité");
	
	public VueSallePriorite(ControleurExamen ctrlexamp) {
		ctrlexam = ctrlexamp;
		this.add(label);
		this.add(ctrlexamp.getChsalle());
		
	}
}
