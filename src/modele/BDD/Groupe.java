package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.mysql.fabric.xmlrpc.base.Array;


// TODO: Auto-generated Javadoc
/**
 * The Class Groupe.
 */
public class Groupe {

	/** The nom. */
	private String nom;

	/** The id groupe. */
	private int idGroupe;

	/**
	 * Instantiates a new groupe.
	 *
	 * @param nom the nom
	 */
	public Groupe(String nom) {
		this.idGroupe=-1;
		this.nom=nom;
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
	 * Gets the id groupe.
	 *
	 * @return the idGroupe
	 */
	public int getIdGroupe() {
		return idGroupe;
	}



	/**
	 * Instantiates a new groupe.
	 *
	 * @param nom the nom
	 * @param idGroupe the id groupe
	 */
	public Groupe(String nom, int idGroupe) {
		this.nom=nom;
		this.idGroupe=idGroupe;
	}


	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();

			String nomBase = DBConnection.getNomDB();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`Groupe` ( `idGroupe` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL, PRIMARY KEY (`idGroupe`)) ENGINE = InnoDB;";
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
			String SQLPrep1 = "DROP TABLE IF EXISTS GROUPE";
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
	 * @return the groupe
	 * @throws SQLException the SQL exception
	 */
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
	 * List groupe.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Groupe> listGroupe() throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM GROUPE;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Groupe> res = new ArrayList<Groupe>();
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resId = rs.getInt("idGroupe");
			res.add(new Groupe(resNom, resId));
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
		//save ou update du groupe
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
	
	public void ajouterEtudiant(ArrayList<Etudiant> listEtudiant) {
		for (int i = 0; i < listEtudiant.size(); i++) {
			if(listEtudiant.get(i).getIdEtu()!=-1) {
				EtudiantGroupe.ajouterEtudiantAUnGroupe(listEtudiant.get(i).getIdEtu(), this.idGroupe);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Groupe groupe = (Groupe) o;
		return Objects.equals(nom, groupe.nom);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom);
	}
}
