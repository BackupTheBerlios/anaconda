
/*
 * Armstrong Project
 * Created on 17 oct. 2003
 *
 */


/**
 * Class "ClasseVide" from the <b>Armstrong project</b> <br>
 * Just an example Class it does nothing particular...
 * @author Adrien Bruneteau
 * @version 1.0
 */

public class ClasseVide {
	
	/**
	 * A basic method example
	 * @return Always true, that's just an example !
	 */
	public boolean isAlwaysTrue() {
		return true;
	}
	
	/**
	 * Main method of that example
	 * @param args Arguments received from command line
	 */
	public static void main(String[] args) {
		if (args.length>0)
			for (int i=0; i<args.length; i++)
				System.out.println (args[i]);
		else
			System.out.println("Yeah");
	}
}
