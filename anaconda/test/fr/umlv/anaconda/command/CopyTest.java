/*
 * Créé le 2 févr. 2004
 * 
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorPastingFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

import junit.framework.TestCase;

/**
 * @author jyam
 * 
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CopyTest extends TestCase {

	/**
	 * Constructor for CopyTest.
	 * 
	 * @param arg0
	 */
	public CopyTest(String arg0) {
		super(arg0);
	}

	public void testCopy() {
		Properties p = System.getProperties();
		String home = p.getProperty("user.dir");
		String file_separator = p.getProperty("file.separator");
		ArrayList al = new ArrayList();
		File f1 = new File(new String(home + file_separator + "testcopy1.txt"));
		File f2 = new File(new String(home + file_separator + "testcopy2.txt"));
		File f3 = new File(new String(home + file_separator + "testcopy3.txt"));
		File rep =
			new File(
				new String(home + file_separator + "tmpcopy" + file_separator));
		rep.mkdir();
		Copy cp = new Copy();
		try {
			f1.createNewFile();
			f2.createNewFile();
			f3.createNewFile();

			al.add(f1);
			al.add(f2);
			al.add(f3);

			cp.run(al);
			Paste pt = new Paste();

			pt.run(rep);
			pt.undo();
			pt.redo();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (IsNotDirectoryException e) {
			e.show();
			System.out.println("mauvais nom de repertoire.");
		} catch (CanNotWriteException e) {
			e.show();
			System.out.println("pas de droit d ecriture.");
		} catch (CanNotReadException e) {
			e.show();
			System.out.println("pas de droit de lecture.");
		} catch (DoNotExistFileException e) {
			e.show();
			System.out.println("repertoire inexistant.");
		} catch (ErrorPastingFileException e) {
			e.show();
			System.out.println("erreur de copie.");
		} catch (CanNotDeleteException e) {
			e.show();
			System.out.println("ne peut supprimer");
		}
	}
}
