/*
 * Cr�� le 3 f�vr. 2004
 * 
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import junit.framework.TestCase;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
/**
 * @author ofiguero
 * 
 * Pour changer le mod�le de ce commentaire de type g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
public class DeleteTest extends TestCase {

	/**
	 * Constructor for DeleteTest.
	 * 
	 * @param arg0
	 */
	public DeleteTest(String arg0) {
		super(arg0);
	}

	public void testDelete() {
		Properties p = System.getProperties();
		String home = p.getProperty("user.dir");
		String file_separator = p.getProperty("file.separator");
		File file = new File(new String(home+file_separator+"testdelete.txt"));
		try {
			file.createNewFile();
			Delete delete = new Delete();
			delete.run(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
