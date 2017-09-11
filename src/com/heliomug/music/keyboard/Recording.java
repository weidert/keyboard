package com.heliomug.music.keyboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.heliomug.music.Note;

public class Recording implements Serializable {
  private static final long serialVersionUID = -2136605424538274565L;
  
  private transient long startTime;
  private transient boolean isActive;

  private List<NoteEvent> noteEvents;
  private Note minNote;
  private Note maxNote;
  private long maxTime;
  
  public Recording() {
    startTime = System.currentTimeMillis();
    noteEvents = new ArrayList<>();
    minNote = maxNote = null;
    maxTime = 0;
    isActive = true;
  }
  
  public boolean isActive() { return isActive; }

  public long getElapsed() {
    return System.currentTimeMillis() - startTime; 
  }
  
  public List<NoteEvent> getNoteEvents() { return noteEvents; }
  public Note getMaxNote() { return maxNote; }
  public Note getMinNote() { return minNote; }
  public long getMaxTimeMillis() { return maxTime; }
  public boolean hasNote() { return noteEvents.size() > 0; }

  public void recordNoteOn(Note note) {
    if (isActive) {
      if (!hasNote()) {
        startTime = System.currentTimeMillis();
      }
      long t = getElapsed();
      noteEvents.add(new NoteEvent(note, t, true));
      if (minNote == null || note.compareTo(minNote) < 0) minNote = note;
      if (maxNote == null || note.compareTo(maxNote) > 0) maxNote = note;
      maxTime = t;
    }
  }
  
  public void recordNoteOff(Note note) {
    if (isActive) {
      long t = getElapsed();
      noteEvents.add(new NoteEvent(note, t, false));
      maxTime = t;
    }
  }
  
  public void stop() {
    isActive = false;
  }
}
