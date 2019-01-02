package listeuretudiant;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import javafx.scene.Node;
import modele.Categorie;
import modele.Groupe;

public class AfficheurTree extends JPanel{

	private JTree tree;
	private DifListeurEtu lisetu;

	public AfficheurTree(DifListeurEtu plisetu) {
		super();
		lisetu=plisetu;
		this.setBackground(Color.PINK);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Liste de tout les �tudiants");
		createNodesExemple(top);
		tree = new JTree(top);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth =gbc.gridheight= GridBagConstraints.REMAINDER;
		gbc.anchor=GridBagConstraints.PAGE_START;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0,0,0,0);


		this.add(tree,gbc);
	}


	public AfficheurTree(ArrayList<Categorie> plc,DifListeurEtu plisetu) {
		super();
		lisetu=plisetu;
		this.setBackground(Color.PINK);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Liste de tout les �tudiants");
		createNodes(top,plc); 

		tree = new JTree(top);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.gridheight= GridBagConstraints.REMAINDER;
		gbc.anchor=GridBagConstraints.PAGE_START;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0,0,0,0);


		this.add(tree,gbc);

		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridx=1;

		ControleurTestSelection cts = new ControleurTestSelection(tree);

		//this.add(cts,gbc);

		TreeSelectionListener tsl = new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				majDonnee();

			}
		};

		tree.addTreeSelectionListener(tsl);
	}


	private ArrayList<Groupe> majDonnee(){
		ArrayList<Groupe> lg= new ArrayList<>();

		TreePath[] treePaths = tree.getSelectionPaths();

		if(treePaths!=null) {
			for (TreePath treePath : treePaths) {
				DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)treePath.getLastPathComponent();
				Object userObject = selectedElement.getUserObject(); 
				System.out.println(userObject.toString());

				if(userObject instanceof Groupe) {

					lg.add((Groupe)userObject);
				}
				if(userObject instanceof Categorie) {

					for (Groupe g :((Categorie)(userObject)).getListegroupe()) {
						lg.add(g);
					}
				}


				//Do what you want with selected element's user object
			}
		}

		lisetu.majData(lg);
		return lg;
	}


	private void createNodes(DefaultMutableTreeNode top,ArrayList<Categorie> plc) {
		DefaultMutableTreeNode categorie = null;
		DefaultMutableTreeNode groupe = null;


		for (int i = 0; i < plc.size(); i++) {
			categorie = new DefaultMutableTreeNode(plc.get(i));
			ArrayList<Groupe> lg = plc.get(i).getListegroupe();
			for (int j = 0; j < lg.size(); j++) {
				groupe = new DefaultMutableTreeNode(lg.get(j));

				categorie.add(groupe);
			}

			top.add(categorie);
		}



		/** categorie = new DefaultMutableTreeNode("Liste des Etudiant");
	    top.add(categorie);

	    //Swing Tutorial
	    groupe = new DefaultMutableTreeNode(new Categorie
	        ("The Swing Tutorial: A Guide to Constructing GUIs"));
	    categorie.add(groupe);

	    //...add more groupes for programmers...

	    categorie = new DefaultMutableTreeNode("groupes for Java Implementers");
	    top.add(categorie); **/

	}



	//exemple simple fonctionnel
	private void createNodesExemple(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode groupe = null;

		category = new DefaultMutableTreeNode("Liste des Etudiants");
		top.add(category);

		//original Tutorial
		groupe = new DefaultMutableTreeNode(new Categorie
				("The Java Tutorial: A Short Course on the Basics"));
		category.add(groupe);

		//Tutorial Continued
		groupe = new DefaultMutableTreeNode(new Categorie
				("The Java Tutorial Continued: The Rest of the JDK"));
		category.add(groupe);

		//Swing Tutorial
		groupe = new DefaultMutableTreeNode(new Categorie
				("The Swing Tutorial: A Guide to Constructing GUIs"));
		category.add(groupe);

		//...add more groupes for programmers...

		category = new DefaultMutableTreeNode("groupes for Java Implementers");
		top.add(category);

		//VM
		groupe = new DefaultMutableTreeNode(new Categorie
				("The Java Virtual Machine Specification"));
		category.add(groupe);

		//Language Spec
		groupe = new DefaultMutableTreeNode(new Categorie
				("The Java Language Specification"));
		category.add(groupe);
	}


}
