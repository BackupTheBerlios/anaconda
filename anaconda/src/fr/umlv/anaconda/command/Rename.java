/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotWriteException;

public class Rename extends AbstractAction {
	private File file;
	private String origin_name;
	private String new_name;

	public Rename() {
	}

	public void run(Object o, String new_name) throws CanNotWriteException {
		File file = (File) o;
		origin_name = file.getName();
		this.file = file;

		File tmp_file = new File(new_name);
		try {
			file.renameTo(tmp_file);
		} catch (SecurityException e) {
			throw new CanNotWriteException(file);
		}
	}

	public void redo() throws CanNotWriteException {
		File tmp_file = new File(new_name);
		try {
			file.renameTo(tmp_file);
		} catch (SecurityException e) {
			throw new CanNotWriteException(file);
		}
	}

	public void undo() throws CanNotWriteException {
		File origin_file = new File(origin_name);
		try {
			file.renameTo(origin_file);
		} catch (SecurityException e) {
			throw new CanNotWriteException(file);
		}
	}

	public void actionPerformed(ActionEvent e) {
		ArrayList selected_file = Main.getSelectionItems();
		if (selected_file.size() != 1)
			//TODO cas ou on n a rien selectionne.
			// (new TooMuchFilesException()).show();
			// return;
			;
		run(Main.getSelectionItems());
	}

}
