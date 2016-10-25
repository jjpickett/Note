package model;

import java.util.Scanner;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import exceptions.InvalidNoteException;
/**
 * This is the Melody class which controls the
 * way in which a melody is created by choosing
 * a note. The octave will then be played one note at a time.
 * @author Jordan
 * @version 1.0.0.0
 */
public class Melody {
	private Note note;
	private Scanner sc;
	/**
	 * This is the main constructor that will instantiate
	 * the global Note variable and the global Scanner 
	 * variable.
	 */
	public Melody(){
		note = null;
		sc = new Scanner(System.in);
	}
	/**
	 * This method is the main menu of the melody system. It will
	 * ask for user input and then change the menu based on the
	 * users decision. If there are any invalid inputs, it will
	 * let the user know and then give the options to the user again. 
	 */
	public void start() {
		String input;
		int option = 4;
		while(true){
			System.out.println("This application will play an octave up from the note you give.");
			System.out.println("\nPlease enter a value for the corresponding option.\n"
							+ "1: Play octave by frequency\n"
							+ "2: Play octave by half steps/semitones\n"
							+ "3: Play octave by writing the note\n"
							+ "4: Exit");
			input = sc.nextLine().trim();
			try{
				option = Integer.parseInt(input);
				if(option > 0 && option < 5)
					break;
				else
					System.out.println("\n\n" + input + " is an Incorrect Value!\n");
			}catch(Exception e){
				System.out.println("\n\n" + input + " is an Incorrect Value!\n");
			}
		}
		switch(option){
		case 1: createNoteFreq();
				break;
		case 2: createNoteSemitones();
				break;
		case 3: createNoteWritten();
				break;
		case 4: System.out.println("Goodbye!");
				System.exit(0);
				break;
		}	
	}
	/**
	 * This method is if the user is trying to write a note using
	 * a String value. If the note is invalid, the user will be notified
	 * and can try again. If the note is too high and an octave cannot
	 * be formed, the user can try again. If they wish to go back, they can write
	 * 'back' and be returned to the main menu. If the value is good, it will go to 
	 * the playMelody() method.
	 */
	private void createNoteWritten() {
		String input;
		while(true){
			System.out.println("\n Please write your note. Range from C-1 to G9. (Write 'back' to go back to Main Menu): ");
			input = sc.nextLine();
			if(input.equalsIgnoreCase("back"))
				start();
			try{
				note = new Note(input);
				if(note.getMIDIValue() > 115)
					System.out.println("\n This note is too high a value. There is not octave that can be played. ");
				else
					break;
			}catch(InvalidNoteException e) {
				System.out.println("\n\n" + input + " is an Invalid Note!\n");
			}
		}
		playMelody();
	}
	/**
	 * This method is if the user is trying to write a note using
	 * semitones. If the note is invalid, the user will be notified
	 * and can try again. If the note is too high and an octave cannot
	 * be formed, the user can try again. If they wish to go back, they can write
	 * 'back' and be returned to the main menu. If the value is good, it will go to 
	 * the playMelody() method.
	 */
	private void createNoteSemitones() {
		String input;
		while(true){
			System.out.println("\n Please write the amount of half tones/semitones. Range from -69 to 58. (Write 'back' to go back to Main Menu): ");
			input = sc.nextLine();
			if(input.equalsIgnoreCase("back"))
				start();
			try{
				note = new Note(Integer.parseInt(input));
				if(note.getMIDIValue() > 115)
					System.out.println("\n This note is too high a value. There is not octave that can be played. ");
				else
					break;
			}catch(InvalidNoteException e) {
				System.out.println("\n\n" + input + " is an Incorrect Value! " + e.getMessage());
			}
		}
		playMelody();
	}
	/**
	 * This method is if the user is trying to write a note using
	 * a String value. If the note is invalid, the user will be notified
	 * and can try again. If the note is too high and an octave cannot
	 * be formed, the user can try again. If they wish to go back, they can write
	 * 'back' and be returned to the main menu. If the value is good, it will go to 
	 * the playMelody() method.
	 */
	private void createNoteFreq() {
		String input;
		while(true){
			System.out.println("\n Please write the frequency in hertz. Maximum being 6271.  (Write 'back' to go back to Main Menu): ");
			input = sc.nextLine();
			if(input.equalsIgnoreCase("back"))
				start();
			try{
				note = new Note(Double.parseDouble(input));
				if(note.getMIDIValue() > 115)
						System.out.println("\n This note is too high a value. There is no octave that can be played. ");
				else
					break;
			}catch(InvalidNoteException e) {
				System.out.println("\n\n" + input + " is too high a value. There is no octave for this value.!\n");
			}
		}
		playMelody();
	}
	/**
	 * This method is what will play the notes from the value given
	 * by the user. It will play a single note incrementally upwards
	 * until it reaches its octave.
	 */
	private void playMelody() {
		Synthesizer synthesizer = null;
		Instrument [] instruments;
		MidiChannel [] channels;
		Note comparable = null;
		try{
			  comparable = new Note(note.getHalfSteps());
	          synthesizer = MidiSystem.getSynthesizer();
	          synthesizer.open();
	       }catch(MidiUnavailableException | InvalidNoteException e){
	    	   e.printStackTrace();
	       }
	       instruments = synthesizer.getDefaultSoundbank().getInstruments();
	       synthesizer.loadInstrument(instruments[30]);
	       channels = synthesizer.getChannels();
	       
	       while(Math.abs(note.compareTo(comparable)) <= 12){
	    	   channels[1].noteOn(comparable.getMIDIValue(), 127);
		       System.out.println("Midi Value: " + comparable.getMIDIValue());
		       try{
			    Thread.sleep(100);
		       }catch(InterruptedException ie){
		    	   ie.printStackTrace();
		       }
		       channels[1].noteOff(comparable.getMIDIValue(),127);
		       try{ 
				comparable.modifyNoteByHalfSteps(1);
				}catch(InvalidNoteException e) {
					break;
				}
	       }
	   start();
	}

}
