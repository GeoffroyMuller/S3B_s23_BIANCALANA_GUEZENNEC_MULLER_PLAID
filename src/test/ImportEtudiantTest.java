package test;

import static org.junit.Assert.*;

import modele.GestionFichiersExcel.ImportEtudiant;
import modele.Groupe;
import org.junit.Before;
import org.junit.Test;

public class ImportEtudiantTest {

    @Test
    public void testConstructeur(){
        ImportEtudiant test = new ImportEtudiant("C:\\Users\\Lucas\\Desktop\\Classeur1.xlsx","Feuil1");

       assertTrue(test.getGroupeTrouveDansLeDernierFichier().size()==2);
        assertEquals("Le nom de l'etudiant du groupe A devrait être Test","TEST",test.getGroupeTrouveDansLeDernierFichier().get(0).getListeEtudiants().get(0).getNom());
        assertEquals("Le nom de l'etudiant du groupe B devrait être Test1","TEST1",test.getGroupeTrouveDansLeDernierFichier().get(1).getListeEtudiants().get(0).getNom());
        assertEquals("Le nom de l'etudiant du groupe B devrait être Test2","TEST2",test.getGroupeTrouveDansLeDernierFichier().get(1).getListeEtudiants().get(1).getNom());

    }
}
