package composante_graphique;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import controleur_Examen.ControleurExamen;
import modele.Examen;
import modele.BDD.Categorie;
import modele.BDD.Salle;

public class ListeurSalle extends JPanel{
	private ControleurExamen ctrlexam;

	private ControleurExamen controleur_Exam;
	private JScrollPane scrollpane;
	private JPanel jp_all;
	private GridBagConstraints gbc = new GridBagConstraints();
	private ArrayList<Salle> prioriteSalle;

	public ListeurSalle(ControleurExamen ctrlexamp) throws SQLException {
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
		for(int i=0;i<1;i++) {
			JPanel jptest = new JPanel();
			jptest.setPreferredSize(new Dimension(WIDTH, 30));
			jptest.add(new JLabel(""+i));
			this.controleur_Exam.ajouterComboSalle();
			this.controleur_Exam.ajouterComboSalle();
			//System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+this.controleur_Exam.getListeComboSalle());
			jptest.add(this.controleur_Exam.getListeComboSalle().get(1));
			jptest.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
