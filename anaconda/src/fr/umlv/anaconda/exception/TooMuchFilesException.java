/*
 * Created on 4 f�vr. 2004
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
public class TooMuchFilesException extends Exception{
	
	public TooMuchFilesException() {
		super();
	}
	
	public void show() {
		JOptionPane.showMessageDialog(null, "Trop d'�l�ments",  "Trop d'�l�ments sont s�lectionn�s, cette op�ration est unitaire." ,  JOptionPane.WARNING_MESSAGE);
	}

}
