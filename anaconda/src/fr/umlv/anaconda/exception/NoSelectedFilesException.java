/*
 * Créé le 2 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.exception;

import javax.swing.JOptionPane;

/**
 * @author ofiguero
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class NoSelectedFilesException extends Exception {
	
	public NoSelectedFilesException(){
		//TODO cas ou pas de files selectionnees
	}
	

	public void show() {
		JOptionPane.showMessageDialog(null, "Pas de sélection",  "Une sélection est necessaire pour cette action" ,  JOptionPane.WARNING_MESSAGE);
	}

}
