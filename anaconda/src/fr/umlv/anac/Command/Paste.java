/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anac.Command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import fr.umlv.anac.Exception.CanNotDeleteException;
import fr.umlv.anac.Exception.CanNotReadException;
import fr.umlv.anac.Exception.CanNotWriteException;
import fr.umlv.anac.Exception.DoNotExistFileException;
import fr.umlv.anac.Exception.ErrorPastingFileException;
import fr.umlv.anac.Exception.IsNotDirectoryException;

public class Paste implements Command {

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
			else
				copyTo(file, child);
		}
	}

	/**
	 * Copies the file "to_read" to the file named "file".
	 * 
	 * @param to
	 *            is the destination.
	 * @param from
	 *            is the origin.
	 * @throws DoNotExistFileException
	 * @throws ErrorPastingFileException
	 */
	public static void copyTo(File to, File from)
		throws DoNotExistFileException, ErrorPastingFileException {
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(from);
			FileOutputStream fileOutputStream = new FileOutputStream(to);
			byte[] buffer = new byte[1024];
			while (fileInputStream.available() > 0) {
				int readNumber = fileInputStream.read(buffer);
				if (readNumber > 0)
					fileOutputStream.write(buffer, 0, readNumber);
			}
		} catch (FileNotFoundException e) {
			throw new DoNotExistFileException(from);
		} catch (IOException e) {
			throw new ErrorPastingFileException(from);
		}
	}

	public void undo()
		throws
			DoNotExistFileException,
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException {

	}

}
