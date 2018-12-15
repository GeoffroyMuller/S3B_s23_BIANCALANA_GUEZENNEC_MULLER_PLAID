package vue_Etudiant;
import modele.*;
import controleur.*;
import controleur_Etudiant.ControleurImportationListe;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.sun.javafx.geom.transform.NoninvertibleTransformException;
import com.sun.org.apache.bcel.internal.generic.NEW;

import composante_graphique.DifListeurCatégorie;
import composante_graphique.DifListeurCatégorie;
import composante_graphique.ListeurCategorie;

public class VueEtudiant extends JPanel implements Observer{
	ControleurImportationListe bouttonImport ;
	
	JPanel topBarre; 
	JTextField cheminFichier;
	ArrayList<Groupe> listeActuelle;
	
	
	public VueEtudiant() {
		this.setLayout(new BorderLayout());
		topBarre = new JPanel();
		topBarre.setLayout(new GridLayout(1,3));
		this.setBackground(Color.YELLOW);
		topBarre.setBackground(Color.green);

		
		listeActuelle = new ArrayList<Groupe>();
		bouttonImport = new ControleurImportationListe(this);
		System.out.println("oooooooooooooooooooooo"+topBarre.getHeight()/5);
		bouttonImport.setBorder(new EmptyBorder(topBarre.getHeight()/5,topBarre.getWidth()/20 ,topBarre.getHeight()/5, topBarre.getWidth()/20));
		cheminFichier = new JTextField(" ");
		
		//this.bouttonImport.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		System.out.println("construct avant setpref");
		System.out.println(this.bouttonImport.getSize());
		
		topBarre.setSize(new Dimension(this.getWidth(), this.getHeight()/8));
		//this.bouttonImport.setSize(new Dimension(topBarre.getWidth()/3,topBarre.getHeight()));
		
		topBarre.add(bouttonImport);
		topBarre.add(cheminFichier);
		topBarre.add(new JComboBox<>());
		
		this.add(topBarre,BorderLayout.NORTH);
		bouttonImport.setBorder(new EmptyBorder(topBarre.getHeight()/5,topBarre.getWidth()/20 ,topBarre.getHeight()/5, topBarre.getWidth()/20));
		System.out.println("ppppppppppppppppppppp"+topBarre.getHeight()/5);
		
		DifListeurCatégorie	dls = new DifListeurCatégorie();
		dls.setBackground(Color.PINK);
		this.add(dls, BorderLayout.CENTER);
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
		
		this.bouttonImport.setBackground(Color.red);
		System.out.println("redim");
		//topBarre.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/10));
		
		System.out.println(this.getSize());
		System.out.println(this.bouttonImport.getParent().getSize());
		
		System.out.println(this.bouttonImport.getSize());
	}


}
