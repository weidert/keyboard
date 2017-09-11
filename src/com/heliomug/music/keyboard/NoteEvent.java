package com.heliomug.music.keyboard;

import java.io.Serializable;

import com.heliomug.music.Note;

public class NoteEvent implements Serializable {
  private static final long serialVersionUID = 8365192790090537187L;

  private long timeMillis;
  private Note note;
  private boolean isOn;
  
  public NoteEvent(Note note, long elapsed, boolean isOn) {
    this.timeMillis = elapsed;
    this.note = note;
    this.isOn = isOn;
  }
  
  public Note getNote() { return note; }
  public long getTimeMillis() { return timeMillis; }
  public boolean isOn() { return isOn; }
}
