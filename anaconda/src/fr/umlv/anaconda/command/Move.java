package fr.umlv.anaconda.command;

/*
 * Créé le 5 févr. 2004
 *
 */

import java.io.File;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.*;
import fr.umlv.anaconda.tools.ChoozRep;
import fr.umlv.anaconda.tools.PressPaper;

/**
 * Moving files
 * @author Anac team
 *
 */
public class Move implements Command {
	private static Cut cut = new Cut();
	private static Paste last_paste;

	public void run() {
		File dest = ChoozRep.frameChoozRep();

		if (dest == null)
			return;

		if (!dest.isDirectory()) {
			(new IsNotDirectoryException(dest)).show();
			return;
		}

		cut.run(Main.getSelectionItems());
		last_paste = new Paste();
		last_paste.run(dest);
		PressPaper.clear();
	}

	public void redo() {
		last_paste.redo();
	}

	public void undo() {
		last_paste.undo();
	}

	public boolean canUndo() {
		return true;
	}

}
