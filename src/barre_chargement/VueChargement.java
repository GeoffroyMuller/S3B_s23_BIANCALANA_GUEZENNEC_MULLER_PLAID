package barre_chargement;

import java.awt.Dimension;

import javax.swing.JFrame;

public class VueChargement extends JFrame{
	BarreChargement bc ;
	
	public VueChargement() {
		this.setTitle("Veuillez patientez...");
		this.bc = new BarreChargement(0,100);
		
		
		
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(600,600));
        this.setVisible(true);
		
		
	}

}
