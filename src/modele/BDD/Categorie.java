package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Popup_Categorie.Frame_Popup;


// TODO: Auto-generated Javadoc
/**
 * The Class Categorie.
 */
public class Categorie{

	public static final int ALPHA_ASC = 0; 
	public static final int ALPHA_DSC = 1;
	public static final int DATE_ASC = 2;
	public static final int DATE_DSC = 3; 
	private static int TRI_CHOISIT;
	
	
	
	/** The nom. */
	private String nom;

	/** The id categorie. */
	private int idCategorie;

	/**
	 * Instantiates a new categorie.
	 *
	 * @param nom the nom
	 */
	public Categorie(String nom) {
		this.idCategorie=-1;
		this.nom=nom;
	}



	/**
	 * Méthode permettant de récupérer les groupes appartenant à la catégorie courante
	 * @return
	 */
	public ArrayList<Groupe> getListGroupe(){
		ArrayList<Groupe> res = new ArrayList<Groupe>();


		Connection connect= null;
		try {
			connect = DBConnection.getConnection();
			PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM groupecategorie WHERE idCategorie ="+this.idCategorie);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();


			PreparedStatement prep2 = connect.prepareStatement("SELECT * FROM groupe WHERE idGroupe = ?");

			while (rs.next()){
				int groupeID = rs.getInt("idGroupe");
				prep2.setInt(1,groupeID);

				prep2.execute();
				ResultSet rs2 = prep2.getResultSet();

				while(rs2.next()){
					Groupe groupe = new Groupe(rs2.getString("nom"),groupeID);
					res.add(groupe);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}


	/**
	 * Sets the nom.
	 *
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}



	/**
	 * Sets the id categorie.
	 *
	 * @param idCategorie the idCategorie to set
	 */
	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}



	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * Gets the id categorie.
	 *
	 * @return the idGroupe
	 */
	public int getIdCategorie() {
		return idCategorie;
	}



	/**
	 * Instantiates a new categorie.
	 *
	 * @param nom the nom
	 * @param idCategorie the id categorie
	 */
	public Categorie(String nom, int idCategorie) {
		this.nom=nom;
		this.idCategorie=idCategorie;
	}


	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String nomBase = DBConnection.getNomDB();

			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS`"+nomBase+"`.`Categorie` ( `idCategorie` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL, PRIMARY KEY (`idCategorie`)) ENGINE = InnoDB;";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e){
			System.out.println(e.getMessage()+" CreateTable "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Delete table.
	 */
	public static void deleteTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "SET FOREIGN_KEY_CHECKS = 0";
			String SQLPrep1 = "DROP TABLE IF EXISTS CATEGORIE";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
			prep0.execute();
			prep1.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" DeleteTable "+e.getErrorCode()+e.toString());
		}
	}


	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the categorie
	 * @throws SQLException the SQL exception
	 */
	public static Categorie findById(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM CATEGORIE WHERE IDCATEGORIE ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		Categorie res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			res = new Categorie(resNom,id);
		}
		return res;
	}

	/**
	 * Get List categorie.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Categorie> getlistCategorie() {
		ArrayList<Categorie> res = new ArrayList<Categorie>();
		try {
			Connection connect = DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM CATEGORIE;";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resId = rs.getInt("idCategorie");
				res.add(new Categorie(resNom, resId));
			}
		}catch(SQLException e){
			/*
			TO DO
			 */
		}
		return res;
	}
	
	
	/**
	 * Get List categorie.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	
	
	/**static final int ALPHA_ASC = 0; 
	static final int ALPHA_DSC = 1;
	static final int DATE_ASC = 2;
	static final int DATE_DSC = 3;**/ 
	
	public static ArrayList<Categorie> getlistCategorieTrier(int tri) throws SQLException {
		
		String SQLPrep ="";
		Connection connect=DBConnection.getConnection();
		
		switch (tri) {
		case 0:
			SQLPrep= "SELECT * FROM CATEGORIE ORDER BY NOM ASC ;";
			Categorie.TRI_CHOISIT=0;
			break;
			
		case 1:
			SQLPrep= "SELECT * FROM CATEGORIE ORDER BY NOM DESC ;";
			Categorie.TRI_CHOISIT=1;
			break;
		case 2:
			SQLPrep= "SELECT * FROM CATEGORIE ORDER BY ID ASC ;";
			Categorie.TRI_CHOISIT=2;
			break;
		case 3:
			SQLPrep= "SELECT * FROM CATEGORIE ORDER BY ID DESC ;";
			Categorie.TRI_CHOISIT=3;
			break;

		default:
			SQLPrep= "SELECT * FROM CATEGORIE;";
			Categorie.TRI_CHOISIT=0;
			break;
		}
		
		
		
		
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Categorie> res = new ArrayList<Categorie>();
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resId = rs.getInt("idCategorie");
			res.add(new Categorie(resNom,resId));
			//System.out.println("eeee"+res);
		}
		return res;
	}


	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM CATEGORIE WHERE idCategorie ='"+this.idCategorie+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idCategorie=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {
		//save ou update de la categorie
		if(checkUniciterNomCategorie()) {
			if(this.idCategorie==-1) {
				this.saveNew();
			}
			else {
				this.update();
			}
		}
		else {
			Frame_Popup popup = new Frame_Popup(this);
			
		}

		
	}

	/**
	 * Save new.
	 */
	private void saveNew() {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "INSERT INTO CATEGORIE (`NOM`) VALUES" + 
					"('"+this.nom+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM CATEGORIE WHERE NOM ='"+this.nom+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idCategorie");
			}
			this.idCategorie=idres;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"new "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Update.
	 */
	private void update() {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "UPDATE CATEGORIE " + 
					"SET NOM = '"+this.nom+"'"+ 
					"WHERE IDCategorie ='"+this.idCategorie+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}
	

	public void ajouterGroupe(Groupe g) {
		GroupeCategorie.ajouterGroupeAUneCategorie(g.getIdGroupe(), this.getIdCategorie());
	}
	


	public void ajouterGroupe(ArrayList<Groupe> listGroupe) {
		for (int i = 0; i < listGroupe.size(); i++) {
			if(listGroupe.get(i).getIdGroupe()!=-1) {
				GroupeCategorie.ajouterGroupeAUneCategorie(listGroupe.get(i).getIdGroupe(), this.idCategorie);
			}
		}

	}
	
	public boolean checkUniciterNomCategorie() {
		boolean res = true;
		int count = 0;
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "select count(*) from Categorie " + 
					"WHERE nom ='"+this.nom+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			ResultSet rs = prep0.getResultSet();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
		
		if(count!=0){
			res=false;
		}
		System.out.println("resulatat du check de nom : "+res);
		return res;
	}
	
	public int getIdDoublon() {
		int res = -1;
		
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "select idCategorie from Categorie " + 
					"WHERE nom ='"+this.nom+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			ResultSet rs = prep0.getResultSet();
			while (rs.next()) {
				res = rs.getInt(1);
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
		

		System.out.println("resulatat du check de l'id : "+res);
		return res;
	}
	
	public String toString() {
		return this.nom;
	}
	
	static public int getTriChoisit() {
		return Categorie.TRI_CHOISIT;
	}



}
