package fr.umlv.anaconda.tools;
/*
 * Created on 20 nov. 2003
 *
 * Matrise info 2003/2004
 * Interface Graphique
 * Projet Hoover
 * 
 */
import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * Directory filter
 * @author Figueroa Olivier
 * @author Bruneteau Adrien
 *
 */
public class RepFileFilter extends FileFilter {

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File f) {
		return f.isDirectory();
		//f.accept(JFileChooser.DIRECTORIES_ONLY);
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription() {
		return "Répertoire";
	}

}
