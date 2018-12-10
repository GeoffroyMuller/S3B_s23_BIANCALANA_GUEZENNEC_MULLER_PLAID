package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modele.Etudiant;
import modele.Groupe;

/**
 * The Class GroupeTest.
 */
public class GroupeTest {
	
	/** The e 1. */
	private Etudiant e1;
	
	/** The e 2. */
	private Etudiant e2;
	
	/** The e 3. */
	private Etudiant e3;
	
	/** The e 4. */
	private Etudiant e4;
	
	/** The e 5. */
	private Etudiant e5;
	
	/** The a. */
	private Groupe a;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		e1=new Etudiant("Plaid","Justin","A");
		e2=new Etudiant("Muller","Geoffroy","B");
		e3=new Etudiant("Guezennec","Lucas","A");
		e4=new Etudiant("Biancalana","Théo","A");
		a=new Groupe("A");
	}

	/**
	 * Test groupe string.
	 */
	@Test
	public void testGroupeString() {
		assertEquals("On devrai avoir le nom A","A",a.getGroupe());
	}

	/**
	 * Test ajouter etudiant.
	 */
	@Test
	public void testAjouterEtudiant() {
		a.ajouterEtudiant(e2);
		a.ajouterEtudiant(e1);
		assertEquals("On devrai avoir l'etudiant e1 seulement ajouter",e1,this.a.getListeEtudiants().get(0));
	}

	/**
	 * Test supprimmer etudiant.
	 */
	@Test
	public void testSupprimmerEtudiant() {
		a.ajouterEtudiant(e3);
		a.supprimmerEtudiant(e1);
		a.supprimmerEtudiant(e2);
		assertEquals("On devrai avoir supprimer e1",e3,this.a.getListeEtudiants().get(0));
	}

	/**
	 * Test trie par nom.
	 */
	@Test
	public void testTrieParNom() {
		a.ajouterEtudiant(e3);
		a.ajouterEtudiant(e4);
		a.ajouterEtudiant(e1);
		a.trieParNom();
		assertEquals("On devrai avoir e4 en premier",e4,this.a.getListeEtudiants().get(0));
	}

	/**
	 * Test trie par prenom.
	 */
	@Test
	public void testTrieParPrenom() {
		a.ajouterEtudiant(e3);
		a.ajouterEtudiant(e4);
		a.ajouterEtudiant(e1);
		a.trieParPrenom();
		assertEquals("On devrai avoir e1 en premier",e1,this.a.getListeEtudiants().get(0));
	}

}

