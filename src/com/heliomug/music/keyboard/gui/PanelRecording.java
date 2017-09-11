package com.heliomug.music.keyboard.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.heliomug.music.Note;
import com.heliomug.music.keyboard.NoteEvent;
import com.heliomug.music.keyboard.Recording;
import com.heliomug.utils.gui.ZoomablePanel;

public class PanelRecording extends JPanel {
  private static final long serialVersionUID = 4285763780913531937L;

  private static final Color CURSOR_COLOR = Color.RED;
  private static final double CURSOR_EXTEND = 1.0;
   
  private Frame frame;

  private Recording recording;
  
  private DisplayPanel displayPanel;
  
  public PanelRecording(Frame frame) {
    super(new BorderLayout());
    this.frame = frame;
    this.recording = null;
    this.displayPanel = new DisplayPanel();
    
    add(displayPanel, BorderLayout.CENTER);
    add(getControlPanel(), BorderLayout.SOUTH);
  }
  
  public void setRecording(Recording recording) {
    this.recording = recording;
  }

  public JPanel getControlPanel() {
    JPanel panel = new JPanel();
    panel.add(new MyButton(SharedConstants.REC_TEXT, () -> { frame.startRecording(); }));
    panel.add(new MyButton(SharedConstants.STOP_TEXT, () -> { frame.stopRecording(); }));
    panel.add(new MyButton(SharedConstants.PLAY_TEXT, () -> { frame.startPlayback(); }));
    panel.add(new MyButton(SharedConstants.PLAY_PAUSE_TEXT, () -> { frame.playPausePlayback(); }));
    panel.add(new MyButton(SharedConstants.PAUSE_TEXT, () -> { frame.pausePlayback(); }));
    
    return panel;
  }
  
  private class MyButton extends JButton {
    private static final long serialVersionUID = -7296252359503661927L;

    public MyButton(String text, Runnable r) {
      super(text);
      setFocusable(false);
      addActionListener((ActionEvent e) -> {
        r.run();
      });
    }
    
  }
  
  private class DisplayPanel extends ZoomablePanel {
    private static final long serialVersionUID = 691286340757028057L;

    private static final double SECOND_TO_SEMI_TONE_RENDER_RATIO = 12.0;

    public DisplayPanel() {
      this.setBackground(Color.BLACK);
      
      Thread t = new Thread(() -> {
        while (true) {
          PanelRecording.this.repaint();
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
            break;
          }
        }
      });
      t.start();
    }
    
    public double longToLoc(long l) {
      return l * SECOND_TO_SEMI_TONE_RENDER_RATIO / 1000.0;
    }
  
    public double getYMin() {
      return recording.getMinNote().getValue() - CURSOR_EXTEND;
    }
    
    public double getYMax() {
      return recording.getMaxNote().getValue() + 1 + CURSOR_EXTEND;  
    }
    
    public void resetScreenBounds() {
      Rectangle2D rect;
      if (recording == null || !recording.hasNote()) {
        rect = new Rectangle2D.Double(0, 10, 20, 30); 
      } else {
        double height = getYMax() - getYMin(); 
        if (recording.isActive()) {
          rect = new Rectangle2D.Double(0, recording.getMinNote().getValue(), longToLoc(recording.getElapsed()), height);
        } else {
          rect = new Rectangle2D.Double(0, recording.getMinNote().getValue(), longToLoc(recording.getMaxTimeMillis()), height);
        }
      }
      super.setScreenBounds(rect);
    }
    
    public void paintComponent(Graphics basicG) {
      super.paintComponent(basicG);

      if (recording != null) {
        resetScreenBounds();
        Graphics2D g = (Graphics2D) basicG;
        Map<Integer, Double> startTimes = new HashMap<>();
        for (NoteEvent event : recording.getNoteEvents()) {
          int val = event.getNote().getValue();
          double sec = event.getTimeMillis() / 1000.0;
          if (event.isOn()) {
            startTimes.put(val, sec); 
          } else {
            double startTime = startTimes.get(val);
            startTimes.remove(val);
            double dur = event.getTimeMillis() / 1000.0 - startTime;
            g.setColor(event.getNote().getColor());
            g.fill(new Rectangle2D.Double(startTime * SECOND_TO_SEMI_TONE_RENDER_RATIO, val, dur * SECOND_TO_SEMI_TONE_RENDER_RATIO, 1));
          }
        }
        double lastTime = recording.getElapsed();
        for (Integer val : startTimes.keySet()) {
          double startTime = startTimes.get(val);
          double dur = lastTime / 1000.0 - startTime;
          g.setColor(new Note(val).getColor());
          g.fill(new Rectangle2D.Double(startTime * SECOND_TO_SEMI_TONE_RENDER_RATIO, val, dur * SECOND_TO_SEMI_TONE_RENDER_RATIO, 1));
        }
        if (frame.getPlayer().isPlaying()) {
          double t = frame.getPlayer().getElapsed() / 1000.0;
          g.setColor(CURSOR_COLOR);
          g.setStroke(new BasicStroke(.1f));
          g.draw(new Line2D.Double(t * SECOND_TO_SEMI_TONE_RENDER_RATIO, getYMin(), t * SECOND_TO_SEMI_TONE_RENDER_RATIO, getYMax()));
        }
      }
    }
  }
}
