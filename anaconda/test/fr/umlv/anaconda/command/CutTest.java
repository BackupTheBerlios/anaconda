/*
 * Created on 3 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorPastingFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

import junit.framework.TestCase;

/**
 */
public class CutTest extends TestCase {
	public void testCut() {
		Cut ct = new Cut();
		ArrayList al = new ArrayList();
		File f1 = new File("C:\\testcut1.txt");
		File f2 = new File("C:\\testcut2.txt");
		File f3 = new File("C:\\testcut3.txt");
		String rep_path = new String("C:\\tmp\\");
		
		try {
			f1.createNewFile();
			f2.createNewFile();
			f3.createNewFile();

			al.add(f1);
			al.add(f2);
			al.add(f3);
			ct.run(al);
			
			Paste pt = new Paste();
			pt.run(new File(rep_path));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (IsNotDirectoryException e) {
			System.out.println("mauvais nom de repertoire.");
		} catch (CanNotWriteException e) {
			System.out.println("pas de droit d ecriture.");
		} catch (CanNotReadException e) {
			System.out.println("pas de droit de lecture.");
		} catch (DoNotExistFileException e) {
			System.out.println("repertoire inexistant.");
		} catch (ErrorPastingFileException e) {
			System.out.println("erreur de copie.");
		}
	}
}
