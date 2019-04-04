package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



/**
 * la Class Particularite.
 */
public class Particularite {

	/** le nom. */
	private String nom;
	
	/** prendre en compte placement. */
	private int prendreEnComptePlacement; //boolean 0 ou 1 dans Sql
	
	/** l' id particularite. */
	private int idParticularite;


	/**
	 * Instantiates une nouvelle particularite.
	 *
	 * @param nom le nom
	 * @param prendreEnComptePlacement le prendre en compte placement
	 */
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
	 * Sets le nom.
	 *
	 * @param nom le nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**
	 * Sets prendre en compte placement.
	 *
	 * @param prendreEnComptePlacement  prendreEnComptePlacement to set
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
	 * Sets l' id particularite.
	 *
	 * @param idParticularite l' idParticularite to set
	 */
	public void setIdParticularite(int idParticularite) {
		this.idParticularite = idParticularite;
	}


	/**
	 * Gets le nom.
	 *
	 * @return le nom
	 */
	public String getNom() {
		return nom;
	}


	/**
	 * Gets  prendre en compte placement.
	 *
	 * @return  prendreEnComptePlacement
	 */
	public int getPrendreEnComptePlacement() {
		return prendreEnComptePlacement;
	}


	/**
	 * Gets l' id particularite.
	 *
	 * @return l' idParticularite
	 */
	public int getIdParticularite() {
		return idParticularite;
	}


	/**
	 * Instantiates une nouvelle particularite.
	 *
	 * @param nom le nom
	 * @param prendreEnComptePlacement le prendre en compte placement
	 * @param idParticularite le id particularite
	 */
	public Particularite(String nom, int prendreEnComptePlacement, int idParticularite) {
		this.idParticularite=idParticularite;
		this.nom=nom;
		if((prendreEnComptePlacement==0) || (prendreEnComptePlacement==1)) {
			this.prendreEnComptePlacement=prendreEnComptePlacement;
		}
		else {
			this.prendreEnComptePlacement=0;
		}
	}


	/**
	 * Creates la table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String nomBase = DBConnection.getNomDB();

			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`particularite` ( `idParticularite` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL , `PrendreEnComptePlacement` BIT NOT NULL , PRIMARY KEY (`idParticularite`)) ENGINE = InnoDB;";
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
			String SQLPrep1 = "DROP TABLE IF EXISTS PARTICULARITE";
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
	 * @param id le id
	 * @return le particularite
	 * @throws SQLException le SQL exception
	 */
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

	public static Particularite findByNom(String nom){
		Connection connect= null;
		Particularite res = null;
		try {
			connect = DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM Particularite WHERE nom ='"+nom+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				int id = rs.getInt("idParticularite");
				int resPrendreEnComptePlacement = rs.getInt("PRENDREENCOMPTEPLACEMENT");
				res = new Particularite(resNom, resPrendreEnComptePlacement, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	
	/**
	 * renvoie la liste des particularites.
	 *
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Particularite> listParticularite() throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Particularite;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Particularite> res = new ArrayList<Particularite>();
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resPrendreEnComptePlacement = rs.getInt("PRENDREENCOMPTEPLACEMENT");
			int resId = rs.getInt("idparticularite");
			res.add(new Particularite(resNom, resPrendreEnComptePlacement, resId));
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
