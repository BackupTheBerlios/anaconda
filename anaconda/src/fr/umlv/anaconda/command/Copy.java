/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorPastingFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

public class Copy extends AbstractAction  implements Command {
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

	
	public void actionPerformed(ActionEvent e) {
		run(Main.);
	}
}
