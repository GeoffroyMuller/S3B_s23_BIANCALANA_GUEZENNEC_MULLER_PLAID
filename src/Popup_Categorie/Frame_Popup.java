package Popup_Categorie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.xml.internal.ws.spi.db.OldBridge;

import controleur_listeur.ListenerDeRefresh;
import modele.BDD.Categorie;

public class Frame_Popup extends JFrame{
	
	Categorie categ;
	JTextField nomCateg;
	JTextField quest;
	String nom;
	Boutton_Annuler bannul;
	Boutton_Confirmer bconfirm;

	
	public Frame_Popup(Categorie pc) {
		categ=pc;
		nom=pc.getNom();
		
		
		
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		top.setLayout(new GridBagLayout());
		bottom.setLayout(new GridBagLayout());
		top.setBackground(Color.green);
		bottom.setBackground(Color.pink);
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        	
        //ajout dans top
	        gbc.anchor=GridBagConstraints.PAGE_START;
	        gbc.weightx = 1;
	        gbc.weighty = 1;
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.insets = new Insets(25,20,0,20);
	        
			
			nomCateg = new JTextField();
			nomCateg.setEditable(false);
			String info = "Le nom : '"+nom+"' est déjà utiliser.";
			String question = "Continuer écrasera cette catégorie voulez vous continuez ?";
			nomCateg.setText(info);
			
			quest = new JTextField(question);
			quest.setEditable(false);
			
			top.add(nomCateg,gbc);
			
			gbc.insets = new Insets(0,20,0,20);
			gbc.gridy=1;
			
			top.add(quest,gbc);
        
        
        
	
		this.setLayout(new GridBagLayout());
		this.setTitle("Le nom utilisé existe déjà");

		
		bannul = new Boutton_Annuler(this);
		bconfirm = new Boutton_Confirmer(this);


		
		
		gbc.insets = new Insets(0,20,0,20);
		bottom.add(bannul,gbc);
		
		gbc.insets = new Insets(0,20,0,20);
		
		gbc.gridx=2;
		
		bottom.add(bconfirm,gbc);
		
       
        gbc.gridx = gbc.gridy = 0;
	        gbc.anchor=GridBagConstraints.PAGE_START;
	        gbc.weightx = 1;
	        gbc.weighty = 2;
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.insets = new Insets(0,0,0,0);
	        
	     this.add(top,gbc);
	     gbc.gridy = 1;
	     
	     gbc.weighty = 1;
	     this.add(bottom,gbc);
		
		
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(400,200));
        this.setVisible(true);
	}

	public void ecraser() {
		
		int id = categ.getIdDoublon();
		try {
		Categorie old = Categorie.findById(id);
		old.delete();
		categ.save();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		ListenerDeRefresh.avertirChangement();
		
		
		
	}

	public String getNom() {
		return nom;
	}
	
	
}
