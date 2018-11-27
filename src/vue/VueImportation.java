package vue;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.ControleurConfirmerImportation;

public class VueImportation extends JFrame{
	
	ControleurConfirmerImportation cci;
	JTextField cheminFichier;
	
	public VueImportation(VueEtudiant ve) {
		this.setTitle("Option d'importation");
		
        cci = new ControleurConfirmerImportation(ve,this);
        JPanel p = new JPanel();
        p.add(cci);
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400,400));
        this.setVisible(true);
		
	}
	
	

}
