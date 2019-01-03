package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Particularite {

	private String nom;
	private int prendreEnComptePlacement; //boolean 0 ou 1 BIT dans Sql
	private int idParticularite;


	public Particularite(String nom, int prendreEnComptePlacement) {
		this.idParticularite=-1;
		this.nom=nom;
		if((prendreEnComptePlacement==0) || (prendreEnComptePlacement==1)) {
			this.prendreEnComptePlacement=prendreEnComptePlacement;
		}
		else {
			this.prendreEnComptePlacement=0;
		}
	}


	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**
	 * @param prendreEnComptePlacement the prendreEnComptePlacement to set
	 */
	public void setPrendreEnComptePlacement(int prendreEnComptePlacement) {
		if((prendreEnComptePlacement==0) || (prendreEnComptePlacement==1)) {
			this.prendreEnComptePlacement=prendreEnComptePlacement;
		}
		else {
			this.prendreEnComptePlacement=0;
		}
	}


	/**
	 * @param idParticularite the idParticularite to set
	 */
	public void setIdParticularite(int idParticularite) {
		this.idParticularite = idParticularite;
	}


	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * @return the prendreEnComptePlacement
	 */
	public int getPrendreEnComptePlacement() {
		return prendreEnComptePlacement;
	}


	/**
	 * @return the idParticularite
	 */
	public int getIdParticularite() {
		return idParticularite;
	}


	private Particularite(String nom, int prendreEnComptePlacement, int idParticularite) {
		this.idParticularite=idParticularite;
		this.nom=nom;
		if((prendreEnComptePlacement==0) || (prendreEnComptePlacement==1)) {
			this.prendreEnComptePlacement=prendreEnComptePlacement;
		}
		else {
			this.prendreEnComptePlacement=0;
		}
	}


	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`particularite` "
					+ "( `idParticularite` INT(11) NOT NULL AUTO_INCREMENT "
					+ ", `nom` VARCHAR(40) NOT NULL , `PrendreEnComptePlacement` INT(1) NOT NULL , "
					+ "PRIMARY KEY (`idParticularite`)) ENGINE = InnoDB";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e){
			System.out.println(e.getMessage()+" CreateTable "+e.getErrorCode()+e.toString());
		}
	}

	public static void deleteTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "SET FOREIGN_KEY_CHECKS = 0";
			String SQLPrep1 = "DROP TABLE PARTICULARITE";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
			prep0.execute();
			prep1.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" DeleteTable "+e.getErrorCode()+e.toString());
		}
	}


	public static Particularite findById(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Particularite WHERE IDPARTICULARITE ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		Particularite res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resPrendreEnComptePlacement = rs.getInt("PRENDREENCOMPTEPLACEMENT");
			res = new Particularite(resNom, resPrendreEnComptePlacement, id);
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM Particularite WHERE id='"+this.idParticularite+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idParticularite=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {

		if(this.idParticularite==-1) {
			this.saveNew();
		}
		else {
			this.update();
		}
	}

	/**
	 * Save new.
	 */
	private void saveNew() {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "INSERT INTO Particularite (`NOM`, `PRENDREENCOMPTEPLACEMENT`) VALUES" + 
					"('"+this.nom+"', '"+this.prendreEnComptePlacement+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Particularite WHERE NOM ='"+this.nom+"' AND PRENDREENCOMPTEPLACEMENT ='"+this.prendreEnComptePlacement+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idParticularite");
			}
			this.idParticularite=idres;
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
			String SQLPrep0 = "UPDATE Particularite " + 
					"SET NOM = '"+this.nom+"', PRENDREENCOMPTEPLACEMENT = '"+this.prendreEnComptePlacement+"'" + 
					"WHERE IDPARTICULARITE ='"+this.idParticularite+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}

}
