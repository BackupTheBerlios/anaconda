/*
 * Cr�� le 2 f�vr. 2004
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.exception;

import javax.swing.JOptionPane;

/**
 * @author ofiguero
 *
 * Pour changer le mod�le de ce commentaire de type g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
public class NoSelectedFilesException extends Exception {
	
	public NoSelectedFilesException(){
		//TODO cas ou pas de files selectionnees
	}
	

	public void show() {
		JOptionPane.showMessageDialog(null, "Pas de s�lection",  "Une s�lection est necessaire pour cette action" ,  JOptionPane.WARNING_MESSAGE);
	}

}
