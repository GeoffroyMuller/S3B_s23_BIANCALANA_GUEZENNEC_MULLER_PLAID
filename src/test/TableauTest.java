package test;

import static org.junit.Assert.*;

import modele.GestionFichiersExcel.Tableau;
import org.junit.Before;
import org.junit.Test;

public class TableauTest {

    @Test
    public void testConstructeur(){
        Tableau test = new Tableau("C:\\Users\\Lucas\\Desktop\\Classeur1.xlsx","Feuil1");

        assertEquals("La premiere valeur de l'en-t�te devrait �tre test","test",test.getTableau()[0][0]);
        assertEquals("La deuxi�me valeur de l'en-t�te devrait �tre test1","test2",test.getTableau()[1][0]);
        assertEquals("La troisi�me valeur de l'en-t�te devrait �tre test2","test3",test.getTableau()[2][0]);
    }
}
