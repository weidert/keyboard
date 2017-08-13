package com.heliomug.music;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Note {
	private static final int INTERVALS_IN_CHROMATIC = 12;
	
	private static final List<Integer> MAJOR_NOTES = Arrays.asList(0, 2, 4, 5, 7, 9, 11);
	private static final float[] COLORS = {
	    0f, //red C
	    0.0417f,
	    0.0833f, //orange D
	    0.1250f,
	    0.1667f, //yellow E
	    0.33f,  //green F
	    0.4167f, 
	    0.5f,  //cyan G
	    0.5833f,
	    0.6667f, // blue A
	    0.75f,
	    0.833f, // purple B
	};
	    
	    

	private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	private static final String[] NOTE_LABELS = {"C", "C#/D\u266D", "D", "D#/E\u266D", "E", "F", "F#/G\u266D", "G", "G#/A\u266D", "A", "A#/B\u266D", "B"};
	
	private static final Map<String, Integer> NOTE_NAME_MAP = new HashMap<>();
	{
		NOTE_NAME_MAP.put("Cb", 11);
		NOTE_NAME_MAP.put("C", 0);
		NOTE_NAME_MAP.put("C#", 1);
		NOTE_NAME_MAP.put("Db", 1);
		NOTE_NAME_MAP.put("D", 2);
		NOTE_NAME_MAP.put("D#", 3);
		NOTE_NAME_MAP.put("Eb", 3);
		NOTE_NAME_MAP.put("E", 4);
		NOTE_NAME_MAP.put("E#", 5);
		NOTE_NAME_MAP.put("Fb", 4);
		NOTE_NAME_MAP.put("F", 5);
		NOTE_NAME_MAP.put("F#", 6);
		NOTE_NAME_MAP.put("Gb", 6);
		NOTE_NAME_MAP.put("G", 7);
		NOTE_NAME_MAP.put("G#", 8);
		NOTE_NAME_MAP.put("Ab", 8);
		NOTE_NAME_MAP.put("A", 9);
		NOTE_NAME_MAP.put("A#", 10);
		NOTE_NAME_MAP.put("Bb", 10);
		NOTE_NAME_MAP.put("B", 11);
		NOTE_NAME_MAP.put("B#", 0);
	}
	
	private final int value;
	
	public Note(int val) {
		assert (val >= 0 && val < 1024);
		this.value = val;
	}
	
	public int getValue() { return this.value; }
	
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (!(other instanceof Note)) {
			return false;
		}
		
		Note otherNote = (Note) other;
		return otherNote.value == this.value;
	}
	
	public String longName() {
		return String.format("%2s%d", getNoteName(), value / 12);
	}

	public String toString() {
		return String.format("%s", getNoteName());
	}

	public Note getHigher(int offset) {
		return new Note(value + offset);
	}
	
	public int difference(Note other) {
		return this.value - other.value;
	}

  public int distanceTo(Note other) {
    return other.value - this.value;
  }

  public Note nextAbove(Note other) {
    return getHigher(distanceTo(other) % 12);
  }
	
	public boolean isSameNoteLetter(Note other) {
		return ((this.value - other.value) % INTERVALS_IN_CHROMATIC == 0); 
	}
	
	public String getNoteName() {
		return NOTE_NAMES[value % INTERVALS_IN_CHROMATIC];
	}
	
	public String getNoteLabel() {
		return NOTE_LABELS[value % INTERVALS_IN_CHROMATIC];
	}
	
	public boolean isMajor() {
	  return MAJOR_NOTES.contains(value % INTERVALS_IN_CHROMATIC);
	}
	
	public Color getColor() {
	  int baseValue = value % INTERVALS_IN_CHROMATIC;
	  float hue = COLORS[baseValue];
	  float sat = isMajor() ? 1.0f : 0.5f;
	  float brightness = 0.7f;
	  return Color.getHSBColor(hue, sat, brightness);
	}
}
