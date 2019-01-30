package vue_Etudiant;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur_Etudiant.ControleurBouttonConfCreationCateg;
import controleur_Etudiant.ControleurCréerNouvelleCateg;
import controleur_Etudiant.ControleurListeDeroulanteCateg;

public class vueCreationCateg extends JFrame{
	
	JTextField nomCateg;
	ControleurBouttonConfCreationCateg ccc;
	ControleurListeDeroulanteCateg cldg;
	
	public vueCreationCateg(ControleurListeDeroulanteCateg pcldg) {
		
		cldg=pcldg;
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(400,400));
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(50,50,0,50);
        
        
        
		this.setTitle("Creation de nouvelle catégorie");
		nomCateg = new JTextField();
		nomCateg.setText("Nom de la nouvelle catégorie");
		this.add(nomCateg,gbc);
		
		gbc.gridy=1;
		gbc.insets = new Insets(0,50,50,50);
		ccc = new ControleurBouttonConfCreationCateg(nomCateg,this,cldg);
		this.add(ccc,gbc);
		
		
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(400,200));
        this.setVisible(true);
		
	}

}
