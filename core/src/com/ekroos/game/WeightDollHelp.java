package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 3.4.2017.
 */

public class WeightDollHelp {
    private float towardsX;
    private float towardsY;
    private Rectangle rectangle;
    private Texture texture;
    private float moveSpeed;
    private float moveSpeedTowardsTrap;
    private Array<WeightDollHelp> list;
    private SoundManager soundManager;

    /**
     * When "weight" trap pattern is drawn this doll is called to crush the weight.
     * @param x
     * @param y
     * @param list
     * @param towardsX
     * @param towardsY
     * @param soundManager
     */
    public WeightDollHelp(float x, float y, Array<WeightDollHelp> list,
                          float towardsX, float towardsY, SoundManager soundManager) {
        this.soundManager = soundManager;
        texture = new Texture(Gdx.files.internal("dollsAndHelps/puunukke.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/45f, texture.getHeight()/45f);

        this.towardsX = towardsX;
        this.towardsY = towardsY;
        this.list = list;
        Gdx.input.vibrate(150);
    }

    /**
     * Moves the doll.
     */
    public void move() {
        moveSpeedTowardsTrap = Gdx.graphics.getDeltaTime() * 1.2f * 4f;
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        towardsX -= moveSpeed;

            if (rectangle.getX() != towardsX + rectangle.width) {
                rectangle.x = towardsX + rectangle.width;
            }

            if (rectangle.getY() >= towardsY) {
                rectangle.y -= moveSpeedTowardsTrap;
            }
    }

    /**
     * Checks if doll is over the map and disposes it.
     */
    public void checkForDispose() {
        if (rectangle.x + texture.getWidth() < 0) {
            texture.dispose();
            list.removeValue(this, true);
        }
    }

    /**
     *
     * @return  returns the hitbox of the doll.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Draws the doll.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y,
                rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Disposes the texture.
     */
    public void dispose() {
        texture.dispose();
    }
}
