package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 1.3.2017.
 */

public class MapMaker {
    private Texture tileTur;
    private Array<BasicTile> basicTiles; //list of the basic tiles
    private Array<TrapTile> trapTiles;  //list of the trap tiles
    private String[] liilaTraps;   //list of trap in "lila" theme
    private String[] keltaTraps;
    private String[] turkoosiTraps;
    private AllTiles latest;    //the most recent tile
    private boolean trapFlag;   //true if trap tile has been made. goes false after 4 tiles
    private int tilesSinceTrap;
    private String theme;
    int tilesCreatedInCurrentTheme;
    final float WORLD_WIDTH = 10f;  //cameras view and all that jazz u know man

    /**
     * Creates the map
     * @param theme initial theme.
     */
    public MapMaker(String theme) {
        tileTur = new Texture(Gdx.files.internal("turkoositiili.png"));
        basicTiles = new Array<BasicTile>();
        trapTiles = new Array<TrapTile>();
        createTrapLists();
        tilesSinceTrap = 0;
        trapFlag = false;
        this.theme = theme;
    }

    public BasicTile getBasicTile() {
        return basicTiles.random();
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

    /**
     * creates the initial map.
     * 10f = width of the game world (should tie this to a variable
     * but mans got to do what mans got to do) the 60f has no explanation it just looks good
     */
    public void createMap() {
        float amountOfTilesNeeded = WORLD_WIDTH/(tileTur.getWidth()/60f);
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

    /**
     * creates the arrays that hold different themes traps
     */
    public void createTrapLists() {
        liilaTraps = new String[1];
        keltaTraps = new String[1];
        turkoosiTraps = new String[2];
        liilaTraps[0] = "punatiili.png";
        keltaTraps[0] = "valkopunatiili.png";
        turkoosiTraps[0] = "punatiili.png";
        turkoosiTraps[1] = "valkopunatiili.png";
    }
    /**
     * Moves every tile in the map and creates the next tile.
     */
    public void mapMove() {

        for (int i = 0;i < basicTiles.size;i++) {
            basicTiles.get(i).move();
        }

        for (int i = 0;i < trapTiles.size;i++) {
            trapTiles.get(i).move();
        }

        if (latest.get_x() + latest.getWidth()/2 <= WORLD_WIDTH) {
             nextTile();
        }
        //Gdx.app.log("amount of tiles", Integer.toString(basicTiles.size));
    }

    /**
     * Creates the next tile. if there has been at least 4 basic tiles
     * from the latest trap tile
     * it draws if the next tile is going to be trap or basic
     */
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

    /**
     *
     * @return returns basic tile that fits the current theme
     */
    public String getRandomBasicTile() {
        String themeTile = theme + "tiili.png";
        return themeTile;
    }

    /**
     *
     * @return returns random trap from the current themes traplist
     */
    public String getRandomTrapTile() {
       String[] list = getTraps();
        int a = MathUtils.random(list.length - 1);


        return list[a];
    }

    public Array<TrapTile> getTrapTiles() {
        return trapTiles;
    }

    /**
     *
     * @return returns the amount of tiles created in current theme
     */
    public int getTilesCreatedInCurrentTheme() {
        return tilesCreatedInCurrentTheme;
    }

    public void setTilesCreatedInCurrentTheme(int amount) {
        tilesCreatedInCurrentTheme = amount;
    }

    public void setTheme(String name) {
        theme = name;
    }


    /**
     * @return returns the newest tile (both basic and trap tiles count as AllTile)
     */
    public AllTiles getLatestTile() {
        return latest;
    }

    /**
     * Goes through each and every basic tile and checks if ekroos is on top of one
     * @param x the x coordinate of ekroos
     * @param y the y coordinate of ekroos
     * @return returns true if ekroos is on top of a basic tile
     */
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

    /**
     *
     * @return returns the traplist of current theme
     */
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

    public String getClosestTrapsType(float ekroosX) {

        for (int i = 0;i < trapTiles.size;i++) {
            if (trapTiles.get(i).get_x() >= ekroosX && !trapTiles.get(i).getIfTileIsSafe()) {
                return trapTiles.get(i).getTrapType();
            }
        }

        return null;
    }

}
