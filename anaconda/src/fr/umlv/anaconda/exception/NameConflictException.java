/*
 * Created on 3 f�vr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.exception;

import javax.swing.JOptionPane;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NameConflictException extends Exception{
	
	public NameConflictException(){
	}

	public void show() {
		JOptionPane.showMessageDialog(null, "Conflit de noms",  "Un probl�me de nommage est apparu" ,  JOptionPane.WARNING_MESSAGE);
	}

}
