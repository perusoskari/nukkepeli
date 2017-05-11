package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 20.3.2017.
 * Plank that appears when box pattern is drawn.
 */

public class BoxDollHelp {
    private Texture texture;
    private Rectangle rectangle;
    private float moveSpeedTowardsTrap;
    private float moveSpeed;
    private boolean lock;
    private Array<BoxDollHelp> list;
    private float towardsX;
    private float towardsY;
    private SoundManager soundManager;

    /**
     * When box shape is drawn this doll is called.
     */
    public BoxDollHelp(float x, float y, Array<BoxDollHelp> list, float towardsX, float towardsY,
                       SoundManager soundManager) {
        this.towardsX = towardsX;
        this.towardsY = towardsY;

        this.soundManager = soundManager;
        Gdx.input.vibrate(150);
        texture = new Texture(Gdx.files.internal("dollsAndHelps/plank.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/59f, texture.getHeight()/60f);
        lock = false;
        this.list = list;

    }

    /**
     * Checks if this object is over the map and disposes is if it does.
     */
    public void checkForDispose() {
        if (rectangle.x + texture.getWidth() < 0) {
            texture.dispose();
            list.removeValue(this, true);
        }
    }

    /**
     * Get if this object is "locked".
     * @return returns true if the plank is at its destination.
     */
    public boolean isLock() {
        return lock;
    }

    /**
     * Get the hitbox of the plank.
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Moves the object. If it is on top of the wanted trap "lock" turns to true.
     */
    public void move() {
        moveSpeed =Gdx.graphics.getDeltaTime() * 1.2f;
        moveSpeedTowardsTrap = moveSpeed * 2;
        towardsX -= moveSpeed;

        if (!lock) {
            if (rectangle.getX() < towardsX) {
                rectangle.x += moveSpeedTowardsTrap;
            }

            if (rectangle.getX() > towardsX) {
                rectangle.x -= moveSpeedTowardsTrap;
            }

            if (rectangle.getY() >= towardsY + rectangle.getHeight()) {
                rectangle.y -= moveSpeedTowardsTrap;
            }
        } else {
            rectangle.x -= moveSpeed;
        }


        if (rectangle.y <= towardsY + rectangle.getHeight() &&
                rectangle.x >= towardsX - moveSpeed &&
                rectangle.x <= towardsX + moveSpeed) {
            if (!lock) {
                soundManager.playSound("boxLockSound", 0.3f);
            }
            lock = true;
        }
    }

    /**
     * Draws the plank.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        if (!lock) {
            batch.draw(texture, rectangle.x, rectangle.y - rectangle.getHeight(),
                    rectangle.getWidth(), rectangle.getHeight());
        } else {
            batch.draw(texture, rectangle.x, rectangle.y - (rectangle.height * 2),
                    rectangle.width, rectangle.height);
        }
    }

    /**
     * Disposes the texture.
     */
    public void dispose() {
        texture.dispose();
    }
}
