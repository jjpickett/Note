import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.InvalidNoteException;
import model.Note;

public class NoteTest {
	Note masterNote;
	/**
	 * This is the set up class that creates the masterNote that
	 * will be used throughout this test.
	 * @throws InvalidNoteException if there is an error with the constructor
	 */
	@Before
	public void setUp() throws InvalidNoteException {
		 masterNote = new Note(440.0);
	}
	/**
	 * This will test to see if the frequency constructor works
	 * within the valid range. It is then checked with
	 * getFrequency to ensure the value is returned properly.
	 */
	@Test
	public void testValidFrequencyConstructor() {
		Note n = null;
		try {
			n = new Note(440.0);
		} catch (InvalidNoteException e) {
			fail("This did not compile properly");
		}
		assertEquals(440.0, n.getFrequencyInHz(), 0);
	}
	/**
	 * This will test to see if the half step constructor works
	 * within the valid range. It is then checked with
	 * getHalfSteps to ensure the value is returned properly.
	 */
	@Test
	public void testValidHalfstepConstructor() {
		Note n = null;
		try {
			n = new Note(-10);
		} catch (InvalidNoteException e) {
			fail("This did not compile properly");
		}
		assertEquals(-10, n.getHalfSteps(), 0);
	}	
	/**
	 * This will test to see if the note constructor works
	 * within the valid range. It is then checked with
	 * getMidiValue to ensure the value is returned properly.
	 */
	@Test
	public void testValidNoteConstructor() {
		Note n = null;
		try {
			n = new Note("C-1");
		} catch (InvalidNoteException e) {
			fail("This did not compile properly");
		}
		assertEquals(0, n.getMIDIValue(), 0);
	}
	/**
	 * This will test to see if the note constructor handles
	 * flats being used with the notes. It is then checked with
	 * getMidiValue to ensure the value is returned properly.
	 */
	@Test
	public void testValidFlatNoteConstructor() {
		Note n = null;
		try {
			n = new Note("F4b");
		} catch (InvalidNoteException e) {
			fail("This did not compile properly");
		}
		assertEquals(64, n.getMIDIValue(), 0);
	}
	/**
	 * This will test to see if the note constructor handles 'E'
	 * sharps being incorrect. It is then checked with
	 * getMidiValue to ensure the value is returned properly.
	 * @throws InvalidNoteException this is thrown as an exception is
	 * to be expected
	 */
	@Test(expected=InvalidNoteException.class)
	public void testInvalidSharpNoteConstructor() throws InvalidNoteException {
		Note n = new Note("E#4");
	}
	/**
	 * This will test to see if the note constructor handles
	 * sharps being used with the correct note. It is then checked with
	 * getMidiValue to ensure the value is returned properly.
	 */
	@Test
	public void testValidSharpNoteConstructor() {
		Note n = null;
		try {
			n = new Note("F#4");
		} catch (InvalidNoteException e) {
			fail("This did not compile properly");
		}
		assertEquals(66, n.getMIDIValue(), 0);
	}
	/**
	 * This will test to see if the constructor compiles the
	 * negative number and converts it to the lowest note possible.
	 * The frequency of the lowest note is checked against
	 * the constructed note.
	 */
	@Test
	public void testLowestFrequencyConstructor() {
		Note n = null;
		try {
			n = new Note(-50000.000);
		} catch (InvalidNoteException e) {
			fail("This did not compile properly");
		}
		assertEquals(8.175, n.getFrequencyInHz(), 0.001);
	}
	/**
	 * This is to check that an error occurs properly
	 * when the frequency is too high for the Note to 
	 * process.
	 * @throws InvalidNoteException this is thrown as an exception is
	 * to be expected
	 */
	@Test(expected = InvalidNoteException.class)
	public void testHighestFrequencyConstructor() throws InvalidNoteException {
		Note n = new Note(50000.0);
	}
	/**
	 * This is to check that an error occurs properly
	 * when the halfsteps given are too low for the Note
	 * to process
	 * @throws InvalidNoteException this is thrown as an exception is
	 * to be expected
	 */
	@Test(expected = InvalidNoteException.class)
	public void testInvalidLowHalfstepConstructor() throws InvalidNoteException {
		Note n = new Note(-1000);
	}
	/**
	 * This is to check that an error occurs properly
	 * when the halfsteps given are too high for the Note
	 * to process
	 * @throws InvalidNoteException this is thrown as an exception is
	 * to be expected
	 */
	@Test(expected = InvalidNoteException.class)
	public void testInvalidHighHalfstepConstructor() throws InvalidNoteException {
		Note n = new Note(1000);
	}
	/**
	 * This will test to see if the note constructor handles incorrect
	 * notes being given. It is then checked with
	 * getMidiValue to ensure the value is returned properly.
	 * @throws InvalidNoteException this is thrown as an exception is
	 * to be expected
	 */
	@Test(expected = InvalidNoteException.class)
	public void testInvalidNoteConstructor() throws InvalidNoteException {
		Note n = new Note("W4");
	}
	/**
	 * This will test to see if the note constructor handles incorrect
	 * octaves being given. It is then checked with
	 * getMidiValue to ensure the value is returned properly.
	 * @throws InvalidNoteException this is thrown as an exception is
	 * to be expected
	 */
	@Test(expected = InvalidNoteException.class)
	public void testInvalidOctaveConstructor() throws InvalidNoteException {
		Note n = new Note("C10");
	}
	/**
	 * This will test to ensure the getFrequency method
	 * is properly being retrieved from the masterNote.
	 * It is checked against a known value to ensure that
	 * it is working as inteded.
	 */
	@Test
	public void testGetFrequency(){
		double freq = masterNote.getFrequencyInHz();
		assertEquals(440.0, freq, 0);
	}
	/**
	 * This will test to ensure the getHalfSteps method
	 * is properly being retrieved from the masterNote.
	 * It is checked against a known value to ensure that
	 * it is working as inteded.
	 */
	@Test
	public void testGetHalfSteps(){
		int halfSteps = masterNote.getHalfSteps();
		assertEquals(0, halfSteps, 0);
	}
	/**
	 * This will test to ensure the getMidiValue method
	 * is properly being retrieved from the masterNote.
	 * It is checked against a known value to ensure that
	 * it is working as inteded.
	 */
	@Test
	public void testGetMidiValue(){
		double midiValue = masterNote.getMIDIValue();
		assertEquals(69, midiValue, 0);
	}
	/**
	 * This will test to ensure the formOctave method
	 * is being properly calculated. The masterNote
	 * checks to see if the otherNote is 12 steps above what
	 * it currently is.
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test
	public void testPositiveFormOctaveTrue() throws InvalidNoteException{
		Note otherNote = new Note(12);
		assertEquals(true, masterNote.formOctave(otherNote));
	}
	/**
	 * This will test to ensure the formOctave method
	 * is being properly calculated. The masterNote
	 * checks to see if the otherNote is 12 steps below what
	 * it currently is.
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test
	public void testNegativeFormOctaveTrue() throws InvalidNoteException{
		Note otherNote = new Note(-12);
		assertEquals(true, masterNote.formOctave(otherNote));
	}
	/**
	 * This will test to ensure the formOctave method
	 * is being properly calculated. The masterNote
	 * checks to see if the otherNote does not equal
	 * 12 and will return false.
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test
	public void testFormOctaveFalse() throws InvalidNoteException{
		Note otherNote = new Note(13);
		assertEquals(false, masterNote.formOctave(otherNote));
	}
	/**
	 * This will test to see if the modifyNoteByHalfSteps is
	 * properly subtracting the appropriate amount from the note.
	 * It checks the midivalue to ensure this is correct.
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test
	public void testModifyHalfNotesSubtraction() throws InvalidNoteException{
		masterNote.modifyNoteByHalfSteps(-10);
		assertEquals(59, masterNote.getMIDIValue());
	}
	/**
	 * This will test to see if the modifyNoteByHalfSteps is
	 * properly adding the appropriate amount from the note.
	 * It checks the midivalue to ensure this is correct.
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test
	public void testModifyHalfNotesAddition() throws InvalidNoteException{
		masterNote.modifyNoteByHalfSteps(10);
		assertEquals(79, masterNote.getMIDIValue());
	}
	/**
	 * This will test to see if the modifyNoteByHalfSteps is
	 * properly throwing an error when attempting to subtract
	 * too much.
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test(expected=InvalidNoteException.class)
	public void testModifyHalfNotesAdditionError() throws InvalidNoteException{
		masterNote.modifyNoteByHalfSteps(-1000000);
	}
	/**
	 * This will test to see if the modifyNoteByHalfSteps is
	 * properly throwing an error when attempting to add
	 * too much. 
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test(expected=InvalidNoteException.class)
	public void testModifyHalfNotesSubtractionError() throws InvalidNoteException{
		masterNote.modifyNoteByHalfSteps(10000000);
		assertEquals(79, masterNote.getMIDIValue());
	}
	/**
	 * This method will test to see if the compareTo method is working as
	 * intended by comparing the otherNote to the masterNote. 
	 * If the value is smaller, a negative number will be retrieved by
	 * the amount it is off by.
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test
	public void testCompareToLessThan() throws InvalidNoteException{
		Note otherNote = new Note(-10);
		int value = otherNote.compareTo(masterNote);
		assertEquals(-10, value, 0);
	}
	/**
	 * This method will test to see if the compareTo method is working as
	 * intended by comparing the otherNote to the masterNote. 
	 * If the value is greater, a possitive number will be retrieved by
	 * the amount it is off by.
	 * @throws InvalidNoteException is an exception being thrown for
	 * the Note constructor
	 */
	@Test
	public void testCompareToGreaterThan() throws InvalidNoteException{
		Note otherNote = new Note(10);
		int value = otherNote.compareTo(masterNote);
		assertEquals(10, value, 0);
	}
	/**
	 * This method will test to see if the compareTo method is working as
	 * intended. If the value the values are the same, a 0 will be returned
	 * to the user.
	 * @throws InvalidNoteException
	 */
	@Test
	public void testCompareToEqual() throws InvalidNoteException{
		Note otherNote = new Note(0);
		int value = otherNote.compareTo(masterNote);
		assertEquals(0, value, 0);
	}
}
