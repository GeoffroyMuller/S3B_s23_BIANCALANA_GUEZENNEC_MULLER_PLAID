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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
//import sun.net.www.content.image.jpeg;

public class ListeurSalle extends JPanel{
	private static Color color = new Color(236, 241, 245);
	private static class ActionSupprElemComboSalle implements ActionListener{
		private int nb;
		private JPanel jp;
		private ControleurExamen ctrlexam;
		public ActionSupprElemComboSalle(int i, JPanel jpp, ControleurExamen ctrl) {
			// TODO Auto-generated constructor stub
			nb = i ;
			jp = jpp;
			ctrlexam = ctrl;
		}
		
		public int getNb() {
			return nb;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ctrlexam.getListeComboSalle().remove(nb);
			jp.removeAll();
			System.out.println("Supprimer "+nb);
		}
		
	}
	private ControleurExamen ctrlexam;

	private ControleurExamen controleur_Exam;
	private JScrollPane scrollpane;
	private JPanel jp_all;
	private GridBagConstraints gbc = new GridBagConstraints();
	private ArrayList<Salle> prioriteSalle;

	private JPanel jptestcomb = new JPanel();
	
	public ListeurSalle(ControleurExamen ctrlexamp) throws SQLException {
		ctrlexam = ctrlexamp;
		this.setBackground(color);

		controleur_Exam = ctrlexamp;
		jp_all = new JPanel();
		jp_all.setLayout(new GridBagLayout());
		jp_all.setBackground(Color.darkGray);
		scrollpane = new JScrollPane(jp_all, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getVerticalScrollBar().setUnitIncrement(15);
		this.setLayout(new GridBagLayout());
		jp_all.setLayout(new GridBagLayout());

		/*test
		GridBagConstraints gbcp = new GridBagConstraints();
		for(int i=0;i<controleur_Exam.getListeComboSalle().size();i++) {
			JPanel jptest = new JPanel();
			jptest.setPreferredSize(new Dimension(WIDTH, 30));
			jptest.add(new JLabel(""+i));
			this.controleur_Exam.ajouterComboSalle();
			this.controleur_Exam.ajouterComboSalle();
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
		*/
		this.controleur_Exam.ajouterComboSalle();
		creerZonePriorite(); 

		
	}
	
	public void creerZonePriorite() {
		jp_all.removeAll();
		GridBagConstraints gbcp = new GridBagConstraints();
		
		for(int i=0;i<controleur_Exam.getListeComboSalle().size();i++) {
			JPanel jp_priorite = new JPanel();
			jp_priorite.setName("jpan"+i);
			jp_priorite.setPreferredSize(new Dimension(WIDTH, 30));
			jp_priorite.add(new JLabel(""+i));
			jp_priorite.add(this.controleur_Exam.getListeComboSalle().get(i));//
			JButton jb_supp = new JButton("Supprimer");
			jp_priorite.add(jb_supp);
			jp_priorite.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			gbcp.gridx = 0;
			gbcp.gridy = i;
			gbcp.fill = GridBagConstraints.BOTH;
			gbcp.insets = new Insets(0, 0, 0, 0);
			gbcp.weightx = 0;
			gbcp.weighty = 0;
			jp_all.add(jp_priorite, gbcp);
			//jb_supp.addActionListener(new ActionSupprElemComboSalle(i, jp_priorite, controleur_Exam));
			
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

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
