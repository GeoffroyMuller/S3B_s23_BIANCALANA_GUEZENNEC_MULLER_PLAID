package controleur_Examen;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import modele.BDD.Salle;
import vue_Examen.DialogVerificationPlacement;
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

	private Checkbox checkboxc;
	
	private HashMap<JButton, Groupe> mapBouton_groupe;
	private HashMap<JButton, Categorie> mapBouton_categorie;
	private ArrayList<JComboBox<String>> listeComboSalle;
	
	private ArrayList<JButton> listeJBSuppr;

	private JButton jb_creerExam;	//JButton : creer un Examen
	
	private String ancienneSelectionJComboBox;
	
	/**
	 * Constructeur
	 * @param examenp
	 */
	public ControleurExamen(Examen examenp)  {
		listeComboSalle = new ArrayList<JComboBox<String>>();
		new ArrayList<ArrayList<Groupe>>();

		examen = examenp;
		
		jtf_nom = new JTextField();
		jtf_matiere = new JTextField();
		jtf_date = new JTextField();
		
		jb_creerExam = new JButton("Créer l'Examen");
		jb_creerExam.setEnabled(true);

		checkboxc = new Checkbox();
		checkboxc.setState(true);
		new ArrayList<ArrayList<Groupe>>();

		mapBouton_groupe = new HashMap<>();
		mapBouton_categorie = new HashMap<>();
		/*
		 * dimensionne les JTextFields 
		 */
		jtf_nom.setPreferredSize(new Dimension(150, 25));
		jtf_matiere.setPreferredSize(new Dimension(150, 25));
		jtf_date.setPreferredSize(new Dimension(150, 25));

		/*
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
				System.out.println(">ContrGrp::"+checkboxc.getState());
				System.out.println("==================");
				
				checkboxc.setEnabled(false);
				examen.setGroupeSepare(checkboxc.getState());
				examen.setDate(jtf_date.getText());
				examen.setMatiere(jtf_matiere.getText());
				examen.setNom(jtf_nom.getText());
				examen.genererUnPlacement();
				checkboxc.setEnabled(true);
				//IMPORTANT ICI LANCER LE DIALOG
				
				if(examen.isFini()){
					DialogVerificationPlacement dialog = new DialogVerificationPlacement(null,"Prévisualisation",true,examen);
					dialog.afficherDialog();
				}

			}
		});


	}

	/**
	 * Creer/associe un bouton pour un Groupe
	 * @param grp Groupe
	 * @return JButton 
	 */
	public JButton creerBoutton_UnGroupe(Groupe grp) {
		final Groupe groupe = grp;
		final JButton jbt = new JButton();
		mapBouton_groupe.put(jbt, grp);
		jbt.setText("Ajouter");
		jbt.setBackground(Color.white);
		jbt.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeButtonGroupe(jbt, groupe);
			}
		});
		
		
		return jbt;
	}
	
	/**
	 * Creer/associe un bouton pour une Categorie
	 * @param categp Categorie
	 * @return JButton
	 */
	public JButton creerBoutton_UneCategorie(Categorie categp) {

		final JButton jbt = new JButton();
		final Categorie cate = categp;
		mapBouton_categorie.put(jbt, categp);
		jbt.setPreferredSize(new Dimension(70, 20));
		jbt.setText("Ajouter");
		jbt.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub
				changeButtonGroupeCategorie(cate, jbt);
			}
		});
		
		return jbt;
	}
	
	/**
	 * Change l'etat du bouton de Groupe et ajoute / retire en fonction de son etat
	 * @param jbt
	 * @param grp
	 */
	private void changeButtonGroupe(JButton jbt, Groupe grp) {
		if(jbt.getText().equals("Ajouter")) {
			jbt.setText("Retirer");

			 examen.ajouterGroupe(grp);
			System.out.println("Ajouter> groupe : "+grp.getNom()+"  nb etudiant::"+examen.getEtudiants().size());
			
		}else {
			if(jbt.getText().equals("Retirer")) {
				jbt.setText("Ajouter");

				examen.enleverDesGroupesDeExamen(grp);
				System.out.println("Retirer> groupe : "+grp.getNom()+"  nb etudiant::"+examen.getEtudiants().size());

			}
		}
	}
	
	
	
	/**
	 * Change l'etat du bouton de Categorie et ajoute / retire en fonction de son etat
	 * @param jbt
	 * @param grp
	 */
	private void changeButtonGroupeCategorie(Categorie categp, JButton jbt) {
		Categorie categcourante = mapBouton_categorie.get(jbt);
		HashMap<JButton, Groupe> mapBoutton_groupe_categorie = new HashMap<>();
		ArrayList<Groupe> listegroupe_categorie = categcourante.getListGroupe();
		for(Map.Entry<JButton, Groupe> mapgrp: mapBouton_groupe.entrySet()) {
			for(Groupe grp : listegroupe_categorie) {
				if(grp.getIdGroupe() == mapgrp.getValue().getIdGroupe()) {
					mapBoutton_groupe_categorie.put(mapgrp.getKey(), mapgrp.getValue());
				}
			}
		}
		if(jbt.getText().equals("Ajouter")) {
			jbt.setText("Retirer");
			
			for(Map.Entry<JButton, Groupe> jb_grp : mapBoutton_groupe_categorie.entrySet()) {
				if(jb_grp.getKey().getText().equals("Ajouter")) {
					jb_grp.getKey().setText("Retirer");

					examen.ajouterGroupe(jb_grp.getValue());
				}
				
			}
			System.out.println("Ajouter> Categorie : "+categp.getNom()+"  nb groupe::"+categp.getListGroupe().size()+"  nb etudiant::"+examen.getEtudiants().size());
		}else {
			if(jbt.getText().equals("Retirer")) {
				jbt.setText("Ajouter");

				for(Map.Entry<JButton, Groupe> jb_grp : mapBoutton_groupe_categorie.entrySet()) {
					if(jb_grp.getKey().getText().equals("Retirer")) {
						jb_grp.getKey().setText("Ajouter");

						examen.enleverDesGroupesDeExamen(jb_grp.getValue());
					}
					
				}
				System.out.println("Retirer> Categorie : "+categp.getNom()+"  nb groupe::"+categp.getListGroupe().size()+"  nb etudiant::"+examen.getEtudiants().size());

			}
		}
	}
	
	/**
	 * Ajoute une Combobox
	 */
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
	
	/**
	 * Getter
	 * @return
	 */
	public ArrayList<JButton> getListeJBSuppr() {
		return listeJBSuppr;
	}

	/**
	 * Getter
	 * @return
	 */
	public ArrayList<JComboBox<String>> getListeComboSalle() {
		return listeComboSalle;
	}

	/**
	 * Getter
	 * @return
	 */
	public JTextField getJtf_nom() {
		return jtf_nom;
	}

	/**
	 * Getter
	 * @return
	 */
	public JTextField getJtf_matiere() {
		return jtf_matiere;
	}

	/**
	 * Getter
	 * @return
	 */
	public JTextField getJtf_Date() {
		return jtf_date;
	}
	
	/**
	 * Getter
	 * @return
	 */
	public JButton getJb_creerExam() {
		return jb_creerExam;
	}

	/**
	 * Getter
	 * @return
	 */
	public Checkbox getCheckboxc() {
		return checkboxc;
	}

}
