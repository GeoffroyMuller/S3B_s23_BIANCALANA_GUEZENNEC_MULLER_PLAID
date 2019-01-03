package modele.BDD.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modele.BDD.Particularite;

public class ParticulariteTest {

	@Before
	public void setUp() throws Exception {
		Particularite.createTable();
		Particularite p1 = new Particularite("p1", 0);
		Particularite p2 = new Particularite("p2", 1);
		Particularite p3 = new Particularite("p3", 0);
		Particularite p4 = new Particularite("p4", 1);
		p1.save();
		p2.save();
		p3.save();
		p4.save();
	}

	@After
	public void tearDown() throws Exception {
		Particularite.deleteTable();
	}

	@Test
	public void testParticularite() {
		Particularite p = new Particularite(null, 0);
		Assert.assertEquals("l'objet doit etre -1", p.getIdParticularite(), -1);
		
		Particularite p1 = new Particularite(null, 7);
		Assert.assertEquals("l'objet doit etre 0", p.getPrendreEnComptePlacement(), 0);
	}

	@Test
	public void testFindById() throws SQLException {
		Particularite pres = Particularite.findById(1);
		Assert.assertEquals("l'objet doit etre p1", pres.getNom(), "p1");
		Particularite pres1 = Particularite.findById(3);
		Assert.assertEquals("l'objet doit etre p1", pres1.getNom(), "p3");
	}


	@Test
	public void testSave() throws SQLException {
		Particularite pres = Particularite.findById(1);
		pres.setNom("test");
		pres.save();
		Assert.assertEquals("l'objet doit etre test", pres.getNom(), "test");
		Particularite pres1 = Particularite.findById(1);
		Assert.assertEquals("l'objet doit etre test", pres1.getNom(), "test");
	}

}
