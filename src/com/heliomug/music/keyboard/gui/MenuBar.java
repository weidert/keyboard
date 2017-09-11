package com.heliomug.music.keyboard.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.heliomug.music.Note;
import com.heliomug.music.StandardInstrument;
import com.heliomug.music.keyboard.KeyLayout;
import com.heliomug.utils.Utils;
import com.heliomug.utils.gui.MenuSelector;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
    private static final int[] VOLUME_OPTIONS = new int[] {0, 1, 2, 5, 7, 10, 20, 40, 50, 70, 100};
    
    private static final StandardInstrument[] BASIC_INSTRUMENTS = new StandardInstrument[] { 
          StandardInstrument.PIANO_GRAND,  
          StandardInstrument.PIANO_ELECTRIC_1,
          StandardInstrument.GUITAR_NYLON,
          StandardInstrument.GUITAR_STEEL, 
          StandardInstrument.GUITAR_OVERDRIVE,
          StandardInstrument.BASS_ACOUSTIC,
          StandardInstrument.BASS_ELECTRIC,
          StandardInstrument.BANJO,
          StandardInstrument.ORGAN_CHURCH,
          StandardInstrument.ORGAN_ROCK,
          StandardInstrument.ATMOSPHERE,
          StandardInstrument.BASSOON,
          StandardInstrument.CELLO,
          StandardInstrument.VOICE,
          StandardInstrument.MUSIC_BOX
    };
    
  private Frame frame;
    
  public MenuBar(Frame frame) {
    super();

    this.frame = frame;
    
    add(getFileMenu());
    add(getSettingsMenu());
    add(getRecorderMenu());
    add(getAboutMenu());
  }
  
  public JMenu getFileMenu() {
    JMenu menu;
    JMenuItem item;
    
    menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_F);
    item = new JMenuItem("Exit", KeyEvent.VK_X);
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
    item.addActionListener((ActionEvent e) -> frame.quit());
    menu.add(item);
    
    return menu;
  }
  
  public JMenu getRecorderMenu() {
    JMenu menu;
    JMenuItem item;
    
    menu = new JMenu("Recorder");
    menu.setMnemonic(KeyEvent.VK_R);
    item = new JMenuItem("Start Recording", KeyEvent.VK_R);
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
    item.addActionListener((ActionEvent e) -> {
      frame.startRecording();
    });
    menu.add(item);
    item = new JMenuItem("Stop Recording", KeyEvent.VK_S);
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    item.addActionListener((ActionEvent e) -> {
      frame.stopRecording();
    });
    menu.add(item);
    menu.addSeparator();
    item = new JMenuItem("Start Playback", KeyEvent.VK_P);
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    item.addActionListener((ActionEvent e) -> {
      frame.startPlayback();
    });
    menu.add(item);
    item = new JMenuItem("Play/Pause Playback");
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.CTRL_MASK));
    item.addActionListener((ActionEvent e) -> {
      frame.playPausePlayback();
    });
    menu.add(item);
    item = new JMenuItem("Stop Playback", KeyEvent.VK_B);
    item.addActionListener((ActionEvent e) -> {
      frame.stopPlayback();
    });
    menu.add(item);
    
    return menu;
  }
  public JMenu getSettingsMenu() {
    JMenu menu = new JMenu("Settings");
    menu.setMnemonic(KeyEvent.VK_S);
    
    List<List<StandardInstrument>> extendedInstrumentList= new ArrayList<>();
    List<StandardInstrument> subList = new ArrayList<>();
    for (int i = 0 ; i < StandardInstrument.values().length; i++) {
      if (i % 15 == 0) {
        subList = new ArrayList<>();
        extendedInstrumentList.add(subList);
      }
      subList.add(StandardInstrument.values()[i]);
    }
    MenuSelector<StandardInstrument> instrumentSelector = new MenuSelector<>(
        "Instrument",
        Arrays.asList(BASIC_INSTRUMENTS), 
        extendedInstrumentList,
        (StandardInstrument instrument) -> {
          frame.repaint();
          frame.getSession().setInstrument(instrument);
        },
        (StandardInstrument instrument) -> instrument.getShortName(),
        (StandardInstrument instrument) -> null
    );
    instrumentSelector.setMnemonic(KeyEvent.VK_I);
    menu.add(instrumentSelector);
  
    MenuSelector<Integer> volumeSelector = new MenuSelector<>( 
        "Volume",
        Utils.toIntegerList(VOLUME_OPTIONS),
        (Integer volume) -> frame.getSession().setVolume(volume)
    );
    volumeSelector.setMnemonic(KeyEvent.VK_V);
    menu.add(volumeSelector);
    
    MenuSelector<KeyLayout> layoutSelector = new MenuSelector<KeyLayout>(
        "Layout",
        Arrays.asList(KeyLayout.values()),
        null, 
        (KeyLayout layout) -> {
          frame.getSession().setLayout(layout);
          frame.update();
        },
        (KeyLayout layout) -> layout.toString(), 
        (KeyLayout layout) -> layout.getMnemonic()
    );
    layoutSelector.setMnemonic(KeyEvent.VK_L);
    menu.add(layoutSelector);
    
    List<Note> mainRoots = new ArrayList<>();
    for (int j = 0 ; j < 12 ; j++) {
      mainRoots.add(new Note(4 * 12 + j));
    }
    List<List<Note>> otherRoots = new ArrayList<>();
    for (int i = 0; i <= 10; i++) {
      List<Note> subNoteList = new ArrayList<>();
      for (int j = 0 ; j < 12 ; j++) {
        subNoteList.add(new Note(i * 12 + j));
      }
      otherRoots.add(subNoteList);
    }
    MenuSelector<Note> rootSelector = new MenuSelector<Note>(
        "Root",
        mainRoots,
        otherRoots, 
        (Note note) -> {
          frame.getSession().setRootNote(note);
          frame.update();
        },
        (Note note) -> note.longName(),
        (Note note) -> null
    );
    rootSelector.setMnemonic(KeyEvent.VK_R);
    menu.add(rootSelector);
    menu.addSeparator();
    menu.add(getPanelMenu());
    menu.addSeparator();
    JMenuItem item = new JMenuItem("Reset to Defaults", KeyEvent.VK_D);
    item.addActionListener((ActionEvent e) -> {
      frame.getSession().resetSettings();
      frame.update();
    });
    menu.add(item);
    
    return menu;
  }
  
  public JMenu getPanelMenu() {
    JMenu menu;
    JMenuItem item;

    menu = new JMenu("Panels");
    menu.setMnemonic(KeyEvent.VK_P);
    item = new JMenuItem("Show Status Panel", KeyEvent.VK_S);
    item.addActionListener((ActionEvent e) -> {
      frame.setStatusVisible(true);
    });
    menu.add(item);
    item = new JMenuItem("Hide Status Panel", KeyEvent.VK_H);
    item.addActionListener((ActionEvent e) -> {
      frame.setStatusVisible(false);
    });
    menu.add(item);
    menu.addSeparator();
    item = new JMenuItem("Show Tabs", KeyEvent.VK_T);
    item.addActionListener((ActionEvent e) -> {
      frame.setTabbedPane(true);
    });
    menu.add(item);
    item = new JMenuItem("Hide Tabs", KeyEvent.VK_B);
    item.addActionListener((ActionEvent e) -> {
      frame.setTabbedPane(false);
    });
    menu.add(item);
    
    return menu;
  }

  public JMenu getAboutMenu() {
    JMenu menu;
    JMenuItem item;

    menu = new JMenu("About");
    menu.setMnemonic(KeyEvent.VK_A);
    item = new JMenuItem("About");
    item.addActionListener((ActionEvent e) -> {
      String message = "By Craig Weidert, 2017";
      javax.swing.JOptionPane.showMessageDialog(frame, message);
    });
    menu.add(item);
    
    return menu;
  }
}