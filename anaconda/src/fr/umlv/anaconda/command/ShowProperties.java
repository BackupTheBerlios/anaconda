/*
 * Created on 3 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;

import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotReadException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.ErrorPastingFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

/**
 */
public class ShowProperties implements Command {
	private String last_permission;
	boolean can_read;
	boolean can_write;
	boolean can_execute;
	boolean can_delete;
	boolean is_hidden;
	long size;
	long last_modified;
	
	public void run(Object o)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorPastingFileException {

		File f = (File) o;
		/*FilePermission fp = new FilePermission("C:\\","read,write,delete,execute");
		
		can_delete = fp.implies(new FilePermission("C:\\fichier","delete"));*/
		
		can_read = f.canRead();
		can_write = f.canWrite();
		//can_delete;
		is_hidden = f.isHidden();
		size = f.length();
		last_modified = f.lastModified();
		System.out.println(
			f.getPath()
				+ "\n"
				+ can_read 
				+ "\n"
				+ can_write
				+ "\n"
				+ can_delete);
	}

	public void redo()
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
	public static void main(String[] args) {
		try {
			File f = new File("C:\\");
			(new CreateFile()).run(f);
			(new ShowProperties()).run(f);
		} catch (IsNotDirectoryException e) {
			e.printStackTrace();
		} catch (CanNotWriteException e) {
			e.printStackTrace();
		} catch (CanNotReadException e) {
			e.printStackTrace();
		} catch (DoNotExistFileException e) {
			e.printStackTrace();
		} catch (ErrorPastingFileException e) {
			e.printStackTrace();
		}
	}
}
