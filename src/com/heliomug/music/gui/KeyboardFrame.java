package com.heliomug.music.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class KeyboardFrame extends JFrame {
  private static final long serialVersionUID = 6029069601675932278L;
  
  private static KeyboardFrame theFrame;
  
  public static KeyboardFrame getTheFrame() {
    if (theFrame == null) {
      theFrame = new KeyboardFrame();
    }
    return theFrame;
  }

  private KeyboardFrame() {
    super("Heliomug.com Keyboard");
    
    setupGUI();
  }
  
  public void setupGUI() {
    setFocusable(true);
    
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int mods = e.getModifiers();
        if (mods == 0 || mods == 1) {
          Session.getTheSession().handleKeyDown(e);
        }
      }
      
      public void keyReleased(KeyEvent e) {
        Session.getTheSession().handleKeyUp(e);
      }
    });
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    setJMenuBar(KeyboardMenuBar.getTheBar());
    
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(KeyPanel.getThePanel(), BorderLayout.CENTER);
    add(panel);
    pack();
    
  }
  
  public void quit() {
    System.exit(0);
  }
}
