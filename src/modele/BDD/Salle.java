package modele.BDD;


import modele.Iterateur;
import module_etudiant.DialogTraitement;
import vue.VueSalle;
import vue_Examen.VueExamen;

import javax.swing.*;
import java.net.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;



/**
 *la Class Salle.
 */
public class Salle extends Observable {

	/** le nom. */
	private String nom;
	
	/** l' id salle. */
	private int idSalle;

	/** le nb case largeur. */
	protected int nbCaseLargeur;
	
	/** le nb case hauteur. */
	protected int nbCaseHauteur;

	/** les places. */
	protected Place[][] places;

	/** D�finition de la largeur par d�faut d'une salle dans la visualisation. */
	public static int DEFAULT_SIZE_ROOM_WIDTH = 10;
	
	/** D�finition de la hauteur par d�faut d'une salle dans la visualisation. */
	public static int DEFAULT_SIZE_ROOM_HEIGHT = 10;

	/**
	 * Correspond au nombre de place disponible d'une salle, les places indisponible ne sont pas compt�es.
	 */
	private int nbPlaces;

	/**
	 * Instantiates une nouvelle salle.
	 *
	 * @param nom le nom
	 * @param nbCaseHauteur le nb case hauteur
	 * @param nbCaseLargeur le nb case largeur
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

	/**
	 * Instantiates une nouvelle salle.
	 *
	 * @param salle le salle
	 */
	public Salle(Salle salle){
		this.nom = salle.getNom();
		this.nbCaseLargeur = salle.getNbCaseLargeur();
		this.nbCaseHauteur = salle.getNbCaseHauteur();
		this.places = salle.places;
		this.idSalle = salle.getIdSalle();
	}


	/**
	 * Instantiates une nouvelle salle.
	 *
	 * @param nom le nom
	 * @param idSalle le id salle
	 * @param nbCaseHauteur le nb case hauteur
	 * @param nbCaseLargeur le nb case largeur
	 */
	private Salle(String nom, int idSalle, int nbCaseHauteur, int nbCaseLargeur) {
		this.nom=nom;
		this.idSalle=idSalle;
		this.nbCaseHauteur=nbCaseHauteur;
		this.nbCaseLargeur=nbCaseLargeur;
	}

	/**
	 * Modifier nom place.
	 *
	 * @param i le i
	 * @param j le j
	 * @param nomPlace le nom place
	 * @param nomColonne le nom colonne
	 * @param nomRangee le nom rangee
	 */
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

	/**
	 * Compter le nombre de place disponible.
	 *
	 * @return le int
	 */
	public int compterLeNombreDePlaceDisponible(){
		int res = 0;
		this.getTableauPlaces(this.getIdSalle());

		for(int i = 0; i < this.getPlaces().length;i++){
			for(int j = 0; j < this.getPlaces()[i].length;j++){
				Place place = this.getPlaces()[i][j];
				if(place.getDisponnible()){
					res++;
				}
			}
		}
		return res;
	}

	/**
	 * Gets le tableau places.
	 *
	 * @param idSalle le id salle
	 * @return le tableau places
	 */
	public void getTableauPlaces(int idSalle){
		try {
			//this.places = new Place[this.nbCaseLargeur][this.nbCaseHauteur];
			this.places = new Place[this.nbCaseHauteur][this.nbCaseLargeur];
			this.places = Place.tableauPlace(idSalle);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Creates le table.
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
	 * @param id le id
	 * @return le salle
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
	 * @param nom le nom
	 * @return le array list
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
			jop.showMessageDialog(null,"La connexion � la base de donn�e n'a pas pu �tre �tablie !","Erreur",JOptionPane.INFORMATION_MESSAGE);
		}

		return res;
	}
	
