/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;

public class ErrorIOFileException extends Exception {

	private File f;

	public ErrorIOFileException(File file) {
		this.f = file;
	}

	public void show() {
		JOptionPane.showMessageDialog(
			null,
			"Erreur d entree sortie",
			"Probleme d'entree/sortie sur <".concat( f.getName() ).concat(">."),
			JOptionPane.ERROR_MESSAGE);
	}

}
