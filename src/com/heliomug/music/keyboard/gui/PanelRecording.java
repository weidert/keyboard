package com.heliomug.music.keyboard.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.heliomug.utils.gui.ZoomablePanel;

public class PanelRecording extends ZoomablePanel {
  private static final long serialVersionUID = 4285763780913531937L;

  private Frame frame;
  
  public PanelRecording(Frame frame, double left, double right, double bottom, double top) {
    super(left, right, bottom, top);
    this.frame = frame;
    this.setBackground(Color.BLACK);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    frame.getSession().getRecording().draw(g2);
  }
}
