/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.util.ArrayList;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.tools.PressPaper;

public class Copy implements Command {
	private final static boolean deleted = false;

	/**
	 * Make a copy of the selected files
	 * 
	 * @param selection
	 */
	public void run() {
		ArrayList selected_file = Main.getSelectionItems();
		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		PressPaper.addToPressPaper(selected_file, deleted);
	}

	public void run(ArrayList selected_file) {
		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		PressPaper.addToPressPaper(selected_file, deleted);
	}

	public void undo() {
	}

	public void redo() {
	}
}
