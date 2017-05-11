package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 1.3.2017.
 * The basic part of the map.
 */

public class BasicTile implements AllTiles{
    private Texture texture;
    private Rectangle rectangle;
    private Array<BasicTile> basicTiles;
    private float moveSpeed;

    /**
     * The basic tile of the map.
     * @param textureName name of the texture.
     * @param x
     * @param y
     * @param array of all the basic tiles.
     */
    public BasicTile(String textureName, float x, float y, Array<BasicTile> array) {
        basicTiles = array;
        texture = new Texture(Gdx.files.internal("tiles/" + textureName));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
        rectangle.setX(x);
        rectangle.setY(y);
    }

    /**
     * Moves the basic tile.
     */
    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        float x = rectangle.getX() - moveSpeed;
        rectangle.setX(x);
        overMap();
    }

    /**
     * Get width of the basic tile.
     * @return  the width.
     */
    public float getWidth() {
        return rectangle.getWidth();
    }

    /**
     * Get height of the basic tile.
     * @return  the height.
     */
    public float getHeight() { return rectangle.getHeight(); }

    /**
     * Get the x coordinate of the basic tile.
     * @return  the x.
     */
    public float get_x() {
        return rectangle.getX();
    }

    /**
     * Get the hitbox of the basic tile.
     * @return  the hitbox.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * removes the tile if its not visible on the map anymore
     */
    public void overMap() {
        if (rectangle.getX() < 0 - rectangle.getWidth()) {
            basicTiles.removeValue(this, true);
            dispose();
        }
    }

    /**
     * Draws the basic tile.
     * @param batch used to draw the tile.
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                rectangle.getHeight());
    }

    /**
     * Disposes the texture of the basic tile.
     */
    public void dispose() {
        texture.dispose();
    }
}
