
package fr.umlv.anaconda.exception;

import javax.swing.JOptionPane;

/**
 * @author ofiguero
 *
 */
public class NoSelectedFilesException extends Exception {
	
	public NoSelectedFilesException(){
	}
	

	public void show() {
		JOptionPane.showMessageDialog(null, "Pas de s?lection",  "Une s?lection est necessaire pour cette action" ,  JOptionPane.WARNING_MESSAGE);
	}

}
