package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 13.4.2017.
 */

public class GhostDollHelp {
    private Texture texture;
    private Rectangle rectangle;
    private Array<GhostDollHelp> list;
    private float timeAlive;
    private SoundManager soundManager;

    /**
     * Doll that shields Ekroos so she doesn't die if she collides with a ghost.
     * @param list arrays of all this type of dolls.
     * @param x
     * @param y
     * @param soundManager
     */
    public GhostDollHelp(Array<GhostDollHelp> list, float x, float y, SoundManager soundManager) {
        texture = new Texture(Gdx.files.internal("dollsAndHelps/kilpi2.png"));
        rectangle = new Rectangle(x - 0.33f, y - 0.2f, texture.getWidth()/65f, texture.getHeight()/45f);
        this.list = list;
        this.soundManager = soundManager;
        timeAlive = 0.0f;
        Gdx.input.vibrate(150);
        soundManager.playSound("shield", 0.3f);
    }

    /**
     * Checks if shield has been up for given time and disposes it if it has.
     */
    public void move() {
        timeAlive += 1;

        if (timeAlive > 300) {
            dispose();
        }
    }

    /**
     * Disposes the shield.
     */
    public void dispose() {
        texture.dispose();
        list.removeValue(this, true);
    }

    /**
     * Draws the shield.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width,rectangle.height);
    }
}
