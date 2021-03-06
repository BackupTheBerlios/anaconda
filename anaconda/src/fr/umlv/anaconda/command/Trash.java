package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
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
public class Trash implements Command {

	/*
	 * public void run(Object o){ //TODO Enlever la methode run de command
	 */
	private FileWriter garbage_out = null;
	private BufferedWriter garbage_writer = null;
	//private HashMap files_deleted = new HashMap();
	private ArrayList file_list = new ArrayList();
	private ArrayList old_paths = new ArrayList();
	private ArrayList new_files_deleted = new ArrayList();
	private static GarbageModel garbage_model = null;
	private File home = new File(System.getProperty("user.home"));
	private File garbage = new File(home, ".anaconda_garbage");
	private File garbage_history = new File(home, ".garbage_history");
	private boolean CanTrash = true;
	public int save_index = 0;
	public boolean delete = false;
	public boolean exit_program = false;
	public boolean restore = false;

	public Trash() {
		new Thread() {
			public void run() {
				try {
					Trash.garbage_model = Main.garbage_model;
					if (!garbage.exists())
						garbage.mkdir();
					loadGarbageHistory();
				} catch (SecurityException e) {
					(new CanNotWriteException(garbage)).show();
					new ExternalException("Les droits de la corbeille ont ete modifies")
						.show();
				}
				while (true) {
					while (!delete && !restore)
						if (exit_program)
							break;
					if (delete) {
						ArrayList selected_files = Main.getSelectionItems();
						if (selected_files.size() < 1) {
							(new NoSelectedFilesException()).show();
							return;
						}
						for (Iterator it = selected_files.iterator();
							it.hasNext();
							) {
							moveToGarbage((File) it.next());

						}
						try {
							saveGarbageHistory();
						} catch (IOException e) {
							System.err.println();
						}
						Main.refresh();
						delete = false;
					} else if (restore) {
						restore(Main.tabb.getGarbageSelectedFiles());
						restore = false;
						Main.refresh();
					} else if (exit_program) {
						try {
							garbage_out.close();
							garbage_writer.close();
							garbage.delete();
							garbage_history.createNewFile();
							FileWriter writer = new FileWriter(garbage_history);
							BufferedWriter out = new BufferedWriter(writer);
							for (Iterator it = file_list.iterator(),it1 = old_paths.iterator();it.hasNext() && it1.hasNext();) {
								writer.write( ((File)it.next()).getAbsolutePath() + '\n');
								writer.write( ((File)it1.next()).getAbsolutePath() + '\n');
							}
							writer.close();
							out.close();
						} catch (FileNotFoundException e1) {

							e1.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;

					}
				}
			}
		}
		.start();
	}

	public void run() {
		delete = true;
	}

	public void stop() {
		exit_program = true;
	}

	public void restore() {
		restore = true;
	}

	public void moveToGarbage(File file) {

		/* SUPPRESSION D'UN FICHIER */

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
				in.close();
				out.close();
				addFile(dest, file.getAbsolutePath());
				file.delete();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/* SUPRESSION d'UN REPERTOIRE */
		else {
			try {
				File[] files = file.listFiles();
				/* Supression des fils */
				for (int k = 0; k < files.length; k++) {
					moveToGarbage(files[k]);
				}
				int child_count = 0;
				while ((child_count = file.listFiles().length) > 0) {
					//System.err.println(
					//"En attente de la suppression des fichiers internes "
					//+ child_count);
				}
				File dest = new File(garbage, checkFileName(file));
				File tmp_file = file;
				file.delete();

				dest.mkdir();
				addFile(dest, tmp_file.getAbsolutePath());

			} catch (SecurityException e) {
				System.err.println("Security Exception in moveToGarbage");
			}
		}

	}

	public void restore(ArrayList list) {
		File target_file;
		File old_target_file = null;
		File parent_file = null;
		for (Iterator it = list.iterator(); it.hasNext();) {
			File f = (File) it.next();
			int index = file_list.indexOf(f);
			if (index != -1) {
				target_file = (File)old_paths.get(index);
				/* Si l'element a restaurer est un fichier */
				if (f.isFile()) {
					try {
						if (target_file != old_target_file) {
							parent_file = target_file.getParentFile();
							parent_file.mkdirs();
						}
						FileInputStream in = new FileInputStream(f);
						target_file.createNewFile();
						FileOutputStream out =
							new FileOutputStream(target_file);
						byte[] buffer = new byte[1024];

						while (in.available() > 0) {
							int readNumber = in.read(buffer);
							if (readNumber > 0)
								out.write(buffer, 0, readNumber);
						}
						in.close();
						out.close();
						file_list.remove(index);
						old_paths.remove(index);
						f.delete();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				/* si l'element a restaurer est un repertoire */
				else {
					try {
						target_file.mkdirs();
						file_list.remove(index);
						old_paths.remove(index);
						/*FileInformation fi = new FileInformation(f,(File) files_deleted.get(f));
						int index;
						if ((index = files_deleted_history.indexOf(fi.getFile())) != -1)
							files_deleted_history.remove(index);*/
						f.delete();
					} catch (SecurityException e) {
						System.err.println(
							"Security Exception in moveToGarbage");
					}
				}
				garbage_model.removeElement(f);
			}
			it.remove();
		}
	}

	/**
	 * Checks the file name before to put it in the garbage
	 * 
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

	public void run(Object o) {
		ArrayList files = (ArrayList) o;
	}

	public void addFile(File file, String old_path) {
		new_files_deleted.add(new FileInformation(file, new File(old_path)));
		garbage_model.addElement(file);
	}
	
	
	public void delete(ArrayList list){
		int index;
		File f;
		for(Iterator it = list.iterator();it.hasNext();){
			f = (File)it.next();
			if((index = file_list.indexOf(f)) != -1){
				file_list.remove(index);
				old_paths.remove(index);
				garbage_model.removeElement(f);
			}
		}
	}

	/**
	 * Save the garbage history
	 * 
	 * @throws IOException
	 */
	public void saveGarbageHistory() throws IOException {
		for (Iterator it = new_files_deleted.iterator(); it.hasNext();) {
			FileInformation fi = (FileInformation) it.next();
			garbage_writer.write(fi.getFile().getAbsolutePath() + '\n');
			garbage_writer.write(fi.getOldPath().getAbsolutePath() + '\n');
			file_list.add(fi.getFile());
			old_paths.add(fi.getOldPath());
		}
		new_files_deleted.clear();
		garbage_writer.flush();
	}

	/**
	 * Loads the garbage history
	 *  
	 */
	public void loadGarbageHistory() {
		if (garbage_history.exists()) {
			try {
				BufferedReader br =
					new BufferedReader(new FileReader(garbage_history));
				String file_name;
				String old_path;
				while ((file_name = br.readLine()) != null) {
					old_path = br.readLine();
					file_list.add(new File(file_name));
					old_paths.add(new File(old_path));
					garbage_model.addElement(new File(file_name));
				}
			} catch (FileNotFoundException e) {
				System.err.println(
					"loadGarbageHistory - FileNotFoundException");
			} catch (IOException e) {
				System.err.println("LoadGarbageHistory - IOException");
			}
		} else {
			try {
				garbage_history.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			garbage_out = new FileWriter(garbage_history);
		} catch (IOException e) {
			System.err.println("IOException e");
		}
		garbage_writer = new BufferedWriter(garbage_out);

	}
	public void redo() {

	}

	public void undo() {
	}

	public boolean canUndo() {
		// TODO Auto-generated method stub
		return false;
	}

	public class FileInformation implements Serializable {
		private File old_path;
		private File file;
		public FileInformation(File file, File old_path) {
			this.file = file;
			this.old_path = old_path;
		}

		public File getOldPath() {
			return old_path;
		}

		public File getFile() {
			return file;
		}

		public String toString() {
			return ""
				+ old_path.getAbsolutePath()
				+ " - "
				+ file.getAbsolutePath();
		}
		public boolean equals(File file){
			if(this.file.equals(file))
				return true;
			return false;
		}
	}

	public Action getAction() {
		return new AbstractAction("Supprimer") {
			public void actionPerformed(ActionEvent e) {
				run();
			}
		};
	}

}
