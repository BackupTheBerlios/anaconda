package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.tools.Tools;

public class Delete implements Command {

	public void run() {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		for (Iterator i = selected_file.iterator(); i.hasNext();) {
			File to_delete = (File) i.next();

			if (Tools.isPointPoint(to_delete))
				 (new CanNotDeleteException(to_delete)).show();
			else
				try {
					delete(to_delete);
				} catch (CanNotDeleteException e) {
					e.show();
				}
		}
	}

	public void run(File selected_file) {

		if (Tools.isPointPoint(selected_file))
			 (new CanNotDeleteException(selected_file)).show();
		else
			try {
				delete(selected_file);
			} catch (CanNotDeleteException e) {
				e.show();
			}
	}

	public void delete(File file) throws CanNotDeleteException {
		if (file.isDirectory()) {
			File[] children = file.listFiles();

			for (int i = 0; i < children.length; i++)
				delete(children[i]);
			try {
				file.delete();
			} catch (SecurityException e) {
				throw new CanNotDeleteException(file);
			}
		} else
			try {
				file.delete();
			} catch (SecurityException e) {
				throw new CanNotDeleteException(file);
			}
	}

	public void redo() {
	}

	public void undo() {
	}

	public boolean canUndo() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#getAction()
	 */
	public Action getAction() {
		return new AbstractAction("Supprimer") {
			public void actionPerformed(ActionEvent e) {
				//Delete de la corbeille
				run();
				Main.refresh();
			}
		};
	}
}
