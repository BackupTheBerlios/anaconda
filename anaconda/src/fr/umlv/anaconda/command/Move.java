package fr.umlv.anaconda.command;

/*
 * Créé le 5 févr. 2004
 *
 */

import java.io.File;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.*;
import fr.umlv.anaconda.tools.ChoozRep;

/**
 * Moving files
 * @author Anac team
 *
 */
public class Move implements Command {
	private static Cut cut = new Cut();
	private static Paste last_paste;
	/*private File dest = null;
	private ArrayList selection;*/

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
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
		/*selection = Main.getSelectionItems();
		if (dest == null || !dest.isDirectory()) {
			dest = ChoozRep.frameChoozRep();
		}
		
		if (!dest.isDirectory()) {
			(new IsNotDirectoryException(dest)).show();
			return;
		}
		
		if (!dest.canWrite()) {
			(new CanNotWriteException(dest)).show();
			return;
		}
		
		if (dest != null && dest.isDirectory()) {
			File origin;
			for (int i = 0; i < selection.size(); i++) {
				origin = (File) selection.get(i);
				origin.renameTo(
					new File(
						dest.getAbsolutePath()
							+ File.separatorChar
							+ origin.getName()));
			}
			Main.model.setFolder(Main.newCurrentFolder);
		}*/
	}

	public void redo() {
		last_paste.redo();
	}

	public void undo() {
		last_paste.undo();
	}

}
