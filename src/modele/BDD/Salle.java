package modele.BDD;


import modele.Iterateur;

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

	protected int nbCaseLargeur;
	protected int nbCaseHauteur;

	public Place[][] getPlaces() {
		return places;
	}

	public void setPlaces(Place[][] places) {
		this.places = places;
	}

	protected Place[][] places;

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

		/*try {
			this.places = Place.tableauPlace(idSalle);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	public void getTableauPlaces(int idSalle){
		try {
			this.places = new Place[this.nbCaseHauteur][this.nbCaseLargeur];
			this.places = Place.tableauPlace(idSalle);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	public static ArrayList<Salle> findByNom(String nom) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Salle WHERE nom ='"+nom+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Salle> res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resNbCaseHauteur = rs.getInt("NbCaseHauteur");
			int resNbCaseLargeur = rs.getInt("NbCaseLargeur");
			int resId = rs.getInt("IdSalle");
			
			res.add(new Salle(resNom,resId,resNbCaseHauteur,resNbCaseLargeur));
		}
		return res;
	}
	
	/**
	 * List salle.
	 *
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
			String SQLPrep0 = "INSERT INTO Salle (`NOM`, `NbCaseHauteur`, `NbCaseLargeur`) VALUES" + 
					"('"+this.nom+"', '"+this.nbCaseHauteur+"', '"+this.nbCaseLargeur+"')";
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

	public Iterateur getIterateur(int i, int j, Salle salle){
		return new SalleIterateur(i,j, salle);
	}

	public int getNbCaseLargeur() {
		return nbCaseLargeur;
	}

	public void setNbCaseLargeur(int nbCaseLargeur) {
		this.nbCaseLargeur = nbCaseLargeur;
	}

	public int getNbCaseHauteur() {
		return nbCaseHauteur;
	}

	public void setNbCaseHauteur(int nbCaseHauteur) {
		this.nbCaseHauteur = nbCaseHauteur;
	}


	public class SalleIterateur implements Iterateur {

		protected int i,j;

		private Salle salle;

		/**
		 * Coordonnées lors de la création de l'itérateur afin de pouvoir y retourner si besoin
		 */
		protected int firstI, firstJ;

		public SalleIterateur(int i, int j, Salle salle){
			this.firstI = this.i = i;
			this.firstJ = this.j = j;
			this.salle = salle;
			//this.salle.getTableauPlaces(this.salle.idSalle);
		}


		@Override
		public boolean hasNext() {
			if(i==nbCaseHauteur-1 && j==nbCaseLargeur-1){
				return false;
			}
			return true;
		}

		@Override
		public boolean hasPrevious() {
			if(i==0 && j==0){
				return false;
			}
			return true;
		}

		@Override
		public boolean hasNext(int pas) {
			if(j+pas>nbCaseLargeur-1){
				if(i==nbCaseHauteur-1){
					return false;
				}
			}
			return true;
		}

		@Override
		public boolean hasPrevious(int pas) {
			if(j-pas<0){
				if(i==0){
					return false;
				}
			}
			return true;
		}

		@Override
		public boolean hasUp() {
			if(!(this.i == 0)){
				return true;
			}
			return false;
		}

		@Override
		public boolean hasDown() {
			if(!(this.i == nbCaseHauteur-1)){
				return true;
			}
			return false;
		}

		@Override
		public Object next() {

			if(j==nbCaseLargeur-1){
				this.i++;
				this.j=0;
			}else{
				this.j++;
			}

			return places[this.i][this.j];
		}

		@Override
		public Object previous() {
			if(j==0){
				this.i--;
				this.j=nbCaseLargeur-1;
			}else{
				this.j--;
			}


			return places[this.i][this.j];
		}

		@Override
		public Object next(int pas) {
			Place place=null;
			if(j==nbCaseLargeur-1 || j+pas>nbCaseLargeur-1){
				this.i++;
				this.j=0;
				place = places[this.i][this.j];
			}else{
				this.j=this.j+pas;
				place = places[this.i][this.j];

				//On vérifie que la place n'est pas une allee ou une place cassé
				//Si la place n'est pas disponible alors on avance d'une case
				if(!(places[this.i][this.j].getDisponnible()==1)){
					place = (Place)this.next();
				}
			}


			return place;
		}

		@Override
		public Object previous(int pas) {
			Place place=null;
			if(j==0 || j-pas<0){
				this.i--;
				this.j=nbCaseLargeur-1;
				place = places[this.i][this.j];
			}else{
				this.j=this.j-pas;
				place = places[this.i][this.j];

				//On vérifie que la place n'est pas une allee ou une place cassé
				//Si la place n'est pas disponible alors on recule d'une case
				if(!(places[this.i][this.j].getDisponnible()==1)){
					place = (Place)this.previous();
				}
			}
			return place;
		}

		@Override
		public Object up() {
			this.i--;
			return places[i][j];
		}

		@Override
		public Object down() {
			this.i++;
			return places[i][j];
		}

		@Override
		public Object actual() {

			return salle.getPlaces()[i][j];
		}

		@Override
		public void reset() {
			this.i = this.firstI;
			this.j = this.firstJ;
		}

		@Override
		public int getCoordI() {
			return this.i;
		}

		@Override
		public int getCoordY() {
			return this.j;
		}
	}

}
