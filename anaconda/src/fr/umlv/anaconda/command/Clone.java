package fr.umlv.anaconda.command;

/*
 * Créé le 5 févr. 2004
 *
 */

import java.awt.HeadlessException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.*;
import fr.umlv.anaconda.tools.ChoozRep;

/**
 * 
 * Duplicate the selected Files
 * @author abrunete
 *
 */
public class Clone implements Command {

	private File origin;
	private File dest;



	public void run() {
		ArrayList selected_file = Main.getSelectionItems();
		if (dest == null) {
			dest = ChoozRep.frameChoozRep();
		}

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

		for (Iterator it = selected_file.iterator(); it.hasNext();) {
			File file = (File) it.next();
			if (!file.exists())
				 (new DoNotExistFileException(file)).show();
			if (!file.canRead())
				 (new CanNotReadException(file)).show();

			try {
				pasteFile(dest, file);
			} catch (DoNotExistFileException e) {
				e.show();
			} catch (ErrorIOFileException e) {
				e.show();
			}
		}
	}

			private void pasteFile(File parent, File child)
				throws DoNotExistFileException, ErrorIOFileException {
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

						file.mkdir();
						File[] list = child.listFiles();
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
								fileInputStream = new FileInputStream(child);
								FileOutputStream fileOutputStream =
									new FileOutputStream(file);
								byte[] buffer = new byte[1024];
								while (fileInputStream.available() > 0) {
									int readNumber = fileInputStream.read(buffer);
									if (readNumber > 0)
										fileOutputStream.write(buffer, 0, readNumber);
								}
							} catch (FileNotFoundException e) {
								throw new DoNotExistFileException(child);
							} catch (IOException e) {
								throw new ErrorIOFileException(child);
							}
						}
					}
				}
			}






	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
	public void run2() {
		ArrayList selected_file = Main.getSelectionItems();
		if (dest == null ) {
			dest = ChoozRep.frameChoozRep();
		}
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f = null;
		byte[] buf = new byte[1024];
		
		for (int i=0; i<selected_file.size(); i++) {
			f = (File)selected_file.get(i);
			try {
				fis = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					 new ErrorIOFileException(f).show();
			}
			f = new File(dest.getAbsolutePath() + File.separatorChar + (File)selected_file.get(i));
			try {
				fos = new FileOutputStream(f);
			} catch (FileNotFoundException e1) {
				new ErrorIOFileException(f).show();
			}	
			try {
				while (fis.read(buf) != -1)  {
					fos.write(buf);
				}
				fis.close();
				fos.close();
			} catch (IOException e2) {
				System.err.println("erreur de flux");
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#redo()
	 */
	public void redo() {
		// TODO Raccord de méthode auto-généré

	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#undo()
	 */
	public void undo() {
		// TODO Raccord de méthode auto-généré

	}

}
