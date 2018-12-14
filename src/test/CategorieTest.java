package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.Categorie;
import modele.Etudiant;
import modele.Groupe;

// TODO: Auto-generated Javadoc
/**
 * The Class CategorieTest.
 */
public class CategorieTest {

	/** The e 1. */
	private Etudiant e1;

	/** The e 2. */
	private Etudiant e2;

	/** The e 3. */
	private Etudiant e3;

	/** The e 4. */
	private Etudiant e4;

	/** The a. */
	private Groupe g1;

	/** The b. */
	private Groupe g2;

	/** The gc. */
	private Groupe g3;

	/** The c 1. */
	private Categorie c1;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		e1=new Etudiant("Plaid","Justin");
		e2=new Etudiant("Muller","Geoffroy");
		e3=new Etudiant("Guezennec","Lucas");
		e4=new Etudiant("Biancalana","Théo");
		g1=new Groupe("A");
		g2=new Groupe("B");
		g3=new Groupe("C");
		g1.ajouterEtudiant(e1);
		g1.ajouterEtudiant(e2);
		g2.ajouterEtudiant(e3);
		g2.ajouterEtudiant(e4);
		g3.ajouterEtudiant(e1);
		c1=new Categorie("cat");
	}

	/**
	 * Test ajouter groupe.
	 */
	@Test
	public void testAjouterGroupe() {
		c1.ajouterGroupe(g1);
		c1.ajouterGroupe(g3);//Ajout d'un groupe qui est deja dedans.
		c1.ajouterGroupe(g2);
		assertEquals("On devrai avoir A en premier",g1,c1.getListegroupe().get(0));
		assertEquals("On devrai avoir B en deuxieme",g2,c1.getListegroupe().get(1));
	}

}
