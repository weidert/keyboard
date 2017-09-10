package com.heliomug.music.keyboard;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import com.heliomug.music.MidiPlayer;
import com.heliomug.music.Note;
import com.heliomug.music.StandardInstrument;
import com.heliomug.music.keyboard.gui.Frame;
import com.heliomug.music.keyboard.gui.PanelKey;

public class Session {
  private static Session theSession;
  
  public static Session getTheSession() {
    if (theSession == null) { 
      theSession = new Session();
    }
    return theSession;
  }
  
  private Settings settings;
  private Map<Integer, Boolean> keysDown;
  private boolean isRecording;
  private Recording recording;
  
  private Session() {
    settings = Settings.loadSettings();
    keysDown = new HashMap<>();
    isRecording = false;
    recording = new Recording();
    MidiPlayer.setInstrument(settings.getInstrument());
  }
  
  public void resetSettings() {
    settings = Settings.getFreshSettings();
    MidiPlayer.setInstrument(settings.getInstrument());
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

  public Settings getSettings() {
    return settings;
  }
  
  public boolean isRecording() {
    return isRecording;
  }
  
  public Recording getRecording() {
    return recording;  
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

  
  public void handleKeyDown(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_ESCAPE) {
      MidiPlayer.allNotesOff();
    }
    if (!keysDown.containsKey(keyCode) || !keysDown.get(keyCode) && e.getModifiers() == 0) {
      PanelKey.getThePanel().whiteKey(keyCode);
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
    PanelKey.getThePanel().recolorKey(keyCode);
    if (keysDown.containsKey(keyCode) && keysDown.get(keyCode)) {
      keysDown.put(keyCode, false);
      int offset = settings.getKeyLayout().getNoteOffset(keyCode);
      Note note = getNote(offset);
      if (note != null && note.getValue() > 0) {
        releaseNote(note);
      }
    }
  }
  
  public void pressNote(Note note) {
    if (isRecording) recording.recordNoteOn(note);
    MidiPlayer.noteOn(note, settings.getVolume());
  }
  
  public void releaseNote(Note note) {
    if (isRecording) recording.recordNoteOff(note);
    MidiPlayer.noteOff(note);
  }


  public void robotOn(Note note) {
    MidiPlayer.noteOn(note, settings.getVolume());
    int offset = getOffset(note);
    int keyCode = settings.getKeyLayout().getKeyCode(offset);
    if (keyCode >= 0) {
      PanelKey.getThePanel().whiteKey(keyCode);
    }
  }
  
  public void robotOff(Note note) {
    MidiPlayer.noteOff(note);
    int offset = getOffset(note);
    int keyCode = settings.getKeyLayout().getKeyCode(offset);
    if (keyCode >= 0) {
      PanelKey.getThePanel().recolorKey(keyCode);
    }
  }
  
  public void robotAllOff() {
    MidiPlayer.allNotesOff();
    PanelKey.getThePanel().recolorAll();
  }
  
  public void recorderStartRecording() {
    this.recording = new Recording();
    this.isRecording = true;
    Frame.update();
  }
  
  public void recorderStopRecording() {
    this.isRecording = false;
    Frame.update();
  }
  
  public void recorderStartPlayback() {
    this.recording.startPlayback();
  }
  
  public void recorderStopPlayback() {
    this.recording.stopPlayback();
  }

  
  public void saveDefault() {
    settings.saveDefault();
  }
}