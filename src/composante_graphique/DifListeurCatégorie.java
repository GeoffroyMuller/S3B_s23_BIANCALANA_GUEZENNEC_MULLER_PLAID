package composante_graphique;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import controleur_composante_graphique.ConstructeurListeModelCategorie;
import controleur_composante_graphique.constructeurDataEtudiant;
import modele.*;

public class DifListeurCatégorie extends JSplitPane{
	
	private ArrayList<Etudiant> listeEtu;
	private JScrollPane jspLeft;
	private JScrollPane jspRight;
	private ConstructeurListeModelCategorie constructeurLMC;
	private constructeurDataEtudiant constructeurDATACOL;
	

	
		public DifListeurCatégorie() {
			//Donnée temporaire ppour la démo
			//Il faudra utiliser le listeur des sauvegardes de catégorie après
			ArrayList<Categorie> lc = new ArrayList<>();
			lc.add(new Categorie("sansgroupe1"));
			lc.add(new Categorie("sansgroupe2"));
			lc.add(new Categorie("sansgroupe3"));
			lc.add(new Categorie("sansgroupe4"));
			
			ArrayList<Etudiant> le = new ArrayList<>();
			le .add(new Etudiant("Muller", "Geoff"));
			le .add(new Etudiant("Guezennec", "Lucas"));
			le .add(new Etudiant("Plaid", "Justin"));
			le .add(new Etudiant("Biancalana", "Théo"));
			
			//---------------
			
			constructeurLMC = new ConstructeurListeModelCategorie(lc);
			constructeurDATACOL = new constructeurDataEtudiant(le);
			
			jspLeft = new JScrollPane(new CatégorieExtension(constructeurLMC.genererModelCatégorie()));
			
			jspRight = new JScrollPane(new JTable(constructeurDATACOL.genererDataLigneEtu(), constructeurDATACOL.GenereColonneEtu()));			
			this.setLeftComponent(jspLeft);
			this.setRightComponent(jspRight);
		}
		
		
		
		
}
