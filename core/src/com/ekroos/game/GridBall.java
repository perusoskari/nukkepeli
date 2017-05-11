package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Puoskari on 11.3.2017.
 *
 */

public class GridBall {
    private Texture texture;
    private Texture texture2;
    private Rectangle rectangle;
    boolean isTouched;
    boolean touchedTwice;
    int ballNumber;
    float timeAlive;

    /**
     * One of the 9 balls of the touch grid where the player draws patterns.
     */
    public GridBall() {
        texture = new Texture(Gdx.files.internal("Piirtopallo1.png"));
        texture2 = new Texture(Gdx.files.internal("Piirtopallo2.png"));
        rectangle = new Rectangle(0f, 0f, texture.getWidth()/75f, texture.getHeight()/75f);
        isTouched = false;
        touchedTwice = false;
        ballNumber = 0;
        timeAlive = 0;
    }

    /**
     * Get the what number the ball is.
     * @return
     */
    public int getBallNumber() {
        return ballNumber;
    }

    /**
     * Set whats the number of the ball.
     * @param ballNumber
     */
    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }

    /**
     * Set the coordinates of the ball
     * @param x
     * @param y
     */
    public void setLocation(float x, float y) {
        rectangle.setCenter(x, y);
    }

    /**
     * Draws the ball. If doll has been touched draws a bit different texture.
     * @param batch
     */
    public void drawThis(SpriteBatch batch) {

        if (!isTouched) {
            batch.draw(texture, rectangle.getX(),
                    rectangle.getY(),
                    rectangle.getWidth(), rectangle.getHeight());
        } else {
            batch.draw(texture2, rectangle.getX(),
                    rectangle.getY(),
                    rectangle.getWidth(), rectangle.getHeight());
        }

    }

    /**
     * Set touchedTwice to true.
     */
    public void setTouchedTwice() {
        touchedTwice = true;
    }

    /**
     * This method counts when ball was first touched to give panStop realistic
     * information about the balls
     */
    public void realityCheck() {
        timeAlive += Gdx.graphics.getDeltaTime();
    }

    /**
     * Get the hitbox of the ball.
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Sets isTouched true if ball has been touched once.
     * @param trueOrFalse
     */
    public void setIsTouched(boolean trueOrFalse) {
        isTouched = trueOrFalse;
    }

    /**
     * Get true or false according to isTouched.
     * @return
     */
    public boolean checkIsTouched() {
        return isTouched;
    }

    /**
     * Get the coordinates of the ball.
     * @return  vector that holds the coordinates.
     */
    public Vector2 getPosition() {
        return new Vector2(rectangle.getX(), rectangle.getY());
    }

    /**
     * Disposes textures.
     */
    public void dispose() {
        texture.dispose();
        texture2.dispose();
    }
}
