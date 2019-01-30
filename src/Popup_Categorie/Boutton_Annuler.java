package Popup_Categorie;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Boutton_Annuler extends JButton implements ActionListener{
	
	Frame_Popup fp;
	
	public Boutton_Annuler(Frame_Popup pfp) {
		super();
		fp =pfp;
		this.setText("Annuler");
		this.addActionListener(this);
		this.setMinimumSize(new Dimension(100, 40));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fp.dispose();
		
	}

}
