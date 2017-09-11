package com.heliomug.music.keyboard.gui;

import com.heliomug.music.keyboard.NoteEvent;
import com.heliomug.music.keyboard.Recording;

public class RecordingPlayer {
  private Frame frame;
  private Thread playbackThread;
  private long playbackStartTime;

  private Recording recording;
  
  public RecordingPlayer(Frame frame) {
    this.frame = frame;
  }
  
  public void setRecording(Recording recording) {
    this.recording = recording;
  }
  
  public long getElapsed() {
    return System.currentTimeMillis() - playbackStartTime; 
  }
  
  public boolean isPlaying() {
    return playbackThread != null && playbackThread.isAlive();
  }
  
  public void startPlayback() {
    if (recording != null) {
      Runnable r = () -> {
        for (int i = 0; i < recording.getNoteEvents().size() ; i++) {
          NoteEvent current = recording.getNoteEvents().get(i);
          if (current.isOn()) {
            frame.getSession().robotOn(current.getNote());
          } else {
            frame.getSession().robotOff(current.getNote());
          }
          
          if (i < recording.getNoteEvents().size() - 1) {
            NoteEvent next = recording.getNoteEvents().get(i + 1);
            long dist = next.getTimeMillis() - current.getTimeMillis();
            try {
              Thread.sleep(dist);
            } catch (InterruptedException e) {
              frame.getSession().robotAllOff();
              break;
            }
          }
        }
        frame.update();
      };
      
      playbackThread = new Thread(r);
      playbackStartTime = System.currentTimeMillis();
      playbackThread.start();
      frame.update();
    }
  }
  
  public void stopPlayback() {
    if (isPlaying()) playbackThread.interrupt();
  }
  
  
}
