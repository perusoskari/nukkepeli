package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 22.4.2017.
 */

public class SovietDollHelp {
    private float towardsX;
    private float towardsY;
    private Rectangle rectangle;
    private Texture texture;
    private float moveSpeed;
    private float moveSpeedTowardsTrap;
    private SoundManager soundManager;
    private Array<SovietDollHelp> list;

    /**
     * When fur cap trap pattern is drawn this doll is called.
     * @param x
     * @param y
     * @param list
     * @param towardsX
     * @param towardsY
     * @param soundManager
     */
    public SovietDollHelp(float x, float y, Array<SovietDollHelp> list,
                          float towardsX, float towardsY, SoundManager soundManager) {
        this.list = list;
        this.soundManager = soundManager;
        texture = new Texture(Gdx.files.internal("dollsAndHelps/Kekkonen1.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
        this.towardsX = towardsX;
        this.towardsY = towardsY;
        Gdx.input.vibrate(150);
        soundManager.playSound("sovietWhistle", 0.15f);
    }

    /**
     * Moves the doll.
     */
    public void move() {
        moveSpeedTowardsTrap = Gdx.graphics.getDeltaTime() * 1.2f * 2.5f;
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        towardsX -= moveSpeed;
        rectangle.x = towardsX + (rectangle.width/2);
        rectangle.y += moveSpeedTowardsTrap;
    }

    /**
     * Checks if doll is off the map and disposes it if it's.
     */
    public void checkForDispose() {
        if (rectangle.x + texture.getWidth() < 0) {
            texture.dispose();
            list.removeValue(this, true);
        }
    }

    /**
     * Get the hitbox of this doll.
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Draws the doll.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x - (rectangle.width/2), rectangle.y,
                rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Disposes the texture.
     */
    public void dispose() {
        texture.dispose();
    }
}
