/*package vue_Etudiant;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur_Etudiant.ControleurConfirmerImportation;

public class VueImportation extends JFrame{
	
	ControleurConfirmerImportation cci;
	JTextField cheminFichier;
	JTextField nomListe ;
	int WidthDispo;
	int HeightDispo;
	
	
	public VueImportation(VueEtudiant ve) {
		this.setTitle("Option d'importation");

		
		cheminFichier = new JTextField(" ");
		
        cci = new ControleurConfirmerImportation(ve,this);
        
        
        JPanel p = new JPanel();
        this.setLayout(new GridBagLayout());
        
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,10,0,0);
        
        this.add(cheminFichier,gbc);
        
        
        
        
        
        gbc.gridy=2;
        this.add(cci,gbc);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(600,600));
        this.setVisible(true);
		
	}
	
	@Override
	public void update(Graphics g) {
		super.update(g);
		
		WidthDispo = this.getWidth();
		HeightDispo = this.getHeight();
		
	
		
	}
	
	public void settextPath(String s) {
		this.cheminFichier.setText(s);
	}
	
	

}*/
