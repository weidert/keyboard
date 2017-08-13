package com.heliomug.utils.gui;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuSelector<T> extends JMenu {
  public MenuSelector(String title, Collection<T> options, Consumer<T> toDo) {
    this(title, options, toDo, (T option) -> option.toString());
  }
    
  public MenuSelector(String title, Collection<T> options, Consumer<T> toDo, Function<T, String> labelFxn) {
    this(title, options, null, toDo, labelFxn);
  }

  public MenuSelector(String title, Collection<T> options, Collection<T> hiddenOptions, Consumer<T> toDo) {
    this(title, options, null, toDo, (T option) -> option.toString());
  }

  public MenuSelector(String title, Collection<T> options, Collection<T> hiddenOptions, Consumer<T> toDo, Function<T, String> labelFxn) {
    super(title);
    JMenuItem item;
    for (T option : options) {
      item = new JMenuItem(labelFxn.apply(option));
      item.addActionListener((ActionEvent e) -> toDo.accept(option));
      add(item);
    }
    
    if (hiddenOptions != null) {
      JMenu menu = new JMenu("...");
      for (T option : hiddenOptions) {
        if (!options.contains(option)) {
          item = new JMenuItem(labelFxn.apply(option));
          item.addActionListener((ActionEvent e) -> toDo.accept(option));
          menu.add(item);
        }
      }
      add(menu);
    }
  }
}