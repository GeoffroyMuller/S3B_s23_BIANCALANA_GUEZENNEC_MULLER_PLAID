package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;


// TODO: Auto-generated Javadoc
/**
 * The Class Place.
 */
public class Place extends Observable {

	/** The nom. */
	private String nom;
	
	/** The type Place. */
	private int idTypePlace;
	
	/** The id salle. */
	private int idSalle;
	
	/** The id place. */
	private int idPlace;
	
	/** The j. */
	private int j;
	
	/** The i. */
	private int i;

	private int disponnible;

	private String nomColonne;

	private String nomRangee;


	/**
	 * Instantiates a new place.
	 *
	 * @param nom the nom
	 * @param i the i
	 * @param j the j
	 * @param idSalle the id salle
	 */
/*	public Place(String nom, int idTypePlace, int i, int j, int disponnible, int idSalle) {
		this.idPlace=-1;
		this.nom=nom;
		this.idTypePlace=idTypePlace;
		this.idSalle=idSalle;
		if(disponnible==0||disponnible==1) {
			this.disponnible=disponnible;
		}
		else {
			this.disponnible=0;
		}
		this.j=j;
		this.i=i;
	}*/

	public Place(String nom, int idTypePlace, int i, int j, int disponnible, int idSalle, String nomColonne, String nomRangee) {
		this.idPlace=-1;
		this.nom=nom;
		this.idTypePlace=idTypePlace;
		this.idSalle=idSalle;
		if(disponnible==0||disponnible==1) {
			this.disponnible=disponnible;
		}
		else {
			this.disponnible=0;
		}
		this.j=j;
		this.i=i;
		this.nomColonne = nomColonne;
		this.nomRangee = nomRangee;
	}




