/*
 * Created on 1 f�vr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;

public class CanNotWriteException extends Exception {

	private File f;

	public CanNotWriteException(File file) {
		this.f = file;
	}

	public void show() {
		JOptionPane.showMessageDialog(
			null,
			"Ecriture impossible",
			"Impossible d'�crire <".concat( f.getName() ).concat(">."),
			JOptionPane.WARNING_MESSAGE);
	}
}
