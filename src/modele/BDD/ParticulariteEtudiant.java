package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



/**
 * La Class ParticulariteEtudiant.
 */
public class ParticulariteEtudiant {

	/**
	 * Creates la table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String nomBase = DBConnection.getNomDB();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`particulariteEtudiant` ( `idParticularite` INT(11) NOT NULL , `idEtu` INT(11) NOT NULL , PRIMARY KEY (`idParticularite`,`idEtu`)) ENGINE = InnoDB;";
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
			String SQLPrep1 = "DROP TABLE IF EXISTS particulariteEtudiant";
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
	 * donne la List particularite pour un etudiant donner.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Integer> listParticularitePourEtudiantId(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM particulariteEtudiant WHERE IdEtu ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = new ArrayList<Integer>();
		int i=0;
		while (rs.next()) {
			res.add(rs.getInt("idParticularite"));
			i++;
		}
		return res;
	}
	
	/**
	 * donne la List particularite pour un etudiant donner.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Particularite> listParticularitePourEtudiant(int id) throws SQLException {
		ArrayList<Integer> list = ParticulariteEtudiant.listParticularitePourEtudiantId(id);
		Connection connect=DBConnection.getConnection();
		ArrayList<Particularite> res = new ArrayList<Particularite>();

		for(int i = 0 ; i < list.size(); i++) {
			String SQLPrep = "SELECT * FROM Particularite WHERE IdParticularite ='"+list.get(i)+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resPrendreEnComptePlacement = rs.getInt("PRENDREENCOMPTEPLACEMENT");
				int resId = rs.getInt("idParticularite");
				res.add(new Particularite(resNom, resPrendreEnComptePlacement,resId));
			}
		}
		return res;
	}
	
	
	
	/**
	 * donne la liste des etudiant pour une particularites donner.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Integer> listEtudiantPourParticulariteId(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM particulariteEtudiant WHERE IdParticularite ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = new ArrayList<Integer>();
		int i=0;
		while (rs.next()) {
			res.add(rs.getInt("idEtu"));
			i++;
		}
		return res;
	}
	
	/**
	 * donne la liste des etudiant pour une particularites donner.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Etudiant> listEtudiantPourParticularite(int id) throws SQLException {
		ArrayList<Integer> list = ParticulariteEtudiant.listEtudiantPourParticulariteId(id);
		Connection connect=DBConnection.getConnection();
		ArrayList<Etudiant> res = new ArrayList<Etudiant>();
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
	
	/**
	 * Ajouter particularite A un etudiant.
	 *
	 * @param idParticularite le id particularite
	 * @param idEtudiant le id etudiant
	 */
	public static void ajouterParticulariteAUnEtudiant(int idParticularite, int idEtudiant) {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "INSERT INTO ParticulariteEtudiant (`IdParticularite`, `IdEtu`) VALUES" +
					"('"+idParticularite+"', '"+idEtudiant+"')";
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
	 * @param idParticularite le id particularite
	 * @param idEtudiant le id etudiant
	 */
	public static void delete(int idParticularite, int idEtudiant){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM ParticulariteEtudiant WHERE idParticularite='"+idParticularite+"' AND idEtu ='"+idEtudiant+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}
}
