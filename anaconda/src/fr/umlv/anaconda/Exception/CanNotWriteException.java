/*
 * Created on 1 f�vr. 2004
 */
package fr.umlv.anaconda.Exception;

import java.io.File;


public class CanNotWriteException extends Exception{
	
	public CanNotWriteException(File file){
	//		TODO cas ou on n a pas les droits en ecriture du file.
	}
}
