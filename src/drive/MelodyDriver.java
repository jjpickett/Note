package drive;

import model.Melody;
/**
 * Driver class that runs the Melody object.
 * @author Jordan
 * @version 1.0.0.0
 *
 */
public class MelodyDriver {
	/**
	 * This is the main constructor that will run the 
	 * Melody object
	 * @param args are the arguements being passed in
	 */
	public static void main(String[] args) {
		Melody m = new Melody();
		m.start();
	}
}
