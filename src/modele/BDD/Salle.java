package modele.BDD;


import modele.Iterateur;
import vue.VueSalle;

import javax.swing.*;
import java.net.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;


// TODO: Auto-generated Javadoc
/**
 * The Class Salle.
 */
public class Salle extends Observable {

	/** The nom. */
	private String nom;
	
	/** The id salle. */
	private int idSalle;

	/** The nb case largeur. */
	protected int nbCaseLargeur;
	
	/** The nb case hauteur. */
	protected int nbCaseHauteur;

	/** The places. */
	protected Place[][] places;

	/**
	 * Définition de la largeur par défaut d'une salle dans la visualisation
	 */
	public static int DEFAULT_SIZE_ROOM_WIDTH = 10;
	/**
	 * Définition de la hauteur par défaut d'une salle dans la visualisation
	 */
	public static int DEFAULT_SIZE_ROOM_HEIGHT = 10;

	/**
	 * Correspond au nombre de place disponible d'une salle, les places indisponible ne sont pas comptées.
	 */
	private int nbPlaces;

	/**
	 * Instantiates a new salle.
	 *
	 * @param nom the nom
	 * @param nbCaseHauteur the nb case hauteur
	 * @param nbCaseLargeur the nb case largeur
	 */
	public Salle(String nom, int nbCaseHauteur, int nbCaseLargeur) {
		this.idSalle=-1;
		this.nom=nom;
		this.nbCaseHauteur=nbCaseHauteur;
		this.nbCaseLargeur=nbCaseLargeur;

		this.places = new Place[nbCaseHauteur][nbCaseLargeur];
		for(int i = 0; i < places.length; i++){
			for(int j = 0; j < places[0].length; j++){
				try {
					this.places[i][j] = new Place(""+i+""+j+"", TypePlace.findByNom("Allee").getIdTypePlace(),i,j,1,this.idSalle,j+"",i+"");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Salle(Salle salle){
		this.nom = salle.getNom();
		this.nbCaseLargeur = salle.getNbCaseLargeur();
		this.nbCaseHauteur = salle.getNbCaseHauteur();
		this.places = salle.places;
		this.idSalle = salle.getIdSalle();
	}

	/**
	 * Instantiates a new salle.
	 *
	 * @param nom the nom
	 * @param idSalle the id salle
	 * @param nbCaseHauteur the nb case hauteur
	 * @param nbCaseLargeur the nb case largeur
	 */
	private Salle(String nom, int idSalle, int nbCaseHauteur, int nbCaseLargeur) {
		this.nom=nom;
		this.idSalle=idSalle;
		this.nbCaseHauteur=nbCaseHauteur;
		this.nbCaseLargeur=nbCaseLargeur;
	}

	public void modifierNomPlace(int i, int j, String nomPlace,String nomColonne,String nomRangee){
		Place place = this.places[i][j];
		this.places[i][j].setNom(nomPlace);
		this.places[i][j].setNomColonne(nomColonne);
		this.places[i][j].setNomRangee(nomRangee);
		setChanged();
		notifyObservers();
		this.save();
	}

	public String toString(){
		return this.nom;
	}

	public int compterLeNombreDePlaceDisponible(){
		int res = 0;
		this.getTableauPlaces(this.getIdSalle());

		for(int i = 0; i < this.getPlaces().length;i++){
			for(int j = 0; j < this.getPlaces().length;j++){
				Place place = this.getPlaces()[i][j];
				if(place.getDisponnible()){
					res++;
				}
			}
		}

		return res;
	}

	/**
	 * Gets the tableau places.
	 *
	 * @param idSalle the id salle
	 * @return the tableau places
	 */
	public void getTableauPlaces(int idSalle){
		try {
			this.places = new Place[this.nbCaseLargeur][this.nbCaseHauteur];
			this.places = Place.tableauPlace(idSalle);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Creates the table.
	 */
	public static void createTable(){
		try {
			Connection connect=DBConnection.getConnection();

			String nomBase = DBConnection.getNomDB();
			String SQLPrep0 = "CREATE TABLE IF NOT EXISTS `"+nomBase+"`.`Salle` "
					+ "( `idSalle` INT(11) NOT NULL AUTO_INCREMENT , `nbCaseHauteur` INT(11) NOT NULL,"
					+ " `nbCaseLargeur` INT(11) NOT NULL, `nom` VARCHAR(40) NOT NULL, "
					+ "PRIMARY KEY (`idSalle`)) ENGINE = InnoDB";

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
			String SQLPrep1 = "DROP TABLE  IF EXISTS SALLE";
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
	 * @return the salle
	 * @throws SQLException the SQL exception
	 */
	public static Salle findById(int id){
		Salle res = null;
		try{
			Connection connect=DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM Salle WHERE IdSalle ='"+id+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resNbCaseHauteur = rs.getInt("NbCaseHauteur");
				int resNbCaseLargeur = rs.getInt("NbCaseLargeur");

				res = new Salle(resNom,id,resNbCaseHauteur,resNbCaseLargeur);
			}
		}catch(SQLException e){
			//EXCEPTION A GERER
		}
		return res;
	}
	
	/**
	 * Find by nom.
	 *
	 * @param nom the nom
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static Salle findByNom(String nom){
		Salle res = null;
		try {
			Connection connect = DBConnection.getConnection();
			String SQLPrep = "SELECT * FROM Salle WHERE nom ='"+nom+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat


			while (rs.next()) {
				String resNom = rs.getString("nom");
				int resNbCaseHauteur = rs.getInt("NbCaseHauteur");
				int resNbCaseLargeur = rs.getInt("NbCaseLargeur");
				int resId = rs.getInt("IdSalle");

				res=new Salle(resNom,resId,resNbCaseHauteur,resNbCaseLargeur);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null,"La connexion à la base de donnée n'a pas pu être établie !","Erreur",JOptionPane.INFORMATION_MESSAGE);
		}

		return res;
	}
	
	/**
	 * List salle.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Salle> listSalle() throws SQLException {
		Connection connect=DBConnection.getConnection();
		String SQLPrep = "SELECT * FROM Salle;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();
		// s'il y a un resultat

		ArrayList<Salle> res = new ArrayList<Salle>();
		while (rs.next()) {
			String resNom = rs.getString("nom");
			int resId = rs.getInt("idSalle");
			int resNbCaseHauteur = rs.getInt("NbCaseHauteur");
			int resNbCaseLargeur = rs.getInt("NbCaseLargeur");
			res.add(new Salle(resNom,resId,resNbCaseHauteur,resNbCaseLargeur));
		}
		return res;
	}

	/**
	 * Delete.
	 */
	public void delete(){
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "DELETE FROM Salle WHERE idSalle ='"+this.idSalle+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			this.idSalle=-1;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
	}

	/**
	 * Save.
	 */
	public void save() {

		if(this.idSalle==-1) {
			this.saveNew();
		}
		else {
			this.update();
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Save new.
	 */
	private void saveNew() {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "INSERT INTO Salle (`NOM`, `NbCaseHauteur`, `NbCaseLargeur`) VALUES" + 
					"('"+this.nom+"', '"+this.nbCaseHauteur+"', '"+this.nbCaseLargeur+"')";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();
			String SQLPrep = "SELECT * FROM Salle WHERE NOM ='"+this.nom+"' AND NbCaseHauteur ='"+this.nbCaseHauteur+"' AND NbCaseLargeur ='"+this.nbCaseLargeur+"';";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			int idres = -1;
			while (rs.next()) {
				idres = rs.getInt("idSalle");
			}
			this.idSalle=idres;

			//On sauvegarde les places liées à la salle
			for(int i = 0; i < nbCaseHauteur;i++){
				for(int j = 0; j < nbCaseLargeur;j++){
					this.places[i][j].setIdSalle(this.idSalle);
					this.places[i][j].save();
				}
			}


		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"new "+e.getErrorCode()+e.toString());
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Update.
	 */
	private void update() {
		try {
			Connection connect=DBConnection.getConnection();
			String SQLPrep0 = "UPDATE Salle " + 
					"SET NOM = '"+this.nom+"', NbCaseHauteur = '"+this.nbCaseHauteur+"', NbCaseLargeur = '"+this.nbCaseLargeur+"'"+ 
					"WHERE IDSalle ='"+this.idSalle+"';";
			PreparedStatement prep0 = connect.prepareStatement(SQLPrep0);
			prep0.execute();

			//Mise a jour des places
			for(int i = 0; i < this.places.length;i++){
				for(int j = 0; j < this.places[i].length;j++){
					System.out.println("Mise a jour de la plce");
					String SQLPrep1 = "UPDATE Place " +
							"SET NOM = '"+this.places[i][j].getNom()+"', IdTypePlace = '"+this.places[i][j].getIdTypePlace()+"', i = '"+this.places[i][j].getI()+"', j = '"+this.places[i][j].getJ()+"', Disponnible = '"+this.places[i][j].getDisponibleIntVersion()+"', idSalle = '"+this.places[i][j].getIdSalle()
							+"'"+ "WHERE idPlace ='"+this.places[i][j].getIdPlace()+"';";
					System.out.println(this.places[i][j].getIdPlace()+" - "+this.places[i][j].getNom());

					PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
					prep1.execute();
				}
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets the iterateur.
	 *
	 * @param i the i
	 * @param j the j
	 * @param salle the salle
	 * @return the iterateur
	 */
	public Iterateur getIterateur(int i, int j, Salle salle){
		return new SalleIterateur(i,j, salle);
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the idSalle
	 */
	public int getIdSalle() {
		return idSalle;
	}

	/**
	 * @param idSalle the idSalle to set
	 */
	public void setIdSalle(int idSalle) {
		this.idSalle = idSalle;
	}

	/**
	 * @return the nbCaseLargeur
	 */
	public int getNbCaseLargeur() {
		return nbCaseLargeur;
	}

	/**
	 * @param nbCaseLargeur the nbCaseLargeur to set
	 */
	public void setNbCaseLargeur(int nbCaseLargeur) {
		this.nbCaseLargeur = nbCaseLargeur;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the nbCaseHauteur
	 */
	public int getNbCaseHauteur() {
		return nbCaseHauteur;
	}

	/**
	 * @param nbCaseHauteur the nbCaseHauteur to set
	 */
	public void setNbCaseHauteur(int nbCaseHauteur) {
		this.nbCaseHauteur = nbCaseHauteur;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the places
	 */
	public Place[][] getPlaces() {
		return this.places;
	}

	/**
	 * @param places the places to set
	 */
	public void setPlaces(Place[][] places) {
		this.places = places;
		setChanged();
		notifyObservers();
	}


	/**
	 * Permet de changer le Type d'une place de la salle
	 * @param coordX
	 * @param coordY
	 * @param nouveauTypeId
	 */
	public void changerLeTypePlace(int coordX, int coordY, int nouveauTypeId){
		this.places[coordX][coordY].setTypePlace(nouveauTypeId);
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet de changer la salle actuel par une autre
	 * @param salle
	 */
	public void  changerSalle(Salle salle){
		this.nom = salle.getNom();
		this.idSalle = salle.getIdSalle();
		this.nbCaseLargeur = salle.getNbCaseLargeur();
		this.nbCaseHauteur=salle.getNbCaseHauteur();
		salle.getTableauPlaces(salle.idSalle);
		this.places=salle.getPlaces();
		setChanged();
		notifyObservers();
	}


	/**
	 * Permet de changer les informations de la salle
	 * @param nom
	 * @param hauteur
	 * @param largeur
	 */
    public void changerInformation(String nom, int hauteur, int largeur) {
    	boolean changementDimension = false;
    	if(!this.nom.contains(nom)){
			this.nom =nom;
			VueSalle.partieAUpdate = VueSalle.UPDATE_NOTHING;
		}
		if(this.nbCaseHauteur != hauteur){
			this.nbCaseHauteur = hauteur;
			 changementDimension = true;
			VueSalle.partieAUpdate = VueSalle.UPDATE_ALL;
		}
		if(this.nbCaseLargeur!=largeur){
			this.nbCaseLargeur = largeur;
			 changementDimension = true;

			VueSalle.partieAUpdate = VueSalle.UPDATE_ALL;
		}
		this.idSalle = -1;

		System.out.println("Création de la salle");
		if(changementDimension){
			this.places = new Place[hauteur][largeur];

			for(int i = 0; i < hauteur; i++){
				for(int j = 0; j < largeur; j++){
					try {
						this.places[i][j] = new Place(""+i+""+j+"", TypePlace.findByNom("Allee").getIdTypePlace(),i,j,1,this.idSalle,j+"",i+"");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		setChanged();
		notifyObservers();
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Salle salle = (Salle) o;
		return idSalle == salle.idSalle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSalle);
	}

	public void renommerLigneAlpha(boolean bashaut){
    	String[] alpha = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","X","Y","Z"};
    	int nbPassage=1;
    	String nom="";
    	int index=0;
    	if(bashaut){
    		index = this.nbCaseHauteur-1;
		}
    	for(int i = 0; i < this.getPlaces().length;i++){
    		for(int j = 0; j < this.getPlaces()[i].length;j++){
				for(int x = 0; x < nbPassage;x++){
					nom+=alpha[index];
				}
				this.getPlaces()[i][j].setNomRangee(nom);
				this.getPlaces()[i][j].setNom(nom+""+this.getPlaces()[i][j].getNomColonne());
				this.getPlaces()[i][j].save();
				nom="";
				if(alpha.length-1==i){
					nbPassage++;
				}
			}
			if(bashaut){
				index--;
			}else{
				index++;
			}

		}
    	this.save();
    	this.getTableauPlaces(this.idSalle);
	}

	public void renommerColonneNumerique(boolean reset){
    	int numeric = 1;
		for(int i = 0; i < this.getPlaces().length;i++){
			for(int j = 0; j < this.getPlaces().length;j++){
				this.getPlaces()[i][j].setNomColonne(numeric+"");
				this.getPlaces()[i][j].setNom(this.getPlaces()[i][j].getNomRangee()+""+numeric);
				this.getPlaces()[i][j].save();
				numeric++;
			}
			if(reset){ numeric=1;}
		}
		this.save();
		this.getTableauPlaces(this.idSalle);
	}


	/**
	 * The Class SalleIterateur.
	 */
	public class SalleIterateur implements Iterateur {

		/** The j. */
		protected int i,j;

		/** The salle. */
		private Salle salle;

		/** Coordonnées lors de la création de l'itérateur afin de pouvoir y retourner si besoin. */
		protected int firstI, firstJ;

		/**
		 * Instantiates a new salle iterateur.
		 *
		 * @param i the i
		 * @param j the j
		 * @param salle the salle
		 */
		public SalleIterateur(int i, int j, Salle salle){
			this.firstI = this.i = i;
			this.firstJ = this.j = j;
			this.salle = salle;
			//this.salle.getTableauPlaces(this.salle.idSalle);
		}


		/* (non-Javadoc)
		 * @see modele.Iterateur#hasNext()
		 */
		@Override
		public boolean hasNext() {
			if(i==nbCaseHauteur-1 && j==nbCaseLargeur-1){
				return false;
			}
			return true;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#hasPrevious()
		 */
		@Override
		public boolean hasPrevious() {
			if(i==0 && j==0){
				return false;
			}
			return true;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#hasNext(int)
		 */
		@Override
		public boolean hasNext(int pas) {
			if(j+pas>nbCaseLargeur-1){
				if(i==nbCaseHauteur-1){
					return false;
				}
			}
			return true;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#hasPrevious(int)
		 */
		@Override
		public boolean hasPrevious(int pas) {
			if(j-pas<0){
				if(i<=0){
					return false;
				}
			}

			return true;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#hasUp()
		 */
		@Override
		public boolean hasUp() {
			if(!(this.i == 0)){
				return true;
			}
			return false;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#hasDown()
		 */
		@Override
		public boolean hasDown() {
			if(!(this.i == nbCaseHauteur-1)){
				return true;
			}
			return false;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#next()
		 */
		@Override
		public Object next() {

			if(j==nbCaseLargeur-1){
				this.i++;
				this.j=0;
			}else{
				this.j++;
			}

			return places[this.i][this.j];
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#previous()
		 */
		@Override
		public Object previous() {

			if(j==0 &&!(i==0 && j==0)){
				this.i--;
				this.j=nbCaseLargeur-1;
			}else if(!(i==0 && j==0)){
				this.j--;
			}
			return places[this.i][this.j];
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#next(int)
		 */
		@Override
		public Object next(int pas) {
			Place place=null;
			if(j==nbCaseLargeur-1 || j+pas>nbCaseLargeur-1){
				this.i++;
				this.j=0;
				place = places[this.i][this.j];
			}else{
				this.j=this.j+pas;
				place = places[this.i][this.j];

				//On vérifie que la place n'est pas une allee ou une place cassé
				//Si la place n'est pas disponible alors on avance d'une case
				if(!(places[this.i][this.j].getDisponnible())){
					place = (Place)this.next();
				}
			}


			return place;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#previous(int)
		 */
		@Override
		public Object previous(int pas) {
			Place place=null;
			if(j==0 || j-pas<0){
				this.i--;
				this.j=nbCaseLargeur-1;
				place = places[this.i][this.j];
			}else{
				this.j=this.j-pas;
				place = places[this.i][this.j];

				//On vérifie que la place n'est pas une allee ou une place cassé
				//Si la place n'est pas disponible alors on recule d'une case
				if(!(places[this.i][this.j].getDisponnible()) && !places[this.i][this.j].verifiersiPlaceCassee()){
					place = (Place)this.previous();
				}
			}
			return place;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#up()
		 */
		@Override
		public Object up() {
			this.i--;
			return places[i][j];
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#down()
		 */
		@Override
		public Object down() {
			this.i++;
			return places[i][j];
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#actual()
		 */
		@Override
		public Object actual() {
			Place place = null;
			try {
				place = salle.getPlaces()[i][j];
			}catch(NullPointerException e){
				System.out.println("TEST");
			}
			return place;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#reset()
		 */
		@Override
		public void reset() {
			this.i = this.firstI;
			this.j = this.firstJ;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#getCoordI()
		 */
		@Override
		public int getCoordI() {
			return this.i;
		}

		/* (non-Javadoc)
		 * @see modele.Iterateur#getCoordY()
		 */
		@Override
		public int getCoordY() {
			return this.j;
		}
	}

}
