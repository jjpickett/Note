package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import exceptions.InvalidNoteException;
/**
 * This is the Song class that controls the way
 * a song is played.
 * @author Jordan
 * @version 1.0.0.0
 */
public class Song {
	private Note note = null;
	private ArrayList<Character> noteLength;
	private ArrayList<Note> notes;
	
	Synthesizer synthesizer = null;
	Instrument [] instruments;
	MidiChannel [] channels;
	/**
	 * This is the main constructor which is empty. It instantiates
	 * the arraylist for the length in which the note is played
	 * and the value of the notes themselves. It also instantiates
	 * the Synthesizer, the Instrument and the MidiChannel that
	 * will be used to play the song.
	 */
	public Song(){
		noteLength = new ArrayList<Character>();
		notes = new ArrayList<Note>();

		try{
	          synthesizer = MidiSystem.getSynthesizer();
	          synthesizer.open();
	       }catch(MidiUnavailableException mue){
	    	   mue.printStackTrace();
	       }
	       instruments = synthesizer.getDefaultSoundbank().getInstruments();
	       synthesizer.loadInstrument(instruments[30]);
	       channels = synthesizer.getChannels();
	}
	/**
	 * This method retrieves the notes from the file given and
	 * then applies them to the notes ArrayList. It also checks
	 * for the length in which the note will be played and applies
	 * it to the noteLength ArrayList.
	 * @param file this is a string of the filepath being passed
	 * @throws IOException this exception is thrown if there is an
	 * issue reading the file
	 */
	public void getNotes(String file) throws IOException{
		BufferedReader br = null;
		String fileLine = null;
		
			br = new BufferedReader(new FileReader("res/"+file+".txt"));
			while((fileLine = br.readLine()) != null){
				String[] fileNote = fileLine.split(",");
				for(String s: fileNote){
					try{
						if(s.equalsIgnoreCase("r"))
							noteLength.add('r');
						else if(s.equalsIgnoreCase("r-"))
							noteLength.add('s');
						else if(Character.isDigit(s.charAt(0)) || (s.charAt(0) == '-' && Character.isDigit(s.charAt(1)))){
							if(s.contains("."))
								note = new Note(Double.parseDouble(s));
							else
								note = new Note(Integer.parseInt(s));
						}		
						else{
							note = new Note(s);
							notes.add(note);
							s = s.toLowerCase();
							if(s.charAt(s.length()-1)== '-')
								noteLength.add('-');
							else
								noteLength.add('n');
						}
					}catch(InvalidNoteException ne){
						System.out.print(s + " is an invalid value." + ne.getMessage() + " Skipping...\n");
					}
				}					
			}
			br.close();
	}
	/**
	 * This method plays the song by reading the notes from the
	 * the arrayList. Each note length is determined first in
	 * case there is a rest that needs to be played. Then the
	 * appropriate note is played for the specified length.
	 */
	public void playSong() {
		int offset = 0;
	       for(Character c : noteLength){
	    	   try{
		    	   if(c == 'r'){
		    		   Thread.sleep(200);
		    		   offset--;
		    	   }
		    	   else if(c == 's'){
		    		   Thread.sleep(400);
		    		   offset--;
		    	   }else{
		    		   channels[1].noteOn(notes.get(offset).getMIDIValue(), 127);
				       if(c == '-')
				    	   Thread.sleep(400);
				       else if(c == 'n')
				    	   Thread.sleep(200);
				       else
				    	   Thread.sleep(200);
				       channels[1].noteOff(notes.get(offset).getMIDIValue(), 127);			    	   
	
				       offset++;   
		    	   }
		       }catch(InterruptedException e){
		    	   e.printStackTrace();
		       }
	       }
	}
}
