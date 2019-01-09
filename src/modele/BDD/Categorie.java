package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class Categorie.
 */
public class Categorie {


	/** The nom. */
	private String nom;

	/** The id categorie. */
	private int idCategorie;

	/**
	 * Instantiates a new categorie.
	 *
	 * @param nom the nom
	 */
	public Categorie(String nom) {
		this.idCategorie=-1;
		this.nom=nom;
	}



	/**
	 * Méthode permettant de récupérer les groupes appartenant à la catégorie courante
	 * @return
	 */
	public ArrayList<Groupe> getListGroupe(){
		ArrayList<Groupe> res = new ArrayList<Groupe>();


		Connection connect= null;
		try {
			connect = DBConnection.getConnection();
			PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM groupecategorie WHERE idCategorie ="+this.idCategorie);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();


			PreparedStatement prep2 = connect.prepareStatement("SELECT * FROM groupe WHERE idGroupe = ?");

			while (rs.next()){
				int groupeID = rs.getInt("idGroupe");
				prep2.setInt(1,groupeID);

				prep2.execute();
				ResultSet rs2 = prep2.getResultSet();

				while(rs2.next()){
					Groupe groupe = new Groupe(rs2.getString("nom"),groupeID);
					res.add(groupe);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
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
	 * Sets the id categorie.
	 *
	 * @param idCategorie the idCategorie to set
	 */
	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
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
	 * Gets the id categorie.
	 *
	 * @return the idGroupe
	 */
	public int getIdCategorie() {
		return idCategorie;
	}



	/**
	 * Instantiates a new categorie.
	 *
	 * @param nom the nom
	 * @param idCategorie the id categorie
	 */
	public Categorie(String nom, int idCategorie) {
		this.nom=nom;
		this.idCategorie=idCategorie;
	}


	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String nomBase = DBConnection.getNomDB();

			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS`"+nomBase+"`.`Categorie` ( `idCategorie` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL, PRIMARY KEY (`idCategorie`)) ENGINE = InnoDB;";
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
			String SQLPrep1 = "DROP TABLE IF EXISTS CATEGORIE";
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
	 * @return the categorie
	 * @throws SQLException the SQL exception
	 */
	public static Categorie findById(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM CATEGORIE WHERE IDCATEGORIE ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		Categorie res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			res = new Categorie(resNom,id);
		}
		return res;
	}

	/**
	 * Get List categorie.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Categorie> getlistCategorie() throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM CATEGORIE;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Categorie> res = new ArrayList<Categorie>();
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resId = rs.getInt("idCategorie");
			res.add(new Categorie(resNom,resId));
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM CATEGORIE WHERE idCategorie ='"+this.idCategorie+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idCategorie=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {
		//save ou update de la categorie
		if(this.idCategorie==-1) {
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
			String SQLPrep0 = "INSERT INTO CATEGORIE (`NOM`) VALUES" + 
					"('"+this.nom+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM CATEGORIE WHERE NOM ='"+this.nom+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idCategorie");
			}
			this.idCategorie=idres;
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
			String SQLPrep0 = "UPDATE CATEGORIE " + 
					"SET NOM = '"+this.nom+"'"+ 
					"WHERE IDCategorie ='"+this.idCategorie+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}
	

	public void ajouterGroupe(Groupe g) {
		GroupeCategorie.ajouterGroupeAUneCategorie(g.getIdGroupe(), this.getIdCategorie());
	}
	


	public void ajouterGroupe(ArrayList<Groupe> listGroupe) {
		for (int i = 0; i < listGroupe.size(); i++) {
			if(listGroupe.get(i).getIdGroupe()!=-1) {
				GroupeCategorie.ajouterGroupeAUneCategorie(listGroupe.get(i).getIdGroupe(), this.idCategorie);
			}
		}

	}
	
	public String toString() {
		return this.nom;
	}

}
