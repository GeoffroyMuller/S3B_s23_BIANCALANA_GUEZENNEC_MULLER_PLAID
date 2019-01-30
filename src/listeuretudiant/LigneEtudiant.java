package listeuretudiant;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import modele.BDD.Etudiant;


public class LigneEtudiant extends JPanel{
	
	private Etudiant e;
	
	private JTextField tfNom;
	private JTextField tfPrenom;
	private JTextField tfGroupe;
	
	
	public LigneEtudiant(Etudiant e) {
		this.tfNom.setText(e.getNom());
		this.tfPrenom.setText(e.getPrenom());
		this.setLayout(new GridLayout(1,2));
		this.add(tfNom);
		this.add(tfPrenom);
		
	}

}