package utilities;


import exceptions.InvalidNoteException;

/**
  *
  *
  * This is the contract specification for a musical note.
  * A note can be completely specified as one of the following:
  * 1. The frequency (number of cycles per second (in Hz)).
  * OR
  * 2. The number of half steps above a commonly agreed upon pitch
  *    (concert pitch=440Hz = A = MIDI 69).
  * OR
  * 3. The common music note name (C, D, E, F, G, A, B) with the
  *    the suffix indicating the octave number [-1,9] and an additional
  *    suffix prefix '#' for sharp notes and 'b' for flat notes.
  * OR
  * 4. The MIDI value [0,127] where 60 is middle C.
  *
  * Higher notes have higher frequencies. Two notes are an octave apart
  * (12 half steps) if one's frequency is twice the other. A half step is
  * approximately an increase in pitch (frequency) of 1.06 times higher.
  */

public abstract class NoteADT implements Comparable<NoteADT>
{

  protected int midiValue = -1;

   /**
   * A half step is approximately an increase in pitch of 1.06 times higher.
   */
  public static final double HALFSTEP_INCREASE_IN_PITCH = Math.pow(2.0,1.0/12.0);

  /**
   * The agreed upon pitch (note) "modern concert pitch".
   */
  public static final double CONCERT_PITCH_FREQUENCY = 440.0; //Hz
  public static final int    CONCERT_PITCH_MIDI = 69;
  
  /**
   * The high and low limits on the range of midi values.
   */
  public static final int    LOW_MIDI_VALUE = 0;
  public static final int    HIGH_MIDI_VALUE = 127;

  
  /**
   * Constructor for a note that accepts a frequency as a double (Hz).
   * Imperfect frequencies are tuned to the nearest half pitch.
   * A note that its MIDI value is outside the range of 0 - 127 is an invalid note and the InvalidNoteException is raised.
   * @param frequency of note
   * @throws InvalidNoteException is thrown when the midi note value falls outside of the range of 0-127
   */

  public NoteADT(double frequency) throws InvalidNoteException
  {
	  midiValue = (int)Math.round((Math.log(frequency/CONCERT_PITCH_FREQUENCY)
              /Math.log(HALFSTEP_INCREASE_IN_PITCH))+
              CONCERT_PITCH_MIDI);
	  midiNoteValueCheck(midiValue);
  }


  /**
   * Constructor for a note that accepts a number of half steps as an integer.
   * A note that its MIDI value is outside the range of 0 - 127 is an invalid note and the InvalidNoteException is raised.
   * @param half step value of note
   * @throws InvalidNoteException is thrown when the midi note value falls outside of the range of 0-127
   */
  public NoteADT(int halfSteps) throws InvalidNoteException
  {
	  midiValue = CONCERT_PITCH_MIDI + halfSteps;
      
      midiNoteValueCheck(midiValue);
  }

/**
   * The constructors for a note that accepts a String representation of a note such as C#4
   * A note that its MIDI value is outside the range of 0 - 127 is an invalid note and the InvalidNoteException is raised.
   * @param stringNote String representation of the note
   * @throws InvalidNoteException is thrown when the midi note value falls outside of the range of 0-127
   */
  public NoteADT(String stringNote) throws InvalidNoteException
  {
	char letter = Character.toUpperCase(stringNote.charAt(0));
	int number = -1;
	
	try{
		number = Integer.parseInt(stringNote.replaceAll("[^0-9]", "").trim());
	}catch(Exception e){
		number = -1;
	}
	
	switch(letter){
		case 'C':	midiValue = 12;
					break;
		case 'D':	midiValue = 14;
					break;
		case 'E':	midiValue = 16;
					break;
		case 'F':	midiValue = 17;
					break;
		case 'G':	midiValue = 19;
					break;
		case 'A':	midiValue = 21;
					break;
		case 'B':	midiValue = 23;
					break;
		default: 
			throw new InvalidNoteException("");
	}
	
	if(stringNote.contains("#")){
		if(letter!='E')
			midiValue++;
		else
			throw new InvalidNoteException("'E' cannot be a Sharp Note");
	}
		
	if(stringNote.substring(1, stringNote.length()).contains("b"))
		midiValue--;

	if(stringNote.contains("-1"))
		midiValue -= 12;
	else if((number >= 0 && number <= 8) || (number == 9 && midiValue < 20))
		midiValue += (number * 12);
	else
		midiValue = -1;
	
	midiNoteValueCheck(midiValue);
  }
  /**
   * Observer method that checks the midi notes calculated value and throws an exception
   * if the midi value is too high or too low.
   * 
   * Preconditions: A calculated midi value is passed in.
   * 
   * Postconditions: An error id thrown if the midiValue is to high or low.
   * 
   * @param midiValue is the int midiValue of the note
   */
  private void midiNoteValueCheck(int midiValue) throws InvalidNoteException {
	if(midiValue < LOW_MIDI_VALUE)
		throw new InvalidNoteException("This brings the MIDI value below 0.");
	else if(midiValue > HIGH_MIDI_VALUE)
		throw new InvalidNoteException("This brings the MIDI value above 127. ");
	
}
  /**
   * Transformer method that returns the frequency of note in cycles per second - Hertz (Hz)
   * 
   * Preconditions: A valid NoteADT object exists.
   * 
   * Postconditions: A double value representing the frequency of the note is returned.
   * 
   * @return The frequency of note in Hz.
   */
   public abstract double getFrequencyInHz();

  /**
   * Transformer method that returns the number of half steps above or below the concert pitch (440Hz or A4) of note.
   * 
   * Preconditions: A valid NoteADT object exists.
   * 
   * Postconditions:  An integer value representing the number of half steps of the note is returned.
   *
   * @return The number of half steps
   */
   public abstract int getHalfSteps();

  /**
   * Accessor method that returns the MIDI value of note.
   * 
   * Preconditions: A valid NoteADT object exists.
   * 
   * Postconditions: An integer value representing the MIDI value of the note is returned.
   *  
   * @return The MIDI value
   */
   public abstract int getMIDIValue();

   /**
    * Observer method returns true if the current note forms an octave with another note (if this note is 12 half steps above or 
    * 12 half steps below the other note).
    * 
    * Preconditions: A valid NoteADT object exists and a valid NoteADT object is passed in.
    * 
    * Postconditions: True is returned if the two notes are exactly 12 half steps apart.
    * 
    * @param note The other note
    * @return True if the two notes form an octave, false otherwise
    */
   public abstract boolean formOctave(NoteADT note);

   /**
    * Mutator method that raises or lowers the pitch (note) by any number of half steps.
    * 
    * Preconditions: A valid NoteADT object exists and an integer value representing the number of half steps is supplied.
    * 
    * Postconditions: The MIDI value of note is increased or decreased by the number of the half steps passed into
    * the method.
    * 
    * @param numberOfHalfSteps used to raise or lower the note MIDI value 
    * @throws InvalidNoteException if resulting note has a MIDI value (after being modified) outside of the valid range of 0 to 127
    */
    public abstract void modifyNoteByHalfSteps(int numberOfHalfSteps) throws InvalidNoteException;

   /**
    * The compareTo method will be implemented by the Comparable interface
    * it will adhere to the Comparable interface contract referenced in
    * the Java API - java.lang.*; and will have the following signature.
    * public int comparteTo(NoteADT note);.
    */
    public abstract int compareTo(NoteADT note);
}