/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.awt.HeadlessException;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.*;

public class Paste extends Thread implements Command {
	private static boolean is_cut;
	private static ArrayList last_selection = new ArrayList();
	private static File dest_rep;
	private static File origin_rep;
	private static Delete deleter = new Delete();

	/**
	 * The 'paste' action. Calls the pasteFile method for each elements in
	 * 'selectedFiles'.
	 */
	public void run() {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		if (selected_file.size() > 1) {
			(new TooMuchFilesException()).show();
			return;
		}

		File dest = (File) selected_file.get(0);
		if( !dest.isDirectory() )
			dest = dest.getParentFile();

		if (!dest.exists()){
			 (new DoNotExistFileException(dest)).show();
			return;
		}
		if (!dest.isDirectory()){	
			 (new IsNotDirectoryException(dest)).show();
			 return;
		}
		if (!dest.canWrite()){
			 (new CanNotWriteException(dest)).show();
			return;
		}

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
				 (new DoNotExistFileException(file)).show();
			if (!file.canRead())
				 (new CanNotReadException(file)).show();

			try {
				pasteFile(dest, file);
			} catch (DoNotExistFileException e) {
				e.show();
			} catch (ErrorIOFileException e) {
				e.show();
			}

		}
	}

	public void run(File dest) {
		if (!dest.exists()){
			 (new DoNotExistFileException(dest)).show();
			 return;
		}
		if (!dest.isDirectory()){
			 (new IsNotDirectoryException(dest)).show();
			 return;
		}
		if (!dest.canWrite()){
			 (new CanNotWriteException(dest)).show();
			 return;
		}

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
				 (new DoNotExistFileException(file)).show();
			if (!file.canRead())
				 (new CanNotReadException(file)).show();

			try {
				pasteFile(dest, file);
			} catch (DoNotExistFileException e) {
				e.show();
			} catch (ErrorIOFileException e) {
				e.show();
			}

		}
	}

	/**
	 * The real 'paste' action. Creates copies of all the elements in
	 * 'selectedFiles' if the last action is 'copy'. Changes path of all the
	 * elements in 'selectedFiles' if the last action is 'cut'. Subdirectories
	 * are pasted to.
	 */
	private void pasteFile(File parent, File child)
		throws DoNotExistFileException, ErrorIOFileException {
		File file = new File(parent, child.getName());
		int option = JOptionPane.YES_OPTION;

		if (Tools.contains(parent, child)) {
			try {
				option =
					JOptionPane.showConfirmDialog(
						null,
						child.getName()
							+ " existe deja, voulez vous l'ecraser ?",
						"Conflit",
						JOptionPane.YES_NO_OPTION);
			} catch (HeadlessException e) {
				option = JOptionPane.NO_OPTION;
			}
		}

		if (option == JOptionPane.YES_OPTION) {
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
						throw new ErrorIOFileException(child);
					}
				}
			}
		}
	}

	public void undo() {
		if (!dest_rep.exists()){
			 (new DoNotExistFileException(dest_rep)).show();
			return;
		}
		if (!dest_rep.isDirectory()){
			 (new IsNotDirectoryException(dest_rep)).show();
			 return;
		}
		if (!dest_rep.canWrite()){
			 (new CanNotWriteException(dest_rep)).show();
			return;
		}
		
		File[] tab_file = dest_rep.listFiles();

		for (int i = 0; i < tab_file.length; i++) {
			for (Iterator j = last_selection.iterator(); j.hasNext();) {
				File tmp = (File) j.next();
				if (((tab_file[i].isDirectory() && tmp.isDirectory())
					|| (tab_file[i].isFile() && tmp.isFile()))
					&& tab_file[i].getName().compareTo(tmp.getName()) == 0) {
					if (is_cut)
						try {
							pasteFile(origin_rep, tab_file[i]);
						} catch (DoNotExistFileException e) {
							e.show();
						} catch (ErrorIOFileException e) {
							e.show();
						}
					deleter.run(tab_file[i]);
				}
			}
		}
	}

	public void redo() {

		if (is_cut)
			 (new Cut()).run(last_selection);
		else
			 (new Copy()).run(last_selection);
		run(dest_rep);
	}

}
