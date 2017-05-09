package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Puoskari on 28.4.2017.
 */

public class DollIngameInfo {
    private Texture trap;
    private Texture trapDrawn;
    private Texture background;
    private OrthographicCamera camera;
    private OrthographicCamera textCamera;
    private GameScreen gameScreen;
    private Bundlenator bundlenator;
    private GlyphLayout text;
    private BitmapFont font;
    private SpriteBatch uiBatch;

    public DollIngameInfo(String type, OrthographicCamera c, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        bundlenator = gameScreen.getBundlenator();
        uiBatch = gameScreen.getUIBatch();
        font = bundlenator.getDescriptionFont();
        text = new GlyphLayout(font, "");
        textCamera = gameScreen.getUICam();
        camera = c;
        gameScreen.setPause(true);
        background = new Texture(Gdx.files.internal("buttonsAndMenu/helpScreenArt.png"));

        System.out.println(type);
        if (type.equals("water")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/waterTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/waterTrapDrawn.png"));
            text.setText(font, bundlenator.getLocal("waterDesc"));
        }

        if (type.equals("drum")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/drumTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/drumTrapDrawn.png"));
            text.setText(font, bundlenator.getLocal("drumDesc"));
        }

        if (type.equals("ghost")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/spookTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/spookTrapDrawn.png"));
            text.setText(font, bundlenator.getLocal("spookDesc"));
        }

        if (type.equals("soviet")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/hatTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/hatTrapDrawn.png"));
            text.setText(font, bundlenator.getLocal("hatDesc"));
        }

        if (type.equals("zombie")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/zombieTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/zombieTrapDrawn.png"));
            text.setText(font, bundlenator.getLocal("zombieDesc"));
        }

        if (type.equals("fire")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/fireTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/fireTrapDrawn.png"));
            text.setText(font, bundlenator.getLocal("fireDesc"));
        }

        if (type.equals("shroom")) {
            trap = new Texture(Gdx.files.internal("helpScreenStuff/shroomTrap.png"));
            trapDrawn = new Texture(Gdx.files.internal("helpScreenStuff/shroomTrapDrawn.png"));
            text.setText(font, bundlenator.getLocal("shroomDesc"));
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
        batch.draw(background, 1.5f, 0.5f, 8f, 4f);

        batch.draw(trap, 2f, 3f, 3f, 1.5f);
        batch.draw(trapDrawn, 5f, 3f, 3f, 1.5f);
    }

    public void drawInfo() {
        uiBatch.setProjectionMatrix(textCamera.combined);
        font.draw(uiBatch, text, 230, 2850);
    }
}
