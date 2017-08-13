package com.heliomug.music.gui;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import com.heliomug.music.MidiPlayer;
import com.heliomug.music.Note;
import com.heliomug.music.StandardInstrument;

public class Session {
  private static final StandardInstrument DEFAULT_INSTRUMENT = StandardInstrument.PIANO_GRAND;
  private static final int DEFAULT_VOLUME = 80;

  private static Session theSession;
  
  public static Session getTheSession() {
    if (theSession == null) { 
      theSession = new Session();
    }
    return theSession;
  }
  
  
  private StandardInstrument instrument;
  private int volume;
  private KeyLayout keyLayout;
  private Map<Integer, Boolean> keysDown;
  
  private Session() {
    instrument = DEFAULT_INSTRUMENT;
    volume = DEFAULT_VOLUME;
    MidiPlayer.setInstrument(instrument);
    keyLayout = KeyLayout.getDefault();
    keysDown = new HashMap<>();
  }
  
  public String getStatusLine() {
    return String.format("Instrument: %s, Volume: %d, Layout: %s", instrument.getName(), volume, keyLayout.getName());
  }
  
  public void setInstrument(StandardInstrument instrument) {
    MidiPlayer.setInstrument(instrument);
  }
  
  public void setNoteMap(KeyLayout noteMap) {
    this.keyLayout = noteMap;
  }
  
  public StandardInstrument getActiveInstrument() {
    return instrument;
  }
  
  public void setVolume(int volume) {
    this.volume = volume;
  }
  
  public void noteOn(Note note) {
    MidiPlayer.noteOn(note, volume);
  }
  
  public void noteOff(Note note) {
    MidiPlayer.noteOff(note);
  }
  
  public void handleKeyDown(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_ESCAPE) {
      MidiPlayer.allNotesOff();
    }
    if (!keysDown.containsKey(keyCode) || !keysDown.get(keyCode) && e.getModifiers() == 0) {
      KeyPanel.getThePanel().whiteKey(keyCode);
      keysDown.put(keyCode, true);
      Note note = keyLayout.getNote(keyCode);
      if (note != null && note.getValue() > 0) {
        noteOn(note);
      }
    }
  }
  
  public void handleKeyUp(KeyEvent e) {
    int code = e.getKeyCode();
    KeyPanel.getThePanel().recolorKey(code);
    if (keysDown.containsKey(code) && keysDown.get(code)) {
      keysDown.put(code, false);
      Note note = keyLayout.getNote(code);
      if (note != null && note.getValue() > 0) {
        noteOff(note);
      }
    }
  }
}