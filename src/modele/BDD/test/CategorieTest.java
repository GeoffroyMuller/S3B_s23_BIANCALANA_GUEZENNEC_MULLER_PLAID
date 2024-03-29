package modele.BDD.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modele.BDD.Categorie;
import modele.BDD.Particularite;

// TODO: Auto-generated Javadoc
/**
 * The Class CategorieTest.
 */
public class CategorieTest {

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		Categorie.createTable();
		Categorie c1 = new Categorie("c1");
		Categorie c2 = new Categorie("c2");
		Categorie c3 = new Categorie("c3");
		c1.save();
		c2.save();
		c3.save();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
		Categorie.deleteTable();
	}

	/**
	 * Test categorie.
	 */
	@Test
	public void testCategorie() {
		Categorie c = new Categorie("c");
		Assert.assertEquals("l'objet doit etre -1", c.getIdCategorie(), -1);
	}

	/**
	 * Test find by id.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void testFindById() throws SQLException {
		Categorie cres = Categorie.findById(1);
		Assert.assertEquals("l'objet doit etre c1", cres.getNom(), "c1");
		Categorie cres1 = Categorie.findById(3);
		Assert.assertEquals("l'objet doit etre c3", cres1.getNom(), "c3");
	}

	/**
	 * Test delete.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test save.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void testSave() throws SQLException {
		Categorie cres = Categorie.findById(1);
		cres.setNom("test");
		cres.save();
		Assert.assertEquals("l'objet doit etre test", cres.getNom(), "test");
		Categorie cres1 = Categorie.findById(1);
		Assert.assertEquals("l'objet doit etre test", cres1.getNom(), "test");
	}

}
