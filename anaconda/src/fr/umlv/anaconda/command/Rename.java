package fr.umlv.anaconda.command;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.exception.TooMuchFilesException;

public class Rename extends AbstractAction {
	private File file;
	private String origin_name;
	private String new_name;

	public Rename() {
	}

	public void run(Object o) throws CanNotWriteException {
		File file = (File) o;
		File parent = file.getParentFile();
		origin_name = file.getName();
		this.file = file;

		String new_name;
		boolean admitRename = true;
		do {
			try{
			new_name = JOptionPane.showInputDialog(null, "Nouveau nom");
			}catch(HeadlessException e){
				//TODO interdire le redo et le undo
				return;
			}
			if(new_name == null){
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
		File tmp_file = new File(new_name);
		try {
			file.renameTo(tmp_file);
		} catch (SecurityException e) {
			throw new CanNotWriteException(file);
		}
	}

	public void redo() throws CanNotWriteException {
		File tmp_file = new File(new_name);
		try {
			file.renameTo(tmp_file);
		} catch (SecurityException e) {
			throw new CanNotWriteException(file);
		}
	}

	public void undo() throws CanNotWriteException {
		File origin_file = new File(origin_name);
		try {
			file.renameTo(origin_file);
		} catch (SecurityException e) {
			throw new CanNotWriteException(file);
		}
	}

	public void actionPerformed(ActionEvent e) {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		if (selected_file.size() > 1) {
			(new TooMuchFilesException()).show();
			return;
		}
		try {
			run(selected_file.get(0));
		} catch (CanNotWriteException e1) {
			e1.show();
		}
	}

}
