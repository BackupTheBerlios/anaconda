package fr.umlv.anaconda.command;

import java.awt.HeadlessException;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.*;
import fr.umlv.anaconda.tools.PressPaper;
import fr.umlv.anaconda.tools.Right;
import fr.umlv.anaconda.tools.Tools;

public class Paste implements Command {
	private boolean is_cut;
	private ArrayList last_selection = new ArrayList();
	private File dest_rep;
	private File origin_rep;
	private Delete deleter = new Delete();
	private NoSelectedFilesException no_selection =
		new NoSelectedFilesException();
	private JProgressBar progress_bar = new JProgressBar();
	private int[] right;

	/**
	 * The 'paste' action. Calls the pasteFile method for each elements in
	 * 'selectedFiles'.
	 */
	public void run() {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			no_selection.show();
			return;
		}

		if (selected_file.size() > 1) {
			(new TooMuchFilesException()).show();
			return;
		}

		File dest = (File) selected_file.get(0);
		if (!dest.isDirectory())
			dest = dest.getParentFile();

		if (!dest.exists()) {
			(new DoNotExistFileException(dest)).show();
			return;
		}
		if (!dest.isDirectory()) {
			(new IsNotDirectoryException(dest)).show();
			return;
		}
		if (!dest.canWrite()) {
			(new CanNotWriteException(dest)).show();
			return;
		}

		dest_rep = dest;

		if (PressPaper.isEmpty()) {
			(new EmptyPressPaperException()).show();
			return;
		}
		ArrayList presspaper = PressPaper.getSelectedFiles();
		origin_rep = ((File) (presspaper.get(0))).getParentFile();

		if (Tools.contains(presspaper, dest)) {
			no_selection.show();
			return;
		}

