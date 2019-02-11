package composante_graphique;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import controleur_Examen.ControleurExamen;
import modele.Examen;
import modele.BDD.Categorie;

public class ListeurSalle extends JPanel{
	private ControleurExamen ctrlexam;

	private ControleurExamen controleur_Exam;
	private JScrollPane scrollpane;
	private JPanel jp_all;
	private GridBagConstraints gbc = new GridBagConstraints();

	public ListeurSalle(ControleurExamen ctrlexamp) {
		ctrlexam = ctrlexamp;
		this.setBackground(new Color(162, 190, 251));

		controleur_Exam = ctrlexamp;
		jp_all = new JPanel();
		jp_all.setLayout(new GridBagLayout());
		jp_all.setBackground(Color.darkGray);
		scrollpane = new JScrollPane(jp_all, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getVerticalScrollBar().setUnitIncrement(15);
		this.setLayout(new GridBagLayout());
		jp_all.setLayout(new GridBagLayout());

		//test
		GridBagConstraints gbcp = new GridBagConstraints();
		for(int i=0;i<2;i++) {
			JPanel jptest = new JPanel();
			jptest.setPreferredSize(new Dimension(WIDTH, 25));
			jptest.add(new JLabel("test"));
			gbcp.gridx = 0;
			gbcp.gridy = i;
			gbcp.fill = GridBagConstraints.BOTH;
			gbcp.insets = new Insets(0, 0, 0, 0);
			gbcp.weightx = 0;
			gbcp.weighty = 0;
			jp_all.add(jptest, gbcp);
		}
		JPanel jptest = new JPanel();
		jptest.add(new JLabel("marge"));
		gbcp.gridx = 0;
		gbcp.gridy = 10;
		gbcp.fill = GridBagConstraints.BOTH;
		gbcp.insets = new Insets(15, 50, 15, 50);
		gbcp.weightx = 1;
		gbcp.weighty = 0.5;
		jp_all.add(jptest, gbcp);
		//fintest
		

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(15, 50, 15, 50);
		gbc.weightx = 1;
		gbc.weighty = 0.5;
		scrollpane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

		this.add(scrollpane, gbc);
	}

	/**
	 * Definie et adapte la taille General
	 */
	public void definirTaille(int w, int h) {
		scrollpane.setPreferredSize(new Dimension(w, h));
		
		/*for(PanelListeur plp : liste_panelListeur) {
			plp.definirTaille(w-200, 30);
		}*/
	}

}