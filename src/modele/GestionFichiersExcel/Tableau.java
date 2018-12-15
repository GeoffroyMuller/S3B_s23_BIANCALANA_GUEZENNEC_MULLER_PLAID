package modele.GestionFichiersExcel;

import org.apache.poi.sl.usermodel.Line;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Tableau {
    /**
     * Tableau à double entrée représentant le fichier excel converti, pour rappel(x = colonnes et y=lignes)
     */
    private String[][] tableau;

    public Tableau(String cheminFichier, String nomDeLaFeuille){
        try {
            //Importation du fichier excel
            FileInputStream fichier = new FileInputStream(cheminFichier);
            Workbook workbook = WorkbookFactory.create(fichier);

            Sheet feuille = workbook.getSheet(nomDeLaFeuille);
            //Pour la taille du tableau, on choisit de se basé sur la premiere ligne du fichier excel pour définir son nombre de colonnes car on considére la premiére ligne comme l'en-tête
            this.tableau = new String[feuille.getRow(0).getLastCellNum()][feuille.getLastRowNum()+1];

            //On récupére l'en-tête du fichier excel (la premiére ligne)
            this.enTete(feuille);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void enTete(Sheet feuille){
        //On récupére la premiére ligne
        int y = feuille.getFirstRowNum();
        Row ligne = feuille.getRow(y);

        //On définit le nombre de colonne dans une ligne
        int x = ligne.getFirstCellNum();
        int xMax = ligne.getLastCellNum();

        //On récupére les valeurs des cellules de la premiére ligne et on les met dans un tableau
        for(int i = 0; i < xMax;i++){
            String value = ligne.getCell(i).getStringCellValue();
            this.tableau[i][0] = value;
        }
    }

    public boolean verifierConformite(){
        /**
         * To do
         */
        return true;
    }


    public String[][] getTableau() {
        return tableau;
    }

    public void setTableau(String[][] tableau) {
        this.tableau = tableau;
    }
}
