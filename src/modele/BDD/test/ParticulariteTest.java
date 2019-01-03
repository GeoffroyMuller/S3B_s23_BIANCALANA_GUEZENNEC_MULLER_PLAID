package modele.BDD.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modele.BDD.Particularite;

public class ParticulariteTest {

	@Before
	public void setUp() throws Exception {
		Particularite.createTable();
	}

	@After
	public void tearDown() throws Exception {
		Particularite.deleteTable();
	}

	@Test
	public void testParticularite() {
		Particularite p = new Particularite(null, false);
		Assert.assertEquals("l'objet doit etre -1", p.getIdParticularite(), -1);
	}

	@Test
	public void testCreateTable() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteTable() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

}
