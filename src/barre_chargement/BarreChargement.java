package barre_chargement;

import javax.swing.JProgressBar;

public class BarreChargement extends JProgressBar{
	private int maximum;
	
	public BarreChargement() {
		super();
		maximum = 100;
		// TODO Auto-generated constructor stub
	}
	
	public BarreChargement(int min,int max) {
		super(min,max);
		this.maximum=max;
	}
	
	@Override
	public void setValue(int n) {
		super.setValue(n);
	}

}
