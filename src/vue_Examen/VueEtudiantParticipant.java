package vue_Examen;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VueEtudiantParticipant extends JPanel{
	private JTable tableau;
	public VueEtudiantParticipant() {
		/*String[] columns = {"Nom","Prenom","Groupe","Email"};
		Integer[][] data = new Integer[1000][columns.length];
		data[0][0]=100;
		tableau = new JTable(new DefaultTableModel(data, columns));
		this.add(tableau);*/
		String data[][]=new String[1000][3];
		String column[]={"Nom","Prenom","Groupe"};         
		JTable jt=new JTable(data,column);     
		JScrollPane sp=new JScrollPane(jt);  
		sp.setPreferredSize(new Dimension(500, 800));
		this.add(sp);          

	}

}
