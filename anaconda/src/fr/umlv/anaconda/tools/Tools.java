/*
 * Cr?? le 5 f?vr. 2004
 *
 * Pour changer le mod?le de ce fichier g?n?r?, allez ? :
 * Fen?tre&gt;Pr?f?rences&gt;Java&gt;G?n?ration de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.tools;

import java.io.File;
/**
 * @author ofiguero
 *
 * Pour changer le mod?le de ce commentaire de type g?n?r?, allez ? :
 * Fen?tre&gt;Pr?f?rences&gt;Java&gt;G?n?ration de code&gt;Code et commentaires
 */
public class Tools {

	public static boolean contains(File parent, File file) {
		File[] children = parent.listFiles();
		for (int i = 0; i < children.length; i++) {
			if (((children[i].isDirectory() && file.isDirectory())
				|| (children[i].isFile() && file.isFile()))
				&& (file.getName().compareTo(children[i].getName())) == 0) {
				return true;
			}
		}
		return false;
	}
}
