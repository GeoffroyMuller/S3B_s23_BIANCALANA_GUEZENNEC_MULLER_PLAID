package composante_graphique;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.BDD.Salle;

public class PanelListeurPriorite extends JPanel{
	private int id;
	private ListeurSalle ls;
	private JLabel jl;
	private JComboBox<String> jcbox;
	private JButton jb_supp;
	public PanelListeurPriorite(int idp, ListeurSalle lsp) {
		id = idp;
		ls = lsp;
		jl = new JLabel(""+id);
		add(jl);
		creerComboSalle();
		add(jcbox);
		jb_supp = new JButton("Supprimer");
		jb_supp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Supprimer :: "+id);

				ls.supprimerPriorite(id);
				ls.creerZonePriorite();
			}
		});
		add(jb_supp);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
	}
	private void creerComboSalle() {
		try {
			ArrayList<Salle> salles = Salle.listSalle();
			String[] nomSalle = new String[salles.size()];
			for(int i=0;i<salles.size();i++) {
				nomSalle[i] = salles.get(i).getNom();
			}
			jcbox = new JComboBox<String> (nomSalle);
		} catch (Exception e1) {
			System.out.println("Erreur:: recuperation de Salle impossible");
			e1.printStackTrace();
		}

	}
	public String getTextJCombo() {
		return (String)jcbox.getSelectedItem();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		this.jl.setText(""+id);
		repaint();
	}
	
	
}
