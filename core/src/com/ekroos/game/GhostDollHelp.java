package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 13.4.2017.
 */

public class GhostDollHelp {
    private Texture texture;
    private Rectangle rectangle;
    private Array<GhostDollHelp> list;
    private float timeAlive;
    private SoundManager soundManager;

    public GhostDollHelp(Array<GhostDollHelp> list, float x, float y, SoundManager soundManager) {
        texture = new Texture(Gdx.files.internal("dollsAndHelps/kilpi.png"));
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);
        this.list = list;
        this.soundManager = soundManager;
        timeAlive = 0.0f;
        Gdx.input.vibrate(150);
        soundManager.playSound("shield", 0.3f);
    }

    public void move() {
        timeAlive += 1;

        if (timeAlive > 300) {
            dispose();
        }
    }

    public void dispose() {
        texture.dispose();
        list.removeValue(this, true);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width,rectangle.height);
    }
}
