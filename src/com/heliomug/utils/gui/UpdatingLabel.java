package com.heliomug.utils.gui;

import java.awt.Graphics;
import java.util.function.Supplier;

import javax.swing.JLabel;

public class UpdatingLabel extends JLabel{
  private static final long serialVersionUID = 1621505639388578602L;
  
  private Supplier<String> labelFxn;

  public UpdatingLabel(String initialText, Supplier<String> labelFxn) {
     super(initialText);
     this.labelFxn = labelFxn;
  }
  
  @Override
  public void paintComponent(Graphics g) {
    setText(labelFxn.get());
    super.paintComponent(g);
  }
}
