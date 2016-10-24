package drive;

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
import model.Note;

public class Song {
	private Note note = null;
	private ArrayList<Character> noteLength;
	private ArrayList<Note> notes;
	
	Synthesizer synthesizer = null;
	Instrument [] instruments;
	MidiChannel [] channels;
	
	public static void main(String[] args) {
		Song s = new Song();
		s.getNotes();
		s.playSong();
	}
	
	public Song(){
		noteLength = new ArrayList<Character>();
		notes = new ArrayList<Note>();

		try
	       {
	          synthesizer = MidiSystem.getSynthesizer();
	          synthesizer.open();
	       }
	       catch(MidiUnavailableException mue)
	       {
	       }
	       instruments = synthesizer.getDefaultSoundbank().getInstruments();
	       synthesizer.loadInstrument(instruments[30]);
	       channels = synthesizer.getChannels();
	}
	
	public void getNotes(){
		BufferedReader br = null;
		String fileLine = null;
		
		try{
			br = new BufferedReader(new FileReader("res/testsong.txt"));
			while((fileLine = br.readLine()) != null){
				String[] fileNote = fileLine.split(",");
				for(String s: fileNote){
					try{
						if(s.equalsIgnoreCase("r"))
							noteLength.add('r');
						else if(s.equalsIgnoreCase("r-"))
							noteLength.add('s');
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
						System.out.println(s + " is an Invalid Note. Skipping...\n");
					}
				}
					
			}
				
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	private void playSong() {
		
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
				       //sets the instrument to play the note.
				       channels[1].programChange(12);
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
