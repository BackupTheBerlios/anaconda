/*
 * Créé le 2 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import junit.framework.TestCase;
import java.io.*;
import java.util.ArrayList;

import fr.umlv.anaconda.exception.NameConflictException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
/**
 * @author ofiguero
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class RenameTest extends TestCase {

	/**
	 * Constructor for RenameTest.
	 * @param arg0
	 */
	public RenameTest(String arg0) {
		super(arg0);

	}

	public void testRename(){
		File f = new File("c:/tmp/anaconda/fichier1.txt");
		ArrayList list = new ArrayList();
		list.add(f);
		PressPaper.addToPressPaper(list,false);
		Rename rename = new Rename();
		try {
			rename.run("fichier3.txt");
			rename.undo();
			rename.redo();
		} catch (NoSelectedFilesException e) {
			System.err.println("pas de fichiers selectionnes");
		} catch (NameConflictException e) {
			System.err.println("il existe deja un fichier avec ce nom ");
		}
	}

}
