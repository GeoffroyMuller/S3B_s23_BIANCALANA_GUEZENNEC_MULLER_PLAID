package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Etudiant {

	private String nom;
	private String prenom;
	private String email;
	private int idEtu;


	public Etudiant(String nom, String prenom) {
		this.idEtu=-1;
		this.nom=nom;
		this.prenom=prenom;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @return the idEtu
	 */
	public int getIdEtu() {
		return idEtu;
	}


	private Etudiant(String nom, String prenom, int idEtu) {
		this.prenom=prenom;
		this.nom=nom;
		this.idEtu=idEtu;
	}


	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`Etudiant` "
					+ "( `idEtu` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL ,"
					+ " `prenom` VARCHAR(40) NOT NULL , `email` VARCHAR(40) NOT NULL, "
					+ "PRIMARY KEY (`idEtu`)) ENGINE = InnoDB";
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
			String SQLPrep1 = "DROP TABLE ETUDIANT";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
			prep0.execute();
			prep1.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" DeleteTable "+e.getErrorCode()+e.toString());
		}
	}


	public static Etudiant findById(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM ETUDIANT WHERE IDETU ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		Etudiant res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			String resPrenom = rs.getString("prenom");
			res = new Etudiant(resNom, resPrenom, id);
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM Etudiant WHERE idEtu ='"+this.idEtu+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idEtu=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {

		if(this.idEtu==-1) {
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
			String SQLPrep0 = "INSERT INTO Etudiant (`NOM`, `PRENOM`) VALUES" + 
					"('"+this.nom+"', '"+this.prenom+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Etudiant WHERE NOM ='"+this.nom+"' AND PRENOM ='"+this.prenom+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idEtu");
			}
			this.idEtu=idres;
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
			String SQLPrep0 = "UPDATE Etudiant " + 
					"SET NOM = '"+this.nom+"', PRENOM = '"+this.prenom+"'" + 
					"WHERE IDEtu ='"+this.idEtu+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}

}
