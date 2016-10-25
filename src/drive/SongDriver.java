package drive;

import java.io.IOException;
import model.Song;
/**
 * Driver class that runs the Song object.
 * @author Jordan
 * @version 1.0.0.0
 *
 */
public class SongDriver {
	/**
	 * This is the main constructor that will run the 
	 * Song object
	 * @param args are the arguements being passed in. These will contain
	 * the name of the song.
	 */
	public static void main(String[] args) {
		Song s = new Song();
		try {
			s.getNotes(args[0].trim());
		} catch (IOException e) {
			System.out.println(args[0] + " is invalid. Please use a different name." );
			e.printStackTrace();
		}
		s.playSong();
	}
}
