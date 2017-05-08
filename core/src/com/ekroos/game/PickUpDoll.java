package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Puoskari on 19.4.2017.
 */

public class PickUpDoll {
    Texture texture;
    Rectangle rectangle;
    float moveSpeed;
    String type;
    Array<PickUpDoll> list;

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
            texture = new Texture(Gdx.files.internal("dollsAndHelps/doll.png"));
        } else {
            texture = new Texture(Gdx.files.internal("dollsAndHelps/doll.png"));
        }
        rectangle = new Rectangle(x, y, texture.getWidth()/75f, texture.getHeight()/75f);
        this.list = list;
        list.add(this);
    }

    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;
        rectangle.x -= moveSpeed;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void dispose() {
        texture.dispose();
        list.removeValue(this, true);
    }

    public String getType() {
        return type;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void checkIfOverTheMap() {
        if (rectangle.x + rectangle.width < -0.5f) {
            dispose();
        }
    }
}
