package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class Etudiant.
 */
public class Etudiant implements Comparable<Etudiant> {

	/** The nom. */
	private String nom;
	
	/** The prenom. */
	private String prenom;
	
	/** The email. */
	private String email;
	
	/** The id etu. */
	private int idEtu;


	/**
	 * Instantiates a new etudiant.
	 *
	 * @param nom the nom
	 * @param prenom the prenom
	 */
	public Etudiant(String nom, String prenom) {
		this.idEtu=-1;
		this.nom=nom;
		this.prenom=prenom;
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
	 * Gets the prenom.
	 *
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Gets the id etu.
	 *
	 * @return the idEtu
	 */
	public int getIdEtu() {
		return idEtu;
	}

	/**
	 * Méthode permettant de récupérer le groupe d'un étudiant
	 * @return
	 */
	public String getGroupe(){
		String res="NON DEFINI";
		try {
			Connection connect=DBConnection.getConnection();
			PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM ETUDIANTGROUPE WHERE idEtu = ? ");

			prep1.setInt(1,this.idEtu);
			prep1.execute();

			ResultSet rs = prep1.getResultSet();
			int idGroupe = rs.getInt("idGroupe");

			prep1 = connect.prepareStatement("SELECT * FROM GROUPE WHERE idGroupe=?");
			prep1.setInt(1,idGroupe);

			rs = prep1.getResultSet();
			res = rs.getString("nom");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * Instantiates a new etudiant.
	 *
	 * @param nom the nom
	 * @param prenom the prenom
	 * @param idEtu the id etu
	 */
	public Etudiant(String nom, String prenom, int idEtu) {
		this.prenom=prenom;
		this.nom=nom;
		this.idEtu=idEtu;
	}


	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();
			String nomBase = DBConnection.getNomDB();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`Etudiant` ( `idEtu` INT(11) NOT NULL AUTO_INCREMENT , `nom` VARCHAR(40) NOT NULL , `prenom` VARCHAR(40) NOT NULL , `email` VARCHAR(40), PRIMARY KEY (`idEtu`)) ENGINE = InnoDB;";
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
			String SQLPrep1 = "DROP TABLE ETUDIANT";
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
	 * @return the etudiant
	 * @throws SQLException the SQL exception
	 */
	public static Etudiant findById(int id) throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM ETUDIANT WHERE IDETU ='"+id+"';";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		Etudiant res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			String resPrenom = rs.getString("prenom");
			res = new Etudiant(resNom, resPrenom, id);
		}
		return res;
	}
	
	/**
	 * List etudiant.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Etudiant> listEtudiant() throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM ETUDIANT;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Etudiant> res = null;
		while (rs.next()) {
			String resNom = rs.getString("nom");
			String resPrenom = rs.getString("prenom");
			int resId = rs.getInt("idEtu");
			res.add(new Etudiant(resNom, resPrenom, resId));
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM Etudiant WHERE idEtu ='"+this.idEtu+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idEtu=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {
		//save ou update de l'etudiant
		if(this.idEtu==-1) {
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
			String SQLPrep0 = "INSERT INTO Etudiant (`NOM`, `PRENOM`) VALUES" + 
					"('"+this.nom+"', '"+this.prenom+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Etudiant WHERE NOM ='"+this.nom+"' AND PRENOM ='"+this.prenom+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idEtu");
			}
			this.idEtu=idres;
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
			String SQLPrep0 = "UPDATE Etudiant " + 
					"SET NOM = '"+this.nom+"', PRENOM = '"+this.prenom+"'" + 
					"WHERE IDEtu ='"+this.idEtu+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
	}


	/**
	 * Méthode indiquant si un Etudiant doit être pris en compte
	 * @return
	 */
	public boolean verifierPriseEnCompte(){
		boolean res = true;

		ArrayList<Particularite> particularites = new ArrayList<Particularite>();
		try {
			particularites = ParticulariteEtudiant.listParticularitePourEtudiant(this.idEtu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Particularite particularite : particularites){
			if(!(particularite.getPrendreEnComptePlacement()==1)){
				res=false;
				break;
			}
		}
		return res;
	}

	/**
	 * Compare les étudiant en fonction de leurs particularités
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(Etudiant o) {
		ArrayList<Particularite> particularites = null;
		ArrayList<Particularite> particularitesCompare = null;
		try {
			particularites = ParticulariteEtudiant.listParticularitePourEtudiant(this.idEtu);
			particularitesCompare = ParticulariteEtudiant.listParticularitePourEtudiant(o.getIdEtu());

		} catch (SQLException e) {
			e.printStackTrace();
		}


		if(particularites.size()>particularitesCompare.size()){
			return -1;
		}else if(particularites.size()==particularitesCompare.size()){
			return 0;
		}
		return 1;
	}
}