	public Place(String nom,int i, int j, int idSalle, String nomColonne,String nomRangee) {
		this.idPlace=-1;
		this.nom=nom;
		this.idTypePlace=1;
		this.idSalle=idSalle;
		this.disponnible=1;
		this.j=j;
		this.i=i;
		this.nomColonne=nomColonne;
		this.nomRangee=nomRangee;
	}



	

	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return this.nom;
	}



	/**
	 * Sets the nom.
	 *
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {

		this.nom = nom;
		this.save();
	}



	/**
	 * Gets the type salle.
	 *
	 * @return the typeSalle
	 */
	public int getIdTypePlace() {
		return idTypePlace;
	}



	/**
	 * Sets the type salle.
	 *
	 */
	public void setTypePlace(int idTypePlace) {
		this.idTypePlace = idTypePlace;
		save();
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
	 * @param idSalle the id salle
	 * @param i the i
	 * @param j the j
	 * @param idPlace the id place
	 */
	private Place(String nom, int idTypePlace, int idSalle,int i, int j,int disponnible, int idPlace,String nomColonne, String nomRangee) {
		this.idTypePlace=idTypePlace;
		this.nom=nom;
		this.j=j;
		this.i=i;
		if(disponnible==0||disponnible==1) {
			this.disponnible=disponnible;
		}
		else {
			this.disponnible=0;
		}
		this.idPlace=idPlace;
		this.idSalle=idSalle;
		this.nomRangee = nomRangee;
		this.nomColonne = nomColonne;
	}


	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {


			Connection connect=DBConnection.getConnection();

			String nomBase = DBConnection.getNomDB();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`Place` ( `idPlace` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL,`IdTypePlace` INT(1) NOT NULL,`i` INT(11) NOT NULL,`Disponnible` INT(1) NOT NULL,`j` INT(11) NOT NULL, `idSalle` INT(11) NOT NULL,`NomColonne` VARCHAR(100) NOT NULL,`NomRangee` VARCHAR(100) NOT NULL, PRIMARY KEY (`idPlace`)) ENGINE = InnoDB;";

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
			String SQLPrep1 = "DROP TABLE IF EXISTS Place";
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
			int resIdTypePlace = rs.getInt("IdTypePlace");
			int resI= rs.getInt("i");
			int resJ = rs.getInt("j");
			int resIdSalle = rs.getInt("idSalle");
			int resDisponnible = rs.getInt("disponnible");
			String resColonne = rs.getString("NomColonne");
			String resRangee = rs.getString("NomRangee");
			res = new Place(resNom, resIdTypePlace, resIdSalle,resI, resJ, resDisponnible, id,resColonne,resRangee);
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

		ArrayList<Place> res = new ArrayList<Place>();
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resIdTypePlace = rs.getInt("idTypePlace");
			int resI= rs.getInt("i");
			int resJ = rs.getInt("j");
			int resIdSalle = rs.getInt("idSalle");
			int resId = rs.getInt("idPlace");
			int resDisponnible = rs.getInt("disponnible");
			String resColonne = rs.getString("NomColonne");
			String resRangee = rs.getString("NomRangee");
			res.add(new Place(resNom, resIdTypePlace, resIdSalle,resI, resJ, resDisponnible, resId,resColonne,resRangee));
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
	public static ArrayList<Place> findByIdSalle(int id){
		ArrayList<Place> res = new ArrayList<Place>();
		try{
			Connection connect=DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM Place WHERE IDSalle ='"+id+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {

				int resId = rs.getInt("idPlace");
				String resNom = rs.getString("nom");
				int resIdTypePlace = rs.getInt("idTypePlace");
				int resIdSalle = rs.getInt("idSalle");
				int resI= rs.getInt("i");
				int resJ = rs.getInt("j");
				int resDisponnible = rs.getInt("disponnible");
				String resColonne = rs.getString("NomColonne");
				String resRangee = rs.getString("NomRangee");
				res.add(new Place(resNom, resIdTypePlace, resIdSalle, resI, resJ, resDisponnible, resId,resColonne,resRangee));
			}
		}catch(SQLException e){
			e.printStackTrace();
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
		setChanged();
		notifyObservers();
	}

	public int getDisponibleIntVersion(){
		return this.disponnible;
	}

	public boolean getDisponnible() {
		boolean res = false;
		TypePlace typeDeLaPlace = TypePlace.findById(this.idTypePlace);
		if(typeDeLaPlace.getDisponnible() == 1){
			res= true;
		}

		return res;
	}

public boolean verifiersiPlaceCassee(){
		TypePlace tp = TypePlace.findById(this.getIdTypePlace());
		boolean res=true;
		if(tp.getNom().equals("placeInutillisable")){
			res = true;
		}else{
			res=false;
		}
		return res;
}

	public void setDisponnible(int disponnible) {
		this.disponnible = disponnible;
	}

	/**
	 * Save new.
	 */
	private void saveNew() {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "INSERT INTO Place (`NOM`, `IdTypePlace`, `i`, `j`, `disponnible`, `idSalle`, `NomColonne`, `NomRangee`) VALUES" +
					"('"+this.nom+"', '"+this.idTypePlace+"', '"+this.i+"', '"+this.j+"', '"+this.disponnible+"', '"+this.idSalle+"', '"+this.nomColonne+"','"+this.nomRangee+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Place WHERE NOM ='"+this.nom+"' AND IdtypePlace ="
					+ "'"+this.idTypePlace+"' AND i ='"+this.i+"' AND Disponnible ='"+this.disponnible+"' AND j ="
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
					"SET NOM = '"+this.nom+"', IdTypePlace = '"+this.idTypePlace+"', i = '"+this.i+"', j = '"+this.j+"', Disponnible = '"+this.disponnible+"', idSalle = '"+this.idSalle+"', NomColonne = '"+this.nomColonne+"', NomRangee = '"+this.nomRangee+"'" +
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
		Salle salle = Salle.findById(idsalle);
		int imax = salle.getNbCaseHauteur();
		int jmax = salle.getNbCaseLargeur();
		Place res[][] = new Place[imax][jmax];


		int coordI,coordJ;
		for(int i=0; i<temp.size(); i++) {
			coordI = temp.get(i).getI();
			coordJ = temp.get(i).getJ();
			res[temp.get(i).getI()][temp.get(i).getJ()] = temp.get(i);
		}
		return res;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Place place = (Place) o;

		if (i != place.i) return false;
		return j == place.j;
	}

	@Override
	public int hashCode() {
		int result = i;
		result = 31 * result + j;
		return result;
	}

	public String getNomColonne() {
		return nomColonne;
	}

	public void setNomColonne(String nomColonne) {
		this.nomColonne = nomColonne;
		save();
	}

	public String getNomRangee() {
		return nomRangee;
	}

	public void setNomRangee(String nomRangee) {
		this.nomRangee = nomRangee;
		save();
	}
}
