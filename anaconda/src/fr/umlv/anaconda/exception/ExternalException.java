/*
 * Créé le 20 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.exception;

import javax.swing.JOptionPane;

/**
 * @author ofiguero
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
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


