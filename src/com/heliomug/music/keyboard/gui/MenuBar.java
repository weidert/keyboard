package com.heliomug.music.keyboard.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.heliomug.music.Note;
import com.heliomug.music.StandardInstrument;
import com.heliomug.music.keyboard.KeyLayout;
import com.heliomug.music.keyboard.Session;
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
    
  public MenuBar() {
    super();

    
    add(getFileMenu());
    add(getRecorderMenu());
    add(getSettingsMenu());
    add(getAboutMenu());
  }
  
  public JMenu getFileMenu() {
    JMenu menu;
    JMenuItem item;
    
    menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_F);
    item = new JMenuItem("Exit", KeyEvent.VK_X);
    item.addActionListener((ActionEvent e) -> Frame.getTheFrame().quit());
    menu.add(item);
    
    return menu;
  }
  
  public JMenu getRecorderMenu() {
    JMenu menu;
    JMenuItem item;
    
    menu = new JMenu("Recorder");
    menu.setMnemonic(KeyEvent.VK_R);
    item = new JMenuItem("Start Recording", KeyEvent.VK_R);
    item.addActionListener((ActionEvent e) -> {
      Session.getTheSession().recorderStartRecording();
    });
    menu.add(item);
    item = new JMenuItem("Stop Recording", KeyEvent.VK_S);
    item.addActionListener((ActionEvent e) -> {
      Session.getTheSession().recorderStopRecording();
    });
    menu.add(item);
    menu.addSeparator();
    item = new JMenuItem("Start Playback", KeyEvent.VK_P);
    item.addActionListener((ActionEvent e) -> {
      Session.getTheSession().recorderStartPlayback();
    });
    menu.add(item);
    item = new JMenuItem("Stop Playback", KeyEvent.VK_B);
    item.addActionListener((ActionEvent e) -> {
      Session.getTheSession().recorderStopPlayback();
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
          Frame.getTheFrame().repaint();
          Session.getTheSession().setInstrument(instrument);
        },
        (StandardInstrument instrument) -> instrument.getShortName(),
        (StandardInstrument instrument) -> null
    );
    instrumentSelector.setMnemonic(KeyEvent.VK_I);
    menu.add(instrumentSelector);
  
    MenuSelector<Integer> volumeSelector = new MenuSelector<>( 
        "Volume",
        Utils.toIntegerList(VOLUME_OPTIONS),
        (Integer volume) -> Session.getTheSession().setVolume(volume)
    );
    volumeSelector.setMnemonic(KeyEvent.VK_V);
    menu.add(volumeSelector);
    
    MenuSelector<KeyLayout> layoutSelector = new MenuSelector<KeyLayout>(
        "Layout",
        Arrays.asList(KeyLayout.values()),
        null, 
        (KeyLayout layout) -> {
          Session.getTheSession().setLayout(layout);
          PanelKey.getThePanel().refresh();
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
          Session.getTheSession().setRootNote(note);
          PanelKey.getThePanel().refresh();
        },
        (Note note) -> note.longName(),
        (Note note) -> null
    );
    rootSelector.setMnemonic(KeyEvent.VK_R);
    menu.add(rootSelector);
    menu.addSeparator();
    JMenuItem item = new JMenuItem("Reset to Defaults", KeyEvent.VK_D);
    item.addActionListener((ActionEvent e) -> {
      Session.getTheSession().resetSettings();
      PanelKey.getThePanel().refresh();
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
      javax.swing.JOptionPane.showMessageDialog(Frame.getTheFrame(), message);
    });
    menu.add(item);
    
    return menu;
  }
}