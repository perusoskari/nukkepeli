package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Puoskari on 11.3.2017.
 */

public class GridBall {
    private Texture texture;
    private Rectangle rectangle;
    boolean isTouched;
    boolean touchedTwice;
    int ballNumber;
    float timeAlive;


    public GridBall() {
        texture = new Texture(Gdx.files.internal("palloRed.png"));
        rectangle = new Rectangle(0f, 0f, texture.getWidth()/20f, texture.getHeight()/20f);
        isTouched = false;
        touchedTwice = false;
        ballNumber = 0;
        timeAlive = 0;
    }
    public int getBallNumber() {
        return ballNumber;
    }
    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }
    public void setLocation(float x, float y) {
        rectangle.x = x;
        rectangle.y = y;
    }

    public void drawThis(SpriteBatch batch) {

        batch.draw(texture, rectangle.getX(), rectangle.getY(),
                 rectangle.getWidth(), rectangle.getHeight());

    }
    //Method which sets touchedTwice
    public void setTouchedTwice() {
        touchedTwice = true;
    }
    //This method counts when ball was first touched to give panStop realistic information about the balls
    public void realityCheck() {
        timeAlive += Gdx.graphics.getDeltaTime();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setIsTouched(boolean trueOrFalse) {
        isTouched = trueOrFalse;
    }

    public boolean checkIsTouched() {
        return isTouched;
    }


    public Vector2 getPosition() {
        return new Vector2(rectangle.getX(), rectangle.getY());
    }

    public void dispose() {
        texture.dispose();
    }
}
