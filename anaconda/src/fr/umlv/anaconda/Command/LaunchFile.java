/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.Command;

import fr.umlv.anaconda.Exception.CanNotDeleteException;
import fr.umlv.anaconda.Exception.CanNotReadException;
import fr.umlv.anaconda.Exception.CanNotWriteException;
import fr.umlv.anaconda.Exception.DoNotExistFileException;
import fr.umlv.anaconda.Exception.ErrorPastingFileException;
import fr.umlv.anaconda.Exception.IsNotDirectoryException;

public class LaunchFile implements Command {

	public void run(Object o)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorPastingFileException {
		
	}

	public void undo()
		throws
			DoNotExistFileException,
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException,
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
