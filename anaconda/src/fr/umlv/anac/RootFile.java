package fr.umlv.anac;

import java.io.*;
import java.net.*;

/**
 * 
 */
public class RootFile extends File {
	/**********/
	/* FIELDS */
	/**********/

	private String rootName;

	/****************/
	/* CONSTRUCTORS */
	/****************/

	public RootFile(File parent, String child) { this(); }
	public RootFile(String pathname) { this(); }
	public RootFile(String parent, String child) { this(); }
	public RootFile(URI uri) { this(); }

	/**
	 * 
	 */
	public RootFile() { super("\\"); rootName = "\\"; }

	/***********/
	/* METHODS */
	/***********/

	public String getName() { return toString(); }
	public String getParent() { return null; }
	public File getParentFile() { return null; }
	public String getPath() { return toString(); }
	public boolean isAbsolute() { return true; }
	public String getAbsolutePath() { return toString(); }
	public File getAbsoluteFile() { return this; }
	public String getCanonicalPath() throws IOException { return toString(); }
	public File getCanonicalFile() throws IOException { return this; }
	public URL toURL() throws MalformedURLException { return null; } // A FAIRE
	public URI toURI() { return null; } // A FAIRE
	public boolean canRead() { return true; }
	public boolean canWrite() { return false; }
	public boolean exists() { return false; }
	public boolean isDirectory() { return true; }
	public boolean isFile() { return false; }
	public boolean isHidden() { return false; }
	public long lastModified() { return 0; }
	public long length() { return this.listFiles().length; }
	public boolean createNewFile() throws IOException { return false; }
	public boolean delete() { return false; }
	public void deleteOnExit() {  }
	public String[] list() {
		File[] list = this.listFiles();
		String[] listName = new String[list.length];
		for(int i = 0; i < list.length; i ++) listName[i] = rootName + list[i].getAbsolutePath();
		return listName;
	}
	public String[] list(FilenameFilter filter) { return this.list(); }
	public File[] listFiles() { return File.listRoots(); }
	public File[] listFiles(FilenameFilter filter) { return this.listFiles(); }
	public File[] listFiles(FileFilter filter) { return this.listFiles(); }
	public boolean mkdir() { return false; }
	public boolean mkdirs() { return false; }
	public boolean renameTo(File dest) { return false; }
	public boolean setLastModified(long time) { return false; }
	public boolean setReadOnly() { return true; }
	public int compareTo(File pathname) { return (pathname instanceof RootFile)? 0: 1; }
	public int compareTo(Object o) { return compareTo((File)o); }
	public boolean equals(Object obj) { return (obj instanceof RootFile); }
	//public int hashCode() {} // A FAIRE
	public String toString() { return rootName; }
}
