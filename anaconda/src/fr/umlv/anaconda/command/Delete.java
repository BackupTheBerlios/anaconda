package fr.umlv.anaconda.command;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;

public class Delete implements Command {

	public void run() {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		for (Iterator i = selected_file.iterator(); i.hasNext();)
			try {
				delete((File) i.next());
			} catch (CanNotDeleteException e) {
				e.show();
			}
	}

	public void run(File selected_file) {
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

	public void redo() {}

	public void undo() {}
}
