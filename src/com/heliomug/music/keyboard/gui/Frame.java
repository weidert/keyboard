package com.heliomug.music.keyboard.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.heliomug.music.keyboard.Recording;
import com.heliomug.music.keyboard.Session;
import com.heliomug.utils.FileUtils;

public class Frame extends JFrame {
  private static final long serialVersionUID = 6029069601675932278L;
  
  private Session session;
  private JPanel mainPanel;
  private PanelKey keyPanel;
  private PanelRecording recordingPanel;
  private PanelStatus statusPanel;
  private RecordingPlayer recordingPlayer; 

  public Frame() {
    super("Heliomug.com Keyboard");
    
    session = new Session(this);
    recordingPlayer = new RecordingPlayer(this);
    mainPanel = new JPanel();
    keyPanel = new PanelKey(this);
    recordingPanel = new PanelRecording(this);
    statusPanel = new PanelStatus(this);
    statusPanel.setVisible(session.getSettings().getShowStatusPanel());
    
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

    mainPanel = new JPanel(new BorderLayout());
    showTabsOrNot();
    add(mainPanel);

    pack();
  }
  
  private void showTabsOrNot() {
    if (session.getSettings().getShowTabbedPanel()) {
      BorderLayout layout = (BorderLayout) mainPanel.getLayout();
      Component comp = layout.getLayoutComponent(BorderLayout.CENTER); 
      if (comp != null) mainPanel.remove(comp);
      mainPanel.add(getTabbedPane(), BorderLayout.CENTER);
    } else {
      BorderLayout layout = (BorderLayout) mainPanel.getLayout();
      Component comp = layout.getLayoutComponent(BorderLayout.CENTER); 
      if (comp != null) mainPanel.remove(comp);
      mainPanel.add(keyPanel, BorderLayout.CENTER);
    }
    mainPanel.add(statusPanel, BorderLayout.SOUTH);
  }
  
  private JTabbedPane getTabbedPane() {
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setFocusable(false);
    tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

    tabbedPane.addTab("Keyboard", null, keyPanel, "The Keyboard");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_K);
    tabbedPane.addTab("Recording", null, recordingPanel, "Recording");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_D);
    
    return tabbedPane;
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
  
  public Session getSession() { return session; }
  public RecordingPlayer getPlayer() { return recordingPlayer; } 
  
  private void moveToBottomRight() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
    int x = (int) rect.getMaxX() - getWidth();
    int y = (int) rect.getMaxY() - getHeight();
    setLocation(x, y);
  }

  private void addIcon() {
    URL url = getClass().getResource("/heliomug256.png");
    setIconImage(new ImageIcon(url).getImage());    
  }

  public void update() {
    keyPanel.refresh();
    repaint();
  }

  
  public void startPlayback() {
    session.stopRecording();
    recordingPlayer.startPlayback();
    update();
  }
  
  public void stopPlayback() {
    recordingPlayer.stopPlayback();
    update();
  }
  
  public void playPausePlayback() {
    if (recordingPlayer.isPlaying()) {
      if (recordingPlayer.isPaused()) {
        recordingPlayer.unPause();
      } else {
        recordingPlayer.pause();
      }
    } else {
      startPlayback();
    }
    update();
  }
  
  public void pausePlayback() {
    recordingPlayer.pause();
  }
  
  private void setRecording(Recording recording) {
    session.startRecording(recording);
    recordingPanel.setRecording(recording);
    recordingPlayer.setRecording(recording);
  }
  
  public void saveRecording() {
    if (session.getRecording() != null) {
      try {
        FileUtils.saveObject(session.getRecording(), "Keyboard Recordings", "rec");
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Not saved!");
      }
    } else {
      JOptionPane.showMessageDialog(this, "No current recording!");
    }
  }
  
  public void loadRecording() {
    try {
      Object obj = FileUtils.loadObject("Keyboard Recordings", "rec");
      if (obj instanceof Recording) {
        setRecording((Recording) obj);
      } else {
        JOptionPane.showMessageDialog(this, "That's not a keyboard recording!");
      }
    } catch (ClassNotFoundException | IOException e) {
      JOptionPane.showMessageDialog(this, "Not loaded!");
    }
  }
  
  public void startRecording() {
    Recording recording = new Recording();
    setRecording(recording);
    update();
  }
  
  public void stopRecording() {
    session.stopRecording();
    update();
  }
  
  public void stopAll() {
    stopRecording();
    stopPlayback();
  }
  
  
  public void setTabbedPane(boolean b) {
    session.getSettings().setShowTabbedPane(b);
    showTabsOrNot();
    pack();
  }
  
  public void setStatusVisible(boolean b) {
    session.getSettings().setShowStatusPanel(b);
    statusPanel.setVisible(b);
    pack();
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
