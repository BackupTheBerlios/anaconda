package fr.umlv.anaconda.command;

import java.io.IOException;

//import fr.umlv.anaconda.exception.ErrorIOFileException;

/* Maitrise info - g?nie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 1 mars 2004 */

/**
 *	Manage the view of the help
 *
 * @author Figeroa Olivier
 * @author Yam Jean Paul
 * @author Tesevic Novak
 * @author Roger Giao Long 
 * @author Bruneteau Adrien
 */
public class Help implements Command {
	/** where the help main page should be found */
	//private static final String HELP_LOCATION = "http://developer.berlios.de/projects/anaconda/";
	private static final String HELP_LOCATION = "http://f1.grp.yahoofs.com/v1/EGxDQI27hSnSVsGzTpnQeY0FxQiK76DhbWS6hrjZu2EgnBcdfviLObsmw_Ug53nBFifDRBdr3G9uNS0uIGR4tw/aide_anaconda.html";
	// pas sur ke cette adresse soit valable...

	/**
	 * Default constructor
	 */
	public Help() {
		super();
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
	public void run() {
		String os = System.getProperties().getProperty("os.name");
		Runtime r = Runtime.getRuntime();
		try
		{
			if (os.endsWith("NT")||os.endsWith("2000")||os.endsWith("XP"))
				r.exec("cmd /c start "+HELP_LOCATION);
			else
			//	r.exec("start "+HELP_LOCATION);
			// pas sur ke ca marche pour linux...
			r.exec("mozilla "+HELP_LOCATION);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		//	new ErrorIOFileException().show();
		}
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#redo()
	 */
	public void redo() {}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#undo()
	 */
	public void undo() {}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#canUndo()
	 */
	public boolean canUndo() {
		return false;
	}

}
