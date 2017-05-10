package com.ekroos.game;

import java.util.ArrayList;

/**
 * Created by tunce on 16.3.2017.
 */
/**
 * Contains a list of the patterns.
 */
public class PatternList {

    private String name;
    private ArrayList<Integer> patternArray;
    private int orderNumber;
    private int patternSize;

    /**
     * Creates a patternlist.
     * @param name of the pattern
     * @param array containing the numbers of the balls.
     * @param orderNumber of the patter
     * @param patternSize is the size of the pattern
     */
    public PatternList(String name, ArrayList<Integer> array, int orderNumber, int patternSize) {
        this.name = name;
        this.patternArray = array;
        this.orderNumber = orderNumber;
        this.patternSize = patternSize;
    }

    /**
     * Returns name.
     * @return name of the pattern.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the patterns array.
     * @return patterns array.
     */
    public ArrayList<Integer> getPatternArray() {
        return patternArray;
    }

    /**
     * Returns the ordernumber.
     * @return ordernumber.
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Adds the box shaped array
     * @param list the arraylist.
     */
    public void addBox(ArrayList<Integer> list) {
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(6);
        list.add(7);
    }
    /**
     * Adds the weight array.
     * @param list the arraylist.
     */
    public void addWeight(ArrayList<Integer> list) {
        list.add(3);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
    }
    /**
     * Adds the spike array.
     * @param list the arraylist.
     */
    public void addSpike(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(2);
    }
    /**
     * Adds the water array.
     * @param list the arraylist.
     */
    public void addWater(ArrayList<Integer> list) {
        list.add(4);
        list.add(4);
        list.add(6);
        list.add(7);
        list.add(8);
    }
    /**
     * Adds the hat array.
     * @param list the arraylist.
     */
    public void addSoviet(ArrayList<Integer> list) {
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
    }
    /**
     * Adds the zombie array.
     * @param list the arraylist.
     */
    public void addZombie(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(6);
        list.add(7);
    }
    /**
     * Adds the drum array.
     * @param list the arraylist.
     */
    public void addDrum(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
    }
    /**
     * Adds the fire array.
     * @param list the arraylist.
     */
    public void addFire(ArrayList<Integer> list) {
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
    }
    /**
     * Adds the mushroom array.
     * @param list the arraylist.
     */
    public void addShroom(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
    }
    /**
     * Adds the ghost array.
     * @param list the arraylist.
     */
    public void addGhost(ArrayList<Integer> list) {
        list.add(0);
        list.add(0);
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(7);
    }
}
