package com.ekroos.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 20.3.2017.
 * List of arrays that hold all the dolls. Also used to create new dolls.
 */

public class Dolls {
    private SoundManager soundManager;
    private Array<BoxDollHelp> boxHelps; //the doll that is called when box pattern is drawn
    private Array<WeightDollHelp> weightHelps;
    private Array<SpikeDollHelp> spikeHelps;
    private Array<WaterDollHelp> waterHelps;
    private Array<SovietDollHelp> sovietHelps;
    private Array<ZombieDollHelp> zombieHelps;
    private Array<DrumDollHelp> drumHelps;
    private Array<FireDollHelp> fireHelps;
    private Array<GhostDollHelp> ghostHelps;
    SpriteBatch batch;

    /**
     * List of all the dolls that can be used.
     * @param batch
     * @param soundManager
     */
    public Dolls(SpriteBatch batch, SoundManager soundManager) {
        this.soundManager = soundManager;
        this.batch = batch;
        boxHelps = new Array<BoxDollHelp>();
        weightHelps = new Array<WeightDollHelp>();
        spikeHelps = new Array<SpikeDollHelp>();
        waterHelps = new Array<WaterDollHelp>();
        sovietHelps = new Array<SovietDollHelp>();
        zombieHelps = new Array<ZombieDollHelp>();
        drumHelps = new Array<DrumDollHelp>();
        fireHelps = new Array<FireDollHelp>();
        ghostHelps = new Array<GhostDollHelp>();
    }

    /**
     * Checks what pattern one has drawn and then calls method to
     * create doll according to the pattern.
     * @param pattern which doll is going to be used.
     * @param trapTiles list of all the trap tiles.
     */
    public void useDoll(String pattern, Array<TrapTile> trapTiles) {
        //Use the box doll
        if (pattern.equals("box")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("box") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useBoxPatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }
        //Use the spike doll
        if (pattern.equals("spike")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("spike") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useSpikePatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }
        //Use the weight doll
        if (pattern.equals("weight")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("weight") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useWeightPatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }
        //Use the water doll
        if (pattern.equals("water")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("water") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useWaterPatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }
        //Use the soviet doll
        if (pattern.equals("soviet")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("soviet") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useSovietPatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }

        //Use the zombie doll
        if (pattern.equals("zombie")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("zombie") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useZombiePatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }

        //Use the drum doll
        if (pattern.equals("drum")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("drum") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useDrumPatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }

        //Use the fire doll
        if (pattern.equals("fire")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("fire") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useFirePatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }
        //Use the ghost doll
        if (pattern.equals("ghost")) {
            useGhostPatternDoll(1.3f, 0.7f);
        }
    }

    /**
     * Get the array of dolls that are used to get over pit traps.
     * @return the array.
     */
    public Array<BoxDollHelp> getBoxHelps() {
        return boxHelps;
    }

    /**
     * Get the array of dolls that are used to get over "weight" traps.
     * @return the array.
     */
    public Array<WeightDollHelp> getWeightHelps() {
        return weightHelps;
    }

    /**
     * Get the array of dolls that are used to get over spike traps.
     * @return the array.
     */
    public Array<SpikeDollHelp> getSpikeHelps() {
        return spikeHelps;
    }

    /**
     * Get the array of dolls that are used to get over water traps.
     * @return the array.
     */
    public Array<WaterDollHelp> getWaterHelps() { return waterHelps;}

    /**
     * Get the array of dolls that are used to get over fur cap traps.
     * @return the array.
     */
    public Array<SovietDollHelp> getSovietHelps() {return sovietHelps;}

    /**
     * Get the array of dolls that are used to get over grave traps.
     * @return the array.
     */
    public Array<ZombieDollHelp> getZombieHelps() {return zombieHelps;}

    /**
     * Get the array of dolls that are used to eliminate incoming ghosts.
     * @return the array.
     */
    public Array<GhostDollHelp> getGhostHelps() { return ghostHelps;}

    /**
     * Get the array of dolls that are used to get over drum traps.
     * @return the array.
     */
    public Array<DrumDollHelp> getDrumHelps() { return drumHelps;}

