/*
 * Created on 3 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.tree.TreePath;


import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;


/**
 * @author FIGUEROA
 */
public class CreateFolder implements Command {
	private File current_folder = null;
	private final String default_name = "repertoire";
	private String current_name = null;

	public void run() {
		/*ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		if (selected_file.size() > 1) {
			(new TooMuchFilesException()).show();
			return;
		}*/

		current_folder = Main.getFolder();/*(File) selected_file.get(0)*/;
		if( !current_folder.isDirectory() )
			current_folder = current_folder.getParentFile();
		
		if( !current_folder.isDirectory() ){
			(new IsNotDirectoryException(current_folder)).show();
			return;
		}
		
		if( !current_folder.canWrite() ){
			(new CanNotWriteException(current_folder)).show();
			return;
		}
	
		File f;
		int i = 1;
		current_name = default_name;
		while ((f = new File(this.current_folder, current_name)).exists()) {
			current_name = default_name.concat("" + i + "");
			i++;
		}

		try {
			f.mkdir();
		} catch (SecurityException e) {
			(new CanNotWriteException(f)).show();
		}

	}

	public void run(File current_folder) {
			this.current_folder = current_folder;
			File f;
			int i = 1;
			current_name = default_name;
			while ((f = new File(this.current_folder, current_name)).exists()) {
				current_name = default_name.concat("" + i + "");
				i++;
			}

			try {
				f.mkdir();
			} catch (SecurityException e) {
				(new CanNotWriteException(f)).show();
			}

		}

	public void redo() {
		File f;
		int i = 1;
		current_name = default_name;
		while ((f = new File(this.current_folder, current_name)).exists()) {
			current_name = default_name.concat("" + i + "");
			i++;
		}

		try {
			f.mkdir();
		} catch (SecurityException e) {
			(new CanNotWriteException(f)).show();
		}

	}

	public void undo() {
		File file = new File(current_folder, current_name);
		try {
			file.delete();
		} catch (SecurityException e) {
			(new CanNotDeleteException(file)).show();
		}
	}

	public boolean canUndo() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#getAction()
	 */
	public Action getAction() {
		return new AbstractAction("Cree Repertoire") {
			public void actionPerformed(ActionEvent e) {
				TreePath path = Main.getTreeSelectionPath();
				run();
				Main.refresh();
			}
		};
	}
}
