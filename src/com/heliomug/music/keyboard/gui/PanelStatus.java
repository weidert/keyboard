package com.heliomug.music.keyboard.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
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
    add(getSettingsPanel(), BorderLayout.EAST);
  }
  
  public JPanel getRecordingPanel() {
    JPanel panel = new JPanel();
    panel.add(new UpdatingLabel("isRecording", () -> frame.getSession().isRecording() ? "⏺" : "◼"));
    panel.add(new UpdatingLabel("isPlaying", () -> frame.getSession().getRecording().isPlaying()? "▶" : "-"));
    
    return panel;
  }
  
  public JPanel getSettingsPanel() {
    JPanel panel = new JPanel();
    panel.add(new UpdatingLabel("instrument", () -> frame.getSession().getSettings().getInstrument().getName()));
    panel.add(new UpdatingLabel("volume", () -> String.format("(vol : %d)", frame.getSession().getSettings().getVolume())));
    return panel;
  }
}
