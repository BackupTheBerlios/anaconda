/*
 * Créé le 3 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import junit.framework.TestCase;
import java.io.File;
import java.io.IOException;
/**
 * @author ofiguero
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class DeleteTest extends TestCase {

	/**
	 * Constructor for DeleteTest.
	 * @param arg0
	 */
	public DeleteTest(String arg0) {
		super(arg0);
	}
	
	public void testDelete(){
		File file = new File("C:\\tmp\\poeut.txt");
		try {
			file.createNewFile();
			Delete delete = new Delete();
			delete.run(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
