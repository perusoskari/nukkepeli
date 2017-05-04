package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Puoskari on 28.4.2017.
 */

public class DollIngameInfo {
    private Texture trap;
    private Texture trapDrawn;
    private Texture background;
    private OrthographicCamera camera;
    private GameScreen gameScreen;

    public DollIngameInfo(String type, OrthographicCamera c, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        camera = c;
        gameScreen.setPause(true);
        background = new Texture(Gdx.files.internal("buttonsAndMenu/helpScreenArt.png"));

        System.out.println(type);
        if (type.equals("water")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/waterTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/waterTrapDrawn.png"));
        }

        if (type.equals("drum")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/drumTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/drumTrapDrawn.png"));
        }

        if (type.equals("ghost")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/spookTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/spookTrapDrawn.png"));
        }

        if (type.equals("soviet")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/hatTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/hatTrapDrawn.png"));
        }

        if (type.equals("zombie")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/zombieTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/zombieTrapDrawn.png"));
        }

        if (type.equals("fire")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/zombieTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/zombieTrapDrawn.png"));
        }

        if (type.equals("shroom")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/zombieTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/zombieTrapDrawn.png"));
        }


    }

    public void dispose() {
        trap.dispose();
        trapDrawn.dispose();
    }

    public void checkForTap() {
            if (Gdx.input.justTouched()) {
                gameScreen.setInfoExists(false);
                gameScreen.setPause(false);
                dispose();
            }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(background, 2f, 0.5f, 6f, 4f);

        batch.draw(trap, 2f, 3f, 3f, 1.5f);
        batch.draw(trapDrawn, 5f, 3f, 3f, 1.5f);
    }
}
