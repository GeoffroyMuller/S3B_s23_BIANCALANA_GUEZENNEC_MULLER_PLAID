package modele.BDD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.mysql.fabric.xmlrpc.base.Array;



/**
 * la Class Groupe.
 */
public class Groupe {



	/** le nom. */
	private String nom;

	/** l' id groupe. */
	private int idGroupe;

	/**
	 * Instantiates un nouveau groupe.
	 *
	 * @param nom le nom
	 */
	public Groupe(String nom) {
		this.idGroupe=-1;
		this.nom=nom;
		
	}


	/**
	 * Gets le nom.
	 *
	 * @return le nom
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * Gets l' id groupe.
	 *
	 * @return l' idGroupe
	 */
	public int getIdGroupe() {
		return idGroupe;
	}

	/**
	 * Sets le nom.
	 *
	 * @param nom le new nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Instantiates un nouveau groupe.
	 *
	 * @param nom le nom
	 * @param idGroupe l' id groupe
	 */
	public Groupe(String nom, int idGroupe) {
		this.nom=nom;
		this.idGroupe=idGroupe;
	}


	/**
	 * Creates la table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();

			String nomBase = DBConnection.getNomDB();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`Groupe` ( `idGroupe` INT(11) NOT NULL AUTO_INCREMENT, `nom` VARCHAR(40) NOT NULL, PRIMARY KEY (`idGroupe`)) ENGINE = InnoDB;";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
		}
		catch(SQLException e){
			System.out.println(e.getMessage()+" CreateTable "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Rechercher etudiant.
	 *
	 * @param nom le nom
	 * @param prenom le prenom
	 * @return le array list
	 */
	public ArrayList<Etudiant> rechercherEtudiant(String nom, String prenom){
		ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
		try{
			Connection connect = DBConnection.getConnection();
			ResultSet rs=null;
			if(null != nom && null != prenom){
				PreparedStatement nomEtPrenom = connect.prepareStatement("SELECT ETUDIANT.idEtu FROM ETUDIANT INNER JOIN ETUDIANTGROUPE ON ETUDIANT.IdEtu = ETUDIANTGROUPE.IdEtu WHERE ETUDIANTGROUPE.IdGroupe ="+this.idGroupe+" AND ETUDIANT.nom LIKE '%"+nom+"%' AND ETUDIANT.prenom LIKE '%"+prenom+"';");
				nomEtPrenom.execute();
				 rs = nomEtPrenom.getResultSet();

			}else if(null!=nom){
				PreparedStatement nomSeulement = connect.prepareStatement("SELECT ETUDIANT.idEtu FROM ETUDIANT INNER JOIN ETUDIANTGROUPE ON ETUDIANT.IdEtu = ETUDIANTGROUPE.IdEtu WHERE ETUDIANTGROUPE.IdGroupe ="+this.idGroupe+" AND ETUDIANT.nom LIKE '"+nom+"';");
				nomSeulement.execute();
				 rs = nomSeulement.getResultSet();
			}else if(null!=prenom){
				PreparedStatement prenomSeulement = connect.prepareStatement("SELECT ETUDIANT.idEtu FROM ETUDIANT INNER JOIN ETUDIANTGROUPE ON ETUDIANT.IdEtu = ETUDIANTGROUPE.IdEtu WHERE ETUDIANTGROUPE.IdGroupe ="+this.idGroupe+" AND ETUDIANT.prenom LIKE '"+prenom+"';");
				prenomSeulement.execute();
				 rs = prenomSeulement.getResultSet();
			}

			while(rs.next()){
				Etudiant etudiant = Etudiant.findById(rs.getInt("idEtu"));
				etudiants.add(etudiant);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return etudiants;
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
	 * @param id le id
	 * @return le groupe
	 * @throws SQLException le SQL exception
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
	 * Gets la categorie.
	 *
	 * @return la categorie
	 */
	public Categorie getCategorie(){
		Categorie categorie = null;
		try{
			Connection connect=DBConnection.getConnection();
			PreparedStatement requete = connect.prepareStatement("SELECT * FROM CATEGORIE INNER JOIN GROUPECATEGORIE on CATEGORIE.IdCategorie = GROUPECATEGORIE.IdCategorie INNER JOIN GROUPE on GROUPE.IdGroupe = GROUPECATEGORIE.IdGroupe WHERE Groupe.IdGroupe="+this.idGroupe+";");
			requete.execute();
			ResultSet rs = requete.getResultSet();
			while(rs.next()){
				categorie = new Categorie(rs.getString("nom"),rs.getInt("IdCategorie"));
			}
		}catch(SQLException e){
/*
TO DO
 */
		}
		return categorie;
	}

	/**
	 * List groupe.
	 *
	 * @return le array list
	 */
	public static ArrayList<Groupe> listGroupe() {
		ArrayList<Groupe> res = new ArrayList<Groupe>();
		try {
			Connection connect = DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM GROUPE;";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resId = rs.getInt("idGroupe");
				res.add(new Groupe(resNom, resId));
			}
		}catch(SQLException e){
			/*
			TO DO
			 */
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


	/**
	 * Retirer etudiant du groupe.
	 *
	 * @param idEtudiant le id etudiant
	 */
	public void retirerEtudiantDuGroupe(int idEtudiant){
		try{
			Connection connect=DBConnection.getConnection();
			PreparedStatement requete = connect.prepareStatement("DELETE FROM ETUDIANTGROUPE WHERE IdEtu="+idEtudiant+" AND IdGroupe="+this.idGroupe+";");
			requete.execute();
			this.save();
		}catch(SQLException e){
/*
TO DO
 */
		}
	}

	/**
	 * Sets la liste etudiants.
	 *
	 * @param la nouvelle liste etudiants
	 */
	public void setListeEtudiants(ArrayList<Etudiant> le) {
		for (Etudiant etudiant : le) {
			EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiant.getIdEtu(), this.getIdGroupe());
		}
	}
	
	/**
	 * Gets le liste etudiants.
	 *
	 * @return le liste etudiants
	 */
	public ArrayList<Etudiant> getListeEtudiants(){
		try {
		return EtudiantGroupe.recupererEtudiantDansGroupe(this.getIdGroupe());
		}
		catch(Exception e) {
			
		}
		
		return null;
		
	}

	/**
	 * Ajouter etudiants.
	 *
	 * @param listEtudiant le list etudiant
	 */
	public void ajouterEtudiants(ArrayList<Etudiant> listEtudiant) {
		for (int i = 0; i < listEtudiant.size(); i++) {
			if(listEtudiant.get(i).getIdEtu()!=-1) {
				EtudiantGroupe.ajouterEtudiantAUnGroupe(listEtudiant.get(i).getIdEtu(), this.idGroupe);
			}
		}
	}

	/**
	 * Ajouter etudiant.
	 *
	 * @param etudiant le etudiant
	 */
	public void ajouterEtudiant(Etudiant etudiant){
		ArrayList<Etudiant> etudiantsGroupe = this.getListeEtudiants();
		if(!etudiantsGroupe.contains(etudiant)){
			EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiant.getIdEtu(), this.idGroupe);

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
	
	
	
	public String toString() {
		return this.nom;
	}
}
