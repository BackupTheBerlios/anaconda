package fr.umlv.anaconda.tools;
/*
 * Created on 29 feb. 2004
 *
 * Matrise info 2003/2004
 * Genie Logiciel
 * Projet Livingstone
 * 
 */
import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * Executable filter
 * @author Anac team
 *
 */
public class ExeFileFilter extends FileFilter {

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File f) {
		return Extension.isExe(f)||f.isDirectory();
		//f.accept(JFileChooser.DIRECTORIES_ONLY);
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription() {
		return "Executable";
	}

}
