/*
 * Créé le 2 févr. 2004
 *
 */
 
package fr.umlv.anaconda.command;

import javax.swing.Action;

/**
 * Interface for the anaconda commands
 * 
 * @author Anac team
 *
 */
public interface Command {
	
	/** launch the command */
	public void run();
	
	/** launch again a canceled command */	
	public void redo();

	/** cancel a command */
	public void undo();
	
	/** tell if the command can be cancelled */
	public boolean canUndo();
	
	/** returns the associated Action */
	public Action getAction();
}
