package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Particularite;


// TODO: Auto-generated Javadoc
/**
 * The Class ParticulariteEtudiant.
 */
public class ParticulariteEtudiant {

	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`particulariteEtudiant` "
					+ "( `idParticularite` INT(11) NOT NULL , `idEtu` INT(11) NOT NULL , "
					+ "PRIMARY KEY (`idParticularite`,`idEtu`), "
					+ "FOREIGN KEY (idParticularite) REFERENCES particularite (idParticularite), "
					+ "FOREIGN KEY (idEtu) REFERENCES Etudiant (idEtu)) ENGINE = InnoDB";
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
			String SQLPrep1 = "DROP TABLE particulariteEtudiant";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
			prep0.execute();
			prep1.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" DeleteTable "+e.getErrorCode()+e.toString());
		}
	}
	
	public static ArrayList<Integer> listParticularitePourEtudiantId(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM particulariteEtudiant WHERE IdEtudiant ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = null;
		int i=0;
		while (rs.next()) {
			res.add(rs.getInt("idParticularite"));
			i++;
		}
		return res;
	}
	
	public static ArrayList<Particularite> listParticularitePourEtudiant(int id) throws SQLException {
		ArrayList<Integer> list = ParticulariteEtudiant.listParticularitePourEtudiantId(id);
		Connection connect=DBConnection.getConnection();
		ArrayList<Particularite> res = null;
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
	
	
	
	public static ArrayList<Integer> listEtudiantPourParticulariteId(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM particulariteEtudiant WHERE IdParticularite ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = null;
		int i=0;
		while (rs.next()) {
			res.add(rs.getInt("idEtudiant"));
			i++;
		}
		return res;
	}
	
	public static ArrayList<Etudiant> listEtudiantPourParticularite(int id) throws SQLException {
		ArrayList<Integer> list = ParticulariteEtudiant.listEtudiantPourParticulariteId(id);
		Connection connect=DBConnection.getConnection();
		ArrayList<Etudiant> res = null;
		for(int i = 0 ; i < list.size(); i++) {
			String SQLPrep = "SELECT * FROM Etudiant WHERE IdEtudiant ='"+list.get(i)+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				String resPrenom = rs.getString("prenom");
				int resId = rs.getInt("idEtudiant");
				res.add(new Etudiant(resNom, resPrenom, resId));
			}
		}
		return res;
	}
}
