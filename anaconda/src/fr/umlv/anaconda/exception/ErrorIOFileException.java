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
			"Erreur d entree sortie sur ",
			f.getName(),
			JOptionPane.ERROR_MESSAGE);
	}

}
