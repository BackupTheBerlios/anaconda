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

public class CutPaste extends Paste {
	private final static boolean deleted = true;
	private ArrayList selection;
	private File dest;

	/**
	 * Create a new cut/paste command.
	 * 
	 * @param selection
	 *            is the selection of files.
	 */
	public CutPaste(ArrayList selection) {
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
		if (!PressPaper.isEmpty()) {
			super.paste(dest);
			PressPaper.clear();
		}
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
			DoNotExistFileException,
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException,
			ErrorPastingFileException {
		if (!dest.exists())
			throw new DoNotExistFileException(dest);
		if (!dest.isDirectory())
			throw new IsNotDirectoryException(dest);
		if (!dest.canWrite())
			throw new CanNotWriteException(dest);

		File[] tab_file = dest.listFiles();

		for (int i = 0; i < tab_file.length; i++) {
			File to_replace = tab_file[i];
			
			if (!to_replace.canRead())
				throw new CanNotReadException(to_replace);
			for (Iterator j = selection.iterator(); j.hasNext();) {
				File file = ((File) j.next()).getParentFile();

				if (to_replace.getName().compareTo(file.getName()) == 0) {
					String origin_path = file.getPath();
					File origin_file = new File(origin_path);
					copyTo(origin_file,to_replace);
					if(!to_replace.delete())
						throw new CanNotDeleteException(to_replace);
				}

			}

		}
	}
}
