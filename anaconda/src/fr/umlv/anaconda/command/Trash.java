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

import fr.umlv.anaconda.GarbageModel;
import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.tools.*;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Trash extends Thread implements Command {

	/*public void run(Object o){
		//TODO Enlever la methode run de command
	}*/
	private ArrayList files_deleted = new ArrayList();
	private GarbageModel model = null;
	private File home = new File(System.getProperty("user.home"));
	private File garbage = new File(home, "anaconda_garbage");
	private File garbage_history = new File(home, "garbage_history");

	public Trash(GarbageModel model) {

		if (!garbage.exists())
			garbage.mkdir();
		else {
			loadGarbageHistory();
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
		String file_name = file.getName();
		System.out.println(file_name + " has been moved to garbage");
		int i = 1;
		while (Tools.contains(garbage, file)) {
			file_name = (file.getName() + "(" + i + ")");
			i++;
		}

		File dest = new File(garbage, file_name);
		if (file.isFile()) {
			try {
				FileInputStream in = new FileInputStream(file);
				dest.createNewFile();
				FileOutputStream out = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				try {
					while (in.available() > 0) {
						int readNumber = in.read(buffer);
						if (readNumber > 0)
							out.write(buffer, 0, readNumber);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
			addFile(dest, file.getAbsolutePath());
			file.delete();
		}
		else{
			File[] files = file.listFiles();
			for(int k =0;k< files.length;k++){
				moveToGarbage(files[k]);
			}
			addFile(dest,file.getAbsolutePath());
			dest.mkdir();
			dest.delete();
		}

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

}
