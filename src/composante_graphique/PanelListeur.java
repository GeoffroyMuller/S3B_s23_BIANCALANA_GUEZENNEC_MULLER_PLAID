package composante_graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.xml.internal.ws.api.Component;

import modele.Categorie;
import modele.Groupe;

public class PanelListeur extends JPanel{

	private Listeur listeur; //listeur qui contient this

	private boolean activer;
	private JPanel jp_all;
	private JPanel jp_categorie;
	private MouseListener ml;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Categorie categorie;	//categorie correspondant a this

	public PanelListeur(Categorie categ, Listeur listeur) {
		jp_all = new JPanel();
		jp_categorie = new JPanel();
		jp_all.setLayout(new GridBagLayout());
		this.categorie = categ;
		this.listeur = listeur;
		activer = false;
		jp_categorie.add(new JLabel(categ.getNom()));
		jp_categorie.setBackground(Color.WHITE);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		jp_all.add(jp_categorie, gbc);
		this.add(jp_all, gbc);
		ml = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				JPanel jptest = new JPanel();
				jptest.setBackground(Color.red);
				jp_all.add(jptest);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				System.out.print("pressed::"+PanelListeur.this.categorie.getNom());

				if(activer) {
					activer = false;
					System.out.println(" >> desactiver");
					jp_categorie.setBackground(Color.WHITE);
				}else {
					activer = true;
					System.out.println(" >> activer");
					int i = 1;
					ArrayList<Groupe> listegroupe = categorie.getListegroupe();
					jp_categorie.setBackground(Color.GRAY);
					JPanel jp;
					for (Groupe groupe : listegroupe) {
						jp = new JPanel();
						jp.add(new JLabel(groupe.getNom()));

						gbc.gridx = 0;
						gbc.gridy = i;
						gbc.fill = GridBagConstraints.BOTH;
						gbc.weightx = 0;
						gbc.weighty = 0;
						System.out.println("ee");
						jp_all.add(jp, gbc);

						i++;
					}

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
		g.fillRect(0, 10, 10, 10);
		System.out.println("rr");
	}

}
