package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Groupe {

	private String nom;
	private int idGroupe;


	public Groupe(String nom) {
		this.idGroupe=-1;
		this.nom=nom;

	}

	

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * @return the idGroupe
	 */
	public int getIdGroupe() {
		return idGroupe;
	}



	private Groupe(String nom, int idGroupe) {
		this.nom=nom;
		this.idGroupe=idGroupe;
	}


	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`Groupe` "
					+ "( `idGroupe` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL, "
					+ "PRIMARY KEY (`idGroupe`)) ENGINE = InnoDB";
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
			String SQLPrep1 = "DROP TABLE GROUPE";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
			prep0.execute();
			prep1.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" DeleteTable "+e.getErrorCode()+e.toString());
		}
	}


	public static Groupe findById(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM GROUPE WHERE IDGROUPE ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		Groupe res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			res = new Groupe(resNom, id);
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM Groupe WHERE idGroupe ='"+this.idGroupe+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idGroupe=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {

		if(this.idGroupe==-1) {
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
			String SQLPrep0 = "INSERT INTO Groupe (`NOM`) VALUES" + 
					"('"+this.nom+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Groupe WHERE NOM ='"+this.nom+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idGroupe");
			}
			this.idGroupe=idres;
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
			String SQLPrep0 = "UPDATE Groupe " + 
					"SET NOM = '"+this.nom+"'"+ 
					"WHERE IDGroupe ='"+this.idGroupe+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}

}
