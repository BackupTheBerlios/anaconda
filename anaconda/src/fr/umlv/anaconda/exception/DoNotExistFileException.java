/*
 * Created on 1 f�vr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;


public class DoNotExistFileException extends Exception{
	
	private File f;
	
	public DoNotExistFileException(File file){
//		TODO cas ou on le file n existe pas.
       this.f = file;
	}

	public void show() {
		JOptionPane.showMessageDialog(null, "Fichier inexistant", f.getName() ,  JOptionPane.WARNING_MESSAGE);
	}
}
