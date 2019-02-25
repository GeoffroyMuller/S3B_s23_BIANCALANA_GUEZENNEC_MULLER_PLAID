package composante_graphique;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.synth.SynthSeparatorUI;

//import com.sun.xml.internal.ws.api.Component;

import controleur_Examen.ControleurExamen;
import modele.BDD.Categorie;
import modele.BDD.Groupe;

public class PanelListeurCategorie extends JPanel{
	private ControleurExamen controleur_Exam;
	private ListeurCategorie listeur; //listeur qui contient this

	private boolean activer;
	private JPanel jp_all;
	private JPanel jp_categorie;
	private ArrayList<JPanel> liste_jp_groupe;
	private MouseListener ml;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Categorie categorie;	//categorie correspondant a this


	public PanelListeurCategorie() {
		
	}
	public PanelListeurCategorie(Categorie categ, ListeurCategorie listeur, ControleurExamen ctrlexamp) {
		liste_jp_groupe = new ArrayList<JPanel>();

		jp_all = new JPanel();
		jp_categorie = new JPanel();
		jp_categorie.setLayout(new GridBagLayout());
		jp_all.setLayout(new GridBagLayout());
		this.categorie = categ;
		jp_categorie.setPreferredSize(new Dimension(400, 30));
		this.listeur = listeur;
		activer = false;
		GridBagConstraints gbcp = new GridBagConstraints();
		gbcp.gridx = 0;
		gbcp.gridy = 0;
		gbcp.fill = GridBagConstraints.BOTH;
		gbcp.insets = new Insets(0, 30, 0, 20);
		gbcp.weightx = 0.5;
		gbcp.weighty = 0;
		jp_categorie.add(new JLabel(""+categ.getNom()) , gbcp);
		
		gbcp.gridx = 1;
		gbcp.gridy = 0;
		gbcp.fill = GridBagConstraints.BOTH;
		gbcp.insets = new Insets(0, 0, 0, 45);
		gbcp.weightx = 0;
		gbcp.weighty = 0;
		jp_categorie.add(new JLabel("Groupe Participant : /"+categ.getListGroupe().size()+""), gbcp);
		
		jp_categorie.setBackground(Color.WHITE);
		
		gbcp.gridx = 2;
		gbcp.gridy = 0;
		gbcp.fill = GridBagConstraints.BOTH;
		gbcp.insets = new Insets(0, 0, 0, 30);
		gbcp.weightx = 0;
		gbcp.weighty = 0;
		jp_categorie.add(ctrlexamp.creerBoutton_UneCategorie(categ), gbcp);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0;
		gbc.weighty = 0;
		jp_categorie.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		jp_all.add(jp_categorie, gbc);

		JPanel jpp;
		ArrayList<Groupe> listegroupe = categ.getListGroupe();
		System.out.println("Categorie "+categ.getIdCategorie()+" nombre de groupe::"+listegroupe.size());
		for (Groupe groupe : listegroupe) {
			jpp = new JPanel();
			jpp.add(new JLabel(groupe.getNom()));
			jpp.add(ctrlexamp.creerBoutton_UnGroupe(groupe));
			liste_jp_groupe.add(jpp);
		}
		this.add(jp_all, gbc);
		ml = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				System.out.print("pressed::"+PanelListeurCategorie.this.categorie.getNom());

				if(activer) {
					activer = false;
					System.out.println(" >> desactiver");
					jp_categorie.setBackground(Color.WHITE);

					for (JPanel jp : liste_jp_groupe) {
						System.out.println("des::"+liste_jp_groupe.size());

						if(jp_all.getComponentCount()>0) {
							jp_all.remove(jp);
						}
					}
					repaint();
				}else {
					activer = true;
					System.out.println(" >> activer");
					jp_categorie.setBackground(Color.GRAY);

					int i = 1;
					for (JPanel jp : liste_jp_groupe) {
						System.out.println("act::"+liste_jp_groupe.size());
						gbc.gridx = 0;
						gbc.gridy = i;
						gbc.fill = GridBagConstraints.BOTH;
						gbc.weightx = 0;
						gbc.weighty = 0;
						jp_all.add(jp, gbc);

						i++;
					}
					//repaint();
					//listeur.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
		jp_categorie.addMouseListener(this.ml);
	}
	
	public void definirTaille(int w, int y) {
		jp_categorie.setPreferredSize(new Dimension(w, y));
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.setColor(new Color((int)(Math.random()*200), (int)(Math.random()*10), (int)(Math.random()*50)));
		//g.fillRect(0, 10, 10, 10);
		//jp_categorie.setPreferredSize(new Dimension(400, 30));
		//this.setBackground(Color.red);
		jp_all.setVisible(false);
		jp_all.setVisible(true);
	}

}
