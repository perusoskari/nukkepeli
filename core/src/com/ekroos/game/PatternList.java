package com.ekroos.game;

import java.util.ArrayList;

/**
 * Created by tunce on 16.3.2017.
 */

public class PatternList {

    /**
     * Arrays of the patterns/shapes used in the game.
     */
    private String name;
    private ArrayList<Integer> patternArray;
    private int orderNumber;
    private int patternSize;

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

    public void addWeight(ArrayList<Integer> list) {
        list.add(3);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
    }
    public void addSpike(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(2);
    }
    public void addWater(ArrayList<Integer> list) {
        list.add(4);
        list.add(4);
        list.add(6);
        list.add(7);
        list.add(8);
    }
    public void addSoviet(ArrayList<Integer> list) {
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
    }
    public void addZombie(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(6);
        list.add(7);
    }
    public void addDrum(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
    }
    public void addGhost(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(7);
    }
}
