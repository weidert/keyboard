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

public class Frame extends JFrame {
  private static final long serialVersionUID = 6029069601675932278L;
  
  private Session session;
  private PanelKey keyPanel;
  
  public Frame() {
    super("Heliomug.com Keyboard");
    
    session = new Session(this);
    
    setupGUI();
  }
  
  public void setupGUI() {
    setFocusable(true);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    moveToBottomRight();
    
    setResizable(false);
    
    setupKeys();
    
    setJMenuBar(new MenuBar(this));
    
    addIcon();

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(makeTabbedPane(), BorderLayout.CENTER);
    panel.add(new PanelStatus(this), BorderLayout.SOUTH);
    add(panel);

    pack();
  }
  
  private void setupKeys() {
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D && e.isControlDown()) {
          quit();
        }
        int mods = e.getModifiers();
        if (mods == 0 || mods == 1) {
          session.handleKeyDown(e);
        }
      }
      
      public void keyReleased(KeyEvent e) {
        session.handleKeyUp(e);
      }
    });
  }
  
  public Session getSession() {
    return session;
  }
  
  private void moveToBottomRight() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
    int x = (int) rect.getMaxX() - getWidth();
    int y = (int) rect.getMaxY() - getHeight();
    setLocation(x, y);
  }

  private JTabbedPane makeTabbedPane() {
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setFocusable(false);

    tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
    
    keyPanel = new PanelKey(this);

    tabbedPane.addTab("Keyboard", null, keyPanel, "The Keyboard");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_K);

    tabbedPane.addTab("Recording", null, new PanelRecording(this, 0, 100, 0, 100), "Recording");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_D);
    
    return tabbedPane;
  }

  private void addIcon() {
    URL url = getClass().getResource("/heliomug256.png");
    setIconImage(new ImageIcon(url).getImage());    
  }

  public void update() {
    repaint();
  }
  
  public void showKeyDown(int keyCode) {
    keyPanel.whiteKey(keyCode);
  }
  
  public void showKeyUp(int keyCode) {
    keyPanel.recolorKey(keyCode);
  }
  
  public void showAllKeysUp() {
    keyPanel.recolorAll();
  }
  
  public void quit() {
    session.saveDefault();
    System.exit(0);
  }
  
}
