/*
 * Created on 3 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.command;

import java.io.File;

import junit.framework.TestCase;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CreateFileTest extends TestCase {
	
	public CreateFileTest(String arg0){
		super(arg0);
	}
	
	public void testCreateFile(){
		File folder = new File("c:/tmp/anaconda/");
		CreateFile creater = new CreateFile();
		creater.run(folder);
		creater.undo();
		creater.run(folder);
		creater.run(folder);
		creater.run(folder);
		creater.undo();
		creater.redo();
	}

}
