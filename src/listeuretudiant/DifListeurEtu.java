package listeuretudiant;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

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
			//Donn�e temporaire ppour la d�mo
			//Il faudra utiliser le listeur des sauvegardes de cat�gorie apr�s
			ArrayList<Categorie> lc = new ArrayList<>();
			lc.add(new Categorie("sansgroupe1"));
			lc.add(new Categorie("sansgroupe2"));
			lc.add(new Categorie("sansgroupe3"));
			lc.add(new Categorie("sansgroupe4"));
			
			ArrayList<Etudiant> le = new ArrayList<>();
			le .add(new Etudiant("Muller", "Geoff"));
			le .add(new Etudiant("Guezennec", "Lucas"));
			le .add(new Etudiant("Plaid", "Justin"));
			le .add(new Etudiant("Biancalana", "Th�o"));
			
			//---------------
			
			constructeurLMC = new ConstructeurListeModelCategorie(lc);
			constructeurDATACOL = new ConstructeurDataEtudiant(le);
			
			jspLeft = new JScrollPane(new Cat�gorieExtension(constructeurLMC.genererModelCat�gorie()));
			
			
			jspRight = new JScrollPane(new JTable(constructeurDATACOL.genererDataLigneEtu(), constructeurDATACOL.GenereColonneEtu()));			
			this.setLeftComponent(new PanelGauche(jspLeft));
			jspLeft.setMinimumSize(new Dimension(250, 500));
			this.setRightComponent(jspRight);
			
		}
		
		
		
		
}
