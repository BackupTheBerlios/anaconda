package fr.umlv.anaconda.command;

/*
 * Créé le 5 févr. 2004
 *
 */

import java.io.*;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.IsNotDirectoryException;
import fr.umlv.anaconda.tools.ChoozRep;
import fr.umlv.anaconda.tools.PressPaper;

/**
 * 
 * Duplicate the selected Files
 * @author abrunete
 *
 */
public class Clone implements Command {
	private static Copy copy = new Copy();
	private static Paste last_paste;

	
	public void run() {

		File dest = ChoozRep.frameChoozRep();

		if (dest == null)
			return;
	
		if( !dest.isDirectory() ){
			(new IsNotDirectoryException(dest)).show();
			return;
		}

		copy.run(Main.getSelectionItems());
		Clone.last_paste = new Paste();
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
