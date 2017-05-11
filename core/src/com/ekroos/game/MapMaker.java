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
    private Texture tileTur;
    private Array<BasicTile> basicTiles; //list of the basic tiles
    private Array<TrapTile> trapTiles;  //list of the trap tiles
    private String[] kitchenTraps;   //list of trap in "lila" theme
    private String[] saloonTraps;
    private String[] cellarTraps;
    private AllTiles latest;    //the most recent tile
    private boolean trapFlag;   //true if trap tile has been made. goes false after 4 tiles
    private int tilesSinceTrap;
    private String theme;
    private String nextTheme;
    int tilesCreatedInCurrentTheme;
    final float WORLD_WIDTH = 10f;  //cameras view and all that jazz u know man

    private Texture background;
    private Texture nextBackground;
    private Rectangle backgroundRectangle;
    private Rectangle nextBackgroundRectangle;

    private Texture kitchenBackground;
    private Texture cellarBackground;
    private Texture saloonBackground;
    String[] themes;

    private float timeSpentInTheme;
    private int timesThemeChanged;

    private Array<Ghost> ghostList;
    private Array<PickUpDoll> pickUpDolls;
    private boolean waterTrapUnlocked;
    private boolean ghostTrapUnlocked;
    private boolean sovietTrapUnlocked;
    private boolean zombieTrapUnlocked;
    private boolean drumTrapUnlocked;
    private boolean fireTrapUnlocked;
    private boolean shroomTrapUnlocked;
    private int amountOfTrapsUnlocked;
    private SoundManager soundManager;
    private boolean tripping;

    /**
     * Creates the map
     * @param theme initial theme.
     */
    public MapMaker(String theme, SoundManager soundManager) {
        tileTur = new Texture(Gdx.files.internal("oldthings/turkoositiili.png"));
        basicTiles = new Array<BasicTile>();
        trapTiles = new Array<TrapTile>();
        this.soundManager = soundManager;
        createTrapLists();
        tilesSinceTrap = 0;
        timesThemeChanged = 0;
        trapFlag = false;
        themes = new String[3];
        nextTheme = "";
        createThemes();
        this.theme = theme;

        backgroundRectangle = new Rectangle();
        nextBackgroundRectangle = new Rectangle();

        kitchenBackground = new Texture(Gdx.files.internal("themeBg/kitchen.png"));
        cellarBackground = new Texture(Gdx.files.internal("themeBg/cellar.png"));
        saloonBackground = new Texture(Gdx.files.internal("themeBg/saloon.png"));

        setBackgrounds();
        ghostList = new Array<Ghost>();
        pickUpDolls = new Array<PickUpDoll>();
        amountOfTrapsUnlocked = 2;
        tripping = false;

    }

    /**
     * Sets the background image to match the theme.
     */
    public void setBackgrounds() {
        if (theme.equals("kitchen")) {
            background = kitchenBackground;
        } else if (theme.equals("saloon")) {
            background = saloonBackground;
        } else if (theme.equals("cellar")) {
            background = cellarBackground;
        }

        backgroundRectangle.set(0f, -3f, background.getWidth()/100f,
                background.getHeight()/100f);

        if (!nextTheme.equals("")) {
            nextBackgroundRectangle.x = backgroundRectangle.x + backgroundRectangle.width;
        }
    }

    /**
     * Sets the next background to match the next upcoming theme.
     */
    public void setNextBackground() {
        if (nextTheme.equals("kitchen")) {
            nextBackground= kitchenBackground;
        } else if (nextTheme.equals("saloon")) {
            nextBackground= saloonBackground;
        } else if (nextTheme.equals("cellar")) {
            nextBackground= cellarBackground;
        }

        nextBackgroundRectangle.set(backgroundRectangle.x + backgroundRectangle.getWidth(),
                -3f, nextBackground.getWidth()/100f, nextBackground.getHeight()/100f);
    }

    /**
     * Checks if its time to change theme. Time is measure by the amount of tiles created.
     */
    public void checkForThemeChange() {

        if (timesThemeChanged == 0) {
            if (timeSpentInTheme > 630) {
                lottoNextTheme();
                theme = nextTheme;
                setNextBackground();
                //tilesCreatedInCurrentTheme = 0;
                timeSpentInTheme = 0;
                timesThemeChanged++;
            }
        } else {
            if (timeSpentInTheme > 1680) {
                lottoNextTheme();
                theme = nextTheme;
                setNextBackground();
                //tilesCreatedInCurrentTheme = 0;
                timeSpentInTheme = 0;
                timesThemeChanged++;
            }
        }


        if (backgroundRectangle.x + backgroundRectangle.getWidth() < 0f) {
            setBackgrounds();
        }
    }

    /**
     * Set the upcoming theme to random.
     */
    public void lottoNextTheme() {
        int a = MathUtils.random(2);

        while (themes[a].equals(theme)) {
             a = MathUtils.random(2);
        }

        nextTheme = themes[a];
    }

    /**
     * Creates all three themes.
     */
    public void createThemes() {
        themes = new String[3];
        themes[0] = "kitchen";
        themes[1] = "cellar";
        themes[2] = "saloon";
    }

    /**
     * Disposes themes.
     */
    public void disposeThemes() {
        kitchenBackground.dispose();
        cellarBackground.dispose();
        saloonBackground.dispose();
    }

    /**
     * Get if player is under the influence of the mushrooms.
     * @return true if player is tripping and false if not.
     */
    public boolean getTripping() {
        return tripping;
    }

    /**
     * Sets tripping variable.
     * @param t boolean to set tripping to.
     */
    public void setTripping(boolean t) {
        tripping = t;
    }

    /**
     * Checks if enough tiles has gone past so its time to unlock new trap and doll which answers to it.
     */
    public void ifItsTimeToUnlock() {
        int tilesAmount = getTilesCreatedInCurrentTheme();

        if (tilesAmount > 50 && pickUpDolls.size == 0 && !waterTrapUnlocked) {
            if (latest.getClass().equals(BasicTile.class)) {
                new PickUpDoll(latest.get_x(),
                        latest.getRectangle().height, "water", pickUpDolls);
            }
        }

        if (tilesAmount > 90 && pickUpDolls.size == 0 && !sovietTrapUnlocked) {
            if (latest.getClass().equals(BasicTile.class)) {
                new PickUpDoll(latest.get_x(),
                        latest.getRectangle().height, "soviet", pickUpDolls);
            }
        }

        if (tilesAmount > 130 && pickUpDolls.size == 0 && !zombieTrapUnlocked) {
            if (latest.getClass().equals(BasicTile.class)) {
                new PickUpDoll(latest.get_x(),
                        latest.getRectangle().height, "zombie", pickUpDolls);
            }
        }

        if (tilesAmount > 170 && pickUpDolls.size == 0 && !drumTrapUnlocked) {
            if (latest.getClass().equals(BasicTile.class)) {
                new PickUpDoll(latest.get_x(),
                        latest.getRectangle().height, "drum", pickUpDolls);
            }
        }

        if (tilesAmount > 210 && pickUpDolls.size == 0 && !fireTrapUnlocked) {
            if (latest.getClass().equals(BasicTile.class)) {
                new PickUpDoll(latest.get_x(),
                        latest.getRectangle().height, "fire", pickUpDolls);
            }
        }

        if (tilesAmount > 250 && pickUpDolls.size == 0 && !shroomTrapUnlocked) {
            if (latest.getClass().equals(BasicTile.class)) {
                new PickUpDoll(latest.get_x(),
                        latest.getRectangle().height, "shroom", pickUpDolls);
            }
        }

        if (tilesAmount > 290 && pickUpDolls.size == 0 && !ghostTrapUnlocked) {
            if (latest.getClass().equals(BasicTile.class)) {
                new PickUpDoll(latest.get_x(),
                        latest.getRectangle().height, "ghost", pickUpDolls);
            }
        }

    }

    /**
     * Unlocks new trap map has a chance to generate.
     * @param type  what type of trap is unlocked.
     */
    public void unlock(String type) {
        amountOfTrapsUnlocked++;
        System.out.println(amountOfTrapsUnlocked);
        if (type.equals("water")) {
            waterTrapUnlocked = true;
        }
        if (type.equals("ghost")) {
            ghostTrapUnlocked = true;
        }
        if (type.equals("soviet")) {
            sovietTrapUnlocked= true;
        }
        if (type.equals("zombie")) {
            zombieTrapUnlocked= true;
        }
        if (type.equals("drum")) {
            drumTrapUnlocked= true;
        }
        if (type.equals("fire")) {
            fireTrapUnlocked = true;
        }
        if (type.equals("shroom")) {
            shroomTrapUnlocked = true;
        }
    }

    /**
     * Get random basic tile from array which holds all the basic tiles.
     * @return
     */
    public BasicTile getBasicTile() {
        return basicTiles.random();
    }

    /**
     * Draws the background, traps, ghosts, dolls that unlock things and basic tiles.
     * @param batch
     */
    public void draw(SpriteBatch batch) {

        batch.draw(background, backgroundRectangle.x, backgroundRectangle.y,
                backgroundRectangle.width, backgroundRectangle.height);

        if (!nextTheme.equals("")) {
            batch.draw(nextBackground, nextBackgroundRectangle.x, nextBackgroundRectangle.y,
                    nextBackgroundRectangle.width, nextBackgroundRectangle.height);
        }

        for (int i = 0;i < basicTiles.size; i++) {
            basicTiles.get(i).draw(batch);
        }

        for (int i = 0;i < trapTiles.size; i++) {
            trapTiles.get(i).draw(batch);
        }

        if (ghostList.size > 0) {
            ghostList.get(0).draw(batch);
        }

        if (pickUpDolls.size > 0) {
            pickUpDolls.get(0).draw(batch);
        }
    }

    /**
     * Disposes basic tiles and traps and doll that unlock stuff.
     */
    public void dispose() {
        tileTur.dispose();

        for (int i = 0;i < basicTiles.size;i++) {
            basicTiles.get(i).dispose();
        }

        for (int i = 0;i < trapTiles.size;i++) {
            trapTiles.get(i).dispose();
        }

        if (pickUpDolls.size > 0) {
            pickUpDolls.get(0).dispose();
        }

        disposeThemes();
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
        kitchenTraps = new String[9];
        cellarTraps = new String[9];
        saloonTraps = new String[9];
        kitchenTraps[0] = "pimeys.png";
        kitchenTraps[1] = "piikkiansa.png";
        kitchenTraps[2] = "weight.png";
        kitchenTraps[3] = "vesiSheet5.png";
        kitchenTraps[4] = "karvalakki2.png";
        kitchenTraps[5] = "hautakiviSheet9.png";
        kitchenTraps[6] = "rumpuSheet6.png";
        kitchenTraps[7] = "campfireSheet4.png";
        kitchenTraps[8] = "shroom.png";
        saloonTraps[0] = "pimeys.png";
        saloonTraps[1] = "piikkiansa.png";
        saloonTraps[2] = "weight.png";
        saloonTraps[3] = "vesiSheet5.png";
        saloonTraps[4] = "karvalakki2.png";
        saloonTraps[5] = "hautakiviSheet9.png";
        saloonTraps[6] = "rumpuSheet6.png";
        saloonTraps[7] = "campfireSheet4.png";
        saloonTraps[8] = "shroom.png";
        cellarTraps[0] = "pimeys.png";
        cellarTraps[1] = "piikkiansa.png";
        cellarTraps[2] = "weight.png";
        cellarTraps[3] = "vesiSheet5.png";
        cellarTraps[4] = "karvalakki2.png";
        cellarTraps[5] = "hautakiviSheet9.png";
        cellarTraps[6] = "rumpuSheet6.png";
        cellarTraps[7] = "campfireSheet4.png";
        cellarTraps[8] = "shroom.png";
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

        backgroundRectangle.x -= 0.01f;

        timeSpentInTheme += 1;

        if (!nextTheme.equals("")) {
            nextBackgroundRectangle.x -= 0.01f;
        }

        if (ghostList.size > 0) {
            ghostList.get(0).move();
        }

        if (pickUpDolls.size > 0) {
            pickUpDolls.get(0).move();
            pickUpDolls.get(0).checkIfOverTheMap();
        }

    }

    /**
     * Creates the next tile. if there has been at least 3 basic tiles
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
            if (amountOfTrapsUnlocked < 9) {
                int a = MathUtils.random(1);

                if (a == 0) {
                    choice1 = true;
                    choice2 = false;
                } else if (a == 1) {
                    choice2 = true;
                    choice1 = false;
                }
            } else {
                int a = MathUtils.random(10);

                if (a <= 2) {
                    choice1 = true;
                    choice2 = false;
                } else {
                    choice2 = true;
                    choice1 = false;
                }
            }
        }

        if (choice1) {
            BasicTile basic = new BasicTile(getRandomBasicTile(), x + (latest.getWidth() - latest.getWidth()/30),
                    0, basicTiles);
            basicTiles.add(basic);
            latest = basic;
        } else if (choice2){
            trapFlag = true;
            TrapTile trap = new TrapTile(getRandomTrapTile(), x + (latest.getWidth() - latest.getWidth()/30),
                    0, trapTiles, this, soundManager);
            trapTiles.add(trap);
            latest = trap;
            tilesSinceTrap = 0;
        }

        tilesCreatedInCurrentTheme++;
        if (tilesSinceTrap >= 3) {
            trapFlag = false;
        }
    }

    /**
     * Get basic tile that matches the current theme.
     * @return returns basic tile that fits the current theme
     */
    public String getRandomBasicTile() {
        String themeTile = theme + "tiili.png";
        return themeTile;
    }

    /**
     * Get random trap from all the traps the player has unlocked.
     * @return returns random trap from the current themes traplist
     */
    public String getRandomTrapTile() {
        String[] list = getTraps();
        int a = MathUtils.random(amountOfTrapsUnlocked);
        boolean shroomOnScreen = false;

        if (a == list.length) {
            if (ghostList.size == 0) {
                ghostList.add(new Ghost(ghostList, soundManager));
            }
            a = MathUtils.random(list.length - 1);
        }


        for (int i = 0; i < trapTiles.size;i++) {
            if (trapTiles.get(i).getTrapType().equals("shroom")) {
                shroomOnScreen = true;
            }
        }

        while (a == 8 && shroomOnScreen) {
            a = MathUtils.random(amountOfTrapsUnlocked);
        }

        return list[a];
    }

    /**
     * Get array that holds all the traps.
     * @return
     */
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

    /**
     * Sets amount of tiles created in this current theme.
     * @param amount
     */
    public void setTilesCreatedInCurrentTheme(int amount) {
        tilesCreatedInCurrentTheme = amount;
    }

    /**
     * Sets the current theme.
     * @param name name of the theme.
     */
    public void setTheme(String name) {
        theme = name;
    }

    /**
     * Get the array of dolls that unlock new traps.
     * @return
     */
    public Array<PickUpDoll> getPickUpDolls() {
        return pickUpDolls;
    }

    /**
     * Get array of the ghosts.
     * @return
     */
    public Array<Ghost> getGhostList() {
        return ghostList;
    }


    /**
     *
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

        if (theme.equals("kitchen")) {
            toReturn = kitchenTraps;
        }

        if (theme.equals("cellar")) {
            toReturn = cellarTraps;
        }

        if (theme.equals("saloon")) {
            toReturn = saloonTraps;
        }

        return toReturn;
    }
}
