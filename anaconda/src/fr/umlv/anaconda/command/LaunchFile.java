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
import fr.umlv.anaconda.exception.ErrorIOFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

public class LaunchFile extends AbstractAction implements Command {

	public void run(Object o)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorIOFileException {
		//TODO launch file.
	}

	public void undo()
		throws
			DoNotExistFileException,
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException,
			ErrorIOFileException {
		//TODO undo launch file.
	}

	public void redo()
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorIOFileException {
		//TODO redo launch file.
	}

	public void actionPerformed(ActionEvent arg0) {
		ArrayList selected_file = Main.getSelectionItems();
		if (selected_file.size() < 1)
			//TODO cas ou on n a rien selectionne.
			;

		//run(selected_file.get(0));

	}

}
