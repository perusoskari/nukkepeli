package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Puoskari on 28.4.2017.
 * Info pop up that tell player about the newly unlocked doll and the trap it answers to.
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

    /**
     * Pops up when new doll is unlocked and tells what it is used to.
     * @param type  the type of the doll that is unlocked.
     * @param c
     * @param gameScreen
     */
    public DollIngameInfo(String type, OrthographicCamera c, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        bundlenator = gameScreen.getBundlenator();
        uiBatch = gameScreen.getUIBatch();
        font = bundlenator.getDescriptionFont();
        text = new GlyphLayout(font, "");
        textCamera = gameScreen.getUICam();
        camera = c;
        gameScreen.setPause(true);
        background = new Texture(Gdx.files.internal("buttonsAndMenu/SmokeTausta2.png"));

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

    /**
     * Disposes the textures
     */
    public void dispose() {
        trap.dispose();
        trapDrawn.dispose();
    }

    /**
     * Check if one taps the screen and closes this pop up if one does.
     */
    public void checkForTap() {
            if (Gdx.input.justTouched()) {
                gameScreen.setInfoExists(false);
                gameScreen.setPause(false);
                dispose();
            }
    }

    /**
     * Draws the picture of the trap that the newest doll is used to,
     * also draws what pattern one is supposed to draw to use the new doll.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(background, 1.5f, 0.5f, 8f, 4f);

        batch.draw(trap, 2f, 3f, 3f, 1.5f);
        batch.draw(trapDrawn, 5f, 2.8f, 3f, 1.5f);
    }

    /**
     * Draws the description of the doll.
     */
    public void drawInfo() {
        uiBatch.setProjectionMatrix(textCamera.combined);
        font.draw(uiBatch, text, 230, 285);
    }
}
