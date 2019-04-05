package modele.BDD;




import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;


/**
 * La Class Etudiant.
 */
public class Etudiant implements Comparable<Etudiant>  {

	/** le nom. */
	private String nom;


	/** le prenom. */
	private String prenom;
	
	/** l' email. */
	private String email;
	
	/** l' id etu. */
	private int idEtu;


	/**
	 * Instantiates le nouveau etudiant.
	 *
	 * @param nom le nom
	 * @param prenom le prenom
	 */
	public Etudiant(String nom, String prenom) {
		this.idEtu=-1;
		this.nom=nom;
		this.prenom=prenom;
		
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
	 * Gets le prenom.
	 *
	 * @return le prenom
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * Gets l'email.
	 *
	 * @return l' email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Gets le id etu.
	 *
	 * @return le idEtu
	 */
	public int getIdEtu() {
		return idEtu;
	}

	/**
	 * Méthode permettant de récupérer le groupe d'un étudiant.
	 *
	 * @return le groupe
	 */
	public String getGroupe(){
		String res="NON DEFINI";
		try {
			Connection connect=DBConnection.getConnection();
			PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM ETUDIANTGROUPE WHERE idEtu = ? ");

			prep1.setInt(1,this.idEtu);
			prep1.execute();

			ResultSet rs = prep1.getResultSet();
			rs.next();
			int idGroupe = rs.getInt("idGroupe");

			PreparedStatement prep2 = connect.prepareStatement("SELECT * FROM GROUPE WHERE idGroupe=?");
			prep2.setInt(1,idGroupe);
			prep2.execute();

			ResultSet rs2 = prep2.getResultSet();
			rs2.next();
			res = rs2.getString(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * Instantiates un nouveau etudiant.
	 *
	 * @param nom le nom
	 * @param prenom le prenom
	 * @param idEtu l'id etu
	 */
	public Etudiant(String nom, String prenom, int idEtu) {
		this.prenom=prenom;
		this.nom=nom;
		this.idEtu=idEtu;
		
	}

	/**
	 * Gets la particularites.
	 *
	 * @return la particularites
	 */
	public ArrayList<Particularite> getParticularites(){
		ArrayList<Particularite> res = new ArrayList<Particularite>();
		try {
			res = ParticulariteEtudiant.listParticularitePourEtudiant(this.idEtu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}


	/**
	 * Creates la table.
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
			String SQLPrep1 = "DROP TABLE IF EXISTS ETUDIANT";
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
	 * Recuperer les groupes.
	 *
	 * @return une array list
	 */
	public ArrayList<Groupe> recupererGroupes(){
		ArrayList<Groupe> res = new ArrayList<Groupe>();
		try{
			Connection connect=DBConnection.getConnection();
			PreparedStatement requete = connect.prepareStatement("SELECT * FROM GROUPE INNER JOIN ETUDIANTGROUPE ON GROUPE.IdGroupe = ETUDIANTGROUPE.IdGroupe INNER JOIN ETUDIANT ON ETUDIANT.IdEtu = ETUDIANTGROUPE.IdEtu WHERE ETUDIANT.IdEtu="+this.idEtu+";");

			requete.execute();
			ResultSet rs = requete.getResultSet();
			while(rs.next()){
				res.add(new Groupe(rs.getString("nom"),rs.getInt("IdGroupe")));
			}
		}catch(SQLException e){

		}

		return res;
	}

	/**
	 * Recuperer tout les nom de groupe.
	 *
	 * @return le string
	 */
	public String recupererToutLesNomDeGroupe(){
		String resultat="";
		ArrayList<Groupe> groupe = this.recupererGroupes();

		for(int i=0; i < groupe.size();i++){
			resultat+=groupe.get(i).getNom();
			if(!(i == groupe.size()-1)){
				resultat+=", ";
			}
		}
		return resultat;
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
	 * Sets le prenom.
	 *
	 * @param prenom le new prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	/**
	 * Enlever la particularite.
	 *
	 * @param particularite particularite
	 */
	public void enleverParticularite(Particularite particularite){
		ParticulariteEtudiant.delete(particularite.getIdParticularite(),this.getIdEtu());
	}


	/**
	 * Rechercher etudiant.
	 *
	 * @param nom le nom
	 * @param prenom le prenom
	 * @return une array list etudiant
	 */
	public static ArrayList<Etudiant> rechercherEtudiant(String nom, String prenom){
		ArrayList<Etudiant> res = new ArrayList<Etudiant>();
		try{
			Connection connect=DBConnection.getConnection();
			PreparedStatement requete = null;
			if(null==nom){
				requete = connect.prepareStatement("SELECT * FROM ETUDIANT WHERE ETUDIANT.prenom='"+prenom+"';");
			}else if(null==prenom){
				requete = connect.prepareStatement("SELECT * FROM ETUDIANT WHERE ETUDIANT.nom='"+nom+"';");

			}else{
				requete = connect.prepareStatement("SELECT * FROM ETUDIANT WHERE ETUDIANT.nom='"+nom+"' AND ETUDIANT.prenom='"+prenom+"';");
			}

			requete.execute();
			ResultSet rs = requete.getResultSet();
			while(rs.next()){
				res.add(new Etudiant(rs.getString("nom"),rs.getString("prenom"),rs.getInt("IdEtu")));
			}
		}catch(SQLException e){

		}

		return res;
	}

	/**
	 * Rechercher tout les etudiants.
	 *
	 * @return le array list etudiant
	 */
	public static ArrayList<Etudiant> rechercherToutLesEtudiants(){
		ArrayList<Etudiant> res = new ArrayList<Etudiant>();
		try{
			Connection connect=DBConnection.getConnection();
			PreparedStatement requete = requete = connect.prepareStatement("SELECT * FROM ETUDIANT;");

			requete.execute();
			ResultSet rs = requete.getResultSet();
			while(rs.next()){
				res.add(new Etudiant(rs.getString("nom"),rs.getString("prenom"),rs.getInt("IdEtu")));
			}
		}catch(SQLException e){

		}

		return res;
	}

	/**
	 * Find by id.
	 *
	 * @param id l' id
	 * @return l' etudiant
	 */
	public static Etudiant findById(int id) {
		Etudiant res = null;

		try {
			Connection connect = DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM ETUDIANT WHERE IDETU ='" + id + "';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat

			while (rs.next()) {
				String resNom = rs.getString("nom");
				String resPrenom = rs.getString("prenom");
				res = new Etudiant(resNom, resPrenom, id);
			}
		}catch(SQLException e){
			/*
			TO DO
			 */
		}
		return res;
	}
	
	/**
	 * List etudiant.
	 *
	 * @return le array list etudiants
	 */
	public static ArrayList<Etudiant> listEtudiant() {
		ArrayList<Etudiant> res = new ArrayList<Etudiant>();

		try {
			Connection connect = DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM ETUDIANT;";
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
			System.out.println("Etudiant nouvelle save");
			this.saveNew();
		}
		else {
			System.out.println("Etudiant UPDATE");
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
		System.out.println(this.getNom());
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
	 * Méthode indiquant si un Etudiant doit être pris en compte.
	 *
	 * @return true, if successful
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
	 * Compare les étudiant en fonction de leurs particularités.
	 *
	 * @param o le o
	 * @return le int
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
	
	/**
	 * Ajouter particularite.
	 *
	 * @param listParticularite le list particularite
	 */
	public void ajouterParticularite(ArrayList<Particularite> listParticularite) {

		ArrayList<Particularite> particularitesEtudiant = this.getParticularites();

		for (int i = 0; i < listParticularite.size(); i++) {
			Particularite particularite = listParticularite.get(i);
			if(particularite.getIdParticularite()!=-1 && !verifierSiParticularitePossede(particularite)) {
				ParticulariteEtudiant.ajouterParticulariteAUnEtudiant(listParticularite.get(i).getIdParticularite(), this.idEtu);
			}
		}
	}

	/**
	 * Verifier si particularite possede.
	 *
	 * @param particularite le particularite
	 * @return true, if successful
	 */
	public boolean verifierSiParticularitePossede(Particularite particularite){
		boolean res=false;
		ArrayList<Particularite> particularites = this.getParticularites();
		for(Particularite part : particularites){
			if(particularite.getNom().equals(part.getNom())){
				res=true;
				break;
			}
		}
		return res;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Etudiant etudiant = (Etudiant) o;
		return idEtu == etudiant.idEtu;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(idEtu);
	}
}
