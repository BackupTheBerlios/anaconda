/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;

public class CanNotReadException extends Exception {
	private File f;

	public CanNotReadException(File file) {
		f = file;
	}

	public void show() {
		JOptionPane.showMessageDialog(
			null,
			"Lecture impossible",
			"Impossible de lire <".concat( f.getName() ).concat(">."),
			JOptionPane.WARNING_MESSAGE);
	}
}
