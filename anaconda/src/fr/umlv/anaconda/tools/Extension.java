/*
 * Cr�� le 1 mars 2004
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.tools;

import java.util.ArrayList;

/**
 * @author ntesevic
 *
 * Pour changer le mod�le de ce commentaire de type g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
public class Extension {

	private static ArrayList imagesExt = new ArrayList();
	static {
		imagesExt.add(".jpg");
		imagesExt.add(".jpeg");
		imagesExt.add(".gif");
		imagesExt.add(".bmp");
		imagesExt.add(".png");
	}

	public static boolean isImage(String file_name) {
		int extIndex = file_name.lastIndexOf('.');
		if (extIndex != -1) {
			if (imagesExt.contains(file_name.substring(extIndex)))
				return true;

		}
		return false;
		 }

}
