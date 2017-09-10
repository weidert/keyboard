package com.heliomug.music.keyboard;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heliomug.music.Note;

public class Recording implements Serializable {
  private static final long serialVersionUID = -2136605424538274565L;
  
  private static final double SECOND_TO_SEMI_TONE_RENDER_RATIO = 12.0;

  private transient long startTime;
  private List<NoteEvent> noteEvents;
  
  private transient Thread playbackThread;

  public Recording() {
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
  
  public boolean isPlaying() {
    return playbackThread != null && playbackThread.isAlive();
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
    if (isPlaying()) playbackThread.interrupt();
  }
  
  public void draw(Graphics2D g) {
    Map<Integer, Double> startTimes = new HashMap<>();
    for (NoteEvent event : noteEvents) {
      int val = event.note.getValue();
      double sec = event.time / 1000.0;
      if (event.isOn) {
        startTimes.put(val, sec); 
      } else {
        double startTime = startTimes.get(val);
        startTimes.remove(val);
        double dur = event.time / 1000.0 - startTime;
        g.setColor(event.note.getColor());
        g.fill(new Rectangle2D.Double(startTime * SECOND_TO_SEMI_TONE_RENDER_RATIO, val, dur * SECOND_TO_SEMI_TONE_RENDER_RATIO, 1));
      }
    }
  }

  private class NoteEvent implements Serializable {
    private static final long serialVersionUID = 8365192790090537187L;

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
