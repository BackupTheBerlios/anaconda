/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.exception;

import java.io.File;
import javax.swing.JOptionPane;


public class CanNotDeleteException extends Exception{
	
	private File f;
	
	public CanNotDeleteException(File file){
		//TODO cas ou file n a pu etre supprime.
	   this.f = file;
	}
	
	public void show() {
		JOptionPane.showMessageDialog(null, "Suppression impossible", f.getName() ,  JOptionPane.WARNING_MESSAGE);
	}

}
