package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Puoskari on 11.3.2017.
 */

public class GridBall {
    Texture texture;
    float x, y;
    Rectangle rectangle;

    public GridBall() {
        texture = new Texture(Gdx.files.internal("pallo.png"));
        rectangle = new Rectangle(0, 0, texture.getWidth()/25f, texture.getHeight()/25f);
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
        rectangle.setX(x);
        rectangle.setY(y);
    }

    public void drawThis(SpriteBatch batch) {
        rectangle.setCenter(rectangle.getWidth()/2, rectangle.getHeight()/2);
        batch.draw(texture, x, y,
                 rectangle.getWidth(), rectangle.getHeight());

    }


}
