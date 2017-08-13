package com.heliomug.music.gui;

import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_W;

import java.util.HashMap;
import java.util.Map;

import com.heliomug.music.Note;

public enum KeyLayout {
  CHROMATIC(new int[][] {
      {-1, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96},
      {-1, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84}, 
      {-1, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71}, 
      {-1, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, -1}},
      "Chromatic",
      VK_C),
  PIANO(new int[][] {
      {-1, -1, 66, 68, 70, -1, 73, 75, -1, 78, 80, 82, -1, -1},
      {-1, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, -1},
      {-1, -1, 49, 51, -1, 54, 56, 58, -1, 61, 63, -1, -1},
      {-1, 48, 50, 52, 53, 55, 57, 59, 60, 62, 64, -1}},
      "Piano Two Row",
      VK_P),
  PIANO_MIDDLE(new int[][] {
      {-1, 54, 56, 58, -1, 61, 63, -1, 66, 68, 70, -1, 73, -1},
      {-1, 55, 57, 59, 60, 62, 64, 65, 67, 69, 71, 72, 74, -1},
      {-1, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, -1}, 
      {-1, 37, 39, -1, 42, 44, 46, -1, 49, 51, -1, -1}},
      "Piano Middle",
      VK_M),
  PIANO_HOMEROW(new int[][] {
      {-1, -1, 61, 63, -1, 66, -1, 66, 68, 70, -1, -1, -1, -1},
      {-1, 60, 62, 64, 65, -1, -1, 67, 69, 71, 72, -1, -1, -1},
      {-1, -1, 49, 51, -1, 54, -1, 54, 56, 58, -1, -1, -1},
      {-1, 48, 50, 52, 53, -1, -1, 55, 57, 59, 60, -1}},
      "Piano Homerow Two Row",
      VK_W),
  PIANO_HOMEROW_MIDDLE(new int[][] {
      {-1, -1, 61, 63, -1, 66, -1, 66, 68, 70, -1, -1, -1, -1},
      {-1, 60, 62, 64, 65, -1, -1, 67, 69, 71, 72, -1, -1, -1},
      {-1, 48, 50, 52, 53, -1, -1, 55, 57, 59, 60, -1, -1},
      {-1, 49, 51, -1, 54, -1, 54, 56, 58, -1, -1, -1}},
      "Piano Homerow Middle",
      VK_H),
  GUITAR(new int[][] {
      {-1, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67},
      {-1, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62},
      {-1, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56}, 
      {-1, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50}},
      "Guitar",
      VK_G),
  BASS_GUITAR(new int[][] {
      {-1, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55},
      {-1, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50},
      {-1, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}, 
      {-1, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38}},
      "Bass Guitar",
      VK_B);

  private static Map<String, KeyLayout> nameMap;
  
  public static boolean contains(String name) {
    if (nameMap == null) initNameMap();
    return nameMap.containsKey(name);
  }
  
  public static KeyLayout getLayout(String name) {
    if (nameMap == null) initNameMap();
    return nameMap.get(name);
  }
  
  private static void initNameMap() {
    nameMap = new HashMap<>();
    for (KeyLayout layout : KeyLayout.values()) {
      nameMap.put(layout.getName(), layout);
    }
  }
  
  public static KeyLayout getDefault() {
    return KeyLayout.PIANO_MIDDLE;
  }
  
  private String name;
  private Map<Integer, Integer> noteMap;
  private int mnemonic;
  
  private KeyLayout(int[][] codes, String name, int mnemonic) {
    this.name = name;
    this.mnemonic = mnemonic;
    noteMap = new HashMap<>();
    for (int i = 0; i < codes.length; i++) {
      for (int j = 0; j < codes[i].length; j++) {
        int keyCode = KeyPanel.KEYBOARD_LAYOUT[i][j];
        int noteCode = codes[i][j];
        noteMap.put(keyCode, noteCode); 
      }
    }
  }
  
  public String getName() {
    return name;
  }
  
  public int getMnemonic() {
    return mnemonic;
  }
  
  public Note getNote(int keyCode) {
    if (noteMap.containsKey(keyCode)) {
      int noteValue = noteMap.get(keyCode);
      if (noteValue >= 0) {
        return new Note(noteValue);
      }
    } 
    return null;
  }
  
  public int getNoteValue(int keyCode) {
    return noteMap.get(keyCode);
  }
  
  @Override
  public String toString() {
    return name;
  }
}