	/**
	 * List salle.
	 *
	 * @return le array list
	 * @throws SQLException le SQL exception
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
			//this.idSalle=-1;
			VueSalle.partieAUpdate = VueSalle.DELETE_SALLE;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+" Delete "+e.getErrorCode()+e.toString());
		}
		setChanged();
		notifyObservers(VueExamen.VUE_ETU);
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
		notifyObservers(VueExamen.VUE_ETU);
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

			//On sauvegarde les places li�es � la salle
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
		VueSalle.partieAUpdate = VueSalle.UPDATE_CREATION_SALLE;
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
					String SQLPrep1 = "UPDATE Place " +
							"SET NOM = '"+this.places[i][j].getNom()+"', IdTypePlace = '"+this.places[i][j].getIdTypePlace()+"', i = '"+this.places[i][j].getI()+"', j = '"+this.places[i][j].getJ()+"', Disponnible = '"+this.places[i][j].getDisponibleIntVersion()+"', idSalle = '"+this.places[i][j].getIdSalle()
							+"'"+ "WHERE idPlace ='"+this.places[i][j].getIdPlace()+"';";

					PreparedStatement prep1 = connect.prepareStatement(SQLPrep1);
					prep1.execute();
				}
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage()+"update "+e.getErrorCode()+e.toString());
		}
		setChanged();
		notifyObservers(VueExamen.VUE_ETU);
	}

	/**
	 * Gets l' iterateur.
	 *
	 * @param i le i
	 * @param j le j
	 * @param salle le salle
	 * @return le iterateur
	 */
	public Iterateur getIterateur(int i, int j, Salle salle){
		return new SalleIterateur(i,j, salle);
	}

