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
	
	public ControleurImportationListe(VueEtudiant ve) {
		this.setText("Importer une liste d'Etudiant");
		this.addActionListener(this);
		this.vueEtu = ve;
		//this.setSize(this.vueEtu.getWidth()/3, this.vueEtu.getHeight()/3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".xlsx fichier Excel", "xlsx");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(getParent());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			vueEtu.setTextChemin(chooser.getSelectedFile().getName());  
			this.vi= new VueImportation(vueEtu);
			vi.settextPath(chooser.getSelectedFile().getPath());
			

		}
		
		
		
	}

}
