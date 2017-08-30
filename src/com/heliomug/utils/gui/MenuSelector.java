package com.heliomug.utils.gui;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuSelector<T> extends JMenu {
  public MenuSelector(String title, List<T> options, Consumer<T> toDo) {
    this(title, options, toDo, (T option) -> option.toString());
  }
    
  public MenuSelector(String title, List<T> options, Consumer<T> toDo, Function<T, String> labelFxn) {
    this(title, options, null, toDo, labelFxn);
  }

  public MenuSelector(String title, List<T> options, List<T> hiddenOptions, Consumer<T> toDo) {
    this(title, options, null, toDo, (T option) -> option.toString());
  }

  public MenuSelector(String title, List<T> options, List<T> hiddenOptions, Consumer<T> toDo, Function<T, String> labelFxn) {
    this(title, options, Arrays.asList(hiddenOptions), toDo, labelFxn, (T option) -> null);
  }

//  public MenuSelector(String title, List<T> options, List<T> hiddenOptions, Consumer<T> toDo, Function<T, String> labelFxn, Function<T, Integer> mnemonicFxn) {
//    this(title, options, Arrays.asList(hiddenOptions), toDo, labelFxn, mnemonicFxn);
//  }

  public MenuSelector(String title, List<T> options, List<List<T>> hiddenOptionsList, Consumer<T> toDo, Function<T, String> labelFxn, Function<T, Integer> mnemonicFxn) {
    super(title);
    JMenuItem item;
    for (T option : options) {
      item = new JMenuItem(labelFxn.apply(option));
      item.addActionListener((ActionEvent e) -> toDo.accept(option));
      Integer mnemonic = mnemonicFxn.apply(option);
      if (mnemonic != null) {
        item.setMnemonic(mnemonic);
      }
      add(item);
    }
    
    JMenu superMenu = this;
    
    if (hiddenOptionsList != null) {
      for (List<T> hiddenOptions : hiddenOptionsList) {
        if (hiddenOptions != null) {
          JMenu subMenu = new JMenu("...");
          for (T option : hiddenOptions) {
            item = new JMenuItem(labelFxn.apply(option));
            item.addActionListener((ActionEvent e) -> toDo.accept(option));
            subMenu.add(item);
          }
          if (superMenu == this) {
            superMenu.add(subMenu);
          } else {
            superMenu.add(subMenu, 0);
          }
          superMenu = subMenu;
        }
      }
    }
  }
}