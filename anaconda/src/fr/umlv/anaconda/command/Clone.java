package fr.umlv.anaconda.command;

/*
 * Créé le 5 févr. 2004
 *
 */

import java.io.*;
import java.util.ArrayList;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.ErrorIOFileException;
import fr.umlv.anaconda.tools.ChoozRep;

/**
 * 
 * Duplicate the selected Files
 * @author abrunete
 *
 */
public class Clone implements Command {

//	private File origin;
	private File dest;

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
	public void run() {
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
