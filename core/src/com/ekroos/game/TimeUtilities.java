package com.ekroos.game;

/**
 * Created by tunce on 6.4.2017.
 */

/**
 * Creates timeutilities for counting seconds.
 */
public class TimeUtilities {

    float startTime;
    float elapsedTime;
    float helperSeconds;
    int flatSeconds;
    int flatHelperSeconds;

    /**
     * Creates the timeutilities.
     */
    public TimeUtilities() {

        startTime = 0;
        elapsedTime = 0;
        helperSeconds = 0;
        flatSeconds = 0;
        flatHelperSeconds = 0;
    }

    /**
     * Returns how many saconds were played.
     * @return
     */
    public int getPlaySeconds() {
        getEndingTime();
        helperSeconds = (float) (elapsedTime / 1000000000.0);
        flatSeconds = Math.round(helperSeconds);
        return flatSeconds;
    }

    /**
     * Sets seconds for comparing.
     * @param flatSeconds
     */
    public void setFlatHelperSeconds(int flatSeconds) {
        flatHelperSeconds = flatSeconds;
    }

    /**
     * Returns rounded seconds.
     * @return
     */
    public int getFlatHelperSeconds() {
        return flatHelperSeconds;
    }

    /**
     * Starts counting time.
     */
    public void startCountingTime() {
        startTime = System.nanoTime();
    }

    /**
     * Sets the ending time.
     */
    public void getEndingTime() {
        elapsedTime = System.nanoTime() - startTime;
    }
}
