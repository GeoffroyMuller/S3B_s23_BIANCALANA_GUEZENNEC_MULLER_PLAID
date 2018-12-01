package vue_Examen;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modele.Categorie;
import modele.Groupe;

public class Listeur extends JPanel{
	
	private ArrayList<Categorie> listecategorie;
	private boolean activCheckbox = false;	//true : active les checkbox
	
	JPanel jpp_principale = new JPanel(new BorderLayout());	//JPanel pricipale
	JScrollPane scrollpane;						//contient le panel listprincipale
	JPanel jp1_listeprincipale = new JPanel();	//contient l'ensemble des lists d' etudiant
	JPanel jp1_grpSelection = new JPanel();
	
	public Listeur(ArrayList<Categorie> listep, boolean activp) {
		activCheckbox = activp;
		listecategorie = listep;
		creeraffichage();

	}
	
	private void creeraffichage() {
		
		jp1_listeprincipale.setBackground(Color.gray);
		jp1_grpSelection.setBackground(Color.blue);
		jpp_principale.setBackground(Color.black);
		
		//pour chaque categorie un jpanel est creer puis placer dans l'affichage
		/*adapter la longeur des nom avec les JPanel*/
		JPanel jp;
		try {
			for (Categorie categorie : listecategorie) {
				jp = new JPanel(new BorderLayout());
				jp.setPreferredSize(new Dimension(325, 25));
				JLabel jl = new JLabel(" "+categorie.getNom()+" ");
				JLabel jl_grpselect = new JLabel("              0/"+categorie.getListegroupe().size()+" Groupe selectionner");
				jp.add(jl, BorderLayout.WEST);
				jp.add(jl_grpselect, BorderLayout.CENTER);
				if(activCheckbox) {
					jp.add(new Checkbox(), BorderLayout.EAST);
				}
				jp.setBackground(Color.red);
				jp1_listeprincipale.add(jp);
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			jp = new JPanel();
			jp.setPreferredSize(new Dimension(350, 25));
			JLabel jl = new JLabel("Aucune Catégorie");
			jp.add(jl);
			jp.setBackground(Color.red);
			jp1_listeprincipale.add(jp);
			System.out.println("Aucune Catégorie");
		}

		
		scrollpane = new JScrollPane(jp1_listeprincipale);
		jpp_principale.add(scrollpane, BorderLayout.NORTH);
		jpp_principale.add(jp1_grpSelection, BorderLayout.CENTER);
		this.add(jpp_principale);
	}
	
	
	
	
	/**
	 * methode main de test interne a Listeur
	 * @param arg
	 */
	public static void main(String arg[]) {
		JFrame fenetre = new JFrame("EtuPlacement");

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(new Dimension(700,600));
		
		ArrayList<Categorie> listcateg = new ArrayList<>();
		Categorie c1 = new Categorie("Année 1", new ArrayList<Groupe>());
		Categorie c2 = new Categorie("Année 2", new ArrayList<Groupe>());	
		listcateg.add(c1);
		listcateg.add(c2);
		Listeur listeur = new Listeur(listcateg, true);
		fenetre.add(listeur);
		
		fenetre.setVisible(true);
		
		
	}
}
