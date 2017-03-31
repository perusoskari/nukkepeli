package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 20.3.2017.
 */

public class BoxDollHelp {
    private Texture texture;
    private Rectangle rectangle;
    private float moveSpeed;
    private boolean lock;
    private Array<BoxDollHelp> list;
    private float towardsX;
    private float towardsY;

    /**
     * When box shape is drawn this doll is called
     */

    public BoxDollHelp(float x, float y, Array<BoxDollHelp> list, float towardsX, float towardsY) {
        this.towardsX = towardsX;
        this.towardsY = towardsY;
        moveSpeed = 0.04f;
        texture = new Texture(Gdx.files.internal("plank.png"));
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
        towardsX -= 0.02f;

        if (!lock) {
            if (rectangle.getX() < towardsX) {
                rectangle.x += moveSpeed;
            }

            if (rectangle.getX() > towardsX) {
                rectangle.x -= moveSpeed;
            }

            if (rectangle.getY() >= towardsY + rectangle.getHeight()) {
                rectangle.y -= moveSpeed;
            }
        } else {
            rectangle.x -= moveSpeed/2;
        }


        if (rectangle.y <= towardsY + rectangle.getHeight() &&
                rectangle.x >= towardsX - 0.02f &&
                rectangle.x <= towardsX + 0.02f) {
            lock = true;
        }


    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y - rectangle.getHeight(),
                rectangle.getWidth(), rectangle.getHeight());
    }

}
