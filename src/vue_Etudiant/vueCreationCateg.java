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

public class vueCreationCateg extends JFrame{
	
	JTextField nomCateg;
	ControleurBouttonConfCreationCateg ccc;
	
	public vueCreationCateg() {
		
		
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(400,400));
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,10,0,0);
        
        
        
		this.setTitle("Creation de nouvelle catégorie");
		nomCateg = new JTextField();
		nomCateg.setText("Nom de la nouvelle catégorie");
		this.add(nomCateg,gbc);
		
		gbc.gridy=1;
		ccc = new ControleurBouttonConfCreationCateg(nomCateg,this);
		this.add(ccc,gbc);
		
		
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(600,600));
        this.setVisible(true);
		
	}

}
