package composante_graphique;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

public class PanelListeur extends JPanel{
	private ControleurExamen controleur_Exam;
	private Listeur listeur; //listeur qui contient this

	private boolean activer;
	private JPanel jp_all;
	private JPanel jp_categorie;
	private ArrayList<JPanel> liste_jp_groupe;
	private ArrayList<JPanel> liste_jb_groupe;
	private MouseListener ml;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Categorie categorie;	//categorie correspondant a this


	public PanelListeur(Categorie categ, Listeur listeur, ControleurExamen ctrlexamp) {
		liste_jp_groupe = new ArrayList<JPanel>();

		jp_all = new JPanel();
		jp_categorie = new JPanel();
		jp_all.setLayout(new GridBagLayout());
		this.categorie = categ;
		this.listeur = listeur;
		activer = false;
		jp_categorie.add(new JLabel(categ.getNom()+"      Groupe Participant : 0/"+categ.getListGroupe().size()+"          "));
		jp_categorie.setBackground(Color.WHITE);
		jp_categorie.add(ctrlexamp.creerBoutton_UneCategorie(categ));
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

				System.out.print("pressed::"+PanelListeur.this.categorie.getNom());

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
					repaint();
					listeur.repaint();
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


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color((int)(Math.random()*200), (int)(Math.random()*10), (int)(Math.random()*50)));
		//g.fillRect(0, 10, 10, 10);
		jp_categorie.setPreferredSize(new Dimension(400, 30));
		jp_all.setVisible(false);

		jp_all.setVisible(true);
		//System.out.println("r");
	}

}
