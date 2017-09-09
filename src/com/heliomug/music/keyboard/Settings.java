package com.heliomug.music.keyboard;

import java.io.IOException;
import java.io.Serializable;

import com.heliomug.music.MidiPlayer;
import com.heliomug.music.Note;
import com.heliomug.music.StandardInstrument;
import com.heliomug.utils.FileUtils;

public class Settings implements Serializable {
  private static final long serialVersionUID = 1632505793730304688L;

  private static final StandardInstrument DEFAULT_INSTRUMENT = StandardInstrument.ORGAN_CHURCH;
  private static final int DEFAULT_VOLUME = 80;
  private static final Note DEFAULT_ROOT_NOTE = new Note(48);
  private static final KeyLayout DEFAULT_KEY_LAYOUT = KeyLayout.PIANO_HOMEROW_MIDDLE;
  private static final String SAVE_NAME = "keyboard.state";

  public static Settings loadSettings() { 
    Settings settings;
    try {
      settings = (Settings) FileUtils.loadObjectFromHeliomugDirectory(SAVE_NAME);
      return settings;
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
      return new Settings();
    }
  }
  
  private StandardInstrument instrument;
  private int volume;
  private KeyLayout keyLayout;
  private Note rootNote;
  
  private Settings() {
    instrument = DEFAULT_INSTRUMENT;
    volume = DEFAULT_VOLUME;
    rootNote = DEFAULT_ROOT_NOTE;
    keyLayout = DEFAULT_KEY_LAYOUT;
  }
  
  public StandardInstrument getActiveInstrument() {
    return instrument;
  }
  
  public KeyLayout getKeyLayout() {
    return keyLayout;
  }
  
  public Note getRootNote() {
    return rootNote;
  }
  
  public int getVolume() {
    return volume;
  }

  
  public void setInstrument(StandardInstrument instrument) {
    this.instrument = instrument;
    MidiPlayer.setInstrument(instrument);
  }
  
  public void setRootNote(Note note) {
    this.rootNote = note;
  }
  
  public void setLayout(KeyLayout keyLayout) {
    this.keyLayout = keyLayout;
  }
  
  public void setVolume(int volume) {
    this.volume = volume;
  }
  
  public void saveDefault() {
    try {
      FileUtils.saveObjectToHeliomugDirectory(this, SAVE_NAME);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}