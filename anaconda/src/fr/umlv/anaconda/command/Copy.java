/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.ArrayList;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.tools.PressPaper;

public class Copy implements Command {
	private final static boolean deleted = false;
	private static NoSelectedFilesException no_selection =
		new NoSelectedFilesException();

	/**
	 * Make a copy of the selected files
	 * 
	 * @param selection
	 */
	public void run() {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			no_selection.show();
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
			no_selection.show();
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
