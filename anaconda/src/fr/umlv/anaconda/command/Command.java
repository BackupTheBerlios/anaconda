/*
 * Créé le 2 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

public interface Command {
	public void run();

	public void redo();

	public void undo();
}
