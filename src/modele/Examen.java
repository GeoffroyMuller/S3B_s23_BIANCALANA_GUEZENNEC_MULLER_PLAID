package modele;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;

public class Examen {
	
	private String nom;
	private String matiere;
	private String date;
	private ArrayList<Categorie> listecateg;
	
	
	
	public String getNom() {
		return nom;
	}
	public String getMatiere() {
		return matiere;
	}
	public String getDate() {
		return date;
	}
	public ArrayList<Categorie> getListecateg() {
		return listecateg;
	}
	
	
	//Groupe Participant
	
	//Salle par Priorité
	
	//Contrainte




	
	
	
}
