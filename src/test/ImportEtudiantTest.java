package test;

import modele.BDD.*;
import modele.GestionFichiersExcel.ImportEtudiant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class ImportEtudiantTest {

    @Before
    public void init(){
        try {
            DBConnection.setNomDB("etuplacementtest");
            Connection connect= DBConnection.getConnection();

            Particularite.createTable();
            Etudiant.createTable();
            ParticulariteEtudiant.createTable();
            TypePlace.createTable();
            Groupe.createTable();
            EtudiantGroupe.createTable();
            Place.createTable();
            Salle.createTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testConstructeur(){
        ImportEtudiant test = new ImportEtudiant("fichierPourTest\\JeuDonnee.xlsx","Feuil1");

        /*assertTrue(test.getGroupeTrouveDansLeDernierFichier().size()==3);
        assertEquals("Le nom de l'etudiant du groupe A devrait être Test1","TEST1",test.getGroupeTrouveDansLeDernierFichier().get(0).getListeEtudiants().get(0).getNom());
        assertEquals("Le nom de l'etudiant du groupe B devrait être Test2","TEST2",test.getGroupeTrouveDansLeDernierFichier().get(1).getListeEtudiants().get(0).getNom());
        assertEquals("Le nom de l'etudiant du groupe C devrait être Test4","TEST4",test.getGroupeTrouveDansLeDernierFichier().get(2).getListeEtudiants().get(0).getNom());*/

    }
}
