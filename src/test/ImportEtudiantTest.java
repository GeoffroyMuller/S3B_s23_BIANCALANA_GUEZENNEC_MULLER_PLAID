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
            DBConnection.setNomDB("etuplacement");
            Connection connect= DBConnection.getConnection();

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

            //AJout de la salle

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testConstructeur(){

        Categorie categorie = new Categorie("Annee 1");
        categorie.save();

        ImportEtudiant test = new ImportEtudiant("fichierPourTest\\JeuDonnee.xlsx","Feuil1",categorie);

        /*assertTrue(test.getGroupeTrouveDansLeDernierFichier().size()==3);
        assertEquals("Le nom de l'etudiant du groupe A devrait être Test1","TEST1",test.getGroupeTrouveDansLeDernierFichier().get(0).getListeEtudiants().get(0).getNom());
        assertEquals("Le nom de l'etudiant du groupe B devrait être Test2","TEST2",test.getGroupeTrouveDansLeDernierFichier().get(1).getListeEtudiants().get(0).getNom());
        assertEquals("Le nom de l'etudiant du groupe C devrait être Test4","TEST4",test.getGroupeTrouveDansLeDernierFichier().get(2).getListeEtudiants().get(0).getNom());*/

    }
}
