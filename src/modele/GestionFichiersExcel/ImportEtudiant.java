package modele.GestionFichiersExcel;

import modele.Etudiant;
import modele.Groupe;
import org.apache.poi.sl.usermodel.Line;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class ImportEtudiant {
    /**
     * Tabbleau de String contenant le nom des colonnes du fichier
     */
    private String[] nomDesColonnes;
    private ArrayList<Groupe> groupeTrouveDansLeDernierFichier;

    public ImportEtudiant(String cheminFichier, String nomDeLaFeuille){
        this.groupeTrouveDansLeDernierFichier = new ArrayList<Groupe>();
        try {
            //Importation du fichier excel
            FileInputStream fichier = new FileInputStream(cheminFichier);
            Workbook workbook = WorkbookFactory.create(fichier);
            Sheet feuille = workbook.getSheet(nomDeLaFeuille);
            this.nomDesColonnes = new String[feuille.getRow(0).getLastCellNum()];
            //On r�cup�re l'en-t�te du fichier excel (la premi�re ligne)
            this.getPremiereLigne(feuille);
            //On r�cup�re le contenu de la feuille et on ajoute les groupes et les �tudiants trouves
            this.getContenu(feuille);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void getPremiereLigne(Sheet feuille){
        //On r�cup�re la premi�re ligne
        int y = feuille.getFirstRowNum();
        Row ligne = feuille.getRow(y);

        //On d�finit le nombre de colonne dans une ligne
        int xMax = ligne.getLastCellNum();

        //On r�cup�re les valeurs des cellules de la premi�re ligne et on les met dans un tableau
        for(int i = 0; i < xMax;i++){
            String value = ligne.getCell(i).getStringCellValue().toLowerCase();
            this.nomDesColonnes[i] = value;
        }
    }

    private void getContenu(Sheet feuille) {
        if (feuille.getLastRowNum() < 1) {
            /*
            Throw une exception (avec une fen�tre contextuelle)
             */
        } else {
            //Row ligne = feuille.getRow(1);
            for(int i = 1; i<=feuille.getLastRowNum();i++){
                this.ajoutEtudiant(feuille.getRow(i));
            }
        }
    }

    private void ajoutEtudiant(Row ligne){
        int indexNom = this.trouverIndex("nom");
        int indexPrenom = this.trouverIndex("prenom");
        int indexGroupe = this.trouverIndex("grp");

        try{
            Etudiant etudiant = new Etudiant(ligne.getCell(indexNom).getStringCellValue().toUpperCase(),
                    ligne.getCell(indexPrenom).getStringCellValue().toLowerCase());

            String nomDuGroupe = ligne.getCell(indexGroupe).getStringCellValue().toUpperCase();
            int indexOfGroupe = this.groupeExiste(nomDuGroupe);
            if(indexOfGroupe != -1){
                this.groupeTrouveDansLeDernierFichier.get(indexOfGroupe).ajouterEtudiant(etudiant);
            }else{
                Groupe gr = new Groupe(nomDuGroupe);
                gr.ajouterEtudiant(etudiant);
                this.groupeTrouveDansLeDernierFichier.add(gr);
            }
        }catch(NullPointerException e){
            /*
            Fenetre fichier non conforme
             */
        }
    }

    private int groupeExiste(String nom){
        int res = -1;
        Groupe groupe = new Groupe(nom);
        if(this.groupeTrouveDansLeDernierFichier.contains(groupe)){
            res = this.groupeTrouveDansLeDernierFichier.indexOf(groupe);
        }
        return res;
    }

    private int trouverIndex(String label){
        List<String> tab = Arrays.asList(this.nomDesColonnes);
        int indexColonne = -1;

        if(tab.contains(label)){
            indexColonne = tab.indexOf(label);
        }
        if(indexColonne == -1){
            //Throw fenetre
        }
        return indexColonne;
    }

    public boolean verifierConformite(){
        /**
         * To do
         */
        return true;
    }

    public String[] getNomDesColonnes() {
        return nomDesColonnes;
    }

    public void setNomDesColonnes(String[] nomDesColonnes) {
        this.nomDesColonnes = nomDesColonnes;
    }

    public ArrayList<Groupe> getGroupeTrouveDansLeDernierFichier() {
        return groupeTrouveDansLeDernierFichier;
    }

    public void setGroupeTrouveDansLeDernierFichier(ArrayList<Groupe> groupeTrouveDansLeDernierFichier) {
        this.groupeTrouveDansLeDernierFichier = groupeTrouveDansLeDernierFichier;
    }
}
