package fr.umlv.anaconda.command;

/*
 * Créé le 5 févr. 2004
 *
 */

import java.io.*;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.IsNotDirectoryException;
import fr.umlv.anaconda.tools.ChoozRep;
import fr.umlv.anaconda.tools.PressPaper;

/**
 * 
 * Duplicate the selected Files
 * @author abrunete
 *
 */
public class Clone implements Command {
	private static Copy copy = new Copy();
	private static Paste last_paste;

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
	public void run() {

		File dest = ChoozRep.frameChoozRep();

		if (dest == null)
			return;
	
		if( !dest.isDirectory() ){
			(new IsNotDirectoryException(dest)).show();
			return;
		}

		copy.run(Main.getSelectionItems());
		Clone.last_paste = new Paste();
		last_paste.run(dest);
		PressPaper.clear();
		/*ArrayList selected_file = Main.getSelectionItems();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File fin = null;
		File fout = null;
		byte[] buf = new byte[1024];
		
		for (int i=0; i<selected_file.size(); i++) {
			fin= (File)selected_file.get(i);
			try {
				fis = new FileInputStream(fin);
				} catch (FileNotFoundException e) {
					 new ErrorIOFileException(fin).show();
			}
		   fout = new File(dest.getAbsolutePath() + File.separatorChar + (File)selected_file.get(i));
			
			try {
				fos = new FileOutputStream(fout);
			} catch (FileNotFoundException e1) {
				new ErrorIOFileException(fout).show();
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
		}*/

	}

	public void redo() {
		last_paste.redo();
	}

	public void undo() {
		last_paste.undo();
	}

}
