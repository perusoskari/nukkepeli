package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 1.3.2017.
 */

public class MapMaker {
    Texture tileTur;
    Array<BasicTile> basicTiles;
    Array<TrapTile> trapTiles;
    String[] liilaTraps;
    String[] keltaTraps;
    String[] turkoosiTraps;
    AllTiles latest;
    boolean trapFlag;
    int tilesSinceTrap;
    String theme;
    int tilesCreatedInCurrentTheme;

    public MapMaker(String theme) {
        tileTur = new Texture(Gdx.files.internal("turkoositiili.png"));
        basicTiles = new Array<BasicTile>();
        trapTiles = new Array<TrapTile>();
        liilaTraps = new String[1];
        keltaTraps = new String[1];
        turkoosiTraps = new String[2];
        liilaTraps[0] = "punatiili.png";
        keltaTraps[0] = "valkopunatiili.png";
        turkoosiTraps[0] = "punatiili.png";
        turkoosiTraps[1] = "valkopunatiili.png";
        tilesSinceTrap = 0;
        trapFlag = false;
        this.theme = theme;
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0;i < basicTiles.size; i++) {
            basicTiles.get(i).draw(batch);
        }

        for (int i = 0;i < trapTiles.size; i++) {
            trapTiles.get(i).draw(batch);
        }
    }

    public void dispose() {
        tileTur.dispose();

        for (int i = 0;i < basicTiles.size;i++) {
            basicTiles.get(i).dispose();
        }

        for (int i = 0;i < trapTiles.size;i++) {
            trapTiles.get(i).dispose();
        }
    }

    public void createMap() {
        float amountOfTilesNeeded = 10f/(tileTur.getWidth()/60f);
        float x = 0;
        float y = 0;

        for (int i = 0; i < amountOfTilesNeeded; i++) {
            basicTiles.add(new BasicTile(getRandomBasicTile(), x, y, basicTiles));
            x += basicTiles.get(i).getWidth();
            tilesCreatedInCurrentTheme++;
        }
        latest = basicTiles.get(basicTiles.size - 1);
        nextTile();
        nextTile();
    }

    public void mapMove() {

        for (int i = 0;i < basicTiles.size;i++) {
            basicTiles.get(i).move();
        }

        for (int i = 0;i < trapTiles.size;i++) {
            trapTiles.get(i).move();
        }

        if (latest.get_x() + latest.getWidth()/2 <= 10f) {
             nextTile();
        }
        //Gdx.app.log("amount of tiles", Integer.toString(basicTiles.size));
    }

    public void nextTile() {
        float x = latest.get_x();
        boolean choice1 = false;
        boolean choice2 = false;

        if (trapFlag) {
            choice1 = true;
            choice2 = false;
            tilesSinceTrap += 1;
        } else {
            int a = MathUtils.random(1);
            //Gdx.app.log("a on", Integer.toString(a));

            if (a == 0) {
                choice1 = true;
                choice2 = false;
            } else if (a == 1) {
                choice2 = true;
                choice1 = false;
            }
        }

        if (choice1) {
            BasicTile basic = new BasicTile(getRandomBasicTile(), x + latest.getWidth(),
                    0, basicTiles);
            basicTiles.add(basic);
            latest = basic;
        } else if (choice2){
            trapFlag = true;
            TrapTile trap = new TrapTile(getRandomTrapTile(), x + latest.getWidth(),
                    0, trapTiles);
            trapTiles.add(trap);
            latest = trap;
            tilesSinceTrap = 0;
        }

        tilesCreatedInCurrentTheme++;
        if (tilesSinceTrap >= 4) {
            trapFlag = false;
        }
    }

    public String getRandomBasicTile() {
        String themeTile = theme + "tiili.png";
        return themeTile;
    }

    public String getRandomTrapTile() {
       String[] list = getTraps();
        int a = MathUtils.random(list.length - 1);


        return list[a];
    }

    public int getTilesCreatedInCurrentTheme() {
        return tilesCreatedInCurrentTheme;
    }

    public void setTilesCreatedInCurrentTheme(int amount) {
        tilesCreatedInCurrentTheme = amount;
    }

    public void setTheme(String name) {
        theme = name;
    }

    public AllTiles getLatestTile() {
        return latest;
    }

    public boolean getIfOnBasicTile(float x, float y) {

       for (int i = 0; i < basicTiles.size;i++) {
           if (x <= (basicTiles.get(i).get_x() + basicTiles.get(i).getWidth()) &&
                   x >= basicTiles.get(i).get_x() &&
                   y <= basicTiles.get(i).getHeight()) {
               return true;
           }
       }

        return false;
    }

    public String[] getTraps() {
        String[] toReturn = null;

        if (theme.equals("liila")) {
            toReturn = liilaTraps;
        }

        if (theme.equals("kelta")) {
            toReturn = keltaTraps;
        }

        if (theme.equals("turkoosi")) {
            toReturn = turkoosiTraps;
        }

        return toReturn;
    }

}
