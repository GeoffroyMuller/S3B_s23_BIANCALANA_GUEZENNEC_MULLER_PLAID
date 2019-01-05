package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class ParticulariteEtudiant.
 */
public class EtudiantGroupe {

	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`EtudiantGroupe` "
					+ "( `idEtu` INT(11) NOT NULL , `idGroupe` INT(11) NOT NULL , "
					+ "PRIMARY KEY (`idEtu`,`idGroupe`)"
					+ ") ENGINE = InnoDB;";
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
			String SQLPrep1 = "DROP TABLE EtudiantGroupe";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
			prep0.execute();
			prep1.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" DeleteTable "+e.getErrorCode()+e.toString());
		}
	}
	
	public static ArrayList<Integer> listEtudiantPourGroupeId(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM EtudiantGroupe WHERE IdEtu ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = null;
		int i=0;
		while (rs.next()) {
			res.add(rs.getInt("idGroupe"));
			i++;
		}
		return res;
	}
	
	public static ArrayList<Etudiant> listEtudiantPourGroupe(int id) throws SQLException {
		ArrayList<Integer> list = EtudiantGroupe.listEtudiantPourGroupeId(id);
		Connection connect=DBConnection.getConnection();
		ArrayList<Etudiant> res = null;
		for(int i = 0 ; i < list.size(); i++) {
			String SQLPrep = "SELECT * FROM Etudiant WHERE IdEtu ='"+list.get(i)+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				String resPrenom = rs.getString("prenom");
				int resId = rs.getInt("idEtu");
				res.add(new Etudiant(resNom, resPrenom, resId));
			}
		}
		return res;
	}
	
	public static ArrayList<Integer> listGroupePourEtudiantId(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM EtudiantGroupe WHERE IdGroupe ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = null;
		int i=0;
		while (rs.next()) {
			res.add(rs.getInt("idEtu"));
			i++;
		}
		return res;
	}
	
	public static ArrayList<Groupe> listGroupePourEtudiant(int id) throws SQLException {
		ArrayList<Integer> list = EtudiantGroupe.listGroupePourEtudiantId(id);
		Connection connect=DBConnection.getConnection();
		ArrayList<Groupe> res = null;
		for(int i = 0 ; i < list.size(); i++) {
			String SQLPrep = "SELECT * FROM Groupe WHERE IdGroupe ='"+list.get(i)+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resId = rs.getInt("idGroupe");
				res.add(new Groupe(resNom, resId));
			}
		}
		return res;
	}
	

	public static void ajouterEtudiantAUnGroupe(int idEtudiant, int idGroupe) {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "INSERT INTO EtudiantGroupe (`IdEtu`, `IdGroupe`) VALUES" + 
					"('"+idEtudiant+"', '"+idGroupe+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"new "+e.getErrorCode()+e.toString());
		}
	}
	
	public static void delete(int idEtu, int idGroupe){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM EtudiantGroupe WHERE IdEtu='"+idEtu+"' AND idGroupe ='"+idGroupe+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}
}
