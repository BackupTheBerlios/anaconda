/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.Command;

import java.util.ArrayList;
import fr.umlv.anaconda.Exception.CanNotDeleteException;
import fr.umlv.anaconda.Exception.CanNotReadException;
import fr.umlv.anaconda.Exception.CanNotWriteException;
import fr.umlv.anaconda.Exception.DoNotExistFileException;
import fr.umlv.anaconda.Exception.ErrorPastingFileException;
import fr.umlv.anaconda.Exception.IsNotDirectoryException;

public class Cut implements Command {
	private final static boolean deleted = true;

	/**
	 * Create a new cut/paste command.
	 * 
	 * @param selection
	 *            is the selection of files.
	 */
	public void run(Object o) {

		PressPaper.addToPressPaper((ArrayList) o, deleted);
	}

	/**
	 * Undoes the last command CutPaste.
	 * 
	 * @throws DoNotExistFileException
	 * @throws IsNotDirectoryException
	 * @throws CanNotWriteException
	 * @throws CanNotReadException
	 * @throws CanNotDeleteException
	 * @throws ErrorPastingFileException
	 */
	public void undo()
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException,
			DoNotExistFileException,
			ErrorPastingFileException {
	}

	public void redo()
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorPastingFileException {
	}
}
