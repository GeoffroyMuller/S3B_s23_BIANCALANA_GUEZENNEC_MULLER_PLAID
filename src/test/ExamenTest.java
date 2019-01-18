package test;

import modele.BDD.*;
import modele.Examen;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamenTest {
    private Examen examen;
    private Etudiant e1,e2,e3,e4;
    private Groupe gA,gB,gC,gD;

    @Before
    public void init(){
        try {
            DBConnection.setNomDB("etuplacementtest");
            Connection connect= DBConnection.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Particularite.createTable();
        Etudiant.createTable();
        ParticulariteEtudiant.createTable();
        TypePlace.createTable();
        Categorie.createTable();
        Groupe.createTable();
        GroupeCategorie.createTable();
        EtudiantGroupe.createTable();
        Place.createTable();
        Salle.createTable();

         gA = new Groupe("A");
         gB = new Groupe("B");
         gC = new Groupe("C");
         gD = new Groupe("D");

        gA.save();
        gB.save();
        gC.save();
        gD.save();

         e1 = new Etudiant("EtudiantA","EtudiantA");
         e2 = new Etudiant("EtudiantB","EtudiantB");
         e3 = new Etudiant("EtudiantC","EtudiantC");
         e4 = new Etudiant("EtudiantD","EtudiantD");

        e1.save();
        e2.save();
        e3.save();
        e4.save();

        gA.ajouterEtudiant(e1);
        gB.ajouterEtudiant(e2);
        gC.ajouterEtudiant(e3);
        gD.ajouterEtudiant(e4);

        examen = new Examen();
        examen.ajouterGroupe(gA);
        examen.ajouterGroupe(gB);
        examen.ajouterGroupe(gC);
        examen.ajouterGroupe(gD);

    }

    @After
    public void fin(){
        Particularite.deleteTable();
        Etudiant.deleteTable();
        ParticulariteEtudiant.deleteTable();
        TypePlace.deleteTable();
        Categorie.deleteTable();
        Groupe.deleteTable();
        GroupeCategorie.deleteTable();
        EtudiantGroupe.deleteTable();
        Place.deleteTable();
        Salle.deleteTable();
    }

    @Test
    public void testAjoutEtudiants(){
        assertTrue("Des élèves devrait être présent", !this.examen.etudiants.keySet().isEmpty());
    }

    @Test
    public void testSupprimerEtudiant(){
        examen.enleverUnEtudiantDeExamen(this.e1);
        assertTrue("L'étudiant devrait ne plus faire parti de l'examen",!this.examen.etudiants.keySet().contains(this.e1));
    }

    @Test
    public void testSupprimerGroupe(){
        examen.enleverDesGroupesDeExamen(this.gB);
        assertTrue("L'étudiant devrait ne plus faire parti de l'examen",!this.examen.etudiants.keySet().contains(this.e2));
    }

    @Test
    public void testSupprimerEtudiants(){
        ArrayList<Etudiant> listEtu = new ArrayList<Etudiant>();
        listEtu.add(this.e3);
        examen.enleverDesEtudiantsDeExamen(listEtu);
        assertTrue("L'étudiant devrait ne plus faire parti de l'examen",!this.examen.etudiants.keySet().contains(this.e3));
    }
}
