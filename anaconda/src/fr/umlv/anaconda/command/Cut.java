/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorPastingFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

public class Cut extends AbstractAction implements Command {
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

	
	public void actionPerformed(ActionEvent arg0) {
		run(Main.getSelectionItems());
	}
}
