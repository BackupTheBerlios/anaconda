package fr.umlv.anac;

/* Maitrise info - génie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 21 janv. 2004 */

/**
 * Interface for all the commands
 * 
 * @version 1.0
 * @author Figeroa Olivier
 * @author Yam Jean Paul
 * @author Tesevic Novak
 * @author Roger Giao Long 
 * @author Bruneteau Adrien
 */
public interface Command {
	
	// private ArrayList list;
	
	// La commande devra avoir un constructeur comme suit
	// public Command(List list);
	
	public void launch();
	
	public void undo();
	
	
}
