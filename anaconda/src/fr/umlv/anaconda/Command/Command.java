/*
 * Créé le 2 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.Command;

import fr.umlv.anaconda.Exception.CanNotDeleteException;
import fr.umlv.anaconda.Exception.CanNotReadException;
import fr.umlv.anaconda.Exception.CanNotWriteException;
import fr.umlv.anaconda.Exception.DoNotExistFileException;
import fr.umlv.anaconda.Exception.ErrorPastingFileException;
import fr.umlv.anaconda.Exception.IsNotDirectoryException;


public interface Command {
	public void run(Object o)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorPastingFileException;

	public void redo()
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorPastingFileException;

	public void undo()
		throws
			DoNotExistFileException,
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException,
			ErrorPastingFileException;
}