    /**
     * Get the array of dolls that are used to get over bonfire traps.
     * @return the array.
     */
    public Array<FireDollHelp> getFireHelps() { return  fireHelps;}

    /**
     * Creates doll that is used to get over pit traps.
     * @param towardsX x coordinate where doll is supposed to move.
     * @param towardsY y coordinate where doll is supposed to move.
     */
    public void useBoxPatternDoll(float towardsX, float towardsY) {
        boxHelps.add(new BoxDollHelp(4f, 2.5f, boxHelps, towardsX, towardsY, soundManager));
    }

    /**
     * Creates doll that is used to get over spike traps.
     * @param towardsX x coordinate where doll is supposed to move.
     * @param towardsY y coordinate where doll is supposed to move.
     */
    public void useSpikePatternDoll(float towardsX, float towardsY) {
        spikeHelps.add(new SpikeDollHelp(4f, 2.5f, spikeHelps, towardsX, towardsY, soundManager));
    }

    /**
     * Creates doll that is used to get over "weight" traps.
     * @param towardsX x coordinate where doll is supposed to move.
     * @param towardsY y coordinate where doll is supposed to move.
     */
    public void useWeightPatternDoll(float towardsX, float towardsY) {
        weightHelps.add(new WeightDollHelp(towardsX, 5f, weightHelps, towardsX, towardsY, soundManager));
    }

    /**
     * Creates doll that is used to get over water traps.
     * @param towardsX x coordinate where doll is supposed to move.
     * @param towardsY y coordinate where doll is supposed to move.
     */
    public void useWaterPatternDoll(float towardsX, float towardsY) {
        waterHelps.add(new WaterDollHelp(waterHelps, towardsX, towardsY, soundManager));
    }

    /**
     * Creates doll that is used to get over fur cap traps.
     * @param towardsX x coordinate where doll is supposed to move.
     * @param towardsY y coordinate where doll is supposed to move.
     */
    public void useSovietPatternDoll(float towardsX, float towardsY) {
        sovietHelps.add(new SovietDollHelp(towardsX, -1f, sovietHelps, towardsX, towardsY, soundManager));
    }

    /**
     * Creates doll that is used to get over grave traps.
     * @param towardsX x coordinate where doll is supposed to move.
     * @param towardsY y coordinate where doll is supposed to move.
     */
    public void useZombiePatternDoll(float towardsX, float towardsY) {
        zombieHelps.add(new ZombieDollHelp( 10f, 0.6f, zombieHelps, towardsX, towardsY));
    }

    /**
     * Creates doll that is used to get over drum traps.
     * @param towardsX x coordinate where doll is supposed to move.
     * @param towardsY y coordinate where doll is supposed to move.
     */
    public void useDrumPatternDoll(float towardsX, float towardsY) {
        drumHelps.add(new DrumDollHelp( 0f, 0.5f, drumHelps, soundManager));
    }

    /**
     * Creates doll that is used to get over bonfire traps.
     * @param towardsX x coordinate where doll is supposed to move.
     * @param towardsY y coordinate where doll is supposed to move.
     */
    public void useFirePatternDoll(float towardsX, float towardsY) {
        fireHelps.add(new FireDollHelp( 0f, 5f, fireHelps, towardsX, towardsY));
    }

    /**
     * Creates doll that is used to shield yourself against ghosts.
     */
    public void useGhostPatternDoll(float x, float y) {
        ghostHelps.add(new GhostDollHelp(ghostHelps, x, y, soundManager));
    }

