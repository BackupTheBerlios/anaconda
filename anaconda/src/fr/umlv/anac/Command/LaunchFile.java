/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anac.Command;

import fr.umlv.anac.Exception.CanNotDeleteException;
import fr.umlv.anac.Exception.CanNotReadException;
import fr.umlv.anac.Exception.CanNotWriteException;
import fr.umlv.anac.Exception.DoNotExistFileException;
import fr.umlv.anac.Exception.ErrorPastingFileException;
import fr.umlv.anac.Exception.IsNotDirectoryException;




public class LaunchFile implements Command{

	/* (non-Javadoc)
	 * @see fr.umlv.anac.Command.Command#run(java.lang.Object)
	 */
	public void run(Object o) throws IsNotDirectoryException, CanNotWriteException, CanNotReadException, DoNotExistFileException, ErrorPastingFileException {
		// TODO Raccord de méthode auto-généré
		
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anac.Command.Command#undo()
	 */
	public void undo() throws DoNotExistFileException, IsNotDirectoryException, CanNotWriteException, CanNotReadException, CanNotDeleteException, ErrorPastingFileException {
		// TODO Raccord de méthode auto-généré
		
	}
	
}
