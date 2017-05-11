package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 23.4.2017.
 *
 */

public class DrumDollHelp {
    private Rectangle rectangle;
    private Texture texture;
    private float moveSpeed;
    private Array<DrumDollHelp> list;
    private boolean hasNullified;

    /**
     * When drum pattern is drawn this doll is called.
     * @param x
     * @param y
     * @param list  of all the dolls that are used to get over drum traps.
     * @param soundManager
     */
    public DrumDollHelp(float x, float y, Array<DrumDollHelp> list, SoundManager soundManager) {
        this.list = list;
        texture = new Texture(Gdx.files.internal("dollsAndHelps/Aslak1.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/120f, texture.getHeight()/120f);
        hasNullified = false;
        soundManager.playSound("drumSound", 0.3f);
        Gdx.input.vibrate(150);
    }

    /**
     * Get if the doll has nullified one trap.
     * @return
     */
    public Boolean hasNullified() {
        return  hasNullified;
    }

    /**
     * Moves the doll.
     */
    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f * 3f;
        rectangle.x += moveSpeed;

        if (rectangle.x > 10f) {
            dispose();
            list.removeValue(this, true);
        }
    }

    /**
     * Disposes the doll
     */
    public void dispose() {
        texture.dispose();
        list.removeValue(this, true);
    }

    /**
     * Draws the doll.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    /**
     * Sets nullified to true.
     */
    public void setNullified() {
        hasNullified = true;
    }

    /**
     * Get the hitbox of the doll.
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }
}

