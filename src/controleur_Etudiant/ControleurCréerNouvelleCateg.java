package controleur_Etudiant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue_Etudiant.vueCreationCateg;

public class ControleurCréerNouvelleCateg extends JButton implements ActionListener{
	vueCreationCateg vcc ;
	ControleurListeDeroulanteCateg cldc;
	
	public ControleurCréerNouvelleCateg(ControleurListeDeroulanteCateg pcldc) {
		this.setText("nouvelle catégorie");
		cldc=pcldc;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		vcc = new vueCreationCateg(cldc);
		
	}
	

}
