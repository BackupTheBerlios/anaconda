/*
 * Created on 3 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.Properties;

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

	public void run(Object o)
		throws
			IsNotDirectoryException,
			CanNotWriteException,
			CanNotReadException,
			DoNotExistFileException,
			ErrorIOFileException {

		File f = (File) o;
		/*
		 * FilePermission fp = new
		 * FilePermission("C:\\","read,write,delete,execute");
		 *  
		 */

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
	public static void main(String[] args) {
		Properties p = System.getProperties();
		System.out.println(p.getProperty("user.dir"));
		System.out.println(p.getProperty("file.separator"));
		System.out.println(
			new String(
				p.getProperty("user.home")
					+ p.getProperty("file.separator")
					+ "testcopy1.txt"));
		/*
		 * try { File f = new File("C:\\"); (new CreateFile()).run(f); (new
		 * ShowProperties()).run(f); } catch (IsNotDirectoryException e) {
		 * e.printStackTrace(); } catch (CanNotWriteException e) {
		 * e.printStackTrace(); } catch (CanNotReadException e) {
		 * e.printStackTrace(); } catch (DoNotExistFileException e) {
		 * e.printStackTrace(); } catch (ErrorPastingFileException e) {
		 * e.printStackTrace();
		 */
	}
}
