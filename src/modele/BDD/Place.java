package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Place {

	private String nom;
	private String typeSalle;
	private int idSalle;
	private int idPlace;


	public Place(String nom, String typeSalle, int idSalle) {
		this.idPlace=-1;
		this.nom=nom;
		this.typeSalle=typeSalle;
		this.idSalle=idSalle;
	}

	

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * @return the typeSalle
	 */
	public String getTypeSalle() {
		return typeSalle;
	}



	/**
	 * @return the idSalle
	 */
	public int getIdSalle() {
		return idSalle;
	}



	/**
	 * @return the idPlace
	 */
	public int getIdPlace() {
		return idPlace;
	}



	private Place(String nom, String typeSalle, int idSalle, int idPlace) {
		this.typeSalle=typeSalle;
		this.nom=nom;
		this.idPlace=idPlace;
		this.idSalle=idSalle;
	}


	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`Place` "
					+ "( `idPlace` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL,"
					+ " `typePlace` VARCHAR(40) NOT NULL, `idSalle` INT(11) NOT NULL, "
					+ "PRIMARY KEY (`idPlace`), "
					+ "FOREIGN KEY (idSalle) REFERENCES Salle (idSalle)) ENGINE = InnoDB";
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
			String SQLPrep1 = "DROP TABLE Place";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
			prep0.execute();
			prep1.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" DeleteTable "+e.getErrorCode()+e.toString());
		}
	}


	public static Place findById(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Place WHERE IDPlace ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		Place res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			String resTypeSalle = rs.getString("typeSalle");
			int resIdSalle = rs.getInt("idSalle");
			res = new Place(resNom, resTypeSalle, resIdSalle, id);
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM Place WHERE idPlace='"+this.idPlace+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idPlace=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {

		if(this.idPlace==-1) {
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
			String SQLPrep0 = "INSERT INTO Place (`NOM`, `TypeSalle`, `idSalle`) VALUES" + 
					"('"+this.nom+"', '"+this.typeSalle+"', '"+this.idSalle+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Place WHERE NOM ='"+this.nom+"' AND typeSalle ='"+this.typeSalle+"' AND idSalle ='"+this.idSalle+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idPlace");
			}
			this.idPlace=idres;
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
			String SQLPrep0 = "UPDATE Place " + 
					"SET NOM = '"+this.nom+"', typeSalle = '"+this.typeSalle+"', idSalle = '"+this.idSalle+"'" + 
					"WHERE IDPlace ='"+this.idPlace+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}

}
