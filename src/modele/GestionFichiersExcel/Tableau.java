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
     * Tableau � double entr�e repr�sentant le fichier excel converti, pour rappel(x = colonnes et y=lignes)
     */
    private String[][] tableau;

    public Tableau(String cheminFichier, String nomDeLaFeuille){
        try {
            //Importation du fichier excel
            FileInputStream fichier = new FileInputStream(cheminFichier);
            Workbook workbook = WorkbookFactory.create(fichier);

            Sheet feuille = workbook.getSheet(nomDeLaFeuille);
            //Pour la taille du tableau, on choisit de se bas� sur la premiere ligne du fichier excel pour d�finir son nombre de colonnes car on consid�re la premi�re ligne comme l'en-t�te
            this.tableau = new String[feuille.getRow(0).getLastCellNum()][feuille.getLastRowNum()+1];

            //On r�cup�re l'en-t�te du fichier excel (la premi�re ligne)
            this.enTete(feuille);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void enTete(Sheet feuille){
        //On r�cup�re la premi�re ligne
        int y = feuille.getFirstRowNum();
        Row ligne = feuille.getRow(y);

        //On d�finit le nombre de colonne dans une ligne
        int x = ligne.getFirstCellNum();
        int xMax = ligne.getLastCellNum();

        //On r�cup�re les valeurs des cellules de la premi�re ligne et on les met dans un tableau
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
