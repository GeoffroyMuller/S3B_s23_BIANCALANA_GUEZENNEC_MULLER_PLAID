package controleur_Etudiant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue_Etudiant.vueCreationCateg;

public class ControleurCr�erNouvelleCateg extends JButton implements ActionListener{
	vueCreationCateg vcc ;
	ControleurListeDeroulanteCateg cldc;
	
	public ControleurCr�erNouvelleCateg(ControleurListeDeroulanteCateg pcldc) {
		this.setText("nouvelle cat�gorie");
		cldc=pcldc;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		vcc = new vueCreationCateg(cldc);
		
	}
	

}
