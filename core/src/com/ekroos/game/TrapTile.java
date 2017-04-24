package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 2.3.2017.
 */

public class TrapTile implements AllTiles{
    private Texture texture;
    private Rectangle rectangle;
    private Array<TrapTile> trapTiles;
    private TextureRegion currentFrame;
    private Animator animator;
    private float moveSpeed;
    private boolean safe;
    private String trapType;
    boolean nullified;
    private float stateTime;
    private MapMaker mapMaker;
    private Texture tileTheme;
    private float textureOnlyMove;

    public TrapTile(String textureName, float x, float y, Array<TrapTile> array, MapMaker mapMaker) {
        this.mapMaker = mapMaker;
        stateTime = 0.0f;
        safe = false;
        trapTiles = array;
        texture = new Texture(Gdx.files.internal("traps/" + textureName));
        animator = new Animator();
        rectangle = new Rectangle(x, y, texture.getWidth()/60f, texture.getHeight()/60f);

        if (textureName.equals("vesiSheet5.png")) {
            animator.createAnimation("traps/" + textureName, stateTime, 5, 1, 1 / 20f);
            currentFrame = new TextureRegion(animator.getCurrentFrame(stateTime));
            rectangle.set(x, y, currentFrame.getRegionWidth()/60f, currentFrame.getRegionHeight()/60f);

        }

        if (textureName.equals("hautakiviSheet9.png")) {
            animator.createAnimation("traps/" + textureName, stateTime, 9, 1, 1 / 15f);
            currentFrame = new TextureRegion(animator.getCurrentFrame(stateTime));
            rectangle.set(x, y, currentFrame.getRegionWidth()/60f, currentFrame.getRegionHeight()/60f);

        }

        if (textureName.equals("rumpuSheet6.png")) {
            animator.createAnimation("traps/" + textureName, stateTime, 6, 1, 1 / 8f);
            currentFrame = new TextureRegion(animator.getCurrentFrame(stateTime));
            rectangle.set(x, y, currentFrame.getRegionWidth()/60f, currentFrame.getRegionHeight()/60f);

        }

        tileTheme = new Texture(Gdx.files.internal("tiles/" + mapMaker.getRandomBasicTile()));
        rectangle.setX(x);
        rectangle.setY(y);
        nullified = false;

        addType(textureName);

        if (trapType.equals("weight")) {
            rectangle.setHeight(texture.getHeight()/132.25f);
        }

        textureOnlyMove = tileTheme.getHeight()/60f;
    }

    public void addType(String trapName) {
        if (trapName.equals("pimeys.png")) {
            trapType = "box";
        }

        if (trapName.equals("piikkiansa.png")) {
            trapType = "spike";
        }

        if (trapName.equals("weight.png")) {
            trapType = "weight";
        }

        if (trapName.equals("vesiSheet5.png")) {
            trapType = "water";
        }

        if (trapName.equals("karvalakki2.png")) {
            trapType = "soviet";
        }

        if (trapName.equals("hautakiviSheet9.png")) {
            trapType = "zombie";
        }

        if (trapName.equals("rumpuSheet6.png")) {
            trapType = "drum";
        }
    }

    public void move() {
        moveSpeed = Gdx.graphics.getDeltaTime() * 1.2f;


        if (trapType.equals("water") || trapType.equals("drum")) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = animator.getCurrentFrame(stateTime);
        }

        if (trapType.equals("zombie")) {
            if (nullified) {
                stateTime += Gdx.graphics.getDeltaTime();
                if (!animator.animation.isAnimationFinished(stateTime))
                    currentFrame = animator.getCurrentFrame(stateTime);
            }
        }

        float x = rectangle.getX() - moveSpeed;
        rectangle.setX(x);

        if (trapType.equals("soviet") && nullified) {
            textureOnlyMove += moveSpeed * 2.5f;
        }

        if (trapType.equals("drum") && !nullified) {
            textureOnlyMove = rectangle.getX();
        } else if (trapType.equals("drum") && nullified) {
            textureOnlyMove += moveSpeed * 3f;
        }
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

        if (!trapType.equals("weight") && (!trapType.equals("water")) && !trapType.equals("soviet") &&
                !trapType.equals("zombie") && !trapType.equals("drum")) {
            batch.draw(texture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                    rectangle.getHeight());
        } else if (trapType.equals("weight")){
            batch.draw(texture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                    texture.getHeight()/60f);
        } else if (trapType.equals("water") || trapType.equals("zombie")) {
            batch.draw(currentFrame, rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                    rectangle.getHeight());
        } else if (trapType.equals("soviet")) {
            batch.draw(tileTheme, rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                    tileTheme.getHeight() / 60f);
            if (!nullified) {
                batch.draw(texture, rectangle.getX(), tileTheme.getHeight() / 60f, rectangle.getWidth(),
                        rectangle.getHeight());
            } else if (nullified) {
                batch.draw(texture, rectangle.getX(), textureOnlyMove, rectangle.getWidth(),
                        rectangle.getHeight());
            }
        } else if (trapType.equals("drum")) {
            batch.draw(tileTheme, rectangle.x, rectangle.y,
                    tileTheme.getWidth()/60f,tileTheme.getHeight()/60f);
            if (!nullified) {
                batch.draw(currentFrame, rectangle.getX(), tileTheme.getHeight()/60f, rectangle.width,
                        rectangle.height);
            } else if (nullified) {
                batch.draw(currentFrame, textureOnlyMove, tileTheme.getHeight()/60f + 0.1f,
                        rectangle.width, rectangle.height);
            }
        }

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
        tileTheme.dispose();
    }

    public void nullify() {
        if (trapType.equals("weight")) {
            texture.dispose();
            texture = new Texture(Gdx.files.internal("traps/weight2.png"));
            nullified = true;
        } else if (trapType.equals("soviet")) {
            nullified = true;
        } else if (trapType.equals("zombie") || trapType.equals("drum")) {
            nullified = true;
        }

    }

    public boolean isNullified() {
        return nullified;
    }
}

