
package fr.umlv.anaconda.command;

import junit.framework.TestCase;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import fr.umlv.anaconda.exception.CanNotDeleteException;
/**
 * @author ofiguero
 * 
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
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
		} catch (CanNotDeleteException e) {
			e.show();
		}
	}
}
