/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorPastingFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

public class Paste extends AbstractAction implements Command {
	private static boolean is_cut;
	private static ArrayList last_selection = new ArrayList();
	private static File dest_rep;
	private static File origin_rep;
	private static Delete deleter = new Delete();

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

		Paste.dest_rep = dest;
		Paste.origin_rep =
			((File) (PressPaper.getSelectedFiles().get(0))).getParentFile();
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
		if (!dest_rep.exists())
			throw new DoNotExistFileException(dest_rep);
		if (!dest_rep.isDirectory())
			throw new IsNotDirectoryException(dest_rep);
		if (!dest_rep.canWrite())
			throw new CanNotWriteException(dest_rep);

		File[] tab_file = dest_rep.listFiles();

		for (int i = 0; i < tab_file.length; i++) {
			for (Iterator j = last_selection.iterator(); j.hasNext();) {
				File tmp = (File) j.next();
				if (((tab_file[i].isDirectory() && tmp.isDirectory())
					|| (tab_file[i].isFile() && tmp.isFile()))
					&& tab_file[i].getName().compareTo(tmp.getName()) == 0) {
					if (is_cut)
						pasteFile(origin_rep, tab_file[i]);
					deleter.run(tab_file[i]);
				}
			}
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
		run(dest_rep);
	}

	public void actionPerformed(ActionEvent arg0) {
		ArrayList selected_file = Main.getSelectionItems();
		if (selected_file.size() < 1)
			//TODO cas ou on n a rien selectionne.
			;

		try {
			run(selected_file.get(0));
		} catch (IsNotDirectoryException e) {
			e.show();
		} catch (CanNotWriteException e) {
			e.show();
		} catch (CanNotReadException e) {
			e.show();
		} catch (DoNotExistFileException e) {
			e.show();
		} catch (ErrorPastingFileException e) {
			e.show();
		}
	}

}
