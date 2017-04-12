package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 20.3.2017.
 */

public class Dolls {
    private Array<BoxDollHelp> boxHelps; //the doll that is called when box pattern is drawn
    private Array<WeightDollHelp> weightHelps;
    private Array<SpikeDollHelp> spikeHelps;
    SpriteBatch batch;

    public Dolls(SpriteBatch batch) {
        this.batch = batch;
        boxHelps = new Array<BoxDollHelp>();
        weightHelps = new Array<WeightDollHelp>();
        spikeHelps = new Array<SpikeDollHelp>();
    }

    /**
     *
     * @param pattern which doll is going to be used
     * @param trapTiles list of all the trap tiles
     */
    public void useDoll(String pattern, Array<TrapTile> trapTiles) {

        if (!pattern.equals("")) {
            Gdx.input.vibrate(500);
        }
        //Use the box doll
        if (pattern.equals("box")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("1") &&
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
                if (trapTiles.get(i).getTrapType().equals("2") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useSpikePatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }

        if (pattern.equals("weight")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("3") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useWeightPatternDoll(trapTiles.get(i).get_x(),
                            trapTiles.get(i).getRectangle().getHeight());
                    trapTiles.get(i).setSafe();
                    break;
                }
            }
        }
    }

    public Array<BoxDollHelp> getBoxHelps() {
        return boxHelps;
    }
    public Array<WeightDollHelp> getWeightHelps() {
        return weightHelps;
    }
    public Array<SpikeDollHelp> getSpikeHelps() {
        return spikeHelps;
    }

    public void useBoxPatternDoll(float towardsX, float towardsY) {
        boxHelps.add(new BoxDollHelp(4f, 2.5f, boxHelps, towardsX, towardsY));
    }

    public void useSpikePatternDoll(float towardsX, float towardsY) {
        spikeHelps.add(new SpikeDollHelp(4f, 2.5f, spikeHelps, towardsX, towardsY));
    }

    public void useWeightPatternDoll(float towardsX, float towardsY) {
        weightHelps.add(new WeightDollHelp(towardsX, 5f, weightHelps, towardsX, towardsY));
    }

    /**
     * render all the dolls
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
    }

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
    }

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
    }
}
