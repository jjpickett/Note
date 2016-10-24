package drive;

import java.util.Scanner;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import exceptions.InvalidNoteException;
import model.Note;

public class Melody {
	static Note note = null;
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("This application will play an octave up from the note you give.");
		mainMenu();
	}

	private static void mainMenu() {
		String input;
		int option = 4;
		while(true){
			System.out.println("\n Please enter a value for the corresponding option.");
			System.out.println("1: Play octave by frequency\n"
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

	private static void createNoteWritten() {
		String input;
		while(true){
			System.out.println("\n Please write your note. Range from C-1 to G9. (Write 'back' to go back to Main Menu): ");
			input = sc.nextLine();
			if(input.equalsIgnoreCase("back"))
				mainMenu();
			try {
				note = new Note(input);
				if(note.getMIDIValue() > 103)
					System.out.println("\n This note is too high a value. There is not octave that can be played. ");
				else
					break;
			} catch (InvalidNoteException e) {
				System.out.println("\n\n" + input + " is an Incorrect Value!\n");
			}
					
			}
		playMelody();
	}

	private static void createNoteSemitones() {
		String input;
		while(true){
			System.out.println("\n Please write the amount of half tones/semitones. Range from -69 to 58. (Write 'back' to go back to Main Menu): ");
			input = sc.nextLine();
			if(input.equalsIgnoreCase("back"))
				mainMenu();
			try {
				note = new Note(Integer.parseInt(input));
				if(note.getMIDIValue() > 103)
					System.out.println("\n This note is too high a value. There is not octave that can be played. ");
				else
					break;
			} catch (InvalidNoteException e) {
				System.out.println("\n\n" + input + " is an Incorrect Value!\n");
			}
		}
		playMelody();
	}

	private static void createNoteFreq() {
		String input;
		while(true){
			System.out.println("\n Please write the frequency in hertz. Range from 8 to 12543.  (Write 'back' to go back to Main Menu): ");
			input = sc.nextLine();
			
			try {
				note = new Note(Double.parseDouble(input));
				if(note.getMIDIValue() > 103)
						System.out.println("\n This note is too high a value. There is not octave that can be played. ");
				else
					break;
			} catch (InvalidNoteException e) {
				System.out.println("\n\n" + input + " is an Incorrect Value!\n");
			}
		
			}
		playMelody();
	}

	private static void playMelody() {
		
		Synthesizer synthesizer = null;
		Instrument [] instruments;
		MidiChannel [] channels;
		Note comparable = null;
		
		try
	       {
			  comparable = new Note(note.getHalfSteps());
	          synthesizer = MidiSystem.getSynthesizer();
	          synthesizer.open();
	       }
	       catch(MidiUnavailableException mue)
	       {
	       } catch (InvalidNoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       instruments = synthesizer.getDefaultSoundbank().getInstruments();
	       synthesizer.loadInstrument(instruments[30]);
	       channels = synthesizer.getChannels();
	       
	       
	       while(Math.abs(note.compareTo(comparable)) <= 12){
	    	   channels[1].noteOn(comparable.getMIDIValue(), 127);
		       //sets the instrument to play the note.
		       channels[1].programChange(12);
		       System.out.println(comparable.getMIDIValue());
		       try
		       {
			    Thread.sleep(100);
		       }
		       catch(InterruptedException ie)
		       {
		       }
		       channels[1].noteOff(comparable.getMIDIValue(),127);
		       
		       try { 
				comparable.modifyNoteByHalfSteps(1);
				} catch (InvalidNoteException e) {
					break;
				}
		       
	       }
		mainMenu();
	}

}
