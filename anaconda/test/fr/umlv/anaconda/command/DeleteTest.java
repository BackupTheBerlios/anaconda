/*
 * Cr�� le 3 f�vr. 2004
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import junit.framework.TestCase;
import java.io.File;
/**
 * @author ofiguero
 *
 * Pour changer le mod�le de ce commentaire de type g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
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
		File file = new File("/home/main01/ofiguero/tmp/coucou/");
		Delete delete = new Delete();
		delete.run(file);
	}
}
