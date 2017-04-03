package com.ekroos.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 20.3.2017.
 */

public class Dolls {
    private Array<BoxDollHelp> boxHelps; //the doll that is called when box pattern is drawn
    SpriteBatch batch;

    public Dolls(SpriteBatch batch) {
        this.batch = batch;
        boxHelps = new Array<BoxDollHelp>();
    }

    /**
     *
     * @param pattern which doll is going to be used
     * @param trapTiles list of all the trap tiles
     */
    public void useDoll(String pattern, Array<TrapTile> trapTiles) {

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

        if (pattern.equals("spike")) {

            for (int i = 0;i < trapTiles.size;i++) {
                if (trapTiles.get(i).getTrapType().equals("2") &&
                        !trapTiles.get(i).getIfTileIsSafe()) {
                    useBoxPatternDoll(trapTiles.get(i).get_x(),
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

    public void useBoxPatternDoll(float towardsX, float towardsY) {
        boxHelps.add(new BoxDollHelp(4f, 2.5f, boxHelps, towardsX, towardsY));
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
    }

    public void dollsDraw() {

        if (boxHelps.size > 0) {
            for (int i = 0; i < boxHelps.size; i++) {
                boxHelps.get(i).draw(batch);
            }
        }
    }
}
