package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import modele.Examen;
import modele.BDD.Etudiant;

public class VueEtudiantParticipant extends JPanel{
	
	private JTable jt;
	private JScrollPane sp;
	private Examen examen;
	private String column[]={"Nom","Prenom","Groupe","Prise en compte"};  
	private HashMap<Etudiant, String> listetudiants;
	public VueEtudiantParticipant(Examen examenp) {
		setLayout( new BorderLayout());
		/*String[] columns = {"Nom","Prenom","Groupe","Email"};
		Integer[][] data = new Integer[1000][columns.length];
		data[0][0]=100;
		tableau = new JTable(new DefaultTableModel(data, columns));
		this.add(tableau);*/
		String data[][]=new String[7][4];      
		jt=new JTable(data,column);     
		sp=new JScrollPane(jt);  
		//sp.setPreferredSize(new Dimension(1500, 800));
		
		this.add(sp,BorderLayout.CENTER); 
		JPanel jpindice = new JPanel();
		jpindice.add(new JLabel("Nombre de participants : 0"));
		this.add(jpindice,BorderLayout.NORTH);
	}
	public void ajouterListes(HashMap<Etudiant, String> listetudiantsp) {
		this.removeAll();
		listetudiants = listetudiantsp;
		
		String data[][]=new String[listetudiants.size()+5][4]; 
		System.out.println("--------------------------"+listetudiants.size()+" ++++"+listetudiantsp);
		int compte = 0;
		for(Etudiant etul : listetudiants.keySet()) {
			data[compte][0] = etul.getNom();
			data[compte][1] = etul.getPrenom();
			data[compte][2] = etul.getGroupe();
			data[compte][3] = "TEST";
			compte++;
		}
		jt=new JTable(data,column);    
		jt.setEnabled(false);
		sp=new JScrollPane(jt);
		this.add(sp,BorderLayout.CENTER); 
		JPanel jpindice = new JPanel();
		jpindice.add(new JLabel("Nombre de participants : "+listetudiants.size()));
		this.add(jpindice,BorderLayout.NORTH);
		repaint();
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
