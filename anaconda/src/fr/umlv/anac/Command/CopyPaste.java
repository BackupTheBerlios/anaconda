/*
 * Created on 1 f�vr. 2004
 */
package fr.umlv.anac.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import fr.umlv.anac.Exception.CanNotDeleteException;
import fr.umlv.anac.Exception.CanNotReadException;
import fr.umlv.anac.Exception.CanNotWriteException;
import fr.umlv.anac.Exception.DoNotExistFileException;
import fr.umlv.anac.Exception.ErrorPastingFileException;
import fr.umlv.anac.Exception.IsNotDirectoryException;


public class CopyPaste extends Paste {
	private final static boolean deleted = false;
	private ArrayList selection;
	private File dest;

	/**
	 * Create a new copy/paste command.
	 * 
	 * @param selection
	 *            is the selection of files.
	 */
	public CopyPaste(ArrayList selection) {
		this.selection = new ArrayList();
		this.selection.addAll(selection);
		PressPaper.addToPressPaper(selection, deleted);
	}

	/**
	 * Makes a paste of the selected file.
	 */
	public void paste(File dest)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorPastingFileException {
		this.dest = dest;
		if (!PressPaper.isEmpty()){
			super.paste(dest);
			PressPaper.clear();
		}
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
		if (!dest.exists())
			throw new DoNotExistFileException(dest);
		if (!dest.isDirectory())
			throw new IsNotDirectoryException(dest);
		if (!dest.canWrite())
			throw new CanNotWriteException(dest);

		File[] tab_file = dest.listFiles();

		for (int i = 0; i < tab_file.length; i++) {
			File file = tab_file[i];
			if (!file.canRead())
				throw new CanNotReadException(file);
			for (Iterator j = selection.iterator(); j.hasNext();) {
				String file_name = ((File)j.next()).getName();
				if ( file.getName().compareTo(file_name) == 0 && !file.delete())
					throw new CanNotDeleteException(file);
			}

		}
	}
}
