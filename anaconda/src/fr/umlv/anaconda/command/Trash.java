package fr.umlv.anaconda.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Action;

import fr.umlv.anaconda.GarbageModel;
import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.ExternalException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.tools.*;

/**
 * @author FIGUEROA
 */
public class Trash extends Thread implements Command {

	/*public void run(Object o){
		//TODO Enlever la methode run de command
	}*/
	private ArrayList files_deleted = new ArrayList();
	private static GarbageModel model = null;
	private File home = new File(System.getProperty("user.home"));
	private File garbage = new File(home, ".anaconda_garbage");
	private File garbage_history = new File(home, ".garbage_history");
	private boolean CanTrash = true;
	public int save_index = 0;

	public Trash() {
		try {
			Trash.model = Main.garbage_model;
			if (!garbage.exists())
				garbage.mkdir();
			else {
				loadGarbageHistory();
			}
		} catch (SecurityException e) {
			(new CanNotWriteException(garbage)).show();
			new ExternalException("Les droits de la corbeille ont ete modifies")
				.show();
		}
	}

	public void run() {
		ArrayList selected_files = Main.getSelectionItems();
		if (selected_files.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}
		for (Iterator it = selected_files.iterator(); it.hasNext();) {
			moveToGarbage((File) it.next());
		}
	}

	public void moveToGarbage(File file) {

		if (file.isFile()) {
			try {
				File dest = new File(garbage, checkFileName(file));
				FileInputStream in = new FileInputStream(file);
				dest.createNewFile();
				FileOutputStream out = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];

				while (in.available() > 0) {
					int readNumber = in.read(buffer);
					if (readNumber > 0)
						out.write(buffer, 0, readNumber);
				}
				addFile(dest, file.getAbsolutePath());
				file.delete();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try{
			File[] files = file.listFiles();
			for (int k = 0; k < files.length; k++) {
				moveToGarbage(files[k]);
			}
			File dest = new File(garbage, checkFileName(file));
			addFile(dest, file.getAbsolutePath());
			dest.mkdir();
			if(!file.delete()){
				System.out.println("c'est pas good pour " + file.getAbsolutePath());
				}
			}catch(SecurityException e){
				System.err.println("Security Exception in moveToGarbage");
			}
		}
	}
	
	/**
	 * Checks the file name before to put it in the garbage 
	 * @return
	 */
	public String checkFileName(File file) {
		String initial_name = file.getName();
		String file_name = initial_name;
		int i = 1;
		while (Tools.contains(garbage, file_name, file.isDirectory())) {
			file_name = (initial_name + "(" + i + ")");
			i++;
		}
		return file_name;
	}

	public class FileInformation implements Serializable {
		private String old_path;
		private File file;
		public FileInformation(File file, String old_path) {
			this.file = file;
			this.old_path = old_path;
		}

		public String getOldPath() {
			return old_path;
		}

		public File getFile() {
			return file;
		}
	}

	public void run(Object o) {
		ArrayList files = (ArrayList) o;
	}

	public void addFile(File file, String old_path) {
		System.out.println(file.toString() + "   " + old_path.toString());
		files_deleted.add(new FileInformation(file, old_path));
		model.addElement(file);
	}

	/**
	 * Save the garbage history
	 * @throws IOException
	 */
	public void saveGarbageHistory() throws IOException {
		if (!garbage_history.exists()) {
			garbage_history.createNewFile();
		}
		ObjectOutputStream out =
			new ObjectOutputStream(new FileOutputStream(garbage_history));
		out.writeObject(new Integer(files_deleted.size()));
		for (Iterator it = files_deleted.iterator(); it.hasNext();) {
			out.writeObject((FileInformation) it.next());
		}

	}

	/**
	 * Loads the garbage history
	 *
	 */
	public void loadGarbageHistory() {
		if (garbage_history.exists()) {
			try {
				ObjectInputStream in =
					new ObjectInputStream(new FileInputStream(garbage_history));

				int tmp_size = ((Integer) in.readObject()).intValue();
				for (int i = 0; i < tmp_size; i++) {
					files_deleted.add((FileInformation) in.readObject());
				}
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			}
			for (Iterator it = files_deleted.iterator(); it.hasNext();) {
				FileInformation fi = (FileInformation) it.next();
				model.addElement(fi.getFile());
			}
		}
	}
	public void redo() {

	}

	public void undo() {

	}

	public boolean canUndo() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#getAction()
	 */
	public Action getAction() {
		return null; // pas dispo d'ici ?
	}

}
