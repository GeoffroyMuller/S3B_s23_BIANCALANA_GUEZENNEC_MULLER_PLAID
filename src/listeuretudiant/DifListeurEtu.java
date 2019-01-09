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
import controleur_listeur.ListenerDeRefresh;
import controleur_listeur.ConstructeurDataEtudiant;
import modele.*;
import modele.BDD.Categorie;
import modele.BDD.Etudiant;
import modele.BDD.Groupe;

public class DifListeurEtu extends JSplitPane{

	private ArrayList<Etudiant> listeEtu;
	private JScrollPane jspLeft;
	private JScrollPane jspRight;
	private ConstructeurListeModelCategorie constructeurLMC;
	private ConstructeurDataEtudiant constructeurDATACOL;
	AfficheurTree afftree;
	JTable tabledata;



	public DifListeurEtu() {

		ArrayList<Categorie> lc = new ArrayList<>();
		try {
		lc= Categorie.getlistCategorie();
		}
		catch(Exception e) {
			
		}
		
		constructeurLMC = new ConstructeurListeModelCategorie(lc);
		
		ListenerDeRefresh ldf = new ListenerDeRefresh(this);
		
		constructeurDATACOL = new ConstructeurDataEtudiant(selectAll(lc));
		
		
		
		afftree = new AfficheurTree(lc,this);
		jspLeft = new JScrollPane( afftree);

		tabledata = new JTable(constructeurDATACOL.genererDataLigneEtu(),constructeurDATACOL.GenereColonneEtu());
		jspRight = new JScrollPane(tabledata);			
		this.setLeftComponent(new PanelGauche(jspLeft));
		jspLeft.setMinimumSize(new Dimension(250, 500));
		this.setRightComponent(jspRight);
		this.setDividerSize(100);

	}
	
	public void majData(ArrayList<Groupe> plg) {
		constructeurDATACOL=new ConstructeurDataEtudiant(plg);
		
		tabledata=new JTable(constructeurDATACOL.genererDataLigneEtu(),constructeurDATACOL.GenereColonneEtu());
		jspRight = new JScrollPane(tabledata);
		this.setRightComponent(jspRight);
		this.revalidate();
	
	}

	public ArrayList<Groupe> selectAll(ArrayList<Categorie> lc){
		ArrayList<Groupe> lg = new ArrayList<Groupe>();
		
		if(lc!=null) {
		for (int i = 0; i < lc.size(); i++) {
			//System.out.println("eeeee"+lc.get(i).getListGroupe());
			
			lg.addAll(lc.get(i).getListGroupe());
			
			ArrayList<Groupe> tmplg = lc.get(i).getListGroupe();
			
			for (int k = 0; k < tmplg.size(); k++) {
				Groupe gtmp = tmplg.get(k);
				lg.add(gtmp);
				//System.out.println("rrrrrr"+tmplg);
				
			}

		}
		
		}
		else {
			
		}

		/*for (int i = 0; i < lg.size(); i++) {
			System.out.println(lg.get(i).getListeEtudiants());
			
		}*/
		return lg;
	}
	
	public void refresh() {
		ArrayList<Categorie> lc = new ArrayList<>();
		try {
		lc= Categorie.getlistCategorie();
		}
		catch(Exception e) {
			
		}
		
		constructeurLMC = new ConstructeurListeModelCategorie(lc);
		
		
		constructeurDATACOL = new ConstructeurDataEtudiant(selectAll(lc));
		
		
		
		afftree = new AfficheurTree(lc,this);
		jspLeft = new JScrollPane( afftree);

		tabledata = new JTable(constructeurDATACOL.genererDataLigneEtu(),constructeurDATACOL.GenereColonneEtu());
		jspRight = new JScrollPane(tabledata);			
		this.setLeftComponent(new PanelGauche(jspLeft));
		jspLeft.setMinimumSize(new Dimension(250, 500));
		this.setRightComponent(jspRight);
		this.setDividerSize(100);
	}




}
