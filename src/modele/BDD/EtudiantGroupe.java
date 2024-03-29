package modele.BDD;


//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



/**
 * le Class ParticulariteEtudiant.
 */
public class EtudiantGroupe {

	/**
	 * Creates la table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();

			String nomBase = DBConnection.getNomDB();




			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`EtudiantGroupe` ( `idEtu` INT(11) NOT NULL , `idGroupe` INT(11) NOT NULL , PRIMARY KEY (`idEtu`,`idGroupe`)) ENGINE = InnoDB;";
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
			String SQLPrep1 = "DROP TABLE IF EXISTS EtudiantGroupe";
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
	 * List groupe pour un etudiant donnee.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Integer> listEtudiantPourGroupeId(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM EtudiantGroupe WHERE IdEtu ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Integer> res = new ArrayList<Integer>();
		while (rs.next()) {
			res.add(rs.getInt("idGroupe"));
		}
		return res;
	}


	/**
	 * M�thode permettant de recuperer tout les �tudiant d'un groupe donn�e.
	 *
	 * @param id 		id du groupe
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Etudiant> recupererEtudiantDansGroupe(int id) throws SQLException{
		ArrayList<Etudiant> res = new ArrayList<Etudiant>();

		Connection connect=DBConnection.getConnection();

		PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM EtudiantGroupe WHERE idGroupe ="+id);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();


		PreparedStatement prep2 = connect.prepareStatement("SELECT * FROM Etudiant WHERE idEtu = ?");

		while (rs.next()){
			int etudiantID = rs.getInt("idEtu");
			prep2.setInt(1,etudiantID);

			prep2.execute();
			ResultSet rs2 = prep2.getResultSet();

			while(rs2.next()){
				Etudiant etudiant = new Etudiant(rs2.getString("nom"),rs2.getString("prenom"),rs2.getInt("idEtu"));
				res.add(etudiant);
			}

		}

		return res;
	}


	/**
	 * M�thode permettant de recuperer tout les �tudiant d'un groupe donn�e.
	 *
	 * @param id le id
	 * @return le array list
	 * @throws SQLException le SQL exception
	 */
	public static ArrayList<Etudiant> listEtudiantPourGroupe(int id) throws SQLException {
		ArrayList<Integer> list = EtudiantGroupe.listEtudiantPourGroupeId(id);
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
	 * id groupe donner donne tout les etudiant
	 *
	 * @param id le id
	 * @return le array list
	 */
	public static ArrayList<Integer> listGroupePourEtudiantId(int id) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		try {
			Connection connect = DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM EtudiantGroupe WHERE IdGroupe ='" + id + "';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			int i = 0;
			while (rs.next()) {
				res.add(rs.getInt("idEtu"));
				i++;
			}
		}catch(SQLException e){
			/*
			TO DO
			 */
		}
		return res;
	}
	
	/**
	 * donner id groupe donne les etudiants
	 *
	 * @param id le id
	 * @return le array list
	 */
	public static ArrayList<Groupe> listGroupePourEtudiant(int id) {
		ArrayList<Groupe> res = new ArrayList<Groupe>();
		try {
			ArrayList<Integer> list = EtudiantGroupe.listGroupePourEtudiantId(id);
			Connection connect = DBConnection.getConnection();
			for (int i = 0; i < list.size(); i++) {
				String SQLPrep = "SELECT * FROM Groupe WHERE IdGroupe ='" + list.get(i) + "';";
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
		}catch(SQLException e){
			/*
			TO DO
			 */
		}
		return res;
	}
	

	/**
	 * Ajouter etudiant A un groupe.
	 *
	 * @param idEtudiant le id etudiant
	 * @param idGroupe le id groupe
	 */
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
	
	/**
	 * Delete.
	 *
	 * @param idEtu le id etu
	 * @param idGroupe le id groupe
	 */
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
