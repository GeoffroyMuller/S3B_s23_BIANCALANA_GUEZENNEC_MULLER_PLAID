package controleur_Etudiant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import modele.BDD.Categorie;
import vue_Etudiant.vueCreationCateg;
import controleur_listeur.*;

public class ControleurBouttonConfCreationCateg extends JButton implements ActionListener{
	JTextField nom;
	vueCreationCateg vcc;
	ControleurListeDeroulanteCateg cldc;
	
	public ControleurBouttonConfCreationCateg(JTextField jtf,vueCreationCateg v,ControleurListeDeroulanteCateg pcldc) {
		nom = jtf;
		vcc=v;
		cldc=pcldc;
		this.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String n = nom.getText();
		this.creerCateg(n);
		
	}
	
	public void creerCateg(String n) {
		Categorie categ = new Categorie(n);
		categ.save();
		cldc.refresh();
		ListenerDeRefresh.avertirChangement();
		vcc.dispose();
	}
	
	

}
