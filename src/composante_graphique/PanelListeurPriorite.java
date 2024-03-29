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

import modele.Examen;
import modele.BDD.Salle;

public class PanelListeurPriorite extends JPanel{
	private int id;
	private ListeurSalle ls;
	private Examen examen;
	private JLabel jl;
	private JComboBox<String> jcbox;
	private JButton jb_supp;
	/**
	 * Constructeur
	 * @param idp
	 * @param lsp
	 * @param examp
	 */
	public PanelListeurPriorite(int idp, ListeurSalle lsp,Examen examp) {
		examen = examp;
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
				ls.ajouterSalles();
				ls.creerZonePriorite();
			}
		});
		add(jb_supp);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
	}
	
	/**
	 * Creer une Combobox contenant les Salles de la BDD
	 */
	public void creerComboSalle() {
		try {
			ArrayList<Salle> salles = Salle.listSalle();
			String[] nomSalle = new String[salles.size()+1];
			nomSalle[0] = "Choisir une salle";
			for(int i=0;i<salles.size();i++) {
				nomSalle[i+1] = salles.get(i).getNom();
			}
			jcbox = new JComboBox<String> (nomSalle);
			jcbox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Salle Select: "+(String)jcbox.getSelectedItem());
					ls.ajouterSalles();
				}
			});
		} catch (Exception e1) {
			System.out.println("Erreur:: recup de Salle impossible");
			e1.printStackTrace();
		}

	}
	
	/**
	 * Getter Salle
	 * @return Salle
	 */
	public Salle getSalle() {
		return Salle.findByNom((String)jcbox.getSelectedItem());
	}
	
	/**
	 * Getter TextJCombo
	 * @return String
	 */
	public String getTextJCombo() {
		return (String)jcbox.getSelectedItem();
	}
	
	/**
	 * Getter Id
	 * @return int 
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter Id
	 * @param id int 
	 */
	public void setId(int id) {
		this.id = id;
		this.jl.setText(""+id);
		repaint();
	}
	
	
}
