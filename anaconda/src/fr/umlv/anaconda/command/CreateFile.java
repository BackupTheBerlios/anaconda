/*
 * Created on 3 févr. 2004
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import fr.umlv.anaconda.Main;

/**
 * @author FIGUEROA
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class CreateFile extends AbstractAction implements Command {

	private File current_folder = null;
	private final String default_name = "fichier";
	private String current_name = null;

	public void run(Object current_folder) {
		this.current_folder = (File) current_folder;
		File f;
		int i = 1;
		current_name = default_name;
		while ((f = new File(this.current_folder, current_name)).exists()) {
			current_name = default_name.concat("" + i + "");
			i++;
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			//TODO Cas d un echec de creation de fichier.
			e.printStackTrace();
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
			f.createNewFile();
		} catch (IOException e) {
			//TODO Cas d un echec de creation de fichier.
			e.printStackTrace();
		}
	}

	public void undo() {
		File file = new File(current_folder, current_name);
		file.delete();
	}

	public void actionPerformed(ActionEvent arg0) {
		ArrayList selected_file = Main.getSelectionItems();
		if (selected_file.size() < 1)
			//TODO cas ou on n a rien selectionne.
			;

		run(selected_file.get(0));
	}

}
