package modele.BDD;


import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class ParticulariteEtudiant.
 */
public class TypePlace {

	public static Color couleurPlace = new Color(0x126B91);
	public static Color couleurPlaceInutilisable = new Color(0xB11000);
	public static Color couleurAllee = new Color(0xA99F00);

	/** The id type place. */
	private int idTypePlace;
	
	/** The nom. */
	private String nom;
	
	/** The disponnible. */
	private int disponnible;
	
	
	/**
	 * Instantiates a new type place.
	 *
	 * @param nom the nom
	 * @param disponnible the disponnible
	 */
	public TypePlace(String nom, int disponnible) {
		this.disponnible=disponnible;
		this.idTypePlace=-1;
		this.nom=nom;
	}
	
	/**
	 * Instantiates a new type place.
	 *
	 * @param nom the nom
	 * @param idTypePlace the id type place
	 * @param disponnible the disponnible
	 */
	public TypePlace(String nom, int idTypePlace, int disponnible) {
		this.disponnible=disponnible;
		this.idTypePlace=idTypePlace;
		this.nom=nom;
	}

	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();

			String nomBase = DBConnection.getNomDB();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`TypePlace` "
					+ "( `idTypePlace` INT(1) NOT NULL AUTO_INCREMENT , `Disponnible` INT(1) NOT NULL,"
					+ " `nom` VARCHAR(40) NOT NULL, "
					+ "PRIMARY KEY (`idTypePlace`)) ENGINE = InnoDB";
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
			String SQLPrep1 = "DROP TABLE TypePlace";
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
	public static TypePlace findById(int id) {
		TypePlace res = null;
		try{
			Connection connect=DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM TypePlace WHERE IDTypePlace ='"+id+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resDisponnible = rs.getInt("Disponnible");

				res = new TypePlace(resNom,id,resDisponnible);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
	
	/**
	 * Find by nom.
	 *
	 * @param nom the nom
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static TypePlace findByNom(String nom) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM TypePlace WHERE nom ='"+nom+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat
		TypePlace res =null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resDisponnible = rs.getInt("Disponnible");
			int resId = rs.getInt("idTypePlace");
			
			res = new TypePlace(resNom,resId,resDisponnible);
		}
		return res;
	}
	
	/**
	 * List salle.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<TypePlace> listTypePlace() throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM TypePlace;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<TypePlace> res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resId = rs.getInt("idTypePlace");
			int resDisponnible = rs.getInt("Disponnible");
			res.add(new TypePlace(resNom,resId,resDisponnible));;
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM TypePlace WHERE idTypePlace ='"+this.idTypePlace+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idTypePlace=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {

		if(this.idTypePlace==-1) {
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
			String SQLPrep0 = "INSERT INTO TypePlace (`Disponnible`, `Nom`) VALUES" + 
					"('"+this.disponnible+"', '"+this.nom+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM TypePlace WHERE Disponnible ='"+this.disponnible+"' AND Nom ='"+this.nom+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idTypePlace");
			}
			this.idTypePlace=idres;
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
			String SQLPrep0 = "UPDATE TypePlace " + 
					"SET Disponnible = '"+this.disponnible+"', Nom = '"+this.nom+"'"+ 
					"WHERE IDTypePlace ='"+this.idTypePlace+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}

	public int getIdTypePlace() {
		return idTypePlace;
	}

	public void setIdTypePlace(int idTypePlace) {
		this.idTypePlace = idTypePlace;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getDisponnible() {
		return disponnible;
	}

	public void setDisponnible(int disponnible) {
		this.disponnible = disponnible;
	}
}
