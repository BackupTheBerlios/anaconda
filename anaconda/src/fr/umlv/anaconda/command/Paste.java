/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorPastingFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

public class Paste implements Command {
	private static boolean is_cut;
	private ArrayList last_selection = new ArrayList();
	private File dest;
	/**
	 * The 'paste' action. Calls the pasteFile method for each elements in
	 * 'selectedFiles'.
	 */
	public void run(Object o)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorPastingFileException {

		File dest = (File) o;

		if (!dest.exists())
			throw new DoNotExistFileException(dest);
		if (!dest.isDirectory())
			throw new IsNotDirectoryException(dest);
		if (!dest.canWrite())
			throw new CanNotWriteException(dest);

		this.dest = dest;
		last_selection.clear();
		last_selection.addAll(PressPaper.getSelectedFiles());
		is_cut = PressPaper.toDelete();

		for (Iterator it = PressPaper.getSelectedFiles().iterator();
			it.hasNext();
			) {
			File file = (File) it.next();
			if (!file.exists())
				throw new DoNotExistFileException(file);
			if (!file.canRead())
				throw new CanNotReadException(file);
			pasteFile(dest, file);
		}
	}

	/**
	 * The real 'paste' action. Creates copies of all the elements in
	 * 'selectedFiles' if the last action is 'copy'. Changes path of all the
	 * elements in 'selectedFiles' if the last action is 'cut'. Subdirectories
	 * are pasted to.
	 */
	private void pasteFile(File parent, File child)
		throws DoNotExistFileException, ErrorPastingFileException {
		File file = new File(parent, child.getName());

		if (child.isDirectory()) {
			file.mkdir();
			File[] list = child.listFiles();
			for (int i = 0; i < list.length; i++)
				pasteFile(file, list[i]);
			if (PressPaper.toDelete())
				child.delete();
		} else {
			if (PressPaper.toDelete())
				child.renameTo(file);
			else {
				FileInputStream fileInputStream;
				try {
					fileInputStream = new FileInputStream(child);
					FileOutputStream fileOutputStream =
						new FileOutputStream(file);
					byte[] buffer = new byte[1024];
					while (fileInputStream.available() > 0) {
						int readNumber = fileInputStream.read(buffer);
						if (readNumber > 0)
							fileOutputStream.write(buffer, 0, readNumber);
					}
				} catch (FileNotFoundException e) {
					throw new DoNotExistFileException(child);
				} catch (IOException e) {
					throw new ErrorPastingFileException(child);
				}
			}
		}
	}

	public void undo()
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException,
			DoNotExistFileException,
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

			File last_site =
				(File) last_selection.get(last_selection.indexOf(to_replace));

			if (is_cut) {
				pasteFile(last_site, to_replace);
				tab_file[i].delete(); //TODO revoir avec supprimer.
			} else
				tab_file[i].delete();
			//TODO revoir avec supprimer.

		}
	}

	public void redo()
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorPastingFileException {
		
		if (is_cut)
			 (new Cut()).run(last_selection);
		else
			 (new Copy()).run(last_selection);
		run(dest);
	}

}
