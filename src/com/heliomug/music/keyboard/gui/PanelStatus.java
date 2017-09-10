package com.heliomug.music.keyboard.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.heliomug.music.keyboard.Session;
import com.heliomug.utils.gui.UpdatingLabel;

public class PanelStatus extends JPanel {
  private static final long serialVersionUID = -8306386547674245596L;

  public PanelStatus() {
    super(new BorderLayout());
    setBorder(BorderFactory.createEtchedBorder());
    setupGUI();
  }
  
  public void setupGUI() {
    add(getRecordingPanel(), BorderLayout.WEST);
    add(getSettingsPanel(), BorderLayout.EAST);
  }
  
  public JPanel getRecordingPanel() {
    JPanel panel = new JPanel();
    panel.add(new UpdatingLabel("isRecording", () -> Session.getTheSession().isRecording() ? "⏺" : "◼"));
    panel.add(new UpdatingLabel("isPlaying", () -> Session.getTheSession().getRecording().isPlaying()? "▶" : "-"));
    
    return panel;
  }
  
  public JPanel getSettingsPanel() {
    JPanel panel = new JPanel();
    panel.add(new UpdatingLabel("instrument", () -> Session.getTheSession().getSettings().getInstrument().getName()));
    panel.add(new UpdatingLabel("volume", () -> String.format("(vol : %d)", Session.getTheSession().getSettings().getVolume())));
    return panel;
  }
}
