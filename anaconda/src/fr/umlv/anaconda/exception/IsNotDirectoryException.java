/*
 * Created on 1 f�vr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;


public class IsNotDirectoryException extends Exception{
	
	private File f;
	
	public IsNotDirectoryException(File file){
//		TODO cas ou file n est pas un repertoire
	  this.f = file;
	}

	public void show() {
		JOptionPane.showMessageDialog(null, "Ce n'est pas un repertoire", f.getName() ,  JOptionPane.WARNING_MESSAGE);
	}
}