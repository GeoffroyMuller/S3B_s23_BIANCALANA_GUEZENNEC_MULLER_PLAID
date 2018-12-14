package vue_Etudiant;
import modele.*;
import controleur.*;
import controleur_Etudiant.ControleurImportationListe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.javafx.geom.transform.NoninvertibleTransformException;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class VueEtudiant extends JPanel implements Observer{
	ControleurImportationListe bouttonImport ;
	
	JPanel topBarre; 
	JTextField cheminFichier;
	ArrayList<Groupe> listeActuelle;
	
	
	public VueEtudiant() {
		topBarre = new JPanel();
		topBarre.setLayout(new GridLayout(1,3));
		this.setBackground(Color.YELLOW);
		
		listeActuelle = new ArrayList<Groupe>();
		bouttonImport = new ControleurImportationListe(this);
		cheminFichier = new JTextField(" ");
		
		//this.bouttonImport.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		System.out.println("construct avant setpref");
		System.out.println(this.bouttonImport.getSize());
		
		topBarre.add(bouttonImport);
		topBarre.add(cheminFichier);
		topBarre.add(new JComboBox<>());
		this.add(topBarre,BorderLayout.NORTH);
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
	
	@Override
	public void paintComponent(Graphics g){
	
		this.redim();
		super.paintComponent(g);
		
	}
	
	public void setTextChemin(String s) {
		cheminFichier.setText(s);
		this.revalidate();
		this.repaint();
		
	}
	
	public void redim() {
		//this.bouttonImport.setSize(bouttonImport.getParent().getSize());
		System.out.println("redim");
		System.out.println(this.getSize());
		System.out.println(this.bouttonImport.getParent().getSize());
	}


}
