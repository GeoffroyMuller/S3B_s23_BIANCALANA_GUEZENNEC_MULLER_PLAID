package modele.BDD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



/**
 * la Class DBConnection.
 */
public class DBConnection {
	
	/** connect. */
	public static Connection connect;

	/** le server name. */
	private static String serverName = "localhost";
	
	/** l' user name. */
	private static String userName = "root";
	
	/** le password. */
	private static String password = "";
	
	/** l' url DB. */
	private static String urlDB = "jdbc:mysql://" + serverName + ":";
	
	/** le port number. */
	private static String portNumber = "3306";
	
	/** la db name. */
	private static String dbName = "etuplacement";


	/**
	 * Instantiates une nouvelle DB connection.
	 */
	private DBConnection(){
		try{
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			urlDB += portNumber + "/" + dbName;
			connect = DriverManager.getConnection(urlDB, connectionProps);
		}catch(SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "La base de données n'est pas accessible.\n Celle-ci est peut-être hors-ligne ou non démarrer.", "Erreur critique", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Gets la connection.
	 *
	 * @return la connection
	 * @throws SQLException  SQL exception
	 */
	public static synchronized Connection getConnection() throws SQLException {
		if(connect==null) {
			new DBConnection();
		}
		return connect;
	}

	/**
	 * Sets le nom DB.
	 *
	 * @param nomDB le nouveaux nom DB
	 * @throws SQLException SQL exception
	 */
	public static void setNomDB(String nomDB) throws SQLException {
		DBConnection.dbName=nomDB;
		getConnection();
	}

	/**
	 * Gets le nom DB.
	 *
	 * @return le nom DB
	 */
	public static String getNomDB() {
		return dbName;
	}

}
