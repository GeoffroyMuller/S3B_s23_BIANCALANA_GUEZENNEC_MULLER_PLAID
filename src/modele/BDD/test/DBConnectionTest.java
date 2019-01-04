package modele.BDD.test;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.mysql.jdbc.Connection;

import modele.BDD.DBConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DBConnectionTest.
 */
class DBConnectionTest {

	/**
	 * Test get connection.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	void testGetConnection() throws SQLException {
		java.sql.Connection connect =(Connection) DBConnection.getConnection();
		java.sql.Connection connect1=(Connection) DBConnection.getConnection();
		Assert.assertEquals("la connection n'est pas la meme", connect, connect1);
	}

	/**
	 * Test set nom DB.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	void testSetNomDB() throws SQLException {
		java.sql.Connection connect =(Connection) DBConnection.getConnection();
		String a = DBConnection.getNomDB();
		DBConnection.setNomDB("test");
		java.sql.Connection connect1=(Connection) DBConnection.getConnection();
		String b = DBConnection.getNomDB();
		Assert.assertEquals("la connection n'est pas la meme", a, "etuplacement");
		Assert.assertEquals("la connection n'est pas la meme", b, "test");
	}

}
