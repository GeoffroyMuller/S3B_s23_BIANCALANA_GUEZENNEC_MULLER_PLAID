package modele.GestionFichiersExcel;

import modele.BDD.Categorie;
import modele.BDD.Etudiant;
import modele.BDD.EtudiantGroupe;
import modele.BDD.Groupe;
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
    private Categorie categorie;

    public ImportEtudiant(String cheminFichier, String nomDeLaFeuille, Categorie categorie){
        this.groupeTrouveDansLeDernierFichier = new ArrayList<Groupe>();
        this.categorie = categorie;
        try {
            //Importation du fichier excel
            FileInputStream fichier = new FileInputStream(cheminFichier);
            Workbook workbook = WorkbookFactory.create(fichier);
            Sheet feuille = workbook.getSheet(nomDeLaFeuille);
            this.nomDesColonnes = new String[feuille.getRow(0).getLastCellNum()];
            //On recupere l'en-tete du fichier excel (la premiére ligne)
            this.getPremiereLigne(feuille);
            //On récupére le contenu de la feuille et on ajoute les groupes et les étudiants trouves
            this.getContenu(feuille);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void getPremiereLigne(Sheet feuille){
        //On récupére la premiére ligne
        int y = feuille.getFirstRowNum();
        Row ligne = feuille.getRow(y);

        //On définit le nombre de colonne dans une ligne
        int xMax = ligne.getLastCellNum();

        //On récupére les valeurs des cellules de la premiére ligne et on les met dans un tableau
        for(int i = 0; i < xMax;i++){
            String value = ligne.getCell(i).getStringCellValue().toLowerCase();
            this.nomDesColonnes[i] = value;
        }
    }

    private void getContenu(Sheet feuille) {
        if (feuille.getLastRowNum() < 1) {
            /*
            Throw une exception (avec une fenêtre contextuelle)
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

            etudiant.save();

            String nomDuGroupe = ligne.getCell(indexGroupe).getStringCellValue().toUpperCase();

            int indexOfGroupe = this.groupeExiste(nomDuGroupe);

            if(indexOfGroupe != -1){
                int idGroupe =  this.groupeTrouveDansLeDernierFichier.get(indexOfGroupe).getIdGroupe();
                int idEtudiant = etudiant.getIdEtu();
                EtudiantGroupe.ajouterEtudiantAUnGroupe(idEtudiant,idGroupe);
            }else{
                Groupe gr = new Groupe(nomDuGroupe);
                gr.save();
                ArrayList<Groupe> groupes = new ArrayList<Groupe>();
                groupes.add(gr);
                this.categorie.ajouterGroupe(groupes);

                EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiant.getIdEtu(),gr.getIdGroupe());

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
