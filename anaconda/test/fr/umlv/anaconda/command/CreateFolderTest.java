/*
 * Created on 3 févr. 2004
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.Properties;

import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotWriteException;

import junit.framework.TestCase;

/**
 * @author FIGUEROA
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class CreateFolderTest extends TestCase {

	public CreateFolderTest() {

	}

	public void testCreateFolder() {
		Properties p = System.getProperties();
		String home = p.getProperty("user.dir");
		String file_separator = p.getProperty("file.separator");
		File folder =
			new File(
				new String(
					home
						+ file_separator
						+ "testcreatefolder"
						+ file_separator));
		CreateFolder creater = new CreateFolder();
		try {
			creater.run(folder);
			creater.undo();
			creater.run(folder);
			creater.run(folder);
			creater.run(folder);
			creater.undo();
		} catch (CanNotWriteException e) {
			e.show();
		} catch (CanNotDeleteException e) {
			e.show();
		}

	}

}
