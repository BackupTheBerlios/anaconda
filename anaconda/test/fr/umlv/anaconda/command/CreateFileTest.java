
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.Properties;

import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.ErrorIOFileException;

import junit.framework.TestCase;

/**
 * @author FIGUEROA
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class CreateFileTest extends TestCase {

	public CreateFileTest(String arg0) {
		super(arg0);
	}

	public void testCreateFile() {
		Properties p = System.getProperties();
		String home = p.getProperty("user.dir");
		String file_separator = p.getProperty("file.separator");

		File folder =
			new File(new String(home + file_separator + "testcreatefile"));
		CreateFile creater = new CreateFile();
		try {
			creater.run(folder);
			creater.undo();
			creater.run(folder);
			creater.run(folder);
			creater.run(folder);
			creater.undo();
			creater.redo();
		} catch (CanNotWriteException e) {
			e.show();
		} catch (ErrorIOFileException e) {
			e.show();
		}

	}

}
