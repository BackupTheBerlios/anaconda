/*
 * Created on 3 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.Date;

import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorIOFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

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

	public void run(Object o)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorIOFileException {

		File f = (File) o;
		String file_path = f.getPath();
		/*
		 * FilePermission fp = new
		 * FilePermission("C:\\","read,write,delete,execute");
		 *  
		 */
		try {
			sm.checkDelete(file_path);
			can_delete = true;
		} catch (SecurityException e) {
			can_delete = false;
		}

		try {
			sm.checkRead(file_path);
			can_read = true;
		} catch (SecurityException e) {
			can_read = false;
		}

		try {
			sm.checkWrite(file_path);
			can_write = true;
		} catch (SecurityException e) {
			can_write = false;
		}

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

	public void redo()
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorIOFileException {

	}

	public void undo()
		throws
			DoNotExistFileException,
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			CanNotDeleteException,
			ErrorIOFileException {

	}
	public static void main(String[] args)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorIOFileException {

		File f = new File("C:\\tmp\\testcopy1.txt");
		(new ShowProperties()).run(f);

	}
}