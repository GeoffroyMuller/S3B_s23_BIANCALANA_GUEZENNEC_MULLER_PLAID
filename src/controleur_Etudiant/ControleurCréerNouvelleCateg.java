package controleur_Etudiant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue_Etudiant.vueCreationCateg;

public class ControleurCr�erNouvelleCateg extends JButton implements ActionListener{
	vueCreationCateg vcc ;
	
	public ControleurCr�erNouvelleCateg() {
		this.setText("nouvelle cat�gorie");
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		vcc = new vueCreationCateg();
		
	}
	

}
