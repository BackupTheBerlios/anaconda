/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;

public class IsNotDirectoryException extends Exception {

	private File f;

	public IsNotDirectoryException(File file) {
		this.f = file;
	}

	public void show() {
		JOptionPane.showMessageDialog(
			null,
			"Ce n'est pas un repertoire",
			"<".concat( f.getName() ).concat("> n'est pas un répertoire."),
			JOptionPane.WARNING_MESSAGE);
	}
}
