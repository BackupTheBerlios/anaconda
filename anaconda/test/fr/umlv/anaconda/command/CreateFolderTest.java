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
public class CreateFolderTest extends TestCase {

	public CreateFolderTest(){
		
	}
	
	public void testCreateFolder(){
		File folder = new File("c:/tmp/anaconda/");
		CreateFolder creater = new CreateFolder();
		creater.run(folder);
		creater.undo();
		creater.run(folder);
		creater.run(folder);
		creater.run(folder);
		creater.undo();
	}
	
}
