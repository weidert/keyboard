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
  private static final Note DEFAULT_ROOT_NOTE = new Note(48);

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
  private Note rootNote;
  private Map<Integer, Boolean> keysDown;
  
  private Session() {
    instrument = DEFAULT_INSTRUMENT;
    volume = DEFAULT_VOLUME;
    rootNote = DEFAULT_ROOT_NOTE;
    MidiPlayer.setInstrument(instrument);
    keyLayout = KeyLayout.getDefault();
    keysDown = new HashMap<>();
  }

  
  public String getStatusLine() {
    return String.format("Instrument: %s, Volume: %d, Layout: %s", instrument.getName(), volume, keyLayout.getName());
  }
  
  public StandardInstrument getActiveInstrument() {
    return instrument;
  }
  
  public KeyLayout getKeyLayout() {
    return keyLayout;
  }
  
  public Note getNote(int noteOffset) {
    return noteOffset >= 0 ? this.rootNote.getHigher(noteOffset) : null;
  }

  
  public void setInstrument(StandardInstrument instrument) {
    MidiPlayer.setInstrument(instrument);
  }
  
  public void setLayout(KeyLayout keyLayout) {
    this.keyLayout = keyLayout;
  }
  
  public void setVolume(int volume) {
    this.volume = volume;
  }
  
  public void setRootNote(Note rootNote) {
    this.rootNote = rootNote;
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
      int offset = keyLayout.getNoteOffset(keyCode);
      Note note = getNote(offset);
      if (note != null && note.getValue() > 0) {
        noteOn(note);
      }
    }
  }
  
  public void handleKeyUp(KeyEvent e) {
    int keyCode = e.getKeyCode();
    KeyPanel.getThePanel().recolorKey(keyCode);
    if (keysDown.containsKey(keyCode) && keysDown.get(keyCode)) {
      keysDown.put(keyCode, false);
      int offset = keyLayout.getNoteOffset(keyCode);
      Note note = getNote(offset);
      if (note != null && note.getValue() > 0) {
        noteOff(note);
      }
    }
  }
}