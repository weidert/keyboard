package com.heliomug.music.keyboard;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import com.heliomug.music.MidiPlayer;
import com.heliomug.music.Note;
import com.heliomug.music.StandardInstrument;

public class Session {
  private static Session theSession;
  
  public static Session getTheSession() {
    if (theSession == null) { 
      theSession = new Session();
    }
    return theSession;
  }
  
  public static void resetToDefaults() {
    theSession = new Session(); 
  }
  
  private Settings settings;
  private transient Map<Integer, Boolean> keysDown;
  private transient Recorder recorder;
  
  private Session() {
    settings = Settings.loadSettings();
    keysDown = new HashMap<>();
    recorder = new Recorder();
    MidiPlayer.setInstrument(settings.getActiveInstrument());
  }
  
  public KeyLayout getKeyLayout() {
    return settings.getKeyLayout();
  }
  
  public int getOffset(Note note) {
    return settings.getRootNote().distanceTo(note);
  }
  
  public Note getNote(int noteOffset) {
    return noteOffset >= 0 ? settings.getRootNote().getHigher(noteOffset) : null;
  }

  
  public void setInstrument(StandardInstrument instrument) {
    settings.setInstrument(instrument);
    MidiPlayer.setInstrument(instrument);
  }
  
  public void setLayout(KeyLayout keyLayout) {
    settings.setLayout(keyLayout);
  }
  
  public void setVolume(int volume) {
    settings.setVolume(volume);
  }
  
  public void setRootNote(Note rootNote) {
    settings.setRootNote(rootNote);
  }
  
  public void pressNote(Note note) {
    recorder.recordNoteOn(note);
    MidiPlayer.noteOn(note, settings.getVolume());
  }
  
  public void releaseNote(Note note) {
    recorder.recordNoteOff(note);
    MidiPlayer.noteOff(note);
  }

  public void robotOn(Note note) {
    MidiPlayer.noteOn(note, settings.getVolume());
  }
  
  public void robotOff(Note note) {
    MidiPlayer.noteOff(note);
  }
  
  public void robotAllOff() {
    MidiPlayer.allNotesOff();
  }
  
  public void recorderReset() {
    this.recorder = new Recorder();
  }
  
  public void recorderPlay() {
    this.recorder.play();
  }
  
  public void recorderStop() {
    this.recorder.stop();
  }
  
  public void handleKeyDown(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_ESCAPE) {
      MidiPlayer.allNotesOff();
    }
    if (!keysDown.containsKey(keyCode) || !keysDown.get(keyCode) && e.getModifiers() == 0) {
      KeyPanel.getThePanel().whiteKey(keyCode);
      keysDown.put(keyCode, true);
      int offset = settings.getKeyLayout().getNoteOffset(keyCode);
      Note note = getNote(offset);
      if (note != null && note.getValue() > 0) {
        pressNote(note);
      }
    }
  }
  
  public void handleKeyUp(KeyEvent e) {
    int keyCode = e.getKeyCode();
    KeyPanel.getThePanel().recolorKey(keyCode);
    if (keysDown.containsKey(keyCode) && keysDown.get(keyCode)) {
      keysDown.put(keyCode, false);
      int offset = settings.getKeyLayout().getNoteOffset(keyCode);
      Note note = getNote(offset);
      if (note != null && note.getValue() > 0) {
        releaseNote(note);
      }
    }
  }
  
  public void saveDefault() {
    settings.saveDefault();
  }
}