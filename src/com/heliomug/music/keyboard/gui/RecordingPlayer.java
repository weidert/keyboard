package com.heliomug.music.keyboard.gui;

import com.heliomug.music.keyboard.NoteEvent;
import com.heliomug.music.keyboard.Recording;

public class RecordingPlayer {
  private Frame frame;
  private Thread playbackThread;
  private long playbackStartTime;
  private boolean isPaused;
  private boolean isActive;
  private long pauseTime;

  private Recording recording;
  
  public RecordingPlayer(Frame frame) {
    this.frame = frame;
    isPaused = false;
    isActive = false;
    pauseTime = 0;
  }
  
  public void setRecording(Recording recording) {
    this.recording = recording;
  }
  
  public long getElapsed() {
    if (isPaused) {
      return pauseTime;
    } else {
      return System.currentTimeMillis() - playbackStartTime; 
    }
  }
  
  public boolean isPlaying() {
    return playbackThread != null && playbackThread.isAlive();
  }
  
  public boolean isPaused() {
    return isPaused;
  }
  
  private void handleEvent(NoteEvent e) {
    if (e.isOn()) {
      frame.getSession().robotOn(e.getNote());
    } else {
      frame.getSession().robotOff(e.getNote());
    }
  }
  
  public void startPlayback() {
    if (recording != null) {
      Runnable r = () -> {
        for (int i = 0; i < recording.getNoteEvents().size() ; i++) {
          if (isPaused) {
            i--;
          } else {
            handleEvent(recording.getNoteEvents().get(i));
          }
          
          if (i < recording.getNoteEvents().size() - 1) {
            NoteEvent next = recording.getNoteEvents().get(i + 1);
            long dist = Math.max(1, next.getTimeMillis() - getElapsed());
            try {
              Thread.sleep(dist);
            } catch (InterruptedException e) {
              frame.getSession().robotAllOff();
              if (!isActive) {
                break;
              }
            }
          }
        }
        isActive = false;
        frame.update();
      };
      
      playbackThread = new Thread(r);
      isActive = true;
      isPaused = false;
      playbackStartTime = System.currentTimeMillis();
      playbackThread.start();
      frame.update();
    }
  }
  
  public void pause() {
    pauseTime = getElapsed();
    isPaused = true;
    if (isPlaying()) playbackThread.interrupt();
  }
  
  public void unPause() {
    playbackStartTime = System.currentTimeMillis() - pauseTime;
    isPaused = false;
  }
  
  public void stopPlayback() {
    isActive = false;
    if (isPlaying()) playbackThread.interrupt();
  }
}
