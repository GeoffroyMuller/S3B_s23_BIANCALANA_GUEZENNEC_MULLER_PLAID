package vue;

import modele.BDD.Salle;
import modele.Examen;
import module_etudiant.VueModuleEtudiant;
import vue_Examen.VueExamen;

import javax.swing.*;

//import vue_Etudiant.VueEtudiant;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;


public class VueOngletModules extends JPanel implements Observer {
	JTabbedPane onglets;
	VueExamen moduleExamen;
	VueSalle moduleSalle;

	public VueOngletModules(Examen examen, Salle salle){
		this.setBackground(new Color(0xFFFFFF));
		this.onglets = new JTabbedPane();
		this.onglets.setFont(new Font("Serial",Font.BOLD,20));
		this.onglets.setUI(new TabbedPanDesign());

		try {
			moduleExamen = new VueExamen(examen);
			examen.addObserver(moduleExamen);
			}
			catch(Exception e) {
				System.out.println("erreur chargement module exam");
			}

		JPanel test = new JPanel();
		//FIN DELETE
		this.setLayout(null);
		this.onglets.setBounds(0,0,800,1000);
		this.onglets.add("Examen", moduleExamen);
		//this.onglets.add("Etudiants",moduleEtudiant);
		VueModuleEtudiant vme = new VueModuleEtudiant();
		this.onglets.add("Etudiants",vme.getJPanel());
		vme.addObserver(moduleExamen);
		this.moduleSalle = new VueSalle(salle);
		//salle.addObserver(this);
		salle.addObserver(this.moduleSalle);

		this.onglets.add("Salles",this.moduleSalle);


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

	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof Salle){
			this.moduleSalle = new VueSalle((Salle)o);
			this.onglets.removeTabAt(2);
			this.onglets.addTab("Salle",this.moduleSalle);
			this.onglets.setSelectedIndex(2);
		}

	}
}
