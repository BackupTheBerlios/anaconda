/*
 * Created on 3 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;

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
//	private boolean can_execute;
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
		
		name = f.getName();
		viewProperties(f);
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

		name = f.getName();
		viewProperties(f);
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
		
		f.setReadOnly();
		(new ShowProperties()).run(f);
//		Permission file_permission = new Permission("write"); // ne reconnais pas "permission"
	}

	public boolean canUndo() {
		return false;
	}
	
	private void viewProperties(File f) {

		Main.info_panel.setAsProperties(name,can_read,can_write,can_delete,is_directory,is_hidden,size,last_modified);

	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#getAction()
	 */
	public Action getAction() {
		return new AbstractAction("Proprietes") {
			public void actionPerformed(ActionEvent e) {
				run();
			}
		};
	}
}