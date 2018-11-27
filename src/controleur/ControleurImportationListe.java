package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import vue.VueEtudiant;

public class ControleurImportationListe extends JButton implements ActionListener{
	JFileChooser explorateur;
	VueEtudiant vueEtu;
	
	public ControleurImportationListe(VueEtudiant ve) {
		this.setText("Importer une liste d'étudiant");
		this.addActionListener(this);
		this.vueEtu = ve;
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
			

		}
		
		
		
	}

}
