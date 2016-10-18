package exceptions;

/**
 * 
 * @author Jordan Pickett
 * @version 1.0.0
 * 
 */
@SuppressWarnings("serial")
public class InvalidNoteException extends Exception {
	
	/**
	 * Constructor for the postal code exception
	 */
	public InvalidNoteException(){
		
	}
	/**
	 * Main constructor that returns a message
	 * 
	 * @param msg	String that contains the error message
	 */
	public InvalidNoteException(String msg){
		super(msg);
	}

}
