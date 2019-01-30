package Popup_Categorie;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Boutton_Confirmer extends JButton implements ActionListener{
	
	Frame_Popup fp;

	public Boutton_Confirmer(Frame_Popup pfp) {
		super();
		fp =pfp;
		this.setText("Confirmer");
		this.addActionListener(this);
		this.setMinimumSize(new Dimension(100, 50));
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		fp.ecraser();
		fp.dispose();
	}

}
