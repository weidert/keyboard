package com.heliomug.music.keyboard;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.heliomug.music.MidiPlayer;
import com.heliomug.music.Note;
import com.heliomug.music.StandardInstrument;
import com.heliomug.utils.FileUtils;

public class Session implements Serializable {
  private static final long serialVersionUID = 1632505793730304688L;

  private static final StandardInstrument DEFAULT_INSTRUMENT = StandardInstrument.ORGAN_CHURCH;
  private static final int DEFAULT_VOLUME = 80;
  private static final Note DEFAULT_ROOT_NOTE = new Note(48);
  private static final KeyLayout DEFAULT_KEY_LAYOUT = KeyLayout.PIANO_HOMEROW_MIDDLE;
  private static final String SAVE_NAME = "keyboard.state";

  private static Session theSession;
  
  public static Session getTheSession() {
    if (theSession == null) { 
      theSession = loadDefault();
    }
    return theSession;
  }
  
  public static Session loadDefault() { 
    Session session;
    try {
      session = (Session) FileUtils.loadObjectFromHeliomugDirectory(SAVE_NAME);
      session.keysDown = new HashMap<>();
      session.finishSetup();
      return session;
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
      return new Session();
    }
  }
  
  public static void resetToDefaults() {
    theSession = new Session(); 
  }
  
  
  private StandardInstrument instrument;
  private int volume;
  private KeyLayout keyLayout;
  private Note rootNote;
  private transient Map<Integer, Boolean> keysDown;
  
  private Session() {
    instrument = DEFAULT_INSTRUMENT;
    volume = DEFAULT_VOLUME;
    rootNote = DEFAULT_ROOT_NOTE;
    keyLayout = DEFAULT_KEY_LAYOUT;
    keysDown = new HashMap<>();
    finishSetup();
  }
  
  private void finishSetup() {
    MidiPlayer.setInstrument(instrument);
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
    this.instrument = instrument;
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
  
  public void saveDefault() {
    try {
      FileUtils.saveObjectToHeliomugDirectory(this, SAVE_NAME);
    } catch (IOException e) {
      // guess we're not saving then
    }
  }
}