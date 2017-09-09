package com.heliomug.music;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class MidiPlayer {
  private static final int DRUM_CHANNEL = 9;
  private static final int DEFAULT_VOLUME = 100;
  
	private static Synthesizer synth;
	private static MidiChannel[] channels;
	
	private static int[] notesOnChannels;
	private static int nextChannel;

	static {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();
			notesOnChannels = new int[channels.length];
			nextChannel = 0;
		} catch (MidiUnavailableException e) {
			System.err.println("CANNOT FIND MIDI.  EXITING...");
			e.printStackTrace();
		}
	}
	
	public static int getOffChannel(Note note) {
	  for (int i = 0; i < notesOnChannels.length; i++) {
	    if (notesOnChannels[i] == note.getValue()) {
	      return i;
	    }
	  }
	  return -1;
	}
	
	public static int getOnChannel(Note note) {
	  for (int i = 0; i < notesOnChannels.length; i++) {
	    if (notesOnChannels[i] == note.getValue()) {
	      return i;
	    }
	  }
	  int ind = nextChannel % channels.length;
	  if (ind == DRUM_CHANNEL) {
	    ind++;
	    nextChannel++;
	  }
	  notesOnChannels[ind] = note.getValue();
	  nextChannel++;
	  return ind;
	}
	
	public static void setInstrument(StandardInstrument instrument) {
	  for (MidiChannel channel : channels) {
	    channel.programChange(instrument.getCode());
	  }
	}
	
	private static void noteOn(int channel, int note, int vol) {
		channels[channel].noteOn(note, vol);
	}
	
	public static void noteOn(Note note, int volume) {
		noteOn(getOnChannel(note), note.getValue(), volume);
	}
    
	public static void noteOn(Note note) {
	  noteOn(note, DEFAULT_VOLUME);
	}
	
	public static void noteOff(Note note) {
	  int channelNumber = getOffChannel(note); 
		if (channelNumber >= 0) channels[channelNumber].allNotesOff();
	}

	public static void allNotesOff() {
		for (MidiChannel channel : channels) {
		  channel.allNotesOff();
		}
	}
}
