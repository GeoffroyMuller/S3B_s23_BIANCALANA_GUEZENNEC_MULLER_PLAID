package listeuretudiant;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class ControleurTestSelection extends JButton implements ActionListener{

	private JTree tree;
	
	public ControleurTestSelection(JTree ptree) {
		super();
		this.setText("Button affichage des sélections");
		this.setMinimumSize(new Dimension(250, 50));
		tree=ptree;
		this.addActionListener(this);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//For multiple selection you can use

	   // TreePath[] treePaths = tree.getSelectionModel().getSelectionPaths();
	   
		
		TreePath[] treePaths = tree.getSelectionPaths();
		
	    if(treePaths!=null) {
		for (TreePath treePath : treePaths) {
	         DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)treePath.getLastPathComponent();
	         Object userObject = selectedElement.getUserObject(); 
	         System.out.println(userObject.toString());
	         //Do what you want with selected element's user object
	          
	          
	    }
		
	    }
		
	}
	
	

}
