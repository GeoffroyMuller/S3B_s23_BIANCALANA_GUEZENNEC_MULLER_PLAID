package vue_Examen;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VueEtudiantParticipant extends JPanel{
	
	private JTable jt;
	private JScrollPane sp;
	public VueEtudiantParticipant() {
		/*String[] columns = {"Nom","Prenom","Groupe","Email"};
		Integer[][] data = new Integer[1000][columns.length];
		data[0][0]=100;
		tableau = new JTable(new DefaultTableModel(data, columns));
		this.add(tableau);*/
		
		String data[][]=new String[7][4];
		String column[]={"Nom","Prenom","Groupe","Prise en compte"};         
		jt=new JTable(data,column);     
		sp=new JScrollPane(jt);  
		//sp.setPreferredSize(new Dimension(1500, 800));
		
		this.add(sp);          

	}
	/**
	 * Definie et adapte la taille General
	 */
	public void definirTaille(int w, int h) {
		this.setPreferredSize(new Dimension(w, h));
		sp.setPreferredSize(new Dimension(w, h));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
