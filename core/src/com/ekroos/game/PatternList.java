package com.ekroos.game;

import java.util.ArrayList;

/**
 * Created by tunce on 16.3.2017.
 */

public class PatternList {

    /**
     * Arrays of the patterns/shapes used in the game.
     */

    String name;
    ArrayList<Integer> patternArray;
    int orderNumber;
    int patternSize;

    public PatternList(String name, ArrayList<Integer> array, int orderNumber, int patternSize) {
        this.name = name;
        this.patternArray = array;
        this.orderNumber = orderNumber;
        this.patternSize = patternSize;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Integer> getPatternArray() {
        return patternArray;
    }
    public int getOrderNumber() {
        return orderNumber;
    }
    public void addBox(ArrayList<Integer> list) {
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(6);
        list.add(7);
    }
}