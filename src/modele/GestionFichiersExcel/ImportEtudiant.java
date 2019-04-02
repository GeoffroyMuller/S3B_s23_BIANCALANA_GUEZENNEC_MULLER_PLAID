package modele.GestionFichiersExcel;

import modele.BDD.*;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.Line;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class ImportEtudiant extends Observable {
    /**
     * Tabbleau de String contenant le nom des colonnes du fichier
     */
    private String[] nomDesColonnes;
    private ArrayList<Groupe> groupeTrouveDansLeDernierFichier;
    private Categorie categorie;
    private String cheminFichier;
    private String nomDeLaFeuille;


    public ImportEtudiant(String cheminFichier, String nomDeLaFeuille, Categorie categorie){
        this.groupeTrouveDansLeDernierFichier = new ArrayList<Groupe>();
        this.categorie = categorie;
        this.cheminFichier = cheminFichier;
        this.nomDeLaFeuille = nomDeLaFeuille;
       
    }

    public void importerEtudiant(){
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
        setChanged();
        notifyObservers();

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
        int indexHandicap = indexGroupe+1;
        int indexHandicapNonCompte = 4;
        int indexTiersTemps = 5;
        int indexTiersTempsNonCompte = 6;


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

             if((null != ligne.getCell(indexHandicap)) && ligne.getCell(indexHandicap).getStringCellValue().toLowerCase().contains("x")){
                System.out.println("HANDICAP");
                ArrayList<Particularite> particularites = new ArrayList<Particularite>();
                Particularite particularite = Particularite.findByNom("Situation de handicap (Prise en compte)");
                particularites.add(particularite);
                etudiant.ajouterParticularite(particularites);
            }


         if((null != ligne.getCell(indexHandicapNonCompte)) && ligne.getCell(indexHandicapNonCompte).getStringCellValue().toLowerCase().contains("x")){

                ArrayList<Particularite> particularites = new ArrayList<Particularite>();
                Particularite particularite = Particularite.findByNom("Situation de handicap (Non pris en compte)");
                System.out.println("Handicap pas compte");

                particularites.add(particularite);
                etudiant.ajouterParticularite(particularites);
            }

            if((null != ligne.getCell(indexTiersTemps)) && ligne.getCell(indexTiersTemps).getStringCellValue().toLowerCase().contains("x")){
                ArrayList<Particularite> particularites = new ArrayList<Particularite>();
                Particularite particularite = Particularite.findByNom("Tiers-Temps (Prendre en compte)");
                System.out.println("Tiers temp");

                particularites.add(particularite);
                etudiant.ajouterParticularite(particularites);
            }


            if((null != ligne.getCell(indexTiersTempsNonCompte)) && ligne.getCell(indexTiersTempsNonCompte).getStringCellValue().toLowerCase().contains("x")){
                ArrayList<Particularite> particularites = new ArrayList<Particularite>();
                Particularite particularite = Particularite.findByNom("Tiers-Temps (Non pris en compte)");
                System.out.println("Tiers temps pas compte");
                particularites.add(particularite);
                etudiant.ajouterParticularite(particularites);
            }
        }catch(NullPointerException e){
            e.printStackTrace();
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

    private static short[] getColorPattern(short colorIdx){
        short[] triplet = null;
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFPalette palette = workbook.getCustomPalette();
        HSSFColor color = palette.getColor(colorIdx);
        triplet = color.getTriplet();
        System.out.println("color : " + triplet[0] +"," + triplet[1] + "," +     triplet[2]);
        return triplet;
    }

    private boolean comparerCouleur(short[] a, Color b){
        boolean res = false;
        if(a[0] == b.getBlue() &&
        a[1] == b.getGreen() &&
        a[2] == b.getRed()){
            res=true;
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
          JOptionPane jop = new JOptionPane();
          jop.showMessageDialog(null,"Erreur, veuillez vérifier que le fichier Excel est conforme.","Erreur",JOptionPane.ERROR_MESSAGE);
        }
        return indexColonne;
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
