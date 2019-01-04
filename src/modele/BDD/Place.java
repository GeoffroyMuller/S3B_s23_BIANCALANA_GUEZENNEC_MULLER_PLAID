package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class Place.
 */
public class Place {

	/** The nom. */
	private String nom;
	
	/** The type salle. */
	private String typeSalle;
	
	/** The id salle. */
	private int idSalle;
	
	/** The id place. */
	private int idPlace;
	
	/** The j. */
	private int j;
	
	/** The i. */
	private int i;


	/**
	 * Instantiates a new place.
	 *
	 * @param nom the nom
	 * @param typeSalle the type salle
	 * @param i the i
	 * @param j the j
	 * @param idSalle the id salle
	 */
	public Place(String nom, String typeSalle, int i, int j, int idSalle) {
		this.idPlace=-1;
		this.nom=nom;
		this.typeSalle=typeSalle;
		this.idSalle=idSalle;
		this.j=j;
		this.i=i;
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
	 * Sets the nom.
	 *
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}



	/**
	 * Gets the type salle.
	 *
	 * @return the typeSalle
	 */
	public String getTypeSalle() {
		return typeSalle;
	}



	/**
	 * Sets the type salle.
	 *
	 * @param typeSalle the typeSalle to set
	 */
	public void setTypeSalle(String typeSalle) {
		this.typeSalle = typeSalle;
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
	 * Sets the id salle.
	 *
	 * @param idSalle the idSalle to set
	 */
	public void setIdSalle(int idSalle) {
		this.idSalle = idSalle;
	}



	/**
	 * Gets the id place.
	 *
	 * @return the idPlace
	 */
	public int getIdPlace() {
		return idPlace;
	}



	/**
	 * Sets the id place.
	 *
	 * @param idPlace the idPlace to set
	 */
	public void setIdPlace(int idPlace) {
		this.idPlace = idPlace;
	}



	/**
	 * Gets the j.
	 *
	 * @return the j
	 */
	public int getJ() {
		return j;
	}



	/**
	 * Sets the j.
	 *
	 * @param j the j to set
	 */
	public void setJ(int j) {
		this.j = j;
	}



	/**
	 * Gets the i.
	 *
	 * @return the i
	 */
	public int getI() {
		return i;
	}



	/**
	 * Sets the i.
	 *
	 * @param i the i to set
	 */
	public void setI(int i) {
		this.i = i;
	}



	/**
	 * Instantiates a new place.
	 *
	 * @param nom the nom
	 * @param typeSalle the type salle
	 * @param idSalle the id salle
	 * @param i the i
	 * @param j the j
	 * @param idPlace the id place
	 */
	private Place(String nom, String typeSalle, int idSalle,int i, int j, int idPlace) {
		this.typeSalle=typeSalle;
		this.nom=nom;
		this.j=j;
		this.i=i;
		this.idPlace=idPlace;
		this.idSalle=idSalle;
	}


	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`Place` "
					+ "( `idPlace` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL,"
					+ " `typePlace` VARCHAR(40) NOT NULL,`i` INT(11) NOT NULL,"
					+ "`j` INT(11) NOT NULL,"
					+ " `idSalle` INT(11) NOT NULL, "
					+ "PRIMARY KEY (`idPlace`), "
					+ "FOREIGN KEY (idSalle) REFERENCES Salle (idSalle)) ENGINE = InnoDB";
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


	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the place
	 * @throws SQLException the SQL exception
	 */
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
			int resI= rs.getInt("i");
			int resJ = rs.getInt("j");
			int resIdSalle = rs.getInt("idSalle");
			res = new Place(resNom, resTypeSalle, resIdSalle,resI, resJ, id);
		}
		return res;
	}
	
	/**
	 * List place.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Place> listPlace() throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Place;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Place> res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			String resTypeSalle = rs.getString("typeSalle");
			int resI= rs.getInt("i");
			int resJ = rs.getInt("j");
			int resIdSalle = rs.getInt("idSalle");
			int resId = rs.getInt("idPlace");
			res.add(new Place(resNom, resTypeSalle, resIdSalle,resI, resJ, resId));
		}
		return res;
	}
	
	/**
	 * Find by id salle.
	 *
	 * @param id the id
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Place> findByIdSalle(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Place WHERE IDSalle ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Place> res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			String resTypeSalle = rs.getString("typeSalle");
			int resIdSalle = rs.getInt("idSalle");
			int resI= rs.getInt("i");
			int resJ = rs.getInt("j");
			res.add(new Place(resNom, resTypeSalle, resIdSalle, resI, resJ, id));
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
	 * DeleteSalle.
	 *
	 * @param id the id
	 */
	public void deleteSalle(int id){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM Place WHERE idSalle='"+id+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete salle"+e.getErrorCode()+e.toString());
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
			String SQLPrep0 = "INSERT INTO Place (`NOM`, `TypeSalle`, `i`, `j`, `idSalle`) VALUES" + 
					"('"+this.nom+"', '"+this.typeSalle+"', '"+this.i+"', '"+this.j+"', '"+this.idSalle+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Place WHERE NOM ='"+this.nom+"' AND typeSalle ="
					+ "'"+this.typeSalle+"' AND i ='"+this.i+"' AND j ="
							+ "'"+this.j+"' AND idSalle ='"+this.idSalle+"';";
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
					"SET NOM = '"+this.nom+"', typeSalle = '"+this.typeSalle+"', i = '"+this.i+"', j = '"+this.j+"', idSalle = '"+this.idSalle+"'" + 
					"WHERE IDPlace ='"+this.idPlace+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}
	
	public int nbCaseHauteur(int idsalle) throws SQLException{
		ArrayList<Place> temp = Place.findByIdSalle(idsalle);
		int jmax = 0;
		for(int i=0; i<temp.size(); i++) {
			if(jmax<temp.get(i).getJ()) {
				jmax=temp.get(i).getJ();
			}
		}
		return jmax;
	}
	
	public int nbCaseLongueur(int idsalle) throws SQLException{
		ArrayList<Place> temp = Place.findByIdSalle(idsalle);
		int imax = 0;

		for(int i=0; i<temp.size(); i++) {
			if(imax<temp.get(i).getI()) {
				imax=temp.get(i).getI();
			}
		}
		return imax;
	}

	/**
	 * Tableau place.
	 *
	 * @param idsalle the idsalle
	 * @return the place[][]
	 * @throws SQLException the SQL exception
	 */
	public static Place[][] tableauPlace(int idsalle) throws SQLException{
		ArrayList<Place> temp = Place.findByIdSalle(idsalle);
		int imax = 0;
		int jmax = 0;
		for(int i=0; i<temp.size(); i++) {
			if(imax<temp.get(i).getI()) {
				imax=temp.get(i).getI();
			}
			if(jmax<temp.get(i).getJ()) {
				jmax=temp.get(i).getJ();
			}
		}
		Place res[][] = new Place[imax][jmax];
		for(int i=0; i<temp.size(); i++) {
			res[temp.get(i).getI()][temp.get(i).getJ()] = temp.get(i);
		}
		return res;
	}
}
