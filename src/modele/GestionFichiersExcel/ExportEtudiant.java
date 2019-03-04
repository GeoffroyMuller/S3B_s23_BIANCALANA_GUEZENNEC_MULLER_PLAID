package modele.GestionFichiersExcel;

import modele.BDD.*;
import modele.Examen;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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
        Cell idCell = row.createCell(3);


        idCell.setCellStyle(cellStyle);
        nomCell.setCellStyle(cellStyle);
        prenomCell.setCellStyle(cellStyle);
        groupeCell.setCellStyle(cellStyle);

            //Valeur dans les cellules de la première ligne
        nomCell.setCellValue("Nom");
        prenomCell.setCellValue("Prenom");
        groupeCell.setCellValue("Groupe");
        idCell.setCellValue("ID");


        //Ajout des données
            //Récupération des étudiants
        try {
            ArrayList<Etudiant> etudiants = EtudiantGroupe.recupererEtudiantDansGroupe(groupe.getIdGroupe());
            for(int i = 0; i < etudiants.size(); i++){

                 row = feuille.createRow((short)i+1);
                Cell etuNomCell = row.createCell(0);
                Cell etuPrenomCell = row.createCell(1);
                Cell etuGroupeCell = row.createCell(2);
                Cell etuIdCell = row.createCell(3);


                etuNomCell.setCellStyle(cellStyle);
                etuPrenomCell.setCellStyle(cellStyle);
                etuGroupeCell.setCellStyle(cellStyle);
                etuIdCell.setCellStyle(cellStyle);



                etuNomCell.setCellValue(etudiants.get(i).getNom());
                etuPrenomCell.setCellValue(etudiants.get(i).getPrenom());
                etuGroupeCell.setCellValue(groupe.getNom());
                etuIdCell.setCellValue(i+1);


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
    public void exporterPlacement(HashMap<modele.BDD.Salle, HashMap<Place, Etudiant>> placement, Examen examen) {
        //Création du fichier et des feuilles
        ArrayList<String> nomFeuille = new ArrayList<String>();
        nomFeuille.add(ExportEtudiant.nomFeuilleEtudiantPlace);
        for(Salle salle : placement.keySet()){
            nomFeuille.add(ExportEtudiant.nomFeuilleSignature+"_"+salle.getNom());
        }

        String[] tabNomFeuille = new String[nomFeuille.size()];
        tabNomFeuille = nomFeuille.toArray(tabNomFeuille);

        XSSFWorkbook wb = creerDocumentVide(tabNomFeuille);

        //Debut par association place=>Etudiant
        Sheet feuillePlacement = wb.getSheet(ExportEtudiant.nomFeuilleEtudiantPlace);

        //Création de l'enTete
        this.creerEntete(1,3,1,7,examen,feuillePlacement);


        //Création de la premiére ligne
        Row row = feuillePlacement.createRow((short)4);



        //Application du style
        XSSFCellStyle cellStyle = creerLeStyle(wb);

        //Ajout des cellules
        String[] valeurs = {"ID","GRP","NOM","PRENOM","SALLE","RANG","PLACE"};
        row = creerCellules(row,valeurs.length,valeurs,cellStyle);

        //Ajout des étudiants a la feuille Excel
        int nbLignePlace = 1;
        int nbLigneSignature = 1;
        for(Salle salle : placement.keySet()){

            Sheet feuilleSalle = wb.getSheet(ExportEtudiant.nomFeuilleSignature+"_"+salle.getNom());

            //Création de l'en tête
            this.creerEntete(1,3,1,8,examen,feuilleSalle);



            Row rowFeuilleSalle = feuilleSalle.createRow(4);
            String[] valeursPremiereLigneSalle = {"ID","GRP","NOM","PRENOM","SALLE","RANG","PLACE","SIGNATURE"};
            rowFeuilleSalle = creerCellules(rowFeuilleSalle,valeursPremiereLigneSalle.length,valeursPremiereLigneSalle,cellStyle);

            int id = 1;
            for(Place place : placement.get(salle).keySet()){
                //Informations étudiant
                String nom = placement.get(salle).get(place).getNom();
                String prenom = placement.get(salle).get(place).getPrenom();
                String groupe = placement.get(salle).get(place).getGroupe();

                //Information place
                String rang = place.getI()+"";
                String emplacement = place.getJ()+"";

                //Ajout de la ligne a la feuille de placement
                Row ligne = feuillePlacement.createRow(nbLignePlace);
                String[] valeursLigne={id+"",groupe,nom,prenom,salle.getNom(),rang,emplacement};
                ligne =  creerCellules(ligne,valeursLigne.length,valeursLigne,cellStyle);

                //Ajout de la ligne à la feuille de signature
                ligne = feuilleSalle.createRow(nbLigneSignature);
                ligne = creerCellules(ligne,valeursLigne.length,valeursLigne,cellStyle);


                id++;
                nbLignePlace++;
                nbLigneSignature++;
            }
            nbLigneSignature = 0;
        }

        Calendar calendar = Calendar.getInstance();
        ExportEtudiant.nomDuDernierFichier = "fichierPourTest/placement_"+calendar.DAY_OF_MONTH+"-"+calendar.MONTH+"-"+calendar.YEAR+".xlsx";
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("fichierPourTest/placement_"+calendar.DAY_OF_MONTH+"-"+calendar.MONTH+"-"+calendar.YEAR+".xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    private void creerEntete(int firstCell,int lastCell,int firstColumn, int lastColumn,Examen examen,Sheet feuille){
        Row ligneEntete = feuille.createRow(1);
        Cell celluleEntete = ligneEntete.createCell(1);
        String texteEntete = "Examen -"+examen.getMatiere()+" - "+examen.getDate()+"\n";
        for(String nomGroupe : examen.groupeParticipant()){
            texteEntete+=nomGroupe+" ";
        }
        celluleEntete.setCellValue(texteEntete);
        feuille.addMergedRegion(new CellRangeAddress(firstCell,lastCell,firstColumn,lastColumn));
    }

    private Row creerCellules(Row row, int nb,String[] valeur,XSSFCellStyle cellStyle){
        for(int i = 0; i < nb; i++){
            Cell cellule = row.createCell(i);
            cellule.setCellStyle(cellStyle);

            try{
                cellule.setCellValue(valeur[i]);
            }catch(IndexOutOfBoundsException e){
                cellule.setCellValue("");
            }

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
