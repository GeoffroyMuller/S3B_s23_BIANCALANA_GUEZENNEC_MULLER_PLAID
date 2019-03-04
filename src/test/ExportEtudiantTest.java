package test;

import modele.BDD.*;
import modele.Examen;
import modele.GestionFichiersExcel.ExportEtudiant;
import modele.GestionFichiersExcel.ImportEtudiant;
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
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
public class ExportEtudiantTest {

    private Groupe groupetest;
    private Examen examen;

    @Before
    public void environnementDeTest(){
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


            //Ajout de salles et d'étudiant pour creer un placement et ainsi pouvoir tester l'export en excel
            Salle salle = new Salle("Salle_Test2",10,10);
            salle.save();



            TypePlace typePlace = new TypePlace("Chaise",1);
            TypePlace typePlaceAllee = new TypePlace("Allee",0);

            typePlaceAllee.save();
            typePlace.save();

            int idSalle = salle.getIdSalle();

            for(int i = 0; i < 10;i++){
                for(int j = 0; j < 10;j++){
                    if(j == 7 || j == 4){
                        Place place = new Place(i+""+j,typePlaceAllee.getIdTypePlace(),i,j,0,salle.getIdSalle());
                        place.save();
                    }else{
                        Place place = new Place(i+""+j,typePlace.getIdTypePlace(),i,j,1,salle.getIdSalle());
                        place.save();
                    }

                }
            }

            //On génére des étudians
            Categorie categorie = new Categorie("Annee 1");
            ImportEtudiant importEtudiant = new ImportEtudiant("fichierPourTest\\JeuDonnee.xlsx","Feuil1",categorie);

            ArrayList<Groupe> groupes = Groupe.listGroupe();

            examen = new Examen();

            salle.getTableauPlaces(salle.getIdSalle());
            examen.ajouterSalle(salle);
            for(Groupe groupe : groupes){
                examen.ajouterGroupe(groupe);
            }

            examen.genererUnPlacement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void detruireEnvironnementTest(){
        Etudiant.deleteTable();
        Salle.deleteTable();
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


    /**
     * Test la méthode ExporterPlacement
     */
    @Test
    public void testExporterPlacement(){
        ExportEtudiant export = new ExportEtudiant();

        export.exporterPlacement(examen.getPlacement(),examen);

        try {
            FileInputStream fichier = new FileInputStream(ExportEtudiant.nomDuDernierFichier);
            Workbook workbook = WorkbookFactory.create(fichier);
            Sheet feuille = workbook.getSheet(ExportEtudiant.nomFeuilleEtudiantPlace);


           /* String nomEtudiantA = feuille.getRow(1).getCell(0).getStringCellValue();
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
            assertEquals("Le prenom devrait être etudiantPrenomC","etudiantPrenomC",prenomEtudiantC);*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
