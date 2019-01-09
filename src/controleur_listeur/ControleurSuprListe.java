package controleur_listeur;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import listeuretudiant.AfficheurTree;
import listeuretudiant.DifListeurEtu;
import modele.BDD.Categorie;
import modele.BDD.Groupe;

public class ControleurSuprListe extends JButton implements ActionListener{

	private JTree tree;
	public ControleurSuprListe(JTree ptree) {
		super();
		this.setText("Supprimer Liste/Groupe Selectioné(s)");
		this.setMinimumSize(new Dimension(250, 50));
		tree=ptree;
		this.addActionListener(this);
	}






	@Override
	public void actionPerformed(ActionEvent e) {



		TreePath[] treePaths = tree.getSelectionPaths();

		if(treePaths!=null) {
			for (TreePath treePath : treePaths) {
				DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)treePath.getLastPathComponent();
				Object userObject = selectedElement.getUserObject(); 

				//System.out.println(userObject.toString());

				if(userObject instanceof Categorie) {
					((Categorie) userObject).delete();
				}
				else {

					if(userObject instanceof Groupe) {
						((Groupe) userObject).delete();

					}
				}



			}

		}
			ListenerDeRefresh.avertirChangement();
	
	}




}
