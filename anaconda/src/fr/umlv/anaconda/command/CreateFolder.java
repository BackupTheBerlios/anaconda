/*
 * Created on 3 févr. 2004
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotWriteException;

/**
 * @author FIGUEROA
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class CreateFolder extends AbstractAction implements Command {
	private File current_folder = null;
	private final String default_name = "repertoire";
	private String current_name = null;

	public void run(Object current_folder) throws CanNotWriteException {
		this.current_folder = (File) current_folder;
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
			throw new CanNotWriteException(f);
		}

	}

	public void redo() throws CanNotWriteException {
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
			throw new CanNotWriteException(f);
		}

	}

	public void undo() throws CanNotDeleteException {
		File file = new File(current_folder, current_name);
		try{
			file.delete();
		}catch(SecurityException e){
			throw new CanNotDeleteException(file);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		ArrayList selected_file = Main.getSelectionItems();
		if (selected_file.size() != 1)
			//TODO cas ou on n a rien selectionne.
			// (new NoSelectedFilesException()).show;
			// return;
			;

		try {
			run(selected_file.get(0));
		} catch (CanNotWriteException e) {
			e.show();
		}
	}

}
