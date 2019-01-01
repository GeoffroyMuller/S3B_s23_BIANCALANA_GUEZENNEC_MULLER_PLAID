package vue_Examen;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur_Examen.ControleurExamen;

public class VueInfoExamen extends JPanel{
	private ControleurExamen controleur_Exam;
	
	private JLabel jl_nom = new JLabel("Nom");							//JLabel Nom
	private JLabel jl_date = new JLabel("Date");						//JLabel Date
	private JLabel jl_matiere = new JLabel("Matiere");					//JLabel Matiere
	
	private JPanel jp_nomExamen = new JPanel();			//JPanel 4 contient le Nom de l'Examen
	private JPanel jp_matiereExamen = new JPanel();		//JPanel 4 contient la Matiere de l'Examen
	private JPanel jp_dateExamen = new JPanel();			//JPanel 4 contient la Date de l'Examen
	
	public VueInfoExamen(ControleurExamen ctrlexamp) {
		controleur_Exam = ctrlexamp;                                     
		creerZoneInfo();
	}
	
	public void creerZoneInfo() {
		jp_nomExamen.add(jl_nom);
		jp_matiereExamen.add(jl_matiere);
		jp_dateExamen.add(jl_date);
		
		jp_nomExamen.add(controleur_Exam.getJtf_nom());
		jp_matiereExamen.add(controleur_Exam.getJtf_matiere());
		jp_dateExamen.add(controleur_Exam.getJtf_Date());
		
		this.add(jp_nomExamen);
		this.add(jp_matiereExamen);
		this.add(jp_dateExamen);
		
		
	}
	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setMinimumSize(new Dimension(500,100));

		VueInfoExamen vuec = new VueInfoExamen(new ControleurExamen());
		fenetre.add(vuec);
		fenetre.setVisible(true);

	}
}
