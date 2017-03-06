package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 2.3.2017.
 */

public class TrapTile implements AllTiles{
    Texture texture;
    Rectangle rectangle;
    Array<TrapTile> trapTiles;

    public TrapTile(String textureName, float x, float y, Array<TrapTile> array) {
        trapTiles = array;
        texture = new Texture(Gdx.files.internal(textureName));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
        rectangle.setX(x);
        rectangle.setY(y);
    }

    public void move() {
        float x = rectangle.getX() - 0.02f;
        rectangle.setX(x);
        overMap();
    }

    public float getWidth() {
        return rectangle.getWidth();
    }

    public float get_x() {
        return rectangle.getX();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * removes the tile if its not visible on the map anymore
     */
    public void overMap() {
        if (rectangle.getX() < 0 - rectangle.getWidth()) {
            trapTiles.removeValue(this, true);
            dispose();
        }
    }


    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                rectangle.getHeight());

    }

    public void dispose() {
        texture.dispose();
    }
}

