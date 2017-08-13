package com.heliomug.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
  public static List<Integer> toIntegerList(int[] arr) {
    List<Integer> li = new ArrayList<>();
    for (int i : arr) {
      li.add(i);
    }
    return li;
  }
}