package vue_Etudiant;

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
		WidthDispo = this.getWidth();
		HeightDispo = this.getHeight();
		
		cheminFichier = new JTextField(" ");
		
        cci = new ControleurConfirmerImportation(ve,this);
        
        
        JPanel p = new JPanel();
        this.setLayout(new BorderLayout());
        p.add(cci);
        this.add(p,BorderLayout.SOUTH);
        this.add(cheminFichier,BorderLayout.NORTH);
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
	
	

}
