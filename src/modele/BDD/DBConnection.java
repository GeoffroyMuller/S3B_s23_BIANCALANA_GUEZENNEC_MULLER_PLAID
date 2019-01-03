package modele.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class DBConnection.
 */
public class DBConnection {
	
	/** The connect. */
	public static Connection connect;

	/** The server name. */
	private static String serverName = "localhost";
	
	/** The user name. */
	private static String userName = "root";
	
	/** The password. */
	private static String password = "";
	
	/** The url DB. */
	private static String urlDB = "jdbc:mysql://" + serverName + ":";
	
	/** The port number. */
	private static String portNumber = "3306";
	
	/** The db name. */
	private static String dbName = "etuplacement";


	/**
	 * Instantiates a new DB connection.
	 *
	 * @throws SQLException the SQL exception
	 */
	private DBConnection() throws SQLException{
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		urlDB += portNumber + "/" + dbName;
		connect = DriverManager.getConnection(urlDB, connectionProps);
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	public static synchronized Connection getConnection() throws SQLException {
		if(connect==null) {
			new DBConnection();
		}
		return connect;
	}

	/**
	 * Sets the nom DB.
	 *
	 * @param nomDB the new nom DB
	 * @throws SQLException the SQL exception
	 */
	public static void setNomDB(String nomDB) throws SQLException {
		DBConnection.dbName=nomDB;
		getConnection();
	}

	/**
	 * Gets the nom DB.
	 *
	 * @return the nom DB
	 */
	public static String getNomDB() {
		return dbName;
	}

}
