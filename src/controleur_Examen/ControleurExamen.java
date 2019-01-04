package controleur_Examen;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ControleurExamen {

	private String exam_nom;
	private String exam_matiere;
	private String exam_date;
	
	private JTextField jtf_nom;		//JTextField : gere le nom de l'examen
	private JTextField jtf_matiere; //JTextField : gere la matiere de l'examen
	private JTextField jtf_date;	//JTextField : gere la date de l'examen
	private JButton jb_creerExam;	//JButton : creer un Examen
	
	public ControleurExamen() {
		jtf_nom = new JTextField();
		jtf_matiere = new JTextField();
		jtf_date = new JTextField();
		jb_creerExam = new JButton("Créer l'Examen");
		
		/**
		 * dimensionne les JTextFields 
		 */
		jtf_nom.setPreferredSize(new Dimension(150, 25));
		jtf_matiere.setPreferredSize(new Dimension(150, 25));
		jtf_date.setPreferredSize(new Dimension(150, 25));

		/**
		 * ajout d'ActionListener sur les JTextFields
		 */
		jtf_nom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				exam_nom = ""+((JTextField)e.getSource()).getText();
				System.out.println("CtrlExam_jtf_Nom: "+((JTextField)e.getSource()).getText());
			}
		});
		jtf_matiere.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				exam_matiere = ""+((JTextField)e.getSource()).getText();
				System.out.println("CtrlExam_jtf_Matiere: "+((JTextField)e.getSource()).getText());
			}
		});
		jtf_date.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				exam_date = ""+((JTextField)e.getSource()).getText();
				System.out.println("CtrlExam_jtf_Date: "+((JTextField)e.getSource()).getText());
			}
		});
		jb_creerExam.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				System.out.println("===Examen creer===");
				System.out.println(">CtrlExam_jtf_Nom: "+jtf_nom.getText());
				System.out.println(">CtrlExam_jtf_Matiere: "+jtf_matiere.getText());
				System.out.println(">CtrlExam_jtf_Date: "+jtf_date.getText());
				System.out.println("==================");
			}
		});
		
		
	}


	public JTextField getJtf_nom() {
		return jtf_nom;
	}


	public JTextField getJtf_matiere() {
		return jtf_matiere;
	}


	public JTextField getJtf_Date() {
		return jtf_date;
	}


	public JButton getJb_creerExam() {
		return jb_creerExam;
	}
	
	
	
}
