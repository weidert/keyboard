package com.heliomug.music.keyboard.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.heliomug.utils.gui.UpdatingLabel;

public class PanelStatus extends JPanel {
  private static final long serialVersionUID = -8306386547674245596L;
  
  private Frame frame;

  public PanelStatus(Frame frame) {
    super(new BorderLayout());
    this.frame = frame;
    setBorder(BorderFactory.createEtchedBorder());
    setupGUI();
  }
  
  public void setupGUI() {
    add(getRecordingPanel(), BorderLayout.WEST);
    add(getControlPanel(), BorderLayout.CENTER);
    add(getSettingsPanel(), BorderLayout.EAST);
  }
  
  public String getPlaybackText() {
    if (frame.getPlayer().isPlaying()) {
      if (frame.getPlayer().isPaused()) {
        return SharedConstants.PAUSE_TEXT;
      } else {
        return SharedConstants.PLAY_TEXT;
      }
    }
    
    if (frame.getSession().isRecording()) {
      return SharedConstants.REC_TEXT;
    }
    
    return SharedConstants.STOP_TEXT;
  }
  
  public JPanel getControlPanel() {
    JPanel panel = new JPanel();
    panel.add(new MyButton(SharedConstants.REC_TEXT, () -> { frame.startRecording(); }));
    panel.add(new MyButton(SharedConstants.STOP_TEXT, () -> { frame.stopAll(); }));
    panel.add(new MyButton(SharedConstants.PLAY_TEXT, () -> { frame.startPlayback(); }));
    panel.add(new MyButton(SharedConstants.PAUSE_TEXT, () -> { frame.pausePlayback(); }));
    panel.add(new MyButton(SharedConstants.PLAY_PAUSE_TEXT, () -> { frame.playPausePlayback(); }));
    
    return panel;
  }
  
  private class MyButton extends JButton {
    private static final long serialVersionUID = -7296252359503661927L;

    public MyButton(String text, Runnable r) {
      super(text);
      setFont(SharedConstants.SMALL_FONT);
      setFocusable(false);
      addActionListener((ActionEvent e) -> {
        r.run();
      });
    }
    
  }
  
  public JPanel getRecordingPanel() {
    JPanel panel = new JPanel();
    
    JLabel label = new UpdatingLabel("isRecording", () -> getPlaybackText()); 
    label.setFont(SharedConstants.MEDIUM_FONT);
    panel.add(label);
    
    return panel;
  }
  
  public JPanel getSettingsPanel() {
    JPanel panel = new JPanel();
    panel.add(new UpdatingLabel("instrument", () -> frame.getSession().getSettings().getInstrument().getName()));
    panel.add(new UpdatingLabel("volume", () -> String.format("(vol : %d)", frame.getSession().getSettings().getVolume())));
    return panel;
  }
}
