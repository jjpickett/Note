package model;

import exceptions.InvalidNoteException;
import utilities.NoteADT;

public class Note extends NoteADT{

	public Note(double frequency) throws InvalidNoteException {
		super(frequency);
	}
	public Note(int halfSteps) throws InvalidNoteException {
		super(halfSteps);
	}
	public Note(String stringNote) throws InvalidNoteException {
		super(stringNote);
	}
	@Override
	public double getFrequencyInHz() {
		return (Math.pow(HALFSTEP_INCREASE_IN_PITCH, (midiValue-CONCERT_PITCH_MIDI)))*CONCERT_PITCH_FREQUENCY;
	}
	@Override
	public int getHalfSteps() {
		return 	  this.midiValue - CONCERT_PITCH_MIDI;
	}
	@Override
	public int getMIDIValue() {
		return this.midiValue;
	}
	@Override
	public boolean formOctave(NoteADT note) {
		if(Math.abs(getHalfSteps()-note.getHalfSteps()) == 12)
			return true;
		return false;
	}
	@Override
	public void modifyNoteByHalfSteps(int numberOfHalfSteps) throws InvalidNoteException {
		if((this.midiValue + numberOfHalfSteps) > 127)
			throw new InvalidNoteException("Invalid Half Step Amount: " + numberOfHalfSteps + " brings the MIDI number beyond 127.");
		else if((this.midiValue + numberOfHalfSteps) < 0)
			throw new InvalidNoteException("Invalid Half Step Amount: " + numberOfHalfSteps + " brings the MIDI number below 0.");
		else
			this.midiValue = midiValue + numberOfHalfSteps;
	}
	@Override
	public int compareTo(NoteADT note) {
		Note otherNote = (Note)note;
		return this.midiValue - otherNote.midiValue;
	}

}
