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
    private Texture texture;
    private Rectangle rectangle;
    private Array<TrapTile> trapTiles;
    private boolean safe;
    private String trapType;

    public TrapTile(String textureName, float x, float y, Array<TrapTile> array) {
        safe = false;
        trapType = "1";
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

    public String getTrapType() {
        return trapType;
    }

    public void setSafe() {
        safe = true;
    }
    public boolean getIfTileIsSafe() {
        return safe;
    }

    public void dispose() {
        texture.dispose();
    }
}

