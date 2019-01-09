package controleur_Etudiant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import modele.BDD.Categorie;
import vue_Etudiant.vueCreationCateg;

public class ControleurBouttonConfCreationCateg extends JButton implements ActionListener{
	JTextField nom;
	vueCreationCateg vcc;
	
	public ControleurBouttonConfCreationCateg(JTextField jtf,vueCreationCateg v) {
		nom = jtf;
		vcc=v;
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
		vcc.dispose();
	}
	
	

}
