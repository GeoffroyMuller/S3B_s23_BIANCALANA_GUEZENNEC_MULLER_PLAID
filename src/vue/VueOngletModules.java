package vue;

import vue_Examen.VueExamen;

import javax.swing.*;

//import vue_Etudiant.VueEtudiant;

import java.awt.*;
import vue_Etudiant.*;

public class VueOngletModules extends JPanel {
	JTabbedPane onglets;
	VueExamen moduleExamen;

	public VueOngletModules(){
		this.setBackground(new Color(0xFFFFFF));
		this.onglets = new JTabbedPane();
		this.onglets.setFont(new Font("Serial",Font.BOLD,20));
		this.onglets.setUI(new TabbedPanDesign());
		//A supprimer
		VueEtudiant moduleEtudiant = new VueEtudiant();
		try {
			moduleExamen = new VueExamen();
			}
			catch(Exception e) {
				System.out.println("erreur chargement module exam");
			}

		JPanel test = new JPanel();
		//FIN DELETE
		this.setLayout(null);
		this.onglets.setBounds(0,0,800,1000);
		this.onglets.add("Examen", moduleExamen);
		this.onglets.add("Etudiants",moduleEtudiant);

		this.onglets.add("Salles",new VueSalle());


		this.onglets.setBounds(0,0,800,1000);

		this.onglets.setToolTipTextAt(0,"Module permettant la gestion de liste");
		this.add(this.onglets);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		moduleExamen.definirTaille(this.getParent().getWidth(),this.getParent().getHeight());
		this.onglets.setBounds(0,0,this.getParent().getWidth(),this.getParent().getHeight());
	}
}
