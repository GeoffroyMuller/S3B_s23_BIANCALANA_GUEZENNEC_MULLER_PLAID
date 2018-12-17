package listeuretudiant;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.Border;


import controleur_listeur.ConstructeurListeModelCategorie;
import controleur_listeur.ConstructeurDataEtudiant;
import modele.*;

public class DifListeurEtu extends JSplitPane{

	private ArrayList<Etudiant> listeEtu;
	private JScrollPane jspLeft;
	private JScrollPane jspRight;
	private ConstructeurListeModelCategorie constructeurLMC;
	private ConstructeurDataEtudiant constructeurDATACOL;



	public DifListeurEtu() {
		//Donnée temporaire ppour la démo
		//Il faudra utiliser le listeur des sauvegardes de catégorie après
		ArrayList<Categorie> lc = new ArrayList<>();
		lc.add(new Categorie("sansgroupe1"));
		lc.add(new Categorie("Avecgroupe1"));
		lc.add(new Categorie("sansgroupe2"));
		lc.add(new Categorie("Avecgroupe2"));


		ArrayList<Etudiant> les4 = new ArrayList<>();
		les4 .add(new Etudiant("Muller", "Geoff"));
		les4 .add(new Etudiant("Guezennec", "Lucas"));
		les4 .add(new Etudiant("Plaid", "Justin"));
		les4 .add(new Etudiant("Biancalana", "Théo"));

		ArrayList<Etudiant> les2 = new ArrayList<>();
		les2 .add(new Etudiant("Viard", "Hugo"));
		les2 .add(new Etudiant("Jhon", "Jhonny"));


		Groupe g1 = new Groupe("A");
		g1.setListeEtudiants(les4);


		Groupe g2 = new Groupe("B");
		g2.setListeEtudiants(les2);


		lc.get(1).ajouterGroupe(g1);
		
		lc.get(1).ajouterGroupe(g2);
		

		
		lc.get(3).ajouterGroupe(g1);

		ArrayList<Etudiant> le = new ArrayList<>();
		le .add(new Etudiant("Muller", "Geoff"));
		le .add(new Etudiant("Guezennec", "Lucas"));
		le .add(new Etudiant("Plaid", "Justin"));
		le .add(new Etudiant("Biancalana", "Théo"));
		

		//---------------

		constructeurLMC = new ConstructeurListeModelCategorie(lc);
		
		/**
		ArrayList<Groupe> lgtest = new ArrayList<Groupe>() ;
		lgtest.add(g1);
		lgtest.add(g2);
		
		System.out.println("kkkkkkkkl"+lgtest);
		System.out.println(g1.getListeEtudiants());
		System.out.println(lgtest.get(0).getListeEtudiants());
		constructeurDATACOL = new ConstructeurDataEtudiant(lgtest);
		*/
		constructeurDATACOL = new ConstructeurDataEtudiant(selectAll(lc));
		
		
		

		jspLeft = new JScrollPane( new AfficheurTree(lc));


		jspRight = new JScrollPane(new JTable(constructeurDATACOL.genererDataLigneEtu(), constructeurDATACOL.GenereColonneEtu()));			
		this.setLeftComponent(new PanelGauche(jspLeft));
		jspLeft.setMinimumSize(new Dimension(250, 500));
		this.setRightComponent(jspRight);

	}

	public ArrayList<Groupe> selectAll(ArrayList<Categorie> lc){
		ArrayList<Groupe> lg = new ArrayList<Groupe>();

		for (int i = 0; i < lc.size(); i++) {
			
			lg.addAll(lc.get(i).getListegroupe());
			
			/**ArrayList<Groupe> tmplg = lc.get(i).getListegroupe();
			
			for (int k = 0; k < tmplg.size(); k++) {
				Groupe gtmp = tmplg.get(k);
				lg.add(gtmp);
				
			}**/

		}

		for (int i = 0; i < lg.size(); i++) {
			System.out.println(lg.get(i).getListeEtudiants());
			
		}
		return lg;
	}




}
