package com.heliomug.music.keyboard.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.heliomug.music.keyboard.Session;
import com.heliomug.utils.gui.ZoomablePanel;

public class RecordingPanel extends ZoomablePanel {
  private static final long serialVersionUID = 4285763780913531937L;

  public RecordingPanel(double left, double right, double bottom, double top) {
    super(left, right, bottom, top);
    this.setBackground(Color.BLACK);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    Session.getTheSession().getRecording().draw(g2);
  }
}
