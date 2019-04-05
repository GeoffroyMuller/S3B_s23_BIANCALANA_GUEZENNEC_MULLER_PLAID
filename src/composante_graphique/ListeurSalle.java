package composante_graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import controleur_Examen.ControleurExamen;
import modele.Examen;

/**
 * Composante graphique permettant d'ajouter/retirer des priorites (PanelListeurPriorite)
 * Les Priorites de type PanelListeurPriorite la compose
 * @author Geoff
 *
 */
public class ListeurSalle extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Color color = new Color(236, 241, 245);
	private static Color color2 = new Color(40, 73, 92);

	private ArrayList<PanelListeurPriorite> listprioritie = new ArrayList<PanelListeurPriorite>();

	private ControleurExamen controleur_Exam;
	private JScrollPane scrollpane;
	private JPanel jp_all;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Examen examen;

	/**
	 * Constructeur
	 * @param ctrlexamp
	 * @param examenp
	 * @throws SQLException
	 */
	public ListeurSalle(ControleurExamen ctrlexamp,Examen examenp) throws SQLException {
		this.setBackground(color);
		examen = examenp;
		controleur_Exam = ctrlexamp;
		jp_all = new JPanel();
		jp_all.setLayout(new GridBagLayout());
		jp_all.setBackground(color2);
		scrollpane = new JScrollPane(jp_all, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getVerticalScrollBar().setUnitIncrement(15);
		this.setLayout(new GridBagLayout());
		jp_all.setLayout(new GridBagLayout());

		this.controleur_Exam.ajouterComboSalle();
		ajouterPriorite();
		creerZonePriorite(); 


	}
	
	/**
	 * Ajoute une Priorite
	 */
	public void ajouterPriorite() {
		listprioritie.add(new PanelListeurPriorite(listprioritie.size(),this,examen));
	}
	
	/**
	 * Ajoute les Salles (contenue dans le listeurSalle) a l'examen
	 */
	public void ajouterSalles() {
		examen.reinitiliserLesSalles();
		for(PanelListeurPriorite plp : listprioritie) {

			if(plp.getTextJCombo().equals("Choisir une salle")) {
			}else {
				examen.ajouterSalle(plp.getSalle());
			}
		}
	}
	
	/**
	 * Supprime une Priorite par son id
	 * @param idp : id de la Priorite a supprimer
	 */
	public void supprimerPriorite(int idp) {
		try {

			listprioritie.remove(idp);
		}catch (Exception e) {
			// TODO: handle exception
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null,"Une erreur est survenue : Supression Impossible, l'élément n'existe pas.","Erreur",JOptionPane.INFORMATION_MESSAGE);
		}
		for(int i=0;i<listprioritie.size();i++) {
			listprioritie.get(i).setId(i);
		}
	}
	
	/**
	 * Creer la zone du listeur de salle contenant les Priorites
	 */
	public void creerZonePriorite() {
		jp_all.removeAll();
		GridBagConstraints gbcp = new GridBagConstraints();
		for(int i=0;i<listprioritie.size();i++) {
			gbcp.gridx = 0;
			gbcp.gridy = i;
			gbcp.fill = GridBagConstraints.BOTH;
			gbcp.insets = new Insets(0, 0, 0, 0);
			gbcp.weightx = 0;
			gbcp.weighty = 0;
			jp_all.add(listprioritie.get(i), gbcp);
		}

		JPanel jptest = new JPanel();
		jptest.setBackground(color2);

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
	 * Recharge a zero le listeur 
	 */
	public void recharger() {
		examen.reinitiliserLesSalles();
		listprioritie.removeAll(listprioritie);
		ajouterPriorite();
		creerZonePriorite(); 
	}
	
	/**
	 * Getter ListeTextSalle
	 * @return ArrayList<String> listtextpriorite 
	 */
	public ArrayList<String> getListeTextSalle() {
		int compte = 0;
		ArrayList<String> listtextpriorite = new ArrayList<>();
		for(PanelListeurPriorite priorite : listprioritie) {
			System.out.println("P_Salle:"+"compte"+": "+priorite.getTextJCombo());
			listtextpriorite.add(priorite.getTextJCombo());
			compte++;
		}
		return listtextpriorite;
	}
	
	/**
	 * Getter de PanelListeurPriorite contenu dans listprioritie par id 
	 * @param idp du PanelListeurPriorite souhaiter
	 * @return PanelListeurPriorite contenu dans listprioritie
	 */
	public PanelListeurPriorite getpanelByID(int idp) {
		for(PanelListeurPriorite plp : listprioritie) {
			if(plp.getId()==idp) {
				return plp;
			}
		}
		return null;
	}

	/**
	 * Getter listprioritie
	 * @return ArrayList<PanelListeurPriorite> listprioritie
	 */
	public ArrayList<PanelListeurPriorite> getListprioritie() {
		return listprioritie;
	}
	
	/**
	 * Definie et adapte la taille General
	 */
	public void definirTaille(int w, int h) {
		scrollpane.setPreferredSize(new Dimension(w, h));

	}
	
	/**
	 * paintComponent
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
