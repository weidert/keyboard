package com.heliomug.music.keyboard;

import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_BACK_QUOTE;
import static java.awt.event.KeyEvent.VK_BACK_SLASH;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_CAPS_LOCK;
import static java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
import static java.awt.event.KeyEvent.VK_COMMA;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_EQUALS;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_I;
import static java.awt.event.KeyEvent.VK_J;
import static java.awt.event.KeyEvent.VK_K;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_OPEN_BRACKET;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_PERIOD;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_QUOTE;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SCROLL_LOCK;
import static java.awt.event.KeyEvent.VK_SEMICOLON;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SLASH;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;
import static java.awt.event.KeyEvent.VK_Z;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.heliomug.music.Note;

public class KeyPanel extends JPanel {
  private static final long serialVersionUID = 7148600854029993474L;

  private static final int[] KEYBOARD_CODES = {
      VK_BACK_QUOTE, VK_1, VK_2, VK_3, VK_4, VK_5, VK_6, VK_7, VK_8, VK_9, VK_0, VK_MINUS, VK_EQUALS, VK_BACK_SPACE,
      VK_TAB, VK_Q, VK_W, VK_E, VK_R, VK_T, VK_Y, VK_U, VK_I, VK_O, VK_P, VK_OPEN_BRACKET, VK_CLOSE_BRACKET, VK_BACK_SLASH,
      VK_CAPS_LOCK, VK_A, VK_S, VK_D, VK_F, VK_G, VK_H, VK_J, VK_K, VK_L, VK_SEMICOLON, VK_QUOTE, VK_ENTER,
      VK_Z, VK_X, VK_C, VK_V, VK_B, VK_N, VK_M, VK_COMMA, VK_PERIOD, VK_SLASH, VK_SHIFT, VK_SCROLL_LOCK};

  public static final int[][] KEYBOARD_LAYOUT = {
      {VK_BACK_QUOTE, VK_1, VK_2, VK_3, VK_4, VK_5, VK_6, VK_7, VK_8, VK_9, VK_0, VK_MINUS, VK_EQUALS, VK_BACK_SPACE},
      {VK_TAB, VK_Q, VK_W, VK_E, VK_R, VK_T, VK_Y, VK_U, VK_I, VK_O, VK_P, VK_OPEN_BRACKET, VK_CLOSE_BRACKET, VK_BACK_SLASH},
      {VK_CAPS_LOCK, VK_A, VK_S, VK_D, VK_F, VK_G, VK_H, VK_J, VK_K, VK_L, VK_SEMICOLON, VK_QUOTE, VK_ENTER},
      {VK_SCROLL_LOCK, VK_Z, VK_X, VK_C, VK_V, VK_B, VK_N, VK_M, VK_COMMA, VK_PERIOD, VK_SLASH, VK_SHIFT}};

  public static final Map<Integer, Double> KEY_WIDTHS = new HashMap<Integer, Double>() {
    private static final long serialVersionUID = 2065048936796769302L;

  {
    put(VK_BACK_QUOTE, 1.33);
    put(VK_BACK_SPACE, 1.67);
    put(VK_BACK_SLASH, 1.33);
    put(VK_ENTER, 2.0);
    put(VK_SCROLL_LOCK, 2.33);
    put(VK_TAB, 1.67);
    put(VK_CAPS_LOCK, 2.0);
    put(VK_SHIFT, 2.67);
  }}; 

  public static final Map<Integer, String> STANDARD_CHARS = new HashMap<Integer, String>() {
    private static final long serialVersionUID = 6090046233053334868L;
  {
    put(VK_BACK_QUOTE, "`");
    put(VK_TAB, "\u2B7E");
    put(VK_CAPS_LOCK, "\u21EA");
    put(VK_MINUS, "-");
    put(VK_EQUALS, "=");
    put(VK_BACK_SPACE, "\u232b");
    put(VK_OPEN_BRACKET, "[");
    put(VK_CLOSE_BRACKET, "]");
    put(VK_BACK_SLASH, "\\");
    put(VK_SEMICOLON, ";");
    put(VK_QUOTE, "'");
    put(VK_ENTER, "\u21b5");
    put(VK_COMMA, ",");
    put(VK_PERIOD, ".");
    put(VK_SLASH, "/");
    put(VK_SCROLL_LOCK, "\u21E7");
    put(VK_SHIFT, "\u21E7");
  }}; 

  private static KeyPanel thePanel;
  
  public static KeyPanel getThePanel() {
    if (thePanel == null) {
      thePanel = new KeyPanel();
    }
    return thePanel; 
  }
  

  private Map<Integer, KeyButton> buttonMap;
  
  private KeyPanel() {
    super();

    buttonMap = new HashMap<>();
    for (int i = 0; i < KEYBOARD_CODES.length; i++) {
      int code = KEYBOARD_CODES[i];
      String letter = translateToCharacter(code);
      double widthFactor = KEY_WIDTHS.containsKey(code) ? KEY_WIDTHS.get(code) : 1.0; 
      KeyButton button = new KeyButton(letter, widthFactor);
      buttonMap.put(code, button);
    }

    refresh();
    
    setupGUI();
  }
  
  public void setupGUI() {
    setLayout(new GridLayout(0, 1));
    for (int i = 0; i < KEYBOARD_LAYOUT.length; i++) {
      GridBagConstraints g = new GridBagConstraints();
      g.fill = GridBagConstraints.BOTH;
      g.weightx = .5;
      g.weighty = .5;
      g.ipadx = 2;
      g.ipady = 2;
      JPanel subpanel = new JPanel(new GridBagLayout());
      subpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      for (int j = 0; j < KEYBOARD_LAYOUT[i].length; j++) {
        g.gridy = 0;
        g.gridx = j;
        int code = KEYBOARD_LAYOUT[i][j];
        subpanel.add(buttonMap.get(code), g);
      }
      add(subpanel);
    }
  }
  
  public void refresh() {
    KeyLayout layout = Session.getTheSession().getKeyLayout();
    for (int i = 0; i < KEYBOARD_LAYOUT.length; i++) {
      for (int j = 0; j < KEYBOARD_LAYOUT[i].length; j++) {
        int keyCode = KEYBOARD_LAYOUT[i][j]; 
        int offset = layout.getNoteOffset(keyCode);
        Note note = Session.getTheSession().getNote(offset);
        buttonMap.get(keyCode).setNote(note);
      }
    }
  }
  
  public void whiteKey(Note note) {
    
  }

  public void whiteKey(int code) {
    if (buttonMap.containsKey(code)) {
      buttonMap.get(code).whiteKey();
    }
  }
  
  public void recolorKey(int code) {
    if (buttonMap.containsKey(code)) {
      buttonMap.get(code).resetColor();
    }
  }
  
  public void recolorAll() {
    for (KeyButton button : buttonMap.values()) {
      button.resetColor();
    }
  }
  
  private static String translateToCharacter(int code) {
    if (STANDARD_CHARS.containsKey(code)) {
      return STANDARD_CHARS.get(code);
    }
    return KeyEvent.getKeyText(code);
  }
}
