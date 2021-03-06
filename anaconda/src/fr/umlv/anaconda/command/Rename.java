package fr.umlv.anaconda.command;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.exception.TooMuchFilesException;
import fr.umlv.anaconda.tools.Tools;

public class Rename implements Command {
	private File file;
	private String origin_name;
	private String new_name;
	private static NoSelectedFilesException no_selection = new NoSelectedFilesException();

	public Rename() {
	}

	public void run() {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() > 1) {
			(new TooMuchFilesException()).show();
			return;
		}

		if (selected_file.size() < 1) {
			no_selection.show();
			return;
		}

		run((File) selected_file.get(0));
	}

	public void run(File file) {

		if(Tools.isPointPoint(file)){
			no_selection.show();
			return;
		}

		File parent = file.getParentFile();
		origin_name = file.getName();

		this.file = file;

		String new_name;
		boolean admitRename = true;
		do {
			try {
				new_name = JOptionPane.showInputDialog(null, "Nouveau nom");
			} catch (HeadlessException e) {
				//TODO interdire le redo et le undo
				return;
			}
			if (new_name == null) {
				return;
			}
			File[] tab_file = parent.listFiles();

			for (int i = 0; i < tab_file.length; i++) {
				if (((tab_file[i].isDirectory() && file.isDirectory())
					|| (tab_file[i].isFile() && file.isFile()))
					&& tab_file[i].getName().compareTo(new_name) == 0) {
					admitRename = false;
				}
			}
		} while (!admitRename);

		this.new_name = new_name;
		File tmp_file = new File(parent, new_name);
		try {
			file.renameTo(tmp_file);
		} catch (SecurityException e) {
			(new CanNotWriteException(file)).show();
		}
	}

	public void redo() {
		File tmp_file = new File(new_name);
		try {
			file.renameTo(tmp_file);
		} catch (SecurityException e) {
			(new CanNotWriteException(file)).show();
		}
	}

	public void undo() {
		File origin_file = new File(origin_name);
		try {
			file.renameTo(origin_file);
		} catch (SecurityException e) {
			(new CanNotWriteException(file)).show();
		}
	}

	public boolean canUndo() {
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#getAction()
	 */
	public Action getAction() {
		return new AbstractAction("Renommer") {
			public void actionPerformed(ActionEvent e) {
				run();
				Main.refresh();
			}
		};
	}
}
