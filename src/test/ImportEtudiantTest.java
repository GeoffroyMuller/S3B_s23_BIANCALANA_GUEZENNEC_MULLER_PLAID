package test;

import modele.GestionFichiersExcel.ImportEtudiant;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class ImportEtudiantTest {

    /*@Test
    public void testConstructeur(){
        Tableau test = new Tableau("C:\\Users\\Lucas\\Desktop\\Classeur1.xlsx","Feuil1");

        //Verification des nom des colonnes
        assertEquals("La premiere valeur de l'en-t�te devrait �tre test","test",test.getNomDesColonnes()[0]);
        assertEquals("La deuxi�me valeur de l'en-t�te devrait �tre test1","test2",test.getNomDesColonnes()[1]);
        assertEquals("La troisi�me valeur de l'en-t�te devrait �tre grp","grp",test.getNomDesColonnes()[2]);

        //Verification du contenu
        assertEquals("La premiere valeur de la deuxieme ligne devrait �tre ligne2Test","ligne2test",test.getTableau()[0][0]);
        assertEquals("La deuxieme valeur de la deuxieme ligne devrait �tre ligne2Test2","ligne2test2",test.getTableau()[0][1]);
        assertEquals("La troisieme valeur de la deuxieme ligne devrait �tre a","a",test.getTableau()[0][2]);

        //Verification des groupes detecte
        assertEquals("Le nombre de groupe detecte devrait �tre 2",2,test.getGroupeTrouveDansLeDernierFichier().size());
        assertTrue(test.getGroupeTrouveDansLeDernierFichier().contains(new Groupe("A")));
        assertTrue(test.getGroupeTrouveDansLeDernierFichier().contains(new Groupe("B")));

    }*/

    @Test
    public void testConstructeur(){
        ImportEtudiant test = new ImportEtudiant("fichierPourTest\\Classeur1.xlsx","Feuil1");

        assertTrue(test.getGroupeTrouveDansLeDernierFichier().size()==3);
        assertEquals("Le nom de l'etudiant du groupe A devrait �tre Test1","TEST1",test.getGroupeTrouveDansLeDernierFichier().get(0).getListeEtudiants().get(0).getNom());
        assertEquals("Le nom de l'etudiant du groupe B devrait �tre Test2","TEST2",test.getGroupeTrouveDansLeDernierFichier().get(1).getListeEtudiants().get(0).getNom());
        assertEquals("Le nom de l'etudiant du groupe C devrait �tre Test4","TEST4",test.getGroupeTrouveDansLeDernierFichier().get(2).getListeEtudiants().get(0).getNom());

    }
}
