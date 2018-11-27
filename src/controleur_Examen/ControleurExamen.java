package controleur_Examen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class ControleurExamen {

	private JTextField jtf_nom;		//JTextField : gere le nom de l'examen
	private JTextField jtf_matiere; //JTextField : gere la matiere de l'examen
	private JTextField jtf_Date;	//JTextField : gere la date de l'examen

	
	public ControleurExamen() {
		jtf_nom = new JTextField();
		jtf_matiere = new JTextField();
		jtf_Date = new JTextField();
		
		ActionListener acl = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}


	public JTextField getJtf_nom() {
		return jtf_nom;
	}


	public JTextField getJtf_matiere() {
		return jtf_matiere;
	}


	public JTextField getJtf_Date() {
		return jtf_Date;
	}
	
	
	
}
