package com.heliomug.music.keyboard.gui;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.heliomug.music.keyboard.Session;

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
        if (e.getKeyCode() == KeyEvent.VK_D && e.isControlDown()) {
          quit();
        }
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
    
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
    int x = (int) rect.getMaxX() - getWidth();
    int y = (int) rect.getMaxY() - getHeight();
    setLocation(x, y);
    
    setResizable(false);
    
    setJMenuBar(new KeyboardMenuBar());
    
    addIcon();

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(makeTabbedPane(), BorderLayout.CENTER);

    add(panel);
    pack();
  }

  private JTabbedPane makeTabbedPane() {
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setFocusable(false);

    tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

    tabbedPane.addTab("Keyboard", null, KeyPanel.getThePanel(), "The Keyboard");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_K);

    tabbedPane.addTab("Recording", null, new RecordingPanel(0, 100, 0, 100), "Recording");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_D);
    
    return tabbedPane;
  }

  private void addIcon() {
    URL url = getClass().getResource("/heliomug256.png");
    setIconImage(new ImageIcon(url).getImage());    
  }
  
  public void quit() {
    Session.getTheSession().saveDefault();
    System.exit(0);
  }
}
