
package fr.umlv.anaconda.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import junit.framework.TestCase;


public class CutTest extends TestCase {
	public void testCut() {	
		Properties p = System.getProperties();
		String home = p.getProperty("user.dir");
		String file_separator = p.getProperty("file.separator");
		ArrayList al = new ArrayList();
		File f1 = new File(new String(home + file_separator + "testcut1.txt"));
		File f2 = new File(new String(home + file_separator + "testcut2.txt"));
		File f3 = new File(new String(home + file_separator + "testcut3.txt"));
		File rep =
			new File(
				new String(home + file_separator + "tmpcut" + file_separator));
		rep.mkdir();
		Cut ct = new Cut();
		try {
			f1.createNewFile();
			f2.createNewFile();
			f3.createNewFile();

			al.add(f1);
			al.add(f2);
			al.add(f3);
			ct.run(al);

			Paste pt = new Paste();
			pt.run(rep);
			pt.undo();
			pt.redo();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}
}
