/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;


public class CanNotWriteException extends Exception{
	
	private File f;
	
	public CanNotWriteException(File file){
	//		TODO cas ou on n a pas les droits en ecriture du file.
	this.f = file;
	}

	public void show() {
		JOptionPane.showMessageDialog(null, "Ecriture impossible", f.getName() ,  JOptionPane.WARNING_MESSAGE);
	}
}
