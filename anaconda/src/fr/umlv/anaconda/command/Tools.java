/*
 * Créé le 5 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import java.io.File;
/**
 * @author ofiguero
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
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
