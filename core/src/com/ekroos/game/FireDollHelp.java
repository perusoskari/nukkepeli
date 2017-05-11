
package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 3.4.2017.
 */

public class FireDollHelp {
    private float towardsX;
    private float towardsY;
    private Rectangle rectangle;
    private Texture texture;
    private float moveSpeed;
    private float moveSpeedTowardsTrap;
    private Array<FireDollHelp> list;

    /**
     * Doll that is called when pattern bonfire pattern is drawn.
     * @param x
     * @param y
     * @param list array of all the dolls that are used to get over bonfire traps.
     * @param towardsX
     * @param towardsY
     */
    public FireDollHelp(float x, float y, Array<FireDollHelp> list,
                          float towardsX, float towardsY) {
        texture = new Texture(Gdx.files.internal("dollsAndHelps/Pannumyssy1.png"));
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

        if (rectangle.getX() != towardsX) {
            rectangle.x = towardsX;
        }

        if (rectangle.getY() >= towardsY) {
            rectangle.y -= moveSpeedTowardsTrap;
        }
    }

    /**
     * Checks if the doll is over the map and disposes it if it is.
     */
    public void checkForDispose() {
        if (rectangle.x + texture.getWidth() < 0) {
            texture.dispose();
            list.removeValue(this, true);
        }
    }

    /**
     * Get the hitbox of the doll.
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