	/**
	 * Gets le nom.
	 *
	 * @return le nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Sets le nom.
	 *
	 * @param nom le nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
		VueExamen.rechargerlisteurSalle();
		/*setChanged();
		notifyObservers();*/
	}

	/**
	 * Gets le id salle.
	 *
	 * @return le idSalle
	 */
	public int getIdSalle() {
		return idSalle;
	}

	/**
	 * Sets le id salle.
	 *
	 * @param idSalle le idSalle to set
	 */
	public void setIdSalle(int idSalle) {
		this.idSalle = idSalle;
	}

	/**
	 * Gets le nb case largeur.
	 *
	 * @return le nbCaseLargeur
	 */
	public int getNbCaseLargeur() {
		return nbCaseLargeur;
	}

	/**
	 * Sets le nb case largeur.
	 *
	 * @param nbCaseLargeur le nbCaseLargeur to set
	 */
	public void setNbCaseLargeur(int nbCaseLargeur) {
		this.nbCaseLargeur = nbCaseLargeur;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets le nb case hauteur.
	 *
	 * @return le nbCaseHauteur
	 */
	public int getNbCaseHauteur() {
		return nbCaseHauteur;
	}

	/**
	 * Sets le nb case hauteur.
	 *
	 * @param nbCaseHauteur le nbCaseHauteur to set
	 */
	public void setNbCaseHauteur(int nbCaseHauteur) {
		this.nbCaseHauteur = nbCaseHauteur;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets le places.
	 *
	 * @return le places
	 */
	public Place[][] getPlaces() {
		return this.places;
	}

	/**
	 * Sets la places.
	 *
	 * @param places le places to set
	 */
	public void setPlaces(Place[][] places) {
		this.places = places;
		setChanged();
		notifyObservers();
	}


	/**
	 * Permet de changer le Type d'une place de la salle.
	 *
	 * @param coordX le coord X
	 * @param coordY le coord Y
	 * @param nouveauTypeId le nouveau type id
	 */
	public void changerLeTypePlace(int coordX, int coordY, int nouveauTypeId){
		this.places[coordX][coordY].setTypePlace(nouveauTypeId);
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet de changer la salle actuel par une autre.
	 *
	 * @param salle le salle
	 */
	public void changerSalle(Salle salle){
		this.nom = salle.getNom();
		this.idSalle = salle.getIdSalle();
		this.nbCaseLargeur = salle.getNbCaseLargeur();
		this.nbCaseHauteur=salle.getNbCaseHauteur();
		salle.getTableauPlaces(salle.idSalle);
		this.places=salle.getPlaces();
		VueExamen.rechargerlisteurSalle();
		setChanged();
		notifyObservers();
	}


	/**
	 * Permet de changer les informations de la salle.
	 *
	 * @param nom le nom
	 * @param hauteur le hauteur
	 * @param largeur le largeur
	 * @param nouvelleSalle le nouvelle salle
	 */
    public void changerInformation(String nom, int hauteur, int largeur, boolean nouvelleSalle) {
    	boolean changementDimension = false;
    	boolean changementNom = false;

    	if(nouvelleSalle){
    		this.idSalle=-1;
		}
    	if(!this.nom.equals(nom)){
			//this.nom =nom;
			changementNom = true;
			this.setNom(nom);
			VueSalle.partieAUpdate = VueSalle.UPDATE_SALLE;
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

		if(changementDimension && changementNom){
			VueSalle.partieAUpdate = VueSalle.MODIFICATION_SALLE_TOTAL;
		}

		if(changementDimension){
			//On supprime de la base les places
			ArrayList<Place> temp = Place.findByIdSalle(this.idSalle);
			for(Place place : temp){
				place.delete();
			}





			this.places = new Place[hauteur][largeur];
			for(int i = 0; i < hauteur; i++){
				for(int j = 0; j < largeur; j++){
					try {
						Place place = new Place(""+i+""+j+"", TypePlace.findByNom("Allee").getIdTypePlace(),i,j,1,this.idSalle,j+"",i+"");
						this.places[i][j] = place;
						place.save();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			this.getPlaces();
		}
		//Sauvegarde de la salle avec chargement
		final DialogTraitement traitement = new DialogTraitement(null, "Traitement en cours...", true);
		this.save();

		final Thread tr = new Thread(new Runnable() {
			@Override
			public void run() {
				save();
				traitement.close();
				VueExamen.rechargerlisteurSalle();
				setChanged();
				notifyObservers(VueExamen.VUE_ETU);
			}
		});
		Thread trLoader = new Thread(new Runnable() {
			@Override
			public void run() {
				traitement.afficherDialog();
				try {
					tr.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		tr.start();
		trLoader.start();

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

	/**
	 * Renommer ligne alpha.
	 *
	 * @param bashaut le bashaut
	 */
	public void renommerLigneAlpha(boolean bashaut){
    	String[] alpha = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","X","Y","Z"};
    	int nbPassage=1;
    	String nom="";
    	int index=0;
    	boolean utilise = false;
    	if(bashaut){
    		index = this.nbCaseHauteur-1;
		}
    	for(int i = 0; i < this.getPlaces().length;i++){
    		for(int j = 0; j < this.getPlaces()[i].length;j++){
				for(int x = 0; x < nbPassage;x++){
					nom+=alpha[index];
				}
				String typePlace = TypePlace.findById(this.getPlaces()[i][j].getIdTypePlace()).getNom();
				if(typePlace.equals("allee")){
					this.getPlaces()[i][j].setNomRangee("Allee");
					this.getPlaces()[i][j].setNomColonne("Allee");
					this.getPlaces()[i][j].setNom("Allee");
				}else{
					this.getPlaces()[i][j].setNomRangee(nom);
					this.getPlaces()[i][j].setNom(nom+""+this.getPlaces()[i][j].getNomColonne());
					utilise=true;
				}
				this.getPlaces()[i][j].save();
				nom="";
				if(alpha.length-1==i){
					nbPassage++;
				}
			}
			if(utilise){
				if(bashaut){
					index--;
				}else{
					index++;
				}
				utilise=false;
			}


		}
    	this.save();
    	this.getTableauPlaces(this.idSalle);
	}

	/**
	 * Renommer colonne numerique.
	 *
	 * @param reset le reset
	 */
	public void renommerColonneNumerique(boolean reset, boolean bas){
    	int numeric = 1;
    	boolean utilise=false;

    	if(bas){
			int indexI = this.getPlaces().length-1;

			for(int i = indexI; i >= 0;i--){
				for(int j = 0; j < this.getPlaces()[i].length;j++){
					String typePlace = TypePlace.findById(this.getPlaces()[i][j].getIdTypePlace()).getNom();

					if(typePlace.equals("allee")){
						this.getPlaces()[i][j].setNomColonne("Allee");
						this.getPlaces()[i][j].setNomRangee("Allee");
						this.getPlaces()[i][j].setNom("Allee");
					}else{
						this.getPlaces()[i][j].setNomColonne(numeric+"");
						this.getPlaces()[i][j].setNom(this.getPlaces()[i][j].getNomRangee()+""+numeric);
						numeric++;
					}
					this.getPlaces()[i][j].save();
				}
				if(reset){ numeric=1;}
			}


		}else{
			for(int i = 0; i < this.getPlaces().length;i++){
				for(int j = 0; j < this.getPlaces()[i].length;j++){
					String typePlace = TypePlace.findById(this.getPlaces()[i][j].getIdTypePlace()).getNom();


					if(typePlace.equals("allee")){
						this.getPlaces()[i][j].setNomColonne("Allee");
						this.getPlaces()[i][j].setNomRangee("Allee");
						this.getPlaces()[i][j].setNom("Allee");
					}else{
						this.getPlaces()[i][j].setNomColonne(numeric+"");
						this.getPlaces()[i][j].setNom(this.getPlaces()[i][j].getNomRangee()+""+numeric);
						numeric++;
					}
					this.getPlaces()[i][j].save();
				}
				if(reset){ numeric=1;}
			}
		}



		this.save();
		this.getTableauPlaces(this.idSalle);
	}


	/**
	 * le Class SalleIterateur.
	 */
	public class SalleIterateur implements Iterateur {

		/** le j. */
		protected int i,j;

		/** le salle. */
		private Salle salle;

		/** Coordonn�es lors de la cr�ation de l'it�rateur afin de pouvoir y retourner si besoin. */
		protected int firstI, firstJ;

		/**
		 * Instantiates a new salle iterateur.
		 *
		 * @param i le i
		 * @param j le j
		 * @param salle le salle
		 */
		public SalleIterateur(int i, int j, Salle salle){
			this.firstI = this.i = i;
			this.firstJ = this.j = j;
			this.salle = salle;
			//this.salle.getTableauPlaces(this.salle.idSalle);
		}


		
		@Override
		public boolean hasNext() {
			if(i==nbCaseHauteur-1 && j==nbCaseLargeur-1){
				return false;
			}
			return true;
		}

		
		@Override
		public boolean hasPrevious() {
			if(i==0 && j==0){
				return false;
			}
			return true;
		}

		
		@Override
		public boolean hasNext(int pas) {
			if(j+pas>nbCaseLargeur-1){
				if(i==nbCaseHauteur-1){
					return false;
				}
			}
			return true;
		}

		
		@Override
		public boolean hasPrevious(int pas) {
			if(j-pas<0){
				if(i<=0){
					return false;
				}
			}

			return true;
		}

		
		@Override
		public boolean hasUp() {
			if(!(this.i == 0)){
				return true;
			}
			return false;
		}

		
		@Override
		public boolean hasDown() {
			if(!(this.i == nbCaseHauteur-1)){
				return true;
			}
			return false;
		}

		
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

				//On v�rifie que la place n'est pas une allee ou une place cass�
				//Si la place n'est pas disponible alors on avance d'une case
				if(!(places[this.i][this.j].getDisponnible())){
					place = (Place)this.next();
				}
			}


			return place;
		}

		
		@Override
		public Object previous(int pas) {
			if(pas==0)pas=1;
			Place place=null;
			if(j==0 || j-pas<0){
				this.i--;
				this.j=nbCaseLargeur-1;
				place = places[this.i][this.j];
			}else{
				this.j=this.j-pas;
				place = places[this.i][this.j];

				//On v�rifie que la place n'est pas une allee ou une place cass�
				//Si la place n'est pas disponible alors on recule d'une case
				if(!(places[this.i][this.j].getDisponnible()) && !places[this.i][this.j].verifiersiPlaceCassee()){
					place = (Place)this.previous();
				}
			}
			return place;
		}

		
		@Override
		public Object up() {
			this.i--;
			return places[i][j];
		}

		
		@Override
		public Object down() {
			this.i++;
			return places[i][j];
		}

		
		@Override
		public Object actual() {
			Place place = null;
			try {
				place = salle.getPlaces()[i][j];
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			return place;
		}

		
		@Override
		public void reset() {
			this.i = this.firstI;
			this.j = this.firstJ;
		}

		
		@Override
		public int getCoordI() {
			return this.i;
		}

		
		@Override
		public int getCoordY() {
			return this.j;
		}
	}

}
