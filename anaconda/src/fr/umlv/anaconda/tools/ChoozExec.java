package fr.umlv.anaconda.tools;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/* Maitrise info - génie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 1 mars 2004 */

/**
 *
 * @author Figeroa Olivier
 * @author Yam Jean Paul
 * @author Tesevic Novak
 * @author Roger Giao Long 
 * @author Bruneteau Adrien
 */
public class ChoozExec {
	/** the runnable file */
	private static File exe;


	/**
	 * 
	 */
	public ChoozExec() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * To create a window used to browse disk in order to choose a directory
	 * @return the choosen directory
	 */
	public File frameChoozRep() {
		
		JFileChooser fileChooz = new JFileChooser();
		FileFilter filter = new ExeFileFilter(); 

		fileChooz.setFileFilter(filter);
		fileChooz.setAcceptAllFileFilterUsed(false);
		fileChooz.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooz.setMultiSelectionEnabled(false);
		fileChooz.setDialogTitle("Lancement");
		fileChooz.setApproveButtonText("Lancer");
		fileChooz.setApproveButtonToolTipText("Lance l'application sélectionnée");
	//	fileChooz.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		int returnVal = fileChooz.showOpenDialog(null); 
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			exe = fileChooz.getSelectedFile();
		}
		return exe;
	}
	

}
