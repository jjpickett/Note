package model;

import exceptions.InvalidNoteException;
import utilities.NoteADT;
/**
 * @see NoteADT
 */
public class Note extends NoteADT{
	/**
	 * @see NoteADT#NoteADT(double)
	 * @param frequency of note
	 * @throws InvalidNoteException is thrown when the midi note value falls outside of the range of 0-127
	 */
	public Note(double frequency) throws InvalidNoteException {
		super(frequency);
	}
	/**
	 * @see NoteADT#NoteADT(int)
	 * @param halfSteps value of note
	 * @throws InvalidNoteException is thrown when the midi note value falls outside of the range of 0-127
	 */
	public Note(int halfSteps) throws InvalidNoteException {
		super(halfSteps);
	}
	/**
	 * @see NoteADT#NoteADT(String)
	 * @param stringNote String representation of the note
	 * @throws InvalidNoteException is thrown when the midi note value falls outside of the range of 0-127
	 */
	public Note(String stringNote) throws InvalidNoteException {
		super(stringNote);
	}
	/**
	 * @see NoteADT#getFrequencyInHz()
	 */
	@Override
	public double getFrequencyInHz() {
		return (Math.pow(HALFSTEP_INCREASE_IN_PITCH, (midiValue-CONCERT_PITCH_MIDI)))*CONCERT_PITCH_FREQUENCY;
	}
	/**
	 * @see NoteADT#getHalfSteps()
	 */
	@Override
	public int getHalfSteps() {
		return 	  this.midiValue - CONCERT_PITCH_MIDI;
	}
	/**
	 * @see NoteADT#getMIDIValue()
	 */
	@Override
	public int getMIDIValue() {
		return this.midiValue;
	}
	/**
	 * @see NoteADT#formOctave(NoteADT)
	 */
	@Override
	public boolean formOctave(NoteADT note) {
		if(Math.abs(getHalfSteps()-note.getHalfSteps()) == 12)
			return true;
		return false;
	}
	/**
	 * @see NoteADT#modifyNoteByHalfSteps(int)
	 */
	@Override
	public void modifyNoteByHalfSteps(int numberOfHalfSteps) throws InvalidNoteException {
		if((this.midiValue + numberOfHalfSteps) > 127)
			throw new InvalidNoteException("Invalid Half Step Amount: " + numberOfHalfSteps + " brings the MIDI number beyond 127.");
		else if((this.midiValue + numberOfHalfSteps) < 0)
			throw new InvalidNoteException("Invalid Half Step Amount: " + numberOfHalfSteps + " brings the MIDI number below 0.");
		else
			this.midiValue = midiValue + numberOfHalfSteps;
	}
	/**
	 * @see NoteADT#compareTo(NoteADT)
	 */
	@Override
	public int compareTo(NoteADT note) {
		Note otherNote = (Note)note;
		return this.midiValue - otherNote.midiValue;
	}

}
