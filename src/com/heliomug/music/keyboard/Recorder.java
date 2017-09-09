package com.heliomug.music.keyboard;

import java.util.ArrayList;
import java.util.List;

import com.heliomug.music.Note;

public class Recorder {
  
  private transient long startTime;
  private List<NoteEvent> noteEvents;
  
  private Thread playbackThread;

  public Recorder() {
    startTime = System.currentTimeMillis();
    noteEvents = new ArrayList<>();
  }
  
  private long getElapsed() {
    return System.currentTimeMillis() - startTime; 
  }
  
  public void recordNoteOn(Note note) {
    noteEvents.add(new NoteEvent(note, true));
  }
  
  public void recordNoteOff(Note note) {
    noteEvents.add(new NoteEvent(note, false));
  }
  
  public void startPlayback() {
    Runnable r = () -> {
      for (int i = 0; i < noteEvents.size() ; i++) {
        NoteEvent current = noteEvents.get(i);
        if (current.isOn) {
          Session.getTheSession().robotOn(current.note);
        } else {
          Session.getTheSession().robotOff(current.note);
        }
        
        if (i < noteEvents.size() - 1) {
          NoteEvent next = noteEvents.get(i + 1);
          long dist = next.time - current.time;
          try {
            Thread.sleep(dist);
          } catch (InterruptedException e) {
            Session.getTheSession().robotAllOff();
            break;
          }
        }
      }
    };
    
    playbackThread = new Thread(r);
    playbackThread.start();
  }
  
  public void stopPlayback() {
    if (playbackThread != null && playbackThread.isAlive()) playbackThread.interrupt();
  }
  
  private class NoteEvent {
    public long time;
    public Note note;
    public boolean isOn;
    
    public NoteEvent(Note note, boolean isOn) {
      this.time = getElapsed();
      this.note = note;
      this.isOn = isOn;
    }
  }
}
