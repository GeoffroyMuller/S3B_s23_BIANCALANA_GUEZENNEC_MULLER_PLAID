package controleur_listeur;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

public class ControleurSuprListe extends JButton implements ActionListener{
	
	
	public ControleurSuprListe() {
		super();
		this.setText("Supprimer Liste/Groupe Selectioné(s)");
		this.setMinimumSize(new Dimension(250, 50));
	}

	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	


}
