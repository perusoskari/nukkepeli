package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by tunce on 4.4.2017.
 */

public class SpikeDollHelp {
    private Texture texture;
    private Rectangle rectangle;
    private float moveSpeedTowardsTrap;
    private float moveSpeed;
    private boolean lock;
    private Array<SpikeDollHelp> list;
    private float towardsX;
    private float towardsY;
    private SoundManager soundManager;

    /**
     * When box shape is drawn this doll is called
     */

    public SpikeDollHelp(float x, float y, Array<SpikeDollHelp> list, float towardsX, float towardsY,
                         SoundManager soundManager) {
        this.soundManager = soundManager;
        this.towardsX = towardsX;
        this.towardsY = towardsY;
        Gdx.input.vibrate(150);

        texture = new Texture(Gdx.files.internal("dollsAndHelps/pilvi.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/59f, texture.getHeight()/60f);
        lock = false;
        this.list = list;
    }

    public void checkForDispose() {
        if (rectangle.x + texture.getWidth() < 0) {
            texture.dispose();
            list.removeValue(this, true);
        }
    }

    /**
     *
     * @return returns true if the plank is at its destination
     */
    public boolean isLock() {
        return lock;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }


    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        moveSpeedTowardsTrap = moveSpeed * 2f;
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
                soundManager.playSound("spikeNullified", 0.2f, 1.5f, false);
            }
            lock = true;
        }
    }

    public void draw(SpriteBatch batch) {
        if (!lock) {
            batch.draw(texture, rectangle.x, rectangle.y - rectangle.getHeight(),
                    rectangle.getWidth(), rectangle.getHeight());
        } else {
            batch.draw(texture, rectangle.x, rectangle.y - (rectangle.height * 2),
                    rectangle.width, rectangle.height);
        }
    }

    public void dispose() {
        texture.dispose();
    }

}
