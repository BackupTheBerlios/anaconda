/*
 * Cr�� le 20 f�vr. 2004
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.exception;

import javax.swing.JOptionPane;

/**
 * @author ofiguero
 *
 * Pour changer le mod�le de ce commentaire de type g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */

public class ExternalException extends Exception {

	private String error_string;

	public ExternalException(String s) {
		error_string = s;
	}

	public void show() {
		JOptionPane.showMessageDialog(
			null,
			"Erreur Externe",
			error_string,
			JOptionPane.WARNING_MESSAGE);
	}

}


