package modele.GestionFichiersExcel;

import modele.BDD.*;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ExportEtudiant {

/*
Convention de nommage des feuilles Excel, si vous devez choisir un nom de feuille se référer tout d'abord aux constantes
 */
    public static String nomFeuilleEtudiantPlace = "Placement";
    public static String nomFeuilleSignature = "Signature_Salle";

    public static String nomDuDernierFichier;


    public static XSSFWorkbook creerDocumentVide(String[] nomDesFeuilles){
        XSSFWorkbook wb = new XSSFWorkbook();
        for(String nom : nomDesFeuilles){
            Sheet feuille = wb.createSheet(nom);
        }
        return wb;
    }


    public static void exporterUnGroupe(Groupe groupe){

        //Création d'un document vide avec une feuille
        String[] nomFeuille = {groupe.getNom()};
        XSSFWorkbook wb = creerDocumentVide(nomFeuille);
        Sheet feuille = wb.getSheet(groupe.getNom());
        feuille.setColumnWidth((short)0,(short)(30*256));
        feuille.setColumnWidth((short)1,(short)(30*256));
        feuille.setColumnWidth((short)2,(short)(30*256));


        //On définit le style des cellules
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        //Création de la premiére ligne
        Row row = feuille.createRow((short)0);
            //Ajout des cellules
        Cell nomCell = row.createCell(0);
        Cell prenomCell = row.createCell(1);
        Cell groupeCell = row.createCell(2);

        nomCell.setCellStyle(cellStyle);
        prenomCell.setCellStyle(cellStyle);
        groupeCell.setCellStyle(cellStyle);

            //Valeur dans les cellules de la première ligne
        nomCell.setCellValue("Nom");
        prenomCell.setCellValue("Prenom");
        groupeCell.setCellValue("Groupe");

        //Ajout des données
            //Récupération des étudiants
        try {
            ArrayList<Etudiant> etudiants = EtudiantGroupe.recupererEtudiantDansGroupe(groupe.getIdGroupe());
            for(int i = 0; i < etudiants.size(); i++){

                 row = feuille.createRow((short)i+1);
                Cell etuNomCell = row.createCell(0);
                Cell etuPrenomCell = row.createCell(1);
                Cell etuGroupeCell = row.createCell(2);

                etuNomCell.setCellStyle(cellStyle);
                etuPrenomCell.setCellStyle(cellStyle);
                etuGroupeCell.setCellStyle(cellStyle);



                etuNomCell.setCellValue(etudiants.get(i).getNom());
                etuPrenomCell.setCellValue(etudiants.get(i).getPrenom());
                etuGroupeCell.setCellValue(groupe.getNom());

            }

            Calendar calendar = Calendar.getInstance();
            ExportEtudiant.nomDuDernierFichier = "fichierPourTest/groupe_"+groupe.getNom()+"_"+calendar.DAY_OF_MONTH+"-"+calendar.MONTH+"-"+calendar.YEAR+".xlsx";
            FileOutputStream fileOut = new FileOutputStream("fichierPourTest/groupe_"+groupe.getNom()+"_"+calendar.DAY_OF_MONTH+"-"+calendar.MONTH+"-"+calendar.YEAR+".xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    /**
     * Méthode permettant de créer le fichier excel associant chaque étudiant à sa place ainsi que les feuilles de signature présent dans ce même Excel
     * @param placement
     */
    public void exporterPlacement(HashMap<modele.BDD.Salle, HashMap<Place, Etudiant>> placement){
        //Création du fichier et des feuilles
        ArrayList<String> nomFeuille = new ArrayList<String>();
        nomFeuille.add(ExportEtudiant.nomFeuilleEtudiantPlace);
        for(Salle salle : placement.keySet()){
            nomFeuille.add(ExportEtudiant.nomFeuilleSignature+"_"+salle.getNom());
        }

        XSSFWorkbook wb = creerDocumentVide((String[])nomFeuille.toArray());

        //Debut par association place=>Etudiant
        Sheet feuille = wb.getSheet(ExportEtudiant.nomFeuilleEtudiantPlace);

        //Création de la premiére ligne
        Row row = feuille.createRow((short)0);


        //Application du style
        XSSFCellStyle cellStyle = creerLeStyle(wb);

        //Ajout des cellules
        String[] valeurs = {"GRP","NOM","PRENOM","SALLE","RANG","PLACE"};
        row = creerCellules(row,5,valeurs,cellStyle);

        //Ajout des étudiants a la feuille Excel

        for(Salle salle : placement.keySet()){
            for(Place place : placement.get(salle).keySet()){
                String nom = placement.get(salle).get(place).getNom();
                String prenom = placement.get(salle).get(place).getPrenom();
                String groupe = placement.get(salle).get(place).getGroupe();
            }
        }





    }

    private Row creerCellules(Row row, int nb,String[] valeur,XSSFCellStyle cellStyle){
        for(int i = 0; i < nb; i++){
            Cell cellule = row.createCell(i);
            cellule.setCellStyle(cellStyle);
            cellule.setCellValue(valeur[i]);

        }
        return row;
    }

    private  XSSFCellStyle creerLeStyle(XSSFWorkbook wb){
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        return cellStyle;
    }

}
