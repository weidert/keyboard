package com.heliomug.music.keyboard;

import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_W;

import java.util.HashMap;
import java.util.Map;

import com.heliomug.music.keyboard.gui.KeyPanel;

public enum KeyLayout {
  CHROMATIC(new int[][] {
      {-1, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46},
      {-1, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34}, 
      {-1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21}, 
      {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1}},
      "Chromatic",
      VK_C),
  PIANO(new int[][] {
      {-1, -1, 18, 20, 22, -1, 25, 27, -1, 30, 32, 34, -1, -1},
      {-1, 17, 19, 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, -1},
      {-1, -1, 1, 3, -1, 6, 8, 10, -1, 13, 15, -1, -1},
      {-1, 0, 2, 4, 5, 7, 9, 11, 12, 14, 16, -1}},
      "Piano Two Row",
      VK_P),
  PIANO_MIDDLE(new int[][] {
      {-1, -1, 18, 20, 22, -1, 25, 27, -1, 30, 32, 34, -1, -1},
      {-1, 17, 19, 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, -1},
      {-1, 0, 2, 4, 5, 7, 9, 11, 12, 14, 16, 17, -1},
      {-1, 1, 3, -1, 6, 8, 10, -1, 13, 15, -1, -1}},
      "Piano Middle",
      VK_M),
  PIANO_HOMEROW(new int[][] {
      {-1, -1, 18, 20, 22, -1, 25, 27, -1, 30, 32, 34, -1, -1},
      {-1, 17, 19, 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, -1},
      {-1, -1, 1, 3, -1, 6, -1, 6, 8, 10, -1, -1, -1},
      {-1, 0, 2, 4, 5, -1, -1, 7, 9, 11, 12, -1}},
      "Piano Homerow Two Row",
      VK_W),
  PIANO_HOMEROW_MIDDLE(new int[][] {
      {-1, -1, 13, 15, -1, 18, -1, 18, 20, 22, -1, -1, -1, -1},
      {-1, 12, 14, 16, 17, -1, -1, 19, 21, 23, 24, -1, -1, -1},
      {-1, 0, 2, 4, 5, -1, -1, 7, 9, 11, 12, -1, -1},
      {-1, 1, 3, -1, 6, -1, 6, 8, 10, -1, -1, -1}},
      "Piano Homerow Middle",
      VK_H),
  GUITAR(new int[][] {
      {-1, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27},
      {-1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22},
      {-1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, 
      {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}},
      "Guitar",
      VK_G),
  UKELELE(new int[][] {
      {-1, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26},
      {-1, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22},
      {-1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, 
      {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}},
      "Ukelele",
      VK_U);

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
  
  private String name;
  private Map<Integer, Integer> noteMap;
  private Map<Integer, Integer> offsetMap;
  private int mnemonic;
  
  private KeyLayout(int[][] codes, String name, int mnemonic) {
    this.name = name;
    this.mnemonic = mnemonic;
    noteMap = new HashMap<>();
    offsetMap = new HashMap<>();
    for (int i = 0; i < codes.length; i++) {
      for (int j = 0; j < codes[i].length; j++) {
        int keyCode = KeyPanel.KEYBOARD_LAYOUT[i][j];
        int noteCode = codes[i][j];
        noteMap.put(keyCode, noteCode); 
        offsetMap.put(noteCode, keyCode); 
      }
    }
  }
  
  public String getName() {
    return name;
  }
  
  public int getMnemonic() {
    return mnemonic;
  }
  
  public int getNoteOffset(int keyCode) {
    return noteMap.containsKey(keyCode) ? noteMap.get(keyCode) : -1;
  }
  
  public int getKeyCode(int offset) {
    return offsetMap.containsKey(offset) ? offsetMap.get(offset) : -1;
  }
  
  @Override
  public String toString() {
    return name;
  }
}
