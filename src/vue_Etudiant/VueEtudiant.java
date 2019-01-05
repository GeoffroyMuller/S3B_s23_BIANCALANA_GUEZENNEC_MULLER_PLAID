/*package vue_Etudiant;
import modele.*;
import controleur.*;
import controleur_Etudiant.ControleurImportationListe;
import listeuretudiant.DifListeurEtu;
import listeuretudiant.PanelListeurEtu;
import modele.BDD.Groupe;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class VueEtudiant extends JPanel implements Observer{
	ControleurImportationListe bouttonImport ;
	
	JPanel topBarre; 
	DifListeurEtu	dls;
	PanelListeurEtu ple;
	JTextField cheminFichier;
	ArrayList<Groupe> listeActuelle;
	
	
	public VueEtudiant() {
		
		topBarre = new JPanel();
		topBarre.setMinimumSize(new Dimension(500, 75));
		topBarre.setLayout(new GridBagLayout());
	

		
		listeActuelle = new ArrayList<Groupe>();
		bouttonImport = new ControleurImportationListe(this);
		bouttonImport.setMinimumSize(new Dimension(200, 25));
		cheminFichier = new JTextField(" ");
		
		
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20,10,10,0);
		
		
		
		topBarre.setBackground(Color.orange);
		topBarre.add(bouttonImport,gbc);
		
		
		gbc.gridx=1;
		gbc.weightx=2;
		topBarre.add(cheminFichier,gbc);
		
		gbc.gridx=2;
		gbc.weightx=2;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		topBarre.add(new JComboBox<>(),gbc);
		
		
		//GridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight, double weightx,
		//double weighty, int anchor, int fill, Insets insets, int ipadx, int ipady)
		
        gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,10,0,0);
		
		this.add(topBarre,gbc);
		

		
		//dls = new DifListeurCatégorie();
		ple = new PanelListeurEtu();
		
		gbc.fill= GridBagConstraints.BOTH;
		gbc.gridy=1;
		gbc.gridheight=GridBagConstraints.REMAINDER;
		gbc.weighty=1;
		 gbc.insets = new Insets(0,10,20,0);
		this.add(ple,gbc);
		
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
	
		super.paintComponent(g);
		
	}
	
	public void setTextChemin(String s) {
		cheminFichier.setText(s);
		this.revalidate();
		this.repaint();
		
	}
	


}*/
