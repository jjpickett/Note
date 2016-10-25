package exceptions;

/**
 * This is a custom exception created
 * for the creation of notes.
 * @author Jordan Pickett
 * @version 1.0.0.0
 * 
 */
public class InvalidNoteException extends Exception {
	/**
	 * Constructor for the postal code exception
	 */
	public InvalidNoteException(){}
	/**
	 * Main constructor that returns a message
	 * 
	 * @param msg	String that contains the error message
	 */
	public InvalidNoteException(String msg){
		super(msg);
	}
}
