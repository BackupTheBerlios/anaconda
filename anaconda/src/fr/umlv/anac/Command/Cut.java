/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anac.Command;

import java.util.ArrayList;
import fr.umlv.anac.Exception.CanNotDeleteException;
import fr.umlv.anac.Exception.CanNotReadException;
import fr.umlv.anac.Exception.CanNotWriteException;
import fr.umlv.anac.Exception.DoNotExistFileException;
import fr.umlv.anac.Exception.ErrorPastingFileException;
import fr.umlv.anac.Exception.IsNotDirectoryException;

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