    /**
     * Move all the dolls.
     */
    public void dollsMove() {

        if (boxHelps.size > 0) {
            for (int i = 0; i < boxHelps.size; i++) {
                boxHelps.get(i).move();
                boxHelps.get(i).checkForDispose();
            }
        }

        if (weightHelps.size > 0) {
            for (int i = 0; i < weightHelps.size; i++) {
                weightHelps.get(i).move();
                weightHelps.get(i).checkForDispose();
            }
        }
        if (spikeHelps.size > 0) {
            for (int i = 0; i < spikeHelps.size; i++) {
                spikeHelps.get(i).move();
                spikeHelps.get(i).checkForDispose();
            }
        }
        if (waterHelps.size > 0) {
            for (int i = 0; i < waterHelps.size; i++) {
                waterHelps.get(i).move();
                waterHelps.get(i).checkForDispose();
            }
        }
        if (sovietHelps.size > 0) {
            for (int i = 0; i < sovietHelps.size; i++) {
                sovietHelps.get(i).move();
                sovietHelps.get(i).checkForDispose();
            }
        }
        if (ghostHelps.size > 0) {
            for (int i = 0; i < ghostHelps.size; i++) {
                ghostHelps.get(i).move();
            }
        }
        if (zombieHelps.size > 0) {
            for (int i = 0; i < zombieHelps.size; i++) {
                zombieHelps.get(i).move();
            }
        }
        if (drumHelps.size > 0) {
            for (int i = 0; i < drumHelps.size; i++) {
                drumHelps.get(i).move();
            }
        }
        if (fireHelps.size > 0) {
            for (int i = 0; i < fireHelps.size; i++) {
                fireHelps.get(i).move();
                fireHelps.get(i).checkForDispose();
            }
        }
    }

    /**
     * Draws all the dolls.
     */
    public void dollsDraw() {

        if (boxHelps.size > 0) {
            for (int i = 0; i < boxHelps.size; i++) {
                boxHelps.get(i).draw(batch);
            }
        }

        if (spikeHelps.size > 0) {
            for (int i = 0; i < spikeHelps.size; i++) {
                spikeHelps.get(i).draw(batch);
            }
        }

        if (weightHelps.size > 0) {
            for (int i = 0; i < weightHelps.size; i++) {
                weightHelps.get(i).draw(batch);
            }
        }

        if (waterHelps.size > 0) {
            for (int i = 0; i < waterHelps.size; i++) {
                waterHelps.get(i).draw(batch);
            }
        }

        if (ghostHelps.size > 0) {
            for (int i = 0; i < ghostHelps.size; i++) {
                ghostHelps.get(i).draw(batch);
            }
        }

        if (sovietHelps.size > 0) {
            for (int i = 0; i < sovietHelps.size; i++) {
                sovietHelps.get(i).draw(batch);
            }
        }

        if (zombieHelps.size > 0) {
            for (int i = 0; i < zombieHelps.size; i++) {
                zombieHelps.get(i).draw(batch);
            }
        }

        if (drumHelps.size > 0) {
            for (int i = 0; i < drumHelps.size; i++) {
                drumHelps.get(i).draw(batch);
            }
        }

        if (fireHelps.size > 0) {
            for (int i = 0; i < fireHelps.size; i++) {
                fireHelps.get(i).draw(batch);
            }
        }
    }

    /**
     * Dispose all the dolls.
     */
    public void dollsDispose() {
        if (boxHelps.size > 0) {
            for (int i = 0; i < boxHelps.size; i++) {
                boxHelps.get(i).dispose();
            }
        }

        if (spikeHelps.size > 0) {
            for (int i = 0; i < spikeHelps.size; i++) {
                spikeHelps.get(i).dispose();
            }
        }

        if (weightHelps.size > 0) {
            for (int i = 0; i < weightHelps.size; i++) {
                weightHelps.get(i).dispose();
            }
        }

        if (waterHelps.size > 0) {
            for (int i = 0; i < waterHelps.size; i++) {
                waterHelps.get(i).dispose();
            }
        }
        if (sovietHelps.size > 0) {
            for (int i = 0; i < sovietHelps.size; i++) {
                sovietHelps.get(i).dispose();
            }
        }
        if (ghostHelps.size > 0) {
            for (int i = 0; i < ghostHelps.size; i++) {
                ghostHelps.get(i).dispose();
            }
        }
        if (zombieHelps.size > 0) {
            for (int i = 0; i < zombieHelps.size; i++) {
                zombieHelps.get(i).dispose();
            }
        }
        if (drumHelps.size > 0) {
            for (int i = 0; i < drumHelps.size; i++) {
                drumHelps.get(i).dispose();
            }
        }
        if (fireHelps.size > 0) {
            for (int i = 0; i < fireHelps.size; i++) {
                fireHelps.get(i).dispose();
            }
        }
    }
}
