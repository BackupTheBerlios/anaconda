/*
 * Created on 3 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorIOFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.exception.TooMuchFilesException;

/**
 */
public class ShowProperties implements Command {
	private boolean can_read;
	private boolean can_write;
	private boolean can_execute;
	private boolean can_delete;
	private boolean is_hidden;
	private long size;
	private long last_modified;
	private String name;
	private boolean is_directory;
	private static SecurityManager sm = new SecurityManager();

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

		File f = (File) selected_file.get(0);
		String file_path = f.getPath();

		try {
			sm.checkDelete(file_path);
			can_delete = true;
		} catch (SecurityException e) {
			can_delete = false;
		}

		can_read = f.canRead();
		can_write = f.canWrite();
		is_directory = f.isDirectory();
		is_hidden = f.isHidden();
		size = f.length();
		last_modified = f.lastModified();

		System.out.println(
			"read \t\t\t"
				+ can_read
				+ "\nwrite \t\t\t"
				+ can_write
				+ "\ndelete \t\t\t"
				+ can_delete
				+ "\nis directory \t"
				+ is_directory
				+ "\nis hidden \t\t"
				+ is_hidden
				+ "\nlength \t\t\t"
				+ size
				+ "\nlast modified \t"
				+ new Date(last_modified));

	}

	public void redo() {
	}

	public void undo() {
	}

	public void run(File selected_file) {

		File f = selected_file;
		String file_path = f.getPath();

		try {
			sm.checkDelete(file_path);
			can_delete = true;
		} catch (SecurityException e) {
			can_delete = false;
		}

		can_read = f.canRead();
		can_write = f.canWrite();
		is_directory = f.isDirectory();
		is_hidden = f.isHidden();
		size = f.length();
		last_modified = f.lastModified();

		System.out.println(
			"read \t\t\t"
				+ can_read
				+ "\nwrite \t\t\t"
				+ can_write
				+ "\ndelete \t\t\t"
				+ can_delete
				+ "\nis directory \t"
				+ is_directory
				+ "\nis hidden \t\t"
				+ is_hidden
				+ "\nlength \t\t\t"
				+ size
				+ "\nlast modified \t"
				+ new Date(last_modified));

	}

	public static void main(String[] args)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorIOFileException {
		Properties p = System.getProperties();
		String home = p.getProperty("user.dir");
		String file_separator = p.getProperty("file.separator");
		File f = new File(new String(home + file_separator + "tmp"));
		(new ShowProperties()).run(f);

	}

	public boolean canUndo() {
		return false;
	}
}