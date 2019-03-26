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

import javax.swing.*;

import modele.BDD.Salle;
import modele.GestionFichiersExcel.ExportEtudiant;
import vue_Examen.DialogVerificationPlacement;
import vue_Examen.VueExamen;

import org.apache.poi.ss.formula.eval.BoolEval;

import modele.Examen;
import modele.BDD.Categorie;
import modele.BDD.Groupe;

public class ControleurExamen {

	private Examen examen;


	private String exam_nom;
	private String exam_matiere;
	private String exam_date;
	private JTextField jtf_nom;		//JTextField : gere le nom de l'examen
	private JTextField jtf_matiere; //JTextField : gere la matiere de l'examen
	private JTextField jtf_date;	//JTextField : gere la date de l'examen

	private JButton chsalle;
	private HashMap<JButton, Groupe> mapBoutton_groupe;
	private HashMap<JButton, Categorie> mapButton_categorie;
	private ArrayList<JComboBox<String>> listeComboSalle;
	
	private ArrayList<JButton> listeJBSuppr;

	private JButton jb_creerExam;	//JButton : creer un Examen
	
	//private JButton jb_groupe;
	//private JButton jb_categorie;
	private ArrayList<ArrayList<Groupe>> liste_listegrp;

	private String ancienneSelectionJComboBox;
	

	public ControleurExamen(Examen examenp)  {
		listeComboSalle = new ArrayList<JComboBox<String>>();
		liste_listegrp= new ArrayList<ArrayList<Groupe>>();

		examen = examenp;
		
		jtf_nom = new JTextField();
		jtf_matiere = new JTextField();
		jtf_date = new JTextField();
		
		jb_creerExam = new JButton("Créer l'Examen");
		jb_creerExam.setEnabled(false);

		liste_listegrp= new ArrayList<ArrayList<Groupe>>();

		//dev
		chsalle = new JButton("Choisir Salle 1 (test)");
		chsalle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Salle 1 (test) selectionner");
				Salle salle = Salle.findById(1);
				salle.getTableauPlaces(salle.getIdSalle());
				System.out.println("Salle" + salle.getNom());
				examen.ajouterSalle(salle);
				if(!examen.vérifierLeNombreDePlace()){
					jb_creerExam.setEnabled(false);
				}else{
					jb_creerExam.setEnabled(true);
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
				if(examen.verifierLesParametresExamen() && examen.vérifierLeNombreDePlace()){
					jb_creerExam.setEnabled(true);
				}else{
					jb_creerExam.setEnabled(false);
				}
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

				examen.setDate(jtf_date.getText());
				examen.setMatiere(jtf_matiere.getText());
				examen.setNom(jtf_nom.getText());
				examen.genererUnPlacement();
				//IMPORTANT ICI LANCER LE DIALOG
				DialogVerificationPlacement dialog = new DialogVerificationPlacement(null,"Prévisualisation",true,examen);
				dialog.afficherDialog();

			}
		});


	}


	public JButton creerBoutton_UnGroupe(Groupe grp) {
		final Groupe groupe = grp;
		final JButton jbt = new JButton();
		mapBoutton_groupe.put(jbt, grp);
		jbt.setText("Ajouter");
		jbt.setBackground(Color.white);
		jbt.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeButtonGroupe(jbt, groupe);

				//On vérifie si le nombre de place est toujours suffisant
				if(!examen.vérifierLeNombreDePlace()){
					jb_creerExam.setEnabled(false);
				}else{
					jb_creerExam.setEnabled(true);
				}
				//VueExamen.paneldev.repaint();//dev
			}
		});
		
		
		return jbt;
	}

	public JButton creerBoutton_UneCategorie(Categorie categp) {

		final JButton jbt = new JButton();
		final Categorie cate = categp;
		mapButton_categorie.put(jbt, categp);
		jbt.setPreferredSize(new Dimension(70, 20));
		jbt.setText("Ajouter");
		//jbt.setBackground(Color.white);
		jbt.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub
				changeButtonGroupeCategorie(cate, jbt);
				if(!examen.vérifierLeNombreDePlace()){
					jb_creerExam.setEnabled(false);
				}else{
					jb_creerExam.setEnabled(true);
				}
			}
		});
		
		//mapBoutton_groupe.put(jbt, grp);

		return jbt;
	}
	
	
	private void changeButtonGroupe(JButton jbt, Groupe grp) {
		if(jbt.getText().equals("Ajouter")) {
			jbt.setText("Retirer");
			//jbt.setBackground(Color.gray);

			 examen.ajouterGroupe(grp);
			System.out.println("Ajouter> groupe : "+grp.getNom()+"  nb etudiant::"+examen.getEtudiants().size());
			
		}else {
			if(jbt.getText().equals("Retirer")) {
				jbt.setText("Ajouter");
				//jbt.setBackground(Color.white);

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
			//jbt.setBackground(Color.black);
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
				//jbt.setBackground(Color.white);
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
	public void ajouterComboSalle() {

		try {
			ArrayList<Salle> salles = Salle.listSalle();
			String[] nomSalle = new String[salles.size()];
			for(int i=0;i<salles.size();i++) {
				nomSalle[i] = salles.get(i).getNom();
			}
			JComboBox combres = new JComboBox (nomSalle);
			this.ancienneSelectionJComboBox = (String)combres.getSelectedItem();
			examen.ajouterSalle(Salle.findByNom(this.ancienneSelectionJComboBox));
			combres.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Action !");
					JComboBox comboBox = (JComboBox)e.getSource();
					String nomSalle = (String)comboBox.getSelectedItem();
					Salle salle = Salle.findByNom(nomSalle);
					examen.ajouterSalle(salle);
					examen.retirerSalle(Salle.findByNom(ancienneSelectionJComboBox));
					ancienneSelectionJComboBox = nomSalle;
				}
			});
			this.listeComboSalle.add(combres);
		} catch (Exception e1) {
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null,"Une erreur c'est produite : Recuperation de la salle impossible","Erreur",JOptionPane.INFORMATION_MESSAGE);
			e1.printStackTrace();
		}

	}
	

	public ArrayList<JButton> getListeJBSuppr() {
		return listeJBSuppr;
	}


	public void addListeJBSuppr() {
		//this.listeJBSuppr.add();
	}


	public ArrayList<JComboBox<String>> getListeComboSalle() {
		return listeComboSalle;
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
