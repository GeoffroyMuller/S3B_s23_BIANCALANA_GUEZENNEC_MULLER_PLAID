package modele.BDD.test;

//import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.Assert;
/*import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;*/

import modele.BDD.Salle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

class SalleTest {

	@Before
	void setUp() throws Exception {
		Salle.createTable();
	}

	@After
	void tearDown() throws Exception {
		Salle.deleteTable();
	}

	@Test
	void testSalle() {
		Salle s = new Salle("salle",10,5);
		Assert.assertEquals("l'objet doit etre -1",s.getIdSalle(), -1);
		Assert.assertEquals("l'objet doit etre salle",s.getNom(), "salle");
	}

	@Test
	void testFindById() throws SQLException {
		Salle s = new Salle("salle",10,5);
		s.save();
		Assert.assertEquals("l'objet doit etre salle",Salle.findById(1).getNom(), "salle");
	}

	@Test
	void testFindByNom() throws SQLException {
		Salle s = new Salle("salle",10,5);
		s.save();
		Assert.assertEquals("l'objet doit etre salle",Salle.findByNom("salle").get(0).getNom(), "salle");
	}

	@Test
	void testListSalle() throws SQLException {
		Salle s = new Salle("salle",10,5);
		Salle s1 = new Salle("salle1",5,10);
		s.save();
		s1.save();
		Assert.assertEquals("l'objet doit etre salle",Salle.listSalle().get(0).getNom(), "salle");
		Assert.assertEquals("l'objet doit etre salle",Salle.listSalle().get(1).getNom(), "salle1");
	}

	@Test
	void testDelete() {
		Salle s = new Salle("salle",10,5);
		s.save();
		Assert.assertEquals("l'objet doit etre 1",s.getIdSalle(), 1);
		s.delete();
		Assert.assertEquals("l'objet doit etre 1",s.getIdSalle(), -1);
	}

	@Test
	void testSave() throws SQLException {
		Salle s = new Salle("salle",10,5);
		Assert.assertEquals("l'objet doit etre -1",s.getIdSalle(), -1);
		s.save();
		Assert.assertEquals("l'objet doit etre salle",Salle.findById(1).getNom(), "salle");
		Assert.assertEquals("l'objet doit etre 1",s.getIdSalle(), 1);
		s.setNom("salleModifier");
		s.save();
		Assert.assertEquals("l'objet doit etre salleModifier",Salle.findById(1).getNom(), "salleModifier");
	}

}
