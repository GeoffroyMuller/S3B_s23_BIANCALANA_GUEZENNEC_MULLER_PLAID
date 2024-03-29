package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * la Class ParticulariteEtudiant.
 */
public class GroupeCategorie {

	/**
	 * Creates la table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String nomBase = DBConnection.getNomDB();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`GroupeCategorie` ( `idGroupe` INT(11) NOT NULL , `idCategorie` INT(11) NOT NULL, PRIMARY KEY (`idGroupe`,`idCategorie`)) ENGINE = InnoDB;";
			
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
			String SQLPrep1 = "DROP TABLE IF EXISTS GroupeCategorie";
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
	 * List groupe pour categorieid.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Integer> listGroupePourCategorieid(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM GroupeCategorie WHERE IdGroupe ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = new ArrayList<Integer>();
		int i=0;
		while (rs.next()) {
			res.add(rs.getInt("idCategorie"));
			i++;
		}
		return res;
	}
	
	/**
	 * List groupe pour categorie.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Groupe> listGroupePourCategorie(int id) throws SQLException {
		ArrayList<Integer> list = GroupeCategorie.listGroupePourCategorieid(id);
		Connection connect=DBConnection.getConnection();
		ArrayList<Groupe> res = new ArrayList<Groupe>();
		for(int i = 0 ; i < list.size(); i++) {
			String SQLPrep = "SELECT * FROM Groupe WHERE IdGroupe ='"+list.get(i)+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resId = rs.getInt("idGroupe");
				res.add(new Groupe(resNom,resId));
			}
		}
		return res;
	}
	
	/**
	 * List categorie pour groupe id.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Integer> listCategoriePourGroupeId(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM GroupeCategorie WHERE IdCategorie ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = new ArrayList<Integer>();
		int i=0;
		while (rs.next()) {
			res.add(rs.getInt("idGroupe"));
			i++;
		}
		return res;
	}
	
	/**
	 * List categorie pour groupe.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Categorie> listCategoriePourGroupe(int id) throws SQLException {
		ArrayList<Integer> list = GroupeCategorie.listCategoriePourGroupeId(id);
		Connection connect=DBConnection.getConnection();
		ArrayList<Categorie> res = new ArrayList<Categorie>();
		for(int i = 0 ; i < list.size(); i++) {
			String SQLPrep = "SELECT * FROM Categorie WHERE IdCategorie ='"+list.get(i)+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resId = rs.getInt("idCategorie");
				res.add(new Categorie(resNom,resId));
			}
		}
		return res;
	}
	
	/**
	 * Ajouter groupe A une categorie.
	 *
	 * @param idGroupe le id groupe
	 * @param idCategorie le id categorie
	 */
	public static void ajouterGroupeAUneCategorie(int idGroupe, int idCategorie) {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "INSERT INTO GroupeCategorie (`IdGroupe`, `IdCategorie`) VALUES" + 
					"('"+idGroupe+"', '"+idCategorie+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"new "+e.getErrorCode()+e.toString());
		}
	}
	
	/**
	 * Delete.
	 *
	 * @param idGroupe le id groupe
	 * @param idCategorie le id categorie
	 */
	public static void delete(int idGroupe, int idCategorie){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM GroupeCategorie WHERE IdGroupe='"+idGroupe+"' AND idCategorie ='"+idCategorie+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}
}
