package com.ekroos.game;

/**
 * Created by tunce on 6.4.2017.
 */

public class TimeUtilities {

    float startTime;
    float elapsedTime;
    float helperSeconds;
    int flatSeconds;
    int flatHelperSeconds;

    public TimeUtilities() {

        startTime = 0;
        elapsedTime = 0;
        helperSeconds = 0;
        flatSeconds = 0;
        flatHelperSeconds = 0;
    }
    public int getPlaySeconds() {
        getEndingTime();
        helperSeconds = (float) (elapsedTime / 1000000000.0);
        flatSeconds = Math.round(helperSeconds);
        return flatSeconds;
    }
    //This is for point counter to check if time has passed one second
    public void setFlatHelperSeconds(int flatSeconds) {
        flatHelperSeconds = flatSeconds;
    }
    public int getFlatHelperSeconds() {
        return flatHelperSeconds;
    }
    public void startCountingTime() {
        startTime = System.nanoTime();
    }
    public void getEndingTime() {
        elapsedTime = System.nanoTime() - startTime;
    }
}
