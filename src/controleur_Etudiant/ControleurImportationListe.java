package controleur_Etudiant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import vue_Etudiant.VueEtudiant;
import vue_Etudiant.VueImportation;

public class ControleurImportationListe extends JButton implements ActionListener{
	VueImportation vi;
	JFileChooser explorateur;
	VueEtudiant vueEtu;
	String lastpath;
	
	public ControleurImportationListe(VueEtudiant ve) {
		this.setText("Importer une liste d'Etudiant");
		this.addActionListener(this);
		this.vueEtu = ve;
		lastpath = "C:\\";
		//this.setSize(this.vueEtu.getWidth()/3, this.vueEtu.getHeight()/3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser chooser = new JFileChooser(lastpath);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".xlsx fichier Excel", "xlsx");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(getParent());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			vueEtu.setTextChemin(chooser.getSelectedFile().getName());  
			this.vi= new VueImportation(vueEtu);
			this.lastpath=chooser.getSelectedFile().getPath();
			vi.settextPath(chooser.getSelectedFile().getPath());
			
			

		}
		
		
		
	}

}
