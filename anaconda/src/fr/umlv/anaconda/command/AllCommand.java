/*
 * Created on 19 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import fr.umlv.anaconda.Main;

/**
 */
public class AllCommand {
	public static final Command[] tab_command =
		new Command[] {
			new CreateFile(),
			new CreateFolder(),
			new Rename(),
			new Copy(),
			new Cut(),
			new Paste(),
			new Clone(),
			new Move(),
			new Delete(),
			new Find(),
			new ShowProperties(),
			new Trash(), 
			new About(),
			new Help()
			};

	public static final String[] command_name =
		new String[] {
			"createfile",
			"createfolder",
			"rename",
			"copy",
			"cut",
			"paste",
			"clone",
			"move",
			"delete",
			"find",
			"showproperties",
			"trash", 
			"about", 
			"help"
	};

	public static Command last_command;
	public static boolean can_undo=false;
	public static boolean can_redo=false;
	
	public static Command get(String cmd_name) {
		for (int i = 0; i < command_name.length; i++)
			if (cmd_name.equalsIgnoreCase(command_name[i])) {
				Command cmd = tab_command[i];
				if (cmd.canUndo()){
					last_command = cmd;
					can_undo = true;
				}
				return cmd;
			}
		System.err.println("Commande inconnue");
		return null;
	}
	
	public static void undoLastCommand(){
		can_undo = false;
		can_redo = true;
		last_command.undo();
	}
	
	public static void redoLastCommand(){
		can_redo = false;
		last_command.redo();
	}
	
	public static boolean canUndo(){
		return can_undo;
	}
	
	public static boolean canRedo(){
		return can_redo;
	}
	
	
	public static Action getAction(String cmd_name) {
		for (int i = 0; i < command_name.length; i++)
			if (cmd_name.equalsIgnoreCase(command_name[i])) {
				return tab_command[i].getAction();
			} 
		return ActionFactory(cmd_name);
	}
	
	public static Action ActionFactory(String cmd_name) {
		
		if (cmd_name.equalsIgnoreCase("refresh")) {
			return new AbstractAction("Actualiser") {
				public void actionPerformed(ActionEvent e) {
					Main.refresh();
				}
			};
		} 
		
		return null;
	}

	
}
