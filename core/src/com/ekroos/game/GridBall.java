package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Puoskari on 11.3.2017.
 */

public class GridBall {
    private Texture texture;
    private Rectangle rectangle;
    boolean isTouched;


    public GridBall() {
        texture = new Texture(Gdx.files.internal("pallo.png"));
        rectangle = new Rectangle(0f, 0f, texture.getWidth()/17f, texture.getHeight()/17f);
        //System.out.println(Float.toString(rectangle.width/));
        isTouched = false;
    }

    public void setLocation(float x, float y) {
        rectangle.x = x;
        rectangle.y = y;
    }

    public void drawThis(SpriteBatch batch) {

        float kaa = rectangle.getWidth()/2;
        batch.draw(texture, rectangle.getX(), rectangle.getY(),
                 rectangle.getWidth(), rectangle.getHeight());

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
