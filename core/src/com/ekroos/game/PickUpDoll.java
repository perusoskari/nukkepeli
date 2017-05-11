package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Puoskari on 19.4.2017.
 * Doll that when Ekroos collides with new trap and answer to that trap is unlocked.
 */
public class PickUpDoll {
    Texture texture;
    Rectangle rectangle;
    float moveSpeed;
    String type;
    Array<PickUpDoll> list;

    /**
     * Doll that unlocks new traps and makes game harder that way.
     * @param x
     * @param y
     * @param type  type of trap that's going to be unlocked.
     * @param list  array that holds this object.
     */
    public PickUpDoll(float x, float y, String type, Array<PickUpDoll> list) {
        this.type = type;
        if (this.type.equals("water")) {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/Charlie1.png"));
        } else if (this.type.equals("soviet")) {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/Kekkonen1.png"));
        } else if (this.type.equals("zombie")) {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/Rasynukke1.png"));
        } else if (this.type.equals("drum")) {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/Aslak1.png"));
        } else if (this.type.equals("fire")) {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/Pannumyssy1.png"));
        } else if (this.type.equals("shroom")) {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/Puutaikanukke1.png"));
        } else if (this.type.equals("ghost")) {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/Auringonjumalanukke1.png"));
        } else {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/doll.png"));
        }
        rectangle = new Rectangle(x, y, texture.getWidth()/75f, texture.getHeight()/75f);
        this.list = list;
        list.add(this);
    }

    /**
     * Moves the doll.
     */
    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        rectangle.x -= moveSpeed;
    }

    /**
     * Draws the trap.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    /**
     * Disposes the texture and removes this object from the list.
     */
    public void dispose() {
        texture.dispose();
        list.removeValue(this, true);
    }

    /**
     * Get the type of the trap that is going to be unlocked.
     * @return  returms the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Get the hitbox of this doll.
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Check if this doll is over the map and removes it if that happens.
     */
    public void checkIfOverTheMap() {
        if (rectangle.x + rectangle.width < -0.5f) {
            dispose();
        }
    }
}
