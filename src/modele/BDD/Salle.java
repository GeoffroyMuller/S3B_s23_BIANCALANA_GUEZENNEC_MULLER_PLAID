package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class Salle.
 */
public class Salle {

	/** The nom. */
	private String nom;
	
	/** The id salle. */
	private int idSalle;

	private int nbCaseLargeur;
	private int nbCaseHauteur;

	/**
	 * Instantiates a new salle.
	 *
	 * @param nom the nom
	 */
	public Salle(String nom, int nbCaseHauteur, int nbCaseLargeur) {
		this.idSalle=-1;
		this.nom=nom;
		this.nbCaseHauteur=nbCaseHauteur;
		this.nbCaseLargeur=nbCaseLargeur;

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
	 * Gets the id salle.
	 *
	 * @return the idSalle
	 */
	public int getIdSalle() {
		return idSalle;
	}



	/**
	 * Instantiates a new salle.
	 *
	 * @param nom the nom
	 * @param idSalle the id salle
	 */
	private Salle(String nom, int idSalle, int nbCaseHauteur, int nbCaseLargeur) {
		this.nom=nom;
		this.idSalle=idSalle;
		this.nbCaseHauteur=nbCaseHauteur;
		this.nbCaseLargeur=nbCaseLargeur;
	}


	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`Salle` "
					+ "( `idSalle` INT(11) NOT NULL AUTO_INCREMENT , `nbCaseHauteur` INT(11) NOT NULL,"
					+ " `nbCaseLargeur` INT(11) NOT NULL, `nom` VARCHAR(40) NOT NULL, "
					+ "PRIMARY KEY (`idSalle`)) ENGINE = InnoDB";
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
			String SQLPrep1 = "DROP TABLE SALLE";
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
	 * @return the salle
	 * @throws SQLException the SQL exception
	 */
	public static Salle findById(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Salle WHERE IDSalle ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		Salle res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resNbCaseHauteur = rs.getInt("NbCaseHauteur");
			int resNbCaseLargeur = rs.getInt("NbCaseLargeur");
			
			res = new Salle(resNom,id,resNbCaseHauteur,resNbCaseLargeur);
		}
		return res;
	}
	
	/**
	 * List salle.
	 *
	 * @param id the id
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Salle> listSalle() throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Salle;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Salle> res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resId = rs.getInt("idSalle");
			int resNbCaseHauteur = rs.getInt("NbCaseHauteur");
			int resNbCaseLargeur = rs.getInt("NbCaseLargeur");
			res.add(new Salle(resNom,resId,resNbCaseHauteur,resNbCaseLargeur));
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM Salle WHERE idSalle ='"+this.idSalle+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idSalle=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {

		if(this.idSalle==-1) {
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
			String SQLPrep0 = "INSERT INTO Salle (`NOM`) VALUES" + 
					"('"+this.nom+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Salle WHERE NOM ='"+this.nom+"' AND NbCaseHauteur ='"+this.nbCaseHauteur+"' AND NbCaseLargeur ='"+this.nbCaseLargeur+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idSalle");
			}
			this.idSalle=idres;
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
			String SQLPrep0 = "UPDATE Salle " + 
					"SET NOM = '"+this.nom+"', NbCaseHauteur = '"+this.nbCaseHauteur+"', NbCaseLargeur = '"+this.nbCaseLargeur+"'"+ 
					"WHERE IDSalle ='"+this.idSalle+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}

}
