/*package listeuretudiant;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import controleur_Etudiant.ControleurCréerNouvelleCateg;
import controleur_Etudiant.ControleurListeDeroulanteCateg;
import controleur_listeur.ControleurSuprListe;

public class PanelGauche extends JPanel{
	
	JScrollPane jsp;
	JLabel label;
	ControleurSuprListe csl;
	ControleurCréerNouvelleCateg ccnc;
	
	public PanelGauche(JScrollPane j) {
		jsp =j;
		label = new JLabel("Liste Disponible :");
		csl = new ControleurSuprListe(AfficheurTree.getTree());
		ccnc = new ControleurCréerNouvelleCateg(new ControleurListeDeroulanteCateg());
		
		
		
		
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,10,0,0);
        
        
        
        this.add(label, gbc);
        
        
        
        gbc.weighty=32;
        gbc.gridy=1;
        this.add(jsp,gbc);
        
        
        gbc.insets = new Insets(20,10,20,0);
        gbc.weighty=1;
        gbc.gridy=2;
        
        this.add(csl, gbc);
        
        
        gbc.insets = new Insets(20,10,20,0);
        gbc.weighty=1;
        gbc.gridy=3;
        gbc.gridheight=GridBagConstraints.REMAINDER;
        this.add(ccnc, gbc);

        

        
  
		
	}

}*/
