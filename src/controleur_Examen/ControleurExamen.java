package controleur_Examen;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import modele.BDD.Salle;
import modele.GestionFichiersExcel.ExportEtudiant;
import vue_Etudiant.VueEtudiant;
import vue_Examen.VueExamen;

import org.apache.poi.ss.formula.eval.BoolEval;

import modele.Examen;
import modele.BDD.Categorie;
import modele.BDD.Groupe;

public class ControleurExamen {

	private Examen examen;
	private JButton chsalle;

	private String exam_nom;
	private String exam_matiere;
	private String exam_date;
	private HashMap<JButton, Groupe> mapBoutton_groupe;
	private HashMap<JButton, Categorie> mapButton_categorie;

	private JTextField jtf_nom;		//JTextField : gere le nom de l'examen
	private JTextField jtf_matiere; //JTextField : gere la matiere de l'examen
	private JTextField jtf_date;	//JTextField : gere la date de l'examen
	private JButton jb_creerExam;	//JButton : creer un Examen
	
	//private JButton jb_groupe;
	//private JButton jb_categorie;
	private ArrayList<ArrayList<Groupe>> liste_listegrp;

	public ControleurExamen(Examen examenp) {
		examen = examenp;
		jtf_nom = new JTextField();
		jtf_matiere = new JTextField();
		jtf_date = new JTextField();
		jb_creerExam = new JButton("Créer l'Examen");
		liste_listegrp= new ArrayList<ArrayList<Groupe>>();
		//dev
		chsalle = new JButton("Choisir Salle 1 (test)");
		chsalle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Salle 1 (test) selectionner");
				try {
					Salle salle = Salle.findById(1);
					salle.getTableauPlaces(salle.getIdSalle());
					System.out.println("Salle" + salle.getNom());
					examen.ajouterSalle(salle);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				chsalle.setText("Retirer Salle 1 (test) ");
				chsalle.setBackground(Color.gray);
			}
		});
		//findev
		mapBoutton_groupe = new HashMap<>();
		mapButton_categorie = new HashMap<>();
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
			
				examen.genererUnPlacement();
				ExportEtudiant exportEtu = new ExportEtudiant();
				exportEtu.exporterPlacement(examen.getPlacement());
			}
		});


	}


	public JButton creerBoutton_UnGroupe(Groupe grp) {
		
		JButton jbt = new JButton();
		mapBoutton_groupe.put(jbt, grp);
		jbt.setText("Ajouter");
		jbt.setBackground(Color.white);
		jbt.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeButtonGroupe(jbt, grp);	
				//VueExamen.paneldev.repaint();//dev
			}
		});
		
		
		return jbt;
	}

	public JButton creerBoutton_UneCategorie(Categorie categp) {

		JButton jbt = new JButton();
		mapButton_categorie.put(jbt, categp);
		jbt.setPreferredSize(new Dimension(70, 20));
		jbt.setText("Ajouter");
		jbt.setBackground(Color.white);
		jbt.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub
				changeButtonGroupeCategorie(categp, jbt);
			}
		});
		
		//mapBoutton_groupe.put(jbt, grp);

		return jbt;
	}
	
	
	private void changeButtonGroupe(JButton jbt, Groupe grp) {
		if(jbt.getText().equals("Ajouter")) {
			jbt.setText("Retirer");
			jbt.setBackground(Color.gray);

			examen.ajouterGroupe(grp);
			System.out.println("Ajouter> groupe : "+grp.getNom()+"  nb etudiant::"+examen.getEtudiants().size());
			
		}else {
			if(jbt.getText().equals("Retirer")) {
				jbt.setText("Ajouter");
				jbt.setBackground(Color.white);

				examen.enleverDesGroupesDeExamen(grp);
				System.out.println("Retirer> groupe : "+grp.getNom()+"  nb etudiant::"+examen.getEtudiants().size());

			}
		}
	}
	private void changeButtonGroupeCategorie(Categorie categp, JButton jbt) {
		Categorie categcourante = mapButton_categorie.get(jbt);
		HashMap<JButton, Groupe> mapBoutton_groupe_categorie = new HashMap<>();
		ArrayList<Groupe> listegroupe_categorie = categcourante.getListGroupe();
		for(Map.Entry<JButton, Groupe> mapgrp: mapBoutton_groupe.entrySet()) {
			for(Groupe grp : listegroupe_categorie) {
				if(grp.getIdGroupe() == mapgrp.getValue().getIdGroupe()) {
					mapBoutton_groupe_categorie.put(mapgrp.getKey(), mapgrp.getValue());
				}
			}
		}
		if(jbt.getText().equals("Ajouter")) {
			jbt.setText("Retirer");
			jbt.setBackground(Color.black);
			//liste_grp.clear();
			
			for(Map.Entry<JButton, Groupe> jb_grp : mapBoutton_groupe_categorie.entrySet()) {
				if(jb_grp.getKey().getText().equals("Ajouter")) {
					jb_grp.getKey().setText("Retirer");
					//liste_listegrp.add(jb_grp.getValue());
					examen.ajouterGroupe(jb_grp.getValue());
				}
				
			}
			System.out.println("Ajouter> Categorie : "+categp.getNom()+"  nb groupe::"+categp.getListGroupe().size());
		}else {
			if(jbt.getText().equals("Retirer")) {
				jbt.setText("Ajouter");
				jbt.setBackground(Color.white);
				//liste_grp.clear();
				for(Map.Entry<JButton, Groupe> jb_grp : mapBoutton_groupe_categorie.entrySet()) {
					if(jb_grp.getKey().getText().equals("Retirer")) {
						jb_grp.getKey().setText("Ajouter");
						liste_listegrp.remove(jb_grp.getValue());
						examen.enleverDesGroupesDeExamen(jb_grp.getValue());
					}
					
				}
				System.out.println("Retirer> Categorie : "+categp.getNom()+"  nb groupe::"+categp.getListGroupe().size());

			}
		}
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


	public JButton getChsalle() {
		return chsalle;
	}



}
