package modele.GestionFichiersExcel;

import modele.BDD.Etudiant;
import modele.BDD.EtudiantGroupe;
import modele.BDD.Groupe;
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

public class ExportEtudiant {

    public static String nomDuDernierFichier;


    public static void exporterUnGroupe(Groupe groupe){



        //Création d'un document vide avec une feuille
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet feuille = wb.createSheet("feuill1");
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

}
