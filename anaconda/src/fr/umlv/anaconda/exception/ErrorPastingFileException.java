/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;


public class ErrorPastingFileException extends Exception{	
	
	private File f;
	
	public ErrorPastingFileException(File file){
//		TODO cas d une I/O exception lors de copie.
	  this.f = file;
	}

	public void show() {
		JOptionPane.showMessageDialog(null, "Erreur de collage", f.getName() ,  JOptionPane.ERROR_MESSAGE);
	}

}
