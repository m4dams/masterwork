package com.gfa.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
  public static void main(String[] args) {


    final List<Integer> list = new ArrayList<>();

    list.add(1);
    list.add(2);
    list.add(3);

    list.remove(2);
    System.out.println(list);

    final double d = 1 / 2;
    System.out.println(d);
    System.out.println(Math.min(Double.MIN_VALUE, 0.0d));
  }
}