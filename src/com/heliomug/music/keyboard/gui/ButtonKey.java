package com.heliomug.music.keyboard.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.heliomug.music.Note;

public class ButtonKey extends JButton {
  private static final long serialVersionUID = -1417804610200497298L;
  
  private static final Font STANDARD_FONT = new Font("Sans Serif", Font.BOLD, 16);

  private static final Color MONOCHROME_COLOR = Color.DARK_GRAY; 

  private static final int HEIGHT = 36;
  private static final int WIDTH = 36;
  
  private String letter;
  private Note note;
  
  private Frame frame;
  
  public ButtonKey(Frame frame, String letter) {
    this(frame, letter, 1.0); 
  }
 
  public ButtonKey(Frame frame, String letter, double widthFactor) {
    super(letter);
    this.frame = frame;
    this.letter = letter;

    setFont(STANDARD_FONT);
    setFocusable(false);
    setForeground(Color.WHITE);
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension((int)Math.round(WIDTH * widthFactor), HEIGHT));
    addChangeListener(new ChangeListener() {
      private boolean pressed = false;

      public void stateChanged(ChangeEvent e) {
        if (note != null) {
          ButtonKey button = (ButtonKey)e.getSource(); 
          if (button.getModel().isPressed() != pressed) {
            pressed = button.getModel().isPressed();
            if (pressed) {
              frame.getSession().pressNote(note);
            } else {
              frame.getSession().releaseNote(note);
            }
          }
        }
      }
    });
  }
  
  public String getLetter() {
    return letter;
  }
  
  public void whiteKey() {
    setBackground(Color.WHITE);
    setForeground(Color.BLACK);
  }
  
  public void resetColor() {
    setForeground(Color.WHITE);
    if (note == null) {
      setBackground(Color.BLACK);
    } else if (frame.getSession().getSettings().getIsColoredKeys()) {
      setBackground(note.getColor());
    } else {
      setBackground(MONOCHROME_COLOR);
    }
  }
  
  public void setNote(Note note) {
    this.note = note;
    resetColor();
    if (note == null || !note.isMajor()) {
      setText("");
    } else {
      setText(String.format("<html>%s</html>", note.getNoteName()));
    }
  }
}
