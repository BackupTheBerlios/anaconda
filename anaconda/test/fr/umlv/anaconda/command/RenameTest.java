
package fr.umlv.anaconda.command;

import junit.framework.TestCase;
import java.io.*;
import java.util.Properties;

import fr.umlv.anaconda.exception.CanNotWriteException;

/**
 * @author ofiguero
 * 
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class RenameTest extends TestCase {

	/**
	 * Constructor for RenameTest.
	 * 
	 * @param arg0
	 */
	public RenameTest(String arg0) {
		super(arg0);

	}

	public void testRename() {
		Properties p = System.getProperties();
		String home = p.getProperty("user.dir");
		String file_separator = p.getProperty("file.separator");
		File f = new File(new String(home+file_separator+"fichier1.txt"));
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Rename rename = new Rename();
		try {
			rename.run(f);
			rename.undo();
			rename.redo();
		} catch (CanNotWriteException e1) {
			e1.show();
		}
	}

}
