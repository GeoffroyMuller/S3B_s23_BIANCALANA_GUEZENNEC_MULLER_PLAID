package test;

import modele.BDD.DBConnection;
import modele.BDD.Etudiant;
import modele.BDD.EtudiantGroupe;
import modele.BDD.Groupe;
import modele.GestionFichiersExcel.ExportEtudiant;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
public class ExportEtudiantTest {

    private Groupe groupetest;

    @Before
    public void environnementDeTest(){
        try {
            DBConnection.setNomDB("etuplacementtest");
            Connection connect= DBConnection.getConnection();

            Etudiant.createTable();


            groupetest = new Groupe("groupeTest");
            groupetest.save();
            Etudiant etudiantA = new Etudiant("etudiantA","etudiantPrenomA");
            Etudiant etudiantB = new Etudiant("etudiantB","etudiantPrenomB");
            Etudiant etudiantC = new Etudiant("etudiantC","etudiantPrenomC");
            etudiantA.save();
            etudiantB.save();
            etudiantC.save();

            EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiantA.getIdEtu(),groupetest.getIdGroupe());
            EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiantB.getIdEtu(),groupetest.getIdGroupe());
            EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiantC.getIdEtu(),groupetest.getIdGroupe());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @After
    public void detruireEnvironnementTest(){
        Etudiant.deleteTable();
    }

    /**
     * Test la methode exporterUnGroupe
     */
    @Test
    public void testExporterUnGroupe(){
        ExportEtudiant.exporterUnGroupe(groupetest);

        try {
            FileInputStream fichier = new FileInputStream(ExportEtudiant.nomDuDernierFichier);
            Workbook workbook = WorkbookFactory.create(fichier);
            Sheet feuille = workbook.getSheet("feuill1");


            String nomEtudiantA = feuille.getRow(1).getCell(0).getStringCellValue();
            String nomEtudiantB = feuille.getRow(2).getCell(0).getStringCellValue();
            String nomEtudiantC = feuille.getRow(3).getCell(0).getStringCellValue();

            String prenomEtudiantA = feuille.getRow(1).getCell(1).getStringCellValue();
            String prenomEtudiantB = feuille.getRow(2).getCell(1).getStringCellValue();
            String prenomEtudiantC = feuille.getRow(3).getCell(1).getStringCellValue();

            assertEquals("Le nom devrait être etudiantA","etudiantA",nomEtudiantA);
            assertEquals("Le nom devrait être etudiantB","etudiantB",nomEtudiantB);
            assertEquals("Le nom devrait être etudiantB","etudiantC",nomEtudiantC);

            assertEquals("Le prenom devrait être etudiantPrenomA","etudiantPrenomA",prenomEtudiantA);
            assertEquals("Le prenom devrait être etudiantPrenomB","etudiantPrenomB",prenomEtudiantB);
            assertEquals("Le prenom devrait être etudiantPrenomC","etudiantPrenomC",prenomEtudiantC);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
