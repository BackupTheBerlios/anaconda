/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.ArrayList;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.tools.PressPaper;

public class Cut implements Command {
	private final static boolean deleted = true;

	/**
	 * Create a new cut/paste command.
	 */
	public void run() {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		for (int i = 0; i < selected_file.size(); i++) {
			if (((File) selected_file.get(i)).getName().compareTo("..") == 0) {
				selected_file.remove(i);
				break;
			}
		}

		PressPaper.addToPressPaper(selected_file, deleted);
	}

	public void run(ArrayList selected_file) {
		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		for (int i = 0; i < selected_file.size(); i++) {
			if (((File) selected_file.get(i)).getName().compareTo("..") == 0) {
				selected_file.remove(i);
				break;
			}
		}

		PressPaper.addToPressPaper(selected_file, deleted);
	}

	public void undo() {
	}

	public void redo() {
	}

}