		last_selection.clear();
		last_selection.addAll(presspaper);
		is_cut = PressPaper.toDelete();
		(new DoThread()).start();
	}

	public void run(File dest) {
		if (!dest.exists()) {
			(new DoNotExistFileException(dest)).show();
			return;
		}
		if (!dest.isDirectory()) {
			(new IsNotDirectoryException(dest)).show();
			return;
		}
		if (!dest.canWrite()) {
			(new CanNotWriteException(dest)).show();
			return;
		}

		dest_rep = dest;

		if (PressPaper.isEmpty()) {
			(new EmptyPressPaperException()).show();
			return;
		}

		origin_rep =
			((File) (PressPaper.getSelectedFiles().get(0))).getParentFile();

		last_selection.clear();
		last_selection.addAll(PressPaper.getSelectedFiles());
		is_cut = PressPaper.toDelete();
		(new DoThread()).start();
		if (is_cut)
			PressPaper.clear();
	}

	/**
	 * The real 'paste' action. Creates copies of all the elements in
	 * 'selectedFiles' if the last action is 'copy'. Changes path of all the
	 * elements in 'selectedFiles' if the last action is 'cut'. Subdirectories
	 * are pasted to.
	 */
	private void pasteFile(File parent, File child)
		throws DoNotExistFileException, ErrorIOFileException, CanNotReadException {
		File file = new File(parent, child.getName());
		int option = JOptionPane.YES_OPTION;

		if (Tools.contains(parent, child)) {
			try {
				option =
					JOptionPane.showConfirmDialog(
						null,
						child.getName()
							+ " existe deja, voulez vous l'ecraser ?",
						"Conflit",
						JOptionPane.YES_NO_OPTION);
			} catch (HeadlessException e) {
				option = JOptionPane.NO_OPTION;
			}
		}

		if (option == JOptionPane.YES_OPTION) {
			if (child.isDirectory()) {

				File[] list = child.listFiles();
				file.mkdir();

				for (int i = 0; i < list.length; i++)
					pasteFile(file, list[i]);
				if (PressPaper.toDelete())
					child.delete();

			} else {
				if (PressPaper.toDelete())
					child.renameTo(file);
				else {
					FileInputStream fileInputStream;
					try {
						//setReadOnly(child);
						fileInputStream = new FileInputStream(child);
						FileOutputStream fileOutputStream =
							new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						while (fileInputStream.available() > 0) {
							int readNumber = fileInputStream.read(buffer);
							if (readNumber > 0)
								fileOutputStream.write(buffer, 0, readNumber);
						}
						//unsetReadOnly(child);
						//setRight(this.right,file);
					} catch (FileNotFoundException e) {
						throw new DoNotExistFileException(child);
					} catch (IOException e) {
						throw new ErrorIOFileException(child);
					} 
				}
			}
		}
	}

	public void undo() {
		if (!dest_rep.exists()) {
			(new DoNotExistFileException(dest_rep)).show();
			return;
		}
		if (!dest_rep.isDirectory()) {
			(new IsNotDirectoryException(dest_rep)).show();
			return;
		}
		if (!dest_rep.canWrite()) {
			(new CanNotWriteException(dest_rep)).show();
			return;
		}
		(new UndoThread()).start();
	}

	private static Cut redo_cut = new Cut();
	private static Copy redo_copy = new Copy();

	public void redo() {
		if (is_cut)
			redo_cut.run(last_selection);
		else
			redo_copy.run(last_selection);
		run(dest_rep);
	}

	public class DoThread extends Thread {
		public void run() {
			for (Iterator it = last_selection.iterator(); it.hasNext();) {
				File file = (File) it.next();
				if (!file.exists())
					 (new DoNotExistFileException(file)).show();
				if (!file.canRead())
					 (new CanNotReadException(file)).show();

				try {
					pasteFile(dest_rep, file);
				} catch (DoNotExistFileException e) {
					e.show();
				} catch (ErrorIOFileException e) {
					e.show();
				} catch (CanNotReadException e) {
					e.show();
				}
			}

		}
	}

	public class UndoThread extends Thread {
		public void run() {
			File[] tab_file = dest_rep.listFiles();

			for (int i = 0; i < tab_file.length; i++) {
				for (Iterator j = last_selection.iterator(); j.hasNext();) {
					File tmp = (File) j.next();
					if (((tab_file[i].isDirectory() && tmp.isDirectory())
						|| (tab_file[i].isFile() && tmp.isFile()))
						&& tab_file[i].getName().compareTo(tmp.getName()) == 0) {
						if (is_cut)
							try {
								pasteFile(origin_rep, tab_file[i]);
							} catch (DoNotExistFileException e) {
								e.show();
							} catch (ErrorIOFileException e) {
								e.show();
							} catch (CanNotReadException e) {
								e.show();
							}
						deleter.run(tab_file[i]);
					}
				}
			}
		}
	}

	public boolean canUndo() {
		return true;
	}
	
	private void setRight(int[] right, File f) throws ErrorIOFileException{
		Runtime r = Runtime.getRuntime();
				String file_path = f.getPath();
				StringBuffer uplus = new StringBuffer("chmod u+");
				StringBuffer uminus = new StringBuffer("chmod u-");
				StringBuffer gplus = new StringBuffer("chmod g+");
				StringBuffer gminus = new StringBuffer("chmod g-");
				StringBuffer oplus = new StringBuffer("chmod o+");
				StringBuffer ominus = new StringBuffer("chmod o-");

				if (right[0] == 0) uminus.append("r");
				else uplus.append("r");
				
				if (right[1] == 0) uminus.append("w");
				else uplus.append("w");
				
				if (right[2] == 0) uminus.append("x");
				else uplus.append("x");
				
				if (right[3] == 0) gminus.append("r");
				else gplus.append("r");
				
				if (right[4] == 0) gminus.append("w");
				else gplus.append("w");
				
				if (right[5] == 0) gminus.append("x");
				else gplus.append("x");
				
				if (right[6] == 0) ominus.append("r");
				else oplus.append("y");
				
				if (right[7] == 0) ominus.append("w");
				else oplus.append("w");
				
				if (right[8] == 0) ominus.append("x");
				else oplus.append("x");
				
				try {
					r.exec((uplus.append(" "+file_path)).toString());
					r.exec((uminus.append(" "+file_path)).toString());
					r.exec((gplus.append(" "+file_path)).toString());
					r.exec((gminus.append(" "+file_path)).toString());
					r.exec((oplus.append(" "+file_path)).toString());
					r.exec((ominus.append(" "+file_path)).toString());
				} catch (IOException e) {
					throw new ErrorIOFileException(f);
				}
	}

	private void setReadOnly(File f)
		throws ErrorIOFileException, CanNotReadException {

		try {
			Right r = new Right(f);
			right = r.get();
			System.out.println("pouettttttttttttttttt" + r);
			StringBuffer sb = new StringBuffer("chmod a-wx ");
			sb.append(f.getPath());

			try {
				Runtime.getRuntime().exec(sb.toString());
			} catch (IOException e) {
				throw new ErrorIOFileException(f);
			} catch (SecurityException e) {
				throw new CanNotReadException(f);
			}
		} catch (IOException e1) {
			throw new ErrorIOFileException(f);
		}
	}

	private void unsetReadOnly(File f) throws ErrorIOFileException {
		Runtime r = Runtime.getRuntime();
		String file_path = f.getPath();
		StringBuffer uplus = new StringBuffer("chmod u+");
		StringBuffer uminus = new StringBuffer("chmod u-");
		StringBuffer gplus = new StringBuffer("chmod g+");
		StringBuffer gminus = new StringBuffer("chmod g-");
		StringBuffer oplus = new StringBuffer("chmod o+");
		StringBuffer ominus = new StringBuffer("chmod o-");

		if (right[0] == 0) uminus.append("r");
		if (right[1] == 1) uplus.append("w");
		if (right[2] == 1) uplus.append("x");
		if (right[3] == 0) gminus.append("r");
		if (right[4] == 1) gplus.append("w");
		if (right[5] == 1) gplus.append("x");
		if (right[6] == 0) ominus.append("r");
		if (right[7] == 1) oplus.append("w");
		if (right[8] == 1) oplus.append("x");
		
		try {
			r.exec((uplus.append(" "+file_path)).toString());
			r.exec((uminus.append(" "+file_path)).toString());
			r.exec((gplus.append(" "+file_path)).toString());
			r.exec((gminus.append(" "+file_path)).toString());
			r.exec((oplus.append(" "+file_path)).toString());
			r.exec((ominus.append(" "+file_path)).toString());
		} catch (IOException e) {
			throw new ErrorIOFileException(f);
		}

	}
}
