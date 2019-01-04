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
public class GroupeCategorie {

	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `etuplacement`.`GroupeCategorie` "
					+ "( `idGroupe` INT(11) NOT NULL , `idCategorie` INT(11) NOT NULL , "
					+ "PRIMARY KEY (`idGroupe`,`idCategorie`), "
					+ "FOREIGN KEY (idGroupe) REFERENCES Groupe (idGroupe), "
					+ "FOREIGN KEY (idCategorie) REFERENCES Categorie (idCategorie)) ENGINE = InnoDB;";
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
			String SQLPrep1 = "DROP TABLE GroupeCategorie";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
			prep0.execute();
			prep1.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" DeleteTable "+e.getErrorCode()+e.toString());
		}
	}
	
	public static int[] listGroupePourCategorie(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM GroupeCategorie WHERE IdGroupe ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		int[] res = null;
		int i=0;
		while (rs.next()) {
			res[i]=rs.getInt("idCategorie");
			i++;
		}
		return res;
	}
	
	public static int[] listCategoriePourGroupe(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM GroupeCategorie WHERE IdCategorie ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		int[] res = null;
		int i=0;
		while (rs.next()) {
			res[i]=rs.getInt("idGroupe");
			i++;
		}
		return res;
	}
}
