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
