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

public class Copy implements Command {
	private final static boolean deleted = false;

	/**
	 * Make a copy of the selected files
	 * 
	 * @param selection
	 */
	public void run(Object o) {
		PressPaper.addToPressPaper((ArrayList) o, deleted);
	}

	/**
	 * Undoes the last command CopyPaste.
	 * 
	 * @throws DoNotExistFileException
	 * @throws IsNotDirectoryException
	 * @throws CanNotWriteException
	 * @throws CanNotReadException
	 * @throws CanNotDeleteException
	 */
	public void undo()
		throws
			DoNotExistFileException,
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException {
		
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
